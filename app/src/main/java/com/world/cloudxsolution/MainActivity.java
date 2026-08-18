package com.world.cloudxsolution;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private VirtualMouseView virtualMouseView;
    private RomStationApi api;
    private final Gson gson = new Gson();
    private boolean loginInProgress = false;
    private boolean isplaying=false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        virtualMouseView = findViewById(R.id.virtual_mouse);
        api = new RomStationApi(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        webView.setWebViewClient(new WebViewClient() {
           private String js ="document.querySelectorAll('#install_rs2').forEach(e=>e.remove());\n" +
                   "if(document.body.dataset.pageid!=undefined&&document.body.dataset.pageid>0){"+
                   "let cloudbtn = document.querySelector('.fa-download');\n" +
                    "cloudbtn.nextSibling.textContent=' Start Cloud Game';\n" +
                    "\n" +
                    "cloudbtn.parentElement.removeAttribute('href');\n" +
                    "cloudbtn.parentElement.onclick = function ji(e) {\n" +
                    "    if(window.RomStationBridge && window.RomStationBridge.startSession){\n" +
                    "        window.RomStationBridge.startSession(String(document.body.dataset.pageid));\n" +
                    "    }\n" +
                    "}}else{document.querySelector('.fa-download')?.parentElement?.remove();}";
            @Override
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(js,null);
                checkLoginStatus();
            }
        });

        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void startSession(String gameId) {
                Log.d("MainActivity", "startSession called via JS bridge for gameId: " + gameId);
                runOnUiThread(() -> startCloudSession(gameId));
            }
        }, "RomStationBridge");

        webView.loadUrl("https://www.romstation.fr/");
    }

    private void checkLoginStatus() {
        String cookies = CookieManager.getInstance().getCookie("https://www.romstation.fr/");
        Log.d("MainActivity", "Cookies: " + cookies);
        if (cookies != null && cookies.contains("PHPSESSID")) {
            String phpsessid = "";
            int memberId = 0;
            for (String cookie : cookies.split(";")) {
                String pair = cookie.trim();
                if (pair.startsWith("PHPSESSID=")) {
                    phpsessid = pair.split("=", 2)[1];
                } else if (pair.startsWith("ips41_member_id=")) {
                    memberId = Integer.parseInt(pair.split("=", 2)[1]);
                }
            }
            if (!phpsessid.isEmpty()) {
                Log.d("MainActivity", "Found PHPSESSID: " + phpsessid + ", Member ID: " + memberId);
                prepareSoftwareThenLogin(phpsessid, memberId);
            } else {
                Log.w("MainActivity", "PHPSESSID is empty");
            }
        } else {
            Log.d("MainActivity", "PHPSESSID cookie not found");
        }
    }

    private void prepareSoftwareThenLogin(String phpsessid, int memberId) {
        if (api.getSessionKey() != null || loginInProgress) {
            return;
        }
        loginInProgress = true;
        api.setLoginContext(phpsessid, memberId);
        if (api.hasSoftwareId()) {
            login(phpsessid, memberId);
            return;
        }
        api.startSoftware(new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                Log.d("MainActivity", "soft_start response: " + response);
                updateSoftwareThenLogin(phpsessid, memberId);
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                Log.e("MainActivity", "soft_start failed: " + message);
                updateSoftwareThenLogin(phpsessid, memberId);
            }
        });
    }

    private void updateSoftwareThenLogin(String phpsessid, int memberId) {
        api.updateSoftware(new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {
                    Log.d("MainActivity", "soft_update response: " + response);
                    Models.SoftUpdateResponse softUpdateResponse = gson.fromJson(response, Models.SoftUpdateResponse.class);
                    if (softUpdateResponse != null
                            && softUpdateResponse.error > 0
                            && softUpdateResponse.soft != null
                            && softUpdateResponse.soft.id > 0) {
                        api.setSoftwareId(softUpdateResponse.soft.id);
                        login(phpsessid, memberId);
                    } else {
                        loginInProgress = false;
                        Log.w("MainActivity", "soft_update response missing software id: " + response);
                    }
                } catch (Exception e) {
                    loginInProgress = false;
                    Log.e("MainActivity", "Error parsing soft_update response", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                loginInProgress = false;
                Log.e("MainActivity", "soft_update failed: " + message);
            }
        });
    }

    private void login(String phpsessid, int memberId) {
        api.login(phpsessid, memberId, new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                loginInProgress = false;
                try {
                    Models.LoginResponse loginResponse = gson.fromJson(response, Models.LoginResponse.class);
                    if (loginResponse != null
                            && loginResponse.error > 0
                            && loginResponse.member != null
                            && loginResponse.member.id != null
                            && !"0".equals(loginResponse.member.id)
                            && loginResponse.member.session_key != null) {
                        Log.d("MainActivity", "Login successful. Member ID: " + loginResponse.member.id);
                        api.setSession(loginResponse.member.id, loginResponse.member.session_key);
                    } else {
                        Log.w("MainActivity", "Login failed or missing session data: " + response);
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Error parsing login response", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                loginInProgress = false;
                Log.e("MainActivity", "Login request failed: " + message);
            }
        });
    }

    private void startCloudSession(String gameId) {
        if (api.getSessionKey() == null || "0".equals(api.getMemberId())) {
            Log.w("MainActivity", "Cannot start cloud session before successful RomStation API login");
            checkLoginStatus();
            return;
        }
        Log.d("MainActivity", "Starting cloud session for game: " + gameId);
        api.getGameInfo(gameId, new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {
                    Models.GameInfoResponse gameInfo = gson.fromJson(response, Models.GameInfoResponse.class);
                    if (gameInfo != null && gameInfo.game != null && gameInfo.game.files != null) {
                        Log.d("MainActivity", "Found " + gameInfo.game.files.size() + " files for game");
                        for (Models.GameInfoResponse.GameFile file : gameInfo.game.files) {
                            Log.d("MainActivity", "Checking file: " + file.file_id + ", status: " + file.status + ", cloud: " + file.cloud + ", cloud_state: " + file.cloud_state);
                            if (file.status == 1 && file.cloud == 1 && file.cloud_state == 4) {
                                Log.d("MainActivity", "Launching GameActivity with fileId: " + file.file_id + ", title: " + gameInfo.game.title);
                                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                intent.putExtra("game_file_id", file.file_id);
                                intent.putExtra("game_title", gameInfo.game.title);
                                isplaying=true;
                                startActivity(intent);
                                return;
                            }
                        }
                        Log.w("MainActivity", "No compatible cloud file found for this game");
                    } else {
                        Log.w("MainActivity", "Game info response invalid: " + response);
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Error parsing game info response", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                Log.e("MainActivity", "Get game info request failed: " + message);
            }
        });
    }


    private void simulateClick(float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        MotionEvent downEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
        MotionEvent upEvent = MotionEvent.obtain(downTime, eventTime + 100, MotionEvent.ACTION_UP, x, y, 0);
        webView.dispatchTouchEvent(downEvent);
        webView.dispatchTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    // --- add these fields ---
    private final android.os.Handler inputHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private boolean loopRunning = false;

    private boolean upHeld, downHeld, leftHeld, rightHeld;
    private long dpadHeldSince = 0L;

    private static final float BASE_SPEED = 8f;      // px per tick at min speed
    private static final float MAX_SPEED = 40f;       // px per tick at max speed
    private static final long RAMP_MS = 600L;          // time to reach max speed
    private static final float STICK_DEADZONE = 0.20f;
    private static final float SCROLL_DEADZONE = 0.20f;
    private static final int TICK_MS = 16;             // ~60fps

    private final Runnable inputLoop = new Runnable() {
        @Override
        public void run() {
            stepCursor();
            stepScroll();
            inputHandler.postDelayed(this, TICK_MS);
        }
    };

    private void startInputLoop() {
        if (!loopRunning) {
            loopRunning = true;
            inputHandler.post(inputLoop);
        }
    }

    // --- left analog stick: move cursor ---
    private float stickX, stickY;
    // --- right analog stick (or triggers): scroll ---
    private float scrollX, scrollY;

    private void stepCursor() {
        float dx = 0, dy = 0;

        // D-pad contribution, with acceleration the longer it's held
        if (upHeld || downHeld || leftHeld || rightHeld) {
            long held = SystemClock.uptimeMillis() - dpadHeldSince;
            float t = Math.min(1f, held / (float) RAMP_MS);
            float speed = BASE_SPEED + (MAX_SPEED - BASE_SPEED) * t;
            if (upHeld) dy -= speed;
            if (downHeld) dy += speed;
            if (leftHeld) dx -= speed;
            if (rightHeld) dx += speed;
        }

        // Left stick contribution, with proper radial deadzone + curve
        float mag = (float) Math.hypot(stickX, stickY);
        if (mag > STICK_DEADZONE) {
            float scale = (mag - STICK_DEADZONE) / (1f - STICK_DEADZONE);
            scale = scale * scale; // ease-in curve so slow tilt = fine control
            dx += (stickX / mag) * scale * MAX_SPEED;
            dy += (stickY / mag) * scale * MAX_SPEED;
        }

        if (dx != 0 || dy != 0) {
            virtualMouseView.moveCursor(dx, dy);
            maybeEdgeAutoScroll(virtualMouseView.getCursorX(), virtualMouseView.getCursorY());
        }
    }

    private void stepScroll() {
        float mag = (float) Math.hypot(scrollX, scrollY);
        if (mag > SCROLL_DEADZONE) {
            float scale = (mag - SCROLL_DEADZONE) / (1f - SCROLL_DEADZONE);
            int dx = (int) (scrollX * scale * 30);
            int dy = (int) (scrollY * scale * 30);
            webView.scrollBy(dx, dy);
        }
    }

    // scroll the page when the cursor nears the screen edge while navigating
    private void maybeEdgeAutoScroll(float x, float y) {
        int margin = 60;
        int h = webView.getHeight();
        int w = webView.getWidth();
        if (y < margin) webView.scrollBy(0, -12);
        else if (y > h - margin) webView.scrollBy(0, 12);
        if (x < margin) webView.scrollBy(-12, 0);
        else if (x > w - margin) webView.scrollBy(12, 0);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(isplaying){return false;}

        int keyCode = event.getKeyCode();

//        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {
//            Log.d("GameActivity", "Gamepad KeyDown: " + keyCode + " (Name: " + KeyEvent.keyCodeToString(keyCode) + ")");
//        }
        boolean isDown = event.getAction() == KeyEvent.ACTION_DOWN;
        boolean isUp = event.getAction() == KeyEvent.ACTION_UP;

        // ignore Android's own auto-repeat DOWN events; our loop handles repetition
        if (isDown && event.getRepeatCount() > 0) return true;

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (isDown && !upHeld) { upHeld = true; dpadHeldSince = SystemClock.uptimeMillis(); startInputLoop(); }
                if (isUp) upHeld = false;
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (isDown && !downHeld) { downHeld = true; dpadHeldSince = SystemClock.uptimeMillis(); startInputLoop(); }
                if (isUp) downHeld = false;
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (isDown && !leftHeld) { leftHeld = true; dpadHeldSince = SystemClock.uptimeMillis(); startInputLoop(); }
                if (isUp) leftHeld = false;
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (isDown && !rightHeld) { rightHeld = true; dpadHeldSince = SystemClock.uptimeMillis(); startInputLoop(); }
                if (isUp) rightHeld = false;
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                if (isDown) simulateClick(virtualMouseView.getCursorX(), virtualMouseView.getCursorY());
                return true;
            case KeyEvent.KEYCODE_BUTTON_L1:
                if (isDown) webView.pageUp(false);
                return true;
            case KeyEvent.KEYCODE_BUTTON_R1:
                if (isDown) webView.pageDown(false);
                return true;
            case 4:
            case KeyEvent.KEYCODE_BUTTON_B:
                if (isDown && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        isplaying=false;
        super.onResume();
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if(isplaying){return false;}
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK
                && event.getAction() == MotionEvent.ACTION_MOVE) {
            stickX = event.getAxisValue(MotionEvent.AXIS_X);
            stickY = event.getAxisValue(MotionEvent.AXIS_Y);
            scrollX = event.getAxisValue(MotionEvent.AXIS_Z);   // right stick X on most gamepads
            scrollY = event.getAxisValue(MotionEvent.AXIS_RZ);  // right stick Y on most gamepads
            startInputLoop();
            return true;
        }
        return super.onGenericMotionEvent(event);
    }
}
