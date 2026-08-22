package com.world.cloudxsolution.xbox;

import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * ARCHITECTURE
 * ============
 * Android has no equivalent of navigator.getGamepads() (JS) -- there is no OS call that hands you
 * "current joystick state" on demand. The ONLY way state reaches user-space is through the
 * dispatch pipeline (onKeyDown/onKeyUp/onGenericMotion), which runs on the UI thread. That means
 * the UI thread is a fixed cost we cannot remove -- but everything AFTER capturing the value
 * (allocation, packet framing, socket write) is discretionary, and that is what was causing
 * jank/latency by running synchronously inline in the old design.
 *
 * This class splits the pipeline into two halves, mirroring the JS reference's
 * pollGamepads()/intervalWorker interval loop:
 *
 *   CAPTURE (UI thread, on every dispatch callback)
 *     - onKeyDown/onKeyUp/onGenericMotion do the *minimum* possible work:
 *       update the shared transitionFrame, then either
 *         (a) publish an immutable snapshot for continuous axis state (AtomicReference swap), or
 *         (b) enqueue an immutable snapshot for discrete button transitions (lock-free queue)
 *       Neither path allocates a network packet, touches a socket, or blocks on a lock across I/O.
 *
 *   SEND (dedicated background thread, fixed-interval loop -- the Java analogue of the JS
 *         pollGamepads()/intervalWorker.scheduleTimer loop)
 *     - drains the transition queue every tick (never dropped -- button edges must all arrive)
 *     - sends the latest continuous-state snapshot every tick (only the newest value matters,
 *       exactly like a poll-based read of "current" stick position)
 *     - owns all allocation / packet building / socket writes, so a slow network call never
 *       blocks the next input event from being captured
 *
 * Neither side blocks the other: AtomicReference.getAndSet / ConcurrentLinkedQueue.offer()/poll()
 * are lock-free (single Java monitor use is limited to the tiny in-place mutation of
 * transitionFrame itself, never held across I/O).
 */
public final class AndroidGamepadListener {

    private static final String TAG = "GamepadListener";

    private static final int TYPE_MOTION = 0;
    private static final int TYPE_KEYDOWN = 1;
    private static final int TYPE_KEYUP = 2;

    // How often the sender thread ships the latest continuous state (axes/triggers), regardless
    // of dispatch rate. Tune to match your server tick rate / network budget. Button transitions
    // are NOT rate-limited by this -- they drain every tick, as fast as the loop runs.
    private static final long SEND_INTERVAL_MS = 8; // ~120Hz cap on continuous-state sends

    // --- Capture-side state (mutated only while holding stateLock, only briefly, never across I/O) ---
    private final Object stateLock = new Object();
    private final GamepadTransitionFrame transitionFrame;

    // Reused scratch buffer to avoid per-event allocation on the input thread (called at 60-120Hz+).
    private final float[] stickScratch = new float[2];
    // Raw (pre-deadzone/curve/sensitivity) stick values, for UI (e.g. stick-test visualizer) only.
    private float rawLeftX = 0f, rawLeftY = 0f, rawRightX = 0f, rawRightY = 0f;
    private volatile InputDevice lastDevice;
    private Runnable onMenuTrigger;

    private float stickDeadzone = 0.12f;
    private float cameraSensitivity = 1.5f;
    private volatile float rightStickCurveExponent = 1.0f;

    private volatile boolean testModeActive = false;

    // --- Handoff between capture (UI thread) and sender (background thread) ---
    // Continuous state: only the newest value is ever useful, so a single swap slot is enough --
    // no queue, no backlog, nothing to coalesce. This is the direct analogue of the JS loop calling
    // navigator.getGamepads() and reading "the state as of right now."
    private final AtomicReference<GamepadPendingState> latestContinuousState = new AtomicReference<>();
    // Discrete transitions: every edge must be delivered, so these ARE queued. Lock-free,
    // single-producer (UI thread) / single-consumer (sender thread), non-blocking offer()/poll().
    private final ConcurrentLinkedQueue<GamepadPendingState> transitionQueue = new ConcurrentLinkedQueue<>();

    private final AtomicLong unreliableToken = new AtomicLong(0);
    private long tickCounter = 0;

    private final Consumer<ByteBuffer> unreliableSendRaw;

    // --- Sender thread lifecycle ---
    private Thread senderThread;
    private final AtomicBoolean senderRunning = new AtomicBoolean(false);

    // --- High-performance mapping (prepared once per device) ---
    private boolean isPrepared = false;
    private String preparedDeviceDescriptor = null;
    private final int[] keyCodeMap = new int[KeyEvent.getMaxKeyCode() + 1];
    private int axisRSX, axisRSY, axisLT, axisRT;
    private boolean hasHatAxes = false;
    private boolean leftTriggerIsAnalog = true;
    private boolean rightTriggerIsAnalog = true;

    private boolean prevHatUp, prevHatDown, prevHatLeft, prevHatRight;
    private final boolean[] keyDown = new boolean[KeyEvent.getMaxKeyCode() + 1];

    public AndroidGamepadListener(int gamepadIndex, float sensitivity, float deadzone,
                                  Consumer<ByteBuffer> unreliableSendRaw) {
        setCameraSensitivity(sensitivity);
        setStickDeadzone(deadzone);
        this.transitionFrame = new GamepadTransitionFrame(gamepadIndex);
        this.unreliableSendRaw = unreliableSendRaw;
        Arrays.fill(keyCodeMap, -1);
        startSenderThread();
    }

    // ==================================================================================
    // SENDER THREAD -- the analogue of the JS pollGamepads()/intervalWorker interval loop
    // ==================================================================================

    private void startSenderThread() {
        if (!senderRunning.compareAndSet(false, true)) return;
        senderThread = new Thread(this::senderLoop, "GamepadSender");
        // Slightly elevated priority: this thread's whole job is "don't let network I/O jitter",
        // so it should not get starved behind other background work, but it must not preempt the
        // UI thread either -- URGENT_AUDIO-tier priorities are for the UI/RenderThread, not here.
        senderThread.setPriority(Thread.MAX_PRIORITY);
        senderThread.start();
    }

    public void stop() {
        senderRunning.set(false);
        if (senderThread != null) {
            senderThread.interrupt();
            senderThread = null;
        }
    }

    private void senderLoop() {
        // Optional: android.os.Process priority gives finer control than java.lang.Thread priority.
        Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);

        while (senderRunning.get()) {
            long tickStart = SystemClock.uptimeMillis();

            // 1) Drain every queued button transition -- none may be dropped, order preserved.
            GamepadPendingState transition;
            while ((transition = transitionQueue.poll()) != null) {
                sendState(transition, /*isTransition=*/true);
            }

            // 2) Ship the latest continuous state (axes/triggers), if anything has been published
            //    since the last tick. Using getAndSet(null) means we never resend an identical,
            //    already-delivered snapshot if the sticks haven't moved since the last tick.
            GamepadPendingState continuous = latestContinuousState.getAndSet(null);
            if (continuous != null) {
                sendState(continuous, /*isTransition=*/false);
            }

            long elapsed = SystemClock.uptimeMillis() - tickStart;
            long sleepFor = Math.max(0, SEND_INTERVAL_MS - elapsed);
            try {
                Thread.sleep(sleepFor);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void sendState(GamepadPendingState state, boolean isTransition) {
        if (testModeActive) return;
        try {
            ByteBuffer packet = UnreliableInputPacket.forChangedGamepads(state.token, state.tick, state.snapshot);
            unreliableSendRaw.accept(packet);
            if (isTransition) {
                // Preserve the old reliability behavior (resend button edges an extra time over
                // the unreliable channel) but do it here, off the UI thread, instead of inline in
                // onKeyDown/onKeyUp.
                ByteBuffer resend = UnreliableInputPacket.forChangedGamepads(state.token, state.tick, state.snapshot);
                unreliableSendRaw.accept(resend);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to send packet", e);
        }
    }

    // ==================================================================================
    // CAPTURE SIDE -- runs on the UI thread inside dispatchKeyEvent/dispatchGenericMotionEvent.
    // Every method below must stay cheap: no allocation beyond one small immutable snapshot,
    // no network I/O, no synchronized block held across anything but a plain field copy.
    // ==================================================================================

    public String prepare(InputDevice device) {
        if (device == null) return "";
        if (device.isVirtual()) return ""; // remote/injected input, never reconfigures mapping

        String descriptor = device.getDescriptor();

        synchronized (stateLock) {
            lastDevice = device;
            if (isPrepared && descriptor != null && descriptor.equals(preparedDeviceDescriptor)) {
                return "";
            }
            preparedDeviceDescriptor = descriptor;
            String devName = device.getName();
            Arrays.fill(keyCodeMap, -1);

            map(KeyEvent.KEYCODE_BUTTON_A, GamepadTransitionFrame.INDEX_A);
            map(KeyEvent.KEYCODE_BUTTON_B, GamepadTransitionFrame.INDEX_B);
            map(KeyEvent.KEYCODE_BUTTON_X, GamepadTransitionFrame.INDEX_X);
            map(KeyEvent.KEYCODE_BUTTON_Y, GamepadTransitionFrame.INDEX_Y);
            map(KeyEvent.KEYCODE_BUTTON_L1, GamepadTransitionFrame.INDEX_LEFT_SHOULDER);
            map(KeyEvent.KEYCODE_BUTTON_R1, GamepadTransitionFrame.INDEX_RIGHT_SHOULDER);
            map(KeyEvent.KEYCODE_BUTTON_THUMBL, GamepadTransitionFrame.INDEX_LEFT_THUMB);
            map(KeyEvent.KEYCODE_BUTTON_THUMBR, GamepadTransitionFrame.INDEX_RIGHT_THUMB);
            map(KeyEvent.KEYCODE_BUTTON_START, GamepadTransitionFrame.INDEX_MENU);
            map(KeyEvent.KEYCODE_BUTTON_SELECT, GamepadTransitionFrame.INDEX_VIEW);
            map(KeyEvent.KEYCODE_BACK, GamepadTransitionFrame.INDEX_VIEW);
            map(KeyEvent.KEYCODE_BUTTON_MODE, GamepadTransitionFrame.INDEX_NEXUS);

            if (device.getMotionRange(MotionEvent.AXIS_Z, InputDevice.SOURCE_JOYSTICK) == null
                    && device.getMotionRange(MotionEvent.AXIS_RX, InputDevice.SOURCE_JOYSTICK) != null) {
                axisRSX = MotionEvent.AXIS_RX;
                axisRSY = MotionEvent.AXIS_RY;
            } else {
                axisRSX = MotionEvent.AXIS_Z;
                axisRSY = MotionEvent.AXIS_RZ;
            }

            if (device.getMotionRange(MotionEvent.AXIS_LTRIGGER, InputDevice.SOURCE_JOYSTICK) != null) {
                axisLT = MotionEvent.AXIS_LTRIGGER;
                leftTriggerIsAnalog = true;
            } else if (device.getMotionRange(MotionEvent.AXIS_BRAKE, InputDevice.SOURCE_JOYSTICK) != null) {
                axisLT = MotionEvent.AXIS_BRAKE;
                leftTriggerIsAnalog = true;
            } else {
                leftTriggerIsAnalog = false;
            }

            if (device.getMotionRange(MotionEvent.AXIS_RTRIGGER, InputDevice.SOURCE_JOYSTICK) != null) {
                axisRT = MotionEvent.AXIS_RTRIGGER;
                rightTriggerIsAnalog = true;
            } else if (device.getMotionRange(MotionEvent.AXIS_GAS, InputDevice.SOURCE_JOYSTICK) != null) {
                axisRT = MotionEvent.AXIS_GAS;
                rightTriggerIsAnalog = true;
            } else {
                rightTriggerIsAnalog = false;
            }

            hasHatAxes = (device.getMotionRange(MotionEvent.AXIS_HAT_X, InputDevice.SOURCE_JOYSTICK) != null &&
                    device.getMotionRange(MotionEvent.AXIS_HAT_Y, InputDevice.SOURCE_JOYSTICK) != null);

            if (!hasHatAxes) {
                map(KeyEvent.KEYCODE_DPAD_UP, GamepadTransitionFrame.INDEX_DPAD_UP);
                map(KeyEvent.KEYCODE_DPAD_DOWN, GamepadTransitionFrame.INDEX_DPAD_DOWN);
                map(KeyEvent.KEYCODE_DPAD_LEFT, GamepadTransitionFrame.INDEX_DPAD_LEFT);
                map(KeyEvent.KEYCODE_DPAD_RIGHT, GamepadTransitionFrame.INDEX_DPAD_RIGHT);
            }

            isPrepared = true;
            return devName + " | Connected";
        }
    }

    public void detectAxisLayout(InputDevice device) {
        prepare(device); // already a no-op fast path if descriptor unchanged
    }

    private boolean isRemoteEvent(InputDevice device) {
        return device == null || device.isVirtual();
    }

    private void map(int keyCode, int transitionIndex) {
        if (keyCode >= 0 && keyCode < keyCodeMap.length) {
            keyCodeMap[keyCode] = transitionIndex;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isPrepared || isRemoteEvent(event.getDevice())) return false;
        if (keyDown[keyCode]) return true;
        if (event.getRepeatCount() > 0) return true;

        int index = (keyCode >= 0 && keyCode < keyCodeMap.length) ? keyCodeMap[keyCode] : -1;
        if (index == -1) return false;

        synchronized (stateLock) {
            if (index == GamepadTransitionFrame.INDEX_MENU
                    && transitionFrame.held[GamepadTransitionFrame.INDEX_VIEW]) {
                transitionFrame.resetAllHeldButtons();
                enqueueTransition();
                if (!testModeActive && onMenuTrigger != null) onMenuTrigger.run();
                keyDown[keyCode] = true;
                return true;
            }
            transitionFrame.bumpButton(index);
            enqueueTransition();
            keyDown[keyCode] = true;
        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!isPrepared || isRemoteEvent(event.getDevice())) return false;

        int index = (keyCode >= 0 && keyCode < keyCodeMap.length) ? keyCodeMap[keyCode] : -1;
        if (index == -1) return false;
        if (!keyDown[keyCode]) return true;

        synchronized (stateLock) {
            transitionFrame.setReleased(index);
            enqueueTransition();
            keyDown[keyCode] = false;
        }
        return true;
    }

    public boolean onGenericMotion(MotionEvent event) {
        if (!isPrepared || isRemoteEvent(event.getDevice())
                || (event.getSource() & InputDevice.SOURCE_JOYSTICK) != InputDevice.SOURCE_JOYSTICK) {
            return false;
        }

        synchronized (stateLock) {
            // Recover samples Android batched into this single dispatch (relevant even with
            // requestUnbufferedDispatch enabled) instead of only reading the final/current value.
            int historySize = event.getHistorySize();
            for (int i = 0; i < historySize; i++) {
                applyMotionSample(
                        event.getHistoricalAxisValue(MotionEvent.AXIS_X, i),
                        event.getHistoricalAxisValue(MotionEvent.AXIS_Y, i),
                        event.getHistoricalAxisValue(axisRSX, i),
                        event.getHistoricalAxisValue(axisRSY, i),
                        event.getHistoricalAxisValue(axisLT, i),
                        event.getHistoricalAxisValue(axisRT, i),
                        event.getHistoricalAxisValue(MotionEvent.AXIS_HAT_X, i),
                        event.getHistoricalAxisValue(MotionEvent.AXIS_HAT_Y, i));
            }
            applyMotionSample(
                    event.getAxisValue(MotionEvent.AXIS_X),
                    event.getAxisValue(MotionEvent.AXIS_Y),
                    event.getAxisValue(axisRSX),
                    event.getAxisValue(axisRSY),
                    event.getAxisValue(axisLT),
                    event.getAxisValue(axisRT),
                    event.getAxisValue(MotionEvent.AXIS_HAT_X),
                    event.getAxisValue(MotionEvent.AXIS_HAT_Y));

            // Only publish the FINAL resulting state for this dispatch -- that's the "current"
            // value the sender thread's next tick cares about (same semantics as
            // navigator.getGamepads() returning the current snapshot).
            publishContinuousState();
        }

        return true;
    }

    /** Applies one raw sample (current or historical) to transitionFrame. No allocation. */
    private void applyMotionSample(float lx, float ly, float rx, float ry, float lt, float rt,
                                   float hatX, float hatY) {
        rawLeftX = lx;
        rawLeftY = ly;
        rawRightX = rx;
        rawRightY = ry;

        // Radial deadzone: computed on stick magnitude, not per-axis, so the dead area is a
        // circle (an axial deadzone makes it a square, up to ~41% larger on diagonals, which
        // desyncs input angle from output angle).
        applyRadialDeadzone(lx, ly, stickDeadzone, 1.0f, stickScratch);
        transitionFrame.setLeftStick(stickScratch[0], stickScratch[1]);

        applyRadialDeadzone(rx, ry, stickDeadzone, rightStickCurveExponent, stickScratch);
        transitionFrame.setRightStick(
                clamp(stickScratch[0] * cameraSensitivity),
                clamp(stickScratch[1] * cameraSensitivity));

        if (leftTriggerIsAnalog) {
            transitionFrame.axes[GamepadTransitionFrame.AXIS_LEFT_TRIGGER] = Math.max(0f, lt);
        }
        if (rightTriggerIsAnalog) {
            transitionFrame.axes[GamepadTransitionFrame.AXIS_RIGHT_TRIGGER] = Math.max(0f, rt);
        }

        if (hasHatAxes) {
            boolean up = hatY < -0.5f;
            boolean down = hatY > 0.5f;
            boolean left = hatX < -0.5f;
            boolean right = hatX > 0.5f;

            boolean anyEdge = (up != prevHatUp) || (down != prevHatDown)
                    || (left != prevHatLeft) || (right != prevHatRight);

            if (up && !prevHatUp) transitionFrame.bumpButton(GamepadTransitionFrame.INDEX_DPAD_UP);
            else if (!up && prevHatUp) transitionFrame.setReleased(GamepadTransitionFrame.INDEX_DPAD_UP);

            if (down && !prevHatDown) transitionFrame.bumpButton(GamepadTransitionFrame.INDEX_DPAD_DOWN);
            else if (!down && prevHatDown) transitionFrame.setReleased(GamepadTransitionFrame.INDEX_DPAD_DOWN);

            if (left && !prevHatLeft) transitionFrame.bumpButton(GamepadTransitionFrame.INDEX_DPAD_LEFT);
            else if (!left && prevHatLeft) transitionFrame.setReleased(GamepadTransitionFrame.INDEX_DPAD_LEFT);

            if (right && !prevHatRight) transitionFrame.bumpButton(GamepadTransitionFrame.INDEX_DPAD_RIGHT);
            else if (!right && prevHatRight) transitionFrame.setReleased(GamepadTransitionFrame.INDEX_DPAD_RIGHT);

            prevHatUp = up;
            prevHatDown = down;
            prevHatLeft = left;
            prevHatRight = right;

            // D-pad edges are discrete like button presses -- route them through the
            // never-dropped transition queue too, not just the coalescible continuous slot.
            if (anyEdge) enqueueTransition();
        }
    }

    /** Must be called while holding stateLock. Publishes a snapshot for the sender thread. */
    private void publishContinuousState() {
        transitionFrame.physicalPhysicality = transitionFrame.computeButtonPhysicality();
        GamepadTransitionFrame snapshot = transitionFrame.copy();
        long token = unreliableToken.incrementAndGet();
        long tick = tickCounter++;
        // Overwrite whatever the previous, not-yet-sent continuous state was -- only the latest
        // value matters, exactly like polling "current" gamepad state.
        latestContinuousState.set(new GamepadPendingState(token, tick, snapshot));
    }

    /** Must be called while holding stateLock. Enqueues a snapshot that MUST be delivered. */
    private void enqueueTransition() {
        transitionFrame.physicalPhysicality = transitionFrame.computeButtonPhysicality();
        GamepadTransitionFrame snapshot = transitionFrame.copy();
        long token = unreliableToken.incrementAndGet();
        long tick = tickCounter++;
        transitionQueue.offer(new GamepadPendingState(token, tick, snapshot));
    }

    private static final class GamepadPendingState {
        final long token;
        final long tick;
        final GamepadTransitionFrame snapshot;

        GamepadPendingState(long token, long tick, GamepadTransitionFrame snapshot) {
            this.token = token;
            this.tick = tick;
            this.snapshot = snapshot;
        }
    }

    /**
     * Radial deadzone + response curve. See prior version's doc comment for full rationale.
     * Writes into `out` to avoid allocation on the hot input path.
     */
    private static void applyRadialDeadzone(float rawX, float rawY, float deadzone, float curveExponent, float[] out) {
        float magnitude = (float) Math.sqrt(rawX * rawX + rawY * rawY);
        if (magnitude < deadzone || magnitude < 1e-6f) {
            out[0] = 0f;
            out[1] = 0f;
            return;
        }
        float normalizedMag = Math.min(1f, (magnitude - deadzone) / (1f - deadzone));
        float curvedMag = (curveExponent == 1.0f) ? normalizedMag : (float) Math.pow(normalizedMag, curveExponent);
        float scale = curvedMag / magnitude;
        out[0] = rawX * scale;
        out[1] = rawY * scale;
    }

    private static float clamp(float value) {
        if (value > 1f) return 1f;
        if (value < -1f) return -1f;
        return value;
    }

    // ==================================================================================
    // Public config / UI-support API -- unchanged from prior version
    // ==================================================================================

    public void setTestModeActive(boolean active) {
        this.testModeActive = active;
    }

    public boolean isTestModeActive() {
        return testModeActive;
    }

    public void setStickDeadzone(float deadzone) {
        this.stickDeadzone = deadzone;
    }

    public float getStickDeadzone() {
        return stickDeadzone;
    }

    public void setCameraSensitivity(float sensitivity) {
        this.cameraSensitivity = sensitivity;
    }

    public float getCameraSensitivity() {
        return cameraSensitivity;
    }

    public float getRightStickResponseCurve() {
        return rightStickCurveExponent;
    }

    public void setRightStickResponseCurve(float exponent) {
        this.rightStickCurveExponent = Math.max(0.25f, Math.min(4.0f, exponent));
    }

    public int getRightStickAxisX() {
        return axisRSX;
    }

    public int getRightStickAxisY() {
        return axisRSY;
    }

    public void getRawStickState(float[] out) {
        synchronized (stateLock) {
            out[0] = rawLeftX;
            out[1] = rawLeftY;
            out[2] = rawRightX;
            out[3] = rawRightY;
        }
    }

    public static void computeStickResponse(float rawX, float rawY, float deadzone, float curveExponent,
                                            float sensitivity, float[] out) {
        applyRadialDeadzone(rawX, rawY, deadzone, curveExponent, out);
        out[0] = clamp(out[0] * sensitivity);
        out[1] = clamp(out[1] * sensitivity);
    }

    public void setgamepadIndex(int idx) {
        this.transitionFrame.gamepadId = idx;
    }

    public InputDevice getLastDevice() {
        return lastDevice;
    }

    public void pressNexusOnce() {
        // No longer needs its own Thread + sendState call -- just goes through the same
        // never-dropped transition queue the sender thread already drains every tick.
        new Thread(() -> {
            try {
                synchronized (stateLock) {
                    transitionFrame.bumpButton(GamepadTransitionFrame.INDEX_NEXUS);
                    enqueueTransition();
                }
                Thread.sleep(60); // Standard guide button press duration
                synchronized (stateLock) {
                    transitionFrame.setReleased(GamepadTransitionFrame.INDEX_NEXUS);
                    enqueueTransition();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void setOnMenuTrigger(Runnable onMenuTrigger) {
        this.onMenuTrigger = onMenuTrigger;
    }
}