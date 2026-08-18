package com.world.cloudxsolution;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.ui.PlayerView;
import com.google.gson.Gson;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;
    private UdpController udpController;
    private RomStationApi api;
    private final Gson gson = new Gson();
    private final Handler retryHandler = new Handler(Looper.getMainLooper());
    // private final Handler inputHandler = new Handler(Looper.getMainLooper());
    private final Handler keepaliveHandler = new Handler(Looper.getMainLooper());
    private static final int MAX_CREATE_LOBBY_ATTEMPTS = 2;
    private static final long CREATE_LOBBY_RETRY_DELAY_MS = 1500;

    private String currentLobbyId;
    private String currentCredentialId;
    private long lastUpdateTimestamp;

    private volatile byte b1 = 0, b2 = 0, lx = 0, ly = 0, l2 = 0, rx = 0, ry = 0, r2 = 0;

    private final Runnable inputHeartbeat = new Runnable() {
        @Override
        public void run() {
            if (udpController != null) {
                sendInput();
            }
            //inputHandler.postDelayed(this, 6); // ~60fps
        }
    };

    private final Runnable lobbyKeepalive = new Runnable() {
        @Override
        public void run() {
            if (currentLobbyId != null && currentCredentialId != null) {
                Log.d("GameActivity", "Sending lobby keepalive...");
                api.updateLobby(currentLobbyId, currentCredentialId, lastUpdateTimestamp, new RequestNetwork.RequestListener() {
                    @Override
                    public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                        try {
                            Models.LobbyUpdateResponse updateResponse = gson.fromJson(response, Models.LobbyUpdateResponse.class);
                            if (updateResponse != null && updateResponse.error > 0) {
                                lastUpdateTimestamp = updateResponse.last_update;
                                Log.d("GameActivity", "Lobby keepalive success. Next update in 30s.");
                            }
                        } catch (Exception e) {
                            Log.e("GameActivity", "Error parsing lobby update response", e);
                        }
                    }

                    @Override
                    public void onErrorResponse(String tag, String message) {
                        Log.e("GameActivity", "Lobby keepalive failed: " + message);
                    }
                });
            }
            keepaliveHandler.postDelayed(this, 30000);
        }
    };

    private final Runnable softKeepalive = new Runnable() {
        @Override
        public void run() {
            Log.d("GameActivity", "Sending software keepalive...");
            api.updateSoftware(new RequestNetwork.RequestListener() {
                @Override
                public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                    Log.d("GameActivity", "Software keepalive success.");
                }

                @Override
                public void onErrorResponse(String tag, String message) {
                    Log.e("GameActivity", "Software keepalive failed: " + message);
                }
            });
            keepaliveHandler.postDelayed(this, 60000);
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playerView = findViewById(R.id.player_view);
        api = new RomStationApi(this);

        String gameFileId = getIntent().getStringExtra("game_file_id");
        String gameTitle = getIntent().getStringExtra("game_title");

        if (gameFileId != null) {
            Log.d("GameActivity", "Initializing session for gameFileId: " + gameFileId + ", title: " + gameTitle);
            initSession(gameFileId, gameTitle != null ? gameTitle : "Game Session");
        } else {
            Log.w("GameActivity", "game_file_id is null!");
        }
    }

    private void initSession(String gameFileId, String title) {
        createLobbyWithRetry(gameFileId, title, 1);
    }

    private void createLobbyWithRetry(String gameFileId, String title, int attempt) {
        Log.d("GameActivity", "Calling createLobby... attempt " + attempt + "/" + MAX_CREATE_LOBBY_ATTEMPTS);
        api.createLobby(gameFileId, title, new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {
                    Log.d("GameActivity", "createLobby response: " + response);
                    Models.LobbyResponse lobbyResponse = gson.fromJson(response, Models.LobbyResponse.class);
                    if (lobbyResponse != null && lobbyResponse.error > 0 && lobbyResponse.lobby != null) {
                        Log.d("GameActivity", "Lobby created with ID: " + lobbyResponse.lobby.id + ". Requesting credential...");
                        getCredential(lobbyResponse.lobby.id);
                    } else {
                        retryCreateLobbyOrFinish(gameFileId, title, attempt, lobbyResponse != null ? lobbyResponse.error : 0, response);
                    }
                } catch (Exception e) {
                    Log.e("GameActivity", "Error parsing createLobby response", e);
                    retryCreateLobbyOrFinish(gameFileId, title, attempt, 0, response);
                }
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                Log.e("GameActivity", "createLobby failed: " + message);
                retryCreateLobbyOrFinish(gameFileId, title, attempt, 0, message);
            }
        });
    }

    private void retryCreateLobbyOrFinish(String gameFileId, String title, int attempt, int error, String response) {
        if (attempt < MAX_CREATE_LOBBY_ATTEMPTS) {
            int nextAttempt = attempt + 1;
            Log.w("GameActivity", "createLobby attempt " + attempt + " failed. Retrying attempt " + nextAttempt + "/" + MAX_CREATE_LOBBY_ATTEMPTS + ". Response: " + response);
            retryHandler.postDelayed(() -> createLobbyWithRetry(gameFileId, title, nextAttempt), CREATE_LOBBY_RETRY_DELAY_MS);
            return;
        }

        finishWithServerError("createLobby", error, response);
    }

    private void getCredential(String lobbyId) {
        Log.d("GameActivity", "Calling getCredential for lobby ID: " + lobbyId);
        api.getCredential(lobbyId, null, new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {
                    Log.d("GameActivity", "getCredential response: " + response);
                    Models.CredentialResponse credentialResponse = gson.fromJson(response, Models.CredentialResponse.class);
                    if (credentialResponse != null
                            && credentialResponse.error > 0
                            && credentialResponse.credential != null
                            && credentialResponse.credential.id != null) {
                        if (credentialResponse.credential.vpn) {
                            Log.w("GameActivity", "Credential requires VPN; Android VPN setup is not implemented here");
                        }
                        joinLobby(lobbyId, credentialResponse.credential.id);
                    } else {
                        handleServerError("getCredential", credentialResponse != null ? credentialResponse.error : 0, response);
                    }
                } catch (Exception e) {
                    Log.e("GameActivity", "Error parsing getCredential response", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                Log.e("GameActivity", "getCredential failed: " + message);
            }
        });
    }

    private void joinLobby(String lobbyId, String credentialId) {
        Log.d("GameActivity", "Calling joinLobby for ID: " + lobbyId + ", credential ID: " + credentialId);
        api.joinLobby(lobbyId, credentialId, new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {
                    Log.d("GameActivity", "joinLobby response: " + response);
                    Models.JoinResponse joinResponse = gson.fromJson(response, Models.JoinResponse.class);
                    if (joinResponse != null && joinResponse.error > 0 && joinResponse.lobby != null) {
                        Models.JoinResponse.Lobby lobby = joinResponse.lobby;
                        currentLobbyId = lobbyId;
                        currentCredentialId = credentialId;
                        // Extract last_update if available in raw response (Models might need update if it's there)
                        try {
                            lastUpdateTimestamp = new org.json.JSONObject(response).getLong("last_update");
                        } catch (Exception e) {
                            lastUpdateTimestamp = System.currentTimeMillis();
                        }

                        String streamUri = getCloudStreamUri(lobby);
                        String controllerHost = getCloudControllerHost(lobby);
                        int controllerPort = getCloudControllerPort(lobby);

                        // Two different candidate ids/keys exist in this payload:
                        //  - lobby.cloud.controller.id: the session-level id on the shared
                        //    cloud relay (there is no session-level "key" alongside it)
                        //  - members[].controller.id / .key: this player's per-lobby id+key
                        // We don't yet know for certain which pairing ccontroller.romstation.fr
                        // expects on the UDP wire protocol, so both are logged here - compare
                        // against what actually gets the controller working (or capture the
                        // desktop client's traffic once to confirm) and delete the unused path.
                        Models.JoinResponse.LobbyMember member = findLobbyMember(lobby, credentialId);
                        int cloudControllerId = (lobby.cloud != null && lobby.cloud.controller != null)
                                ? lobby.cloud.controller.id : -1;
                        int memberControllerId = getControllerId(member);
                        int memberControllerKey = getControllerKey(member);

                        Log.d("GameActivity", "Candidate ids -> cloud.controller.id=" + cloudControllerId
                                + ", member.controller.id=" + memberControllerId
                                + ", member.controller.key=" + memberControllerKey);

                        // Using the per-member id/key pairing since that's a complete
                        // (id, key) pair from a single source; cloud.controller.id has no
                        // matching "key" field in this payload, so it's likely NOT what's
                        // sent per-packet - it's probably just relay/session metadata.
                        int controllerId = memberControllerId;
                        int controllerKey = memberControllerKey;
                        Log.d("GameActivity", "Parsed join fields: stream=" + streamUri + ", host=" + controllerHost + ", port=" + controllerPort + ", id=" + controllerId + ", key=" + controllerKey);

                        if (streamUri != null) {
                            Log.d("GameActivity", "Stream URI: " + streamUri);
                            retryHandler.postDelayed(
                                    () -> setupStream(streamUri),
                                    10000
                            );
                        } else {
                            Log.w("GameActivity", "joinLobby response missing stream URI");
                        }

                        if (controllerHost != null && controllerPort > 0 && controllerId >= 0 && controllerKey >= 0) {
                            byte id = (byte) controllerId;
                            byte key = (byte) controllerKey;
                            Log.d("GameActivity", "Controller details: IP=" + controllerHost + ", Port=" + controllerPort + ", ID=" + id + ", Key=" + key);
                            setupController(controllerHost, controllerPort, id, key);
                            startKeepalives();
                        } else {
                            Log.w("GameActivity", "joinLobby response missing controller/credential data");
                        }
                    } else {
                        handleServerError("joinLobby", joinResponse != null ? joinResponse.error : 0, response);
                    }
                } catch (Exception e) {
                    Log.e("GameActivity", "Error parsing joinLobby response", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                Log.e("GameActivity", "joinLobby failed: " + message);
            }
        });
    }

    private void handleServerError(String operation, int error, String response) {
        String message = getServerErrorMessage(error);
        Log.w("GameActivity", operation + " failed: " + message + " Response: " + response);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void finishWithServerError(String operation, int error, String response) {
        handleServerError(operation, error, response);
        Log.w("GameActivity", "Returning to main after " + MAX_CREATE_LOBBY_ATTEMPTS + " failed createLobby attempts");
        finish();
    }

    private String getServerErrorMessage(int error) {
        switch (error) {
            case -99:
                return "Cloud server limit reached for this account.";
            case -92:
                return "Cloud servers are unavailable.";
            case -91:
                return "Cloud service is disabled.";
            case -87:
                return "RomStation app update is required.";
            case -35:
                return "Netplay service is unavailable.";
            case 0:
                return "Server response is missing required data.";
            default:
                return "Server returned error " + error + ".";
        }
    }
    // NOTE: The old implementation re-parsed the raw JSON string looking for a
    // "lobby" wrapper object (e.g. "lobby.cloud.controller.server.hostname").
    // Confirmed from an actual join_lobby.php response: everything (stream,
    // controller, members) lives under a "lobby" object - there is no
    // top-level "credential" field on this endpoint's response at all.
    // See Models.JoinResponse.Lobby.

    private String getCloudStreamUri(Models.JoinResponse.Lobby lobby) {
        if (lobby.cloud != null && lobby.cloud.stream != null && lobby.cloud.stream.uri != null) {
            return lobby.cloud.stream.uri;
        }
        Log.w("GameActivity", "Unable to find a stream uri in join response");
        return null;
    }

    private String getCloudControllerHost(Models.JoinResponse.Lobby lobby) {
        if (lobby.cloud != null && lobby.cloud.controller != null && lobby.cloud.controller.server != null) {
            return lobby.cloud.controller.server.hostname;
        }
        Log.w("GameActivity", "Unable to find a controller host in join response");
        return null;
    }

    private int getCloudControllerPort(Models.JoinResponse.Lobby lobby) {
        if (lobby.cloud != null && lobby.cloud.controller != null && lobby.cloud.controller.server != null) {
            return lobby.cloud.controller.server.port;
        }
        Log.w("GameActivity", "Unable to find a controller port in join response");
        return 0;
    }

    private Models.JoinResponse.LobbyMember findLobbyMember(Models.JoinResponse.Lobby lobby, String credentialId) {
        if (lobby.members == null) {
            return null;
        }
        String memberId = api.getMemberId();
        for (Models.JoinResponse.LobbyMember member : lobby.members) {
            boolean idMatches = credentialId != null && credentialId.equals(member.id);
            boolean memberIdMatches = memberId != null && memberId.equals(String.valueOf(member.member_id));
            if (idMatches || memberIdMatches) {
                return member;
            }
        }
        return null;
    }

    private int getControllerId(Models.JoinResponse.LobbyMember member) {
        if (member == null) {
            return -1;
        }
        if (member.controller != null) {
            return member.controller.id;
        }
        return member.controller_id;
    }

    private int getControllerKey(Models.JoinResponse.LobbyMember member) {
        if (member == null) {
            return -1;
        }
        if (member.controller != null) {
            return member.controller.key;
        }
        return member.controller_key;
    }



    @OptIn(markerClass = UnstableApi.class)
    private void setupStream(String rtspUri) {

        Log.d("GameActivity", "=================================");
        Log.d("GameActivity", "RTSP START");
        Log.d("GameActivity", "URI = " + rtspUri);
        Log.d("GameActivity", "=================================");

        // Default ExoPlayer LoadControl buffers several seconds of media before
        // playback (tuned for VOD, where a bit of stutter-avoidance buffering is
        // fine). For a live, interactive cloud-gaming stream that buffering shows
        // up directly as input lag: your button press reaches the game instantly,
        // but you don't SEE the result until several seconds of buffered video
        // drain first. Use a minimal LoadControl instead so playback starts
        // (and stays) as close to the live edge as possible.
        DefaultLoadControl loadControl = new DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                        /* minBufferMs= */ 32,
                        /* maxBufferMs= */ 48,
                        /* bufferForPlaybackMs= */ 0,
                        /* bufferForPlaybackAfterRebufferMs= */ 16)
                .build();

        player = new ExoPlayer.Builder(this)
                .setLoadControl(loadControl)
                .build();
        playerView.setPlayer(player);

        player.addListener(new Player.Listener() {

            @Override
            public void onPlaybackStateChanged(int state) {
                Log.d("GameActivity", "PlaybackState = " + state);

                if (state == Player.STATE_BUFFERING) {
                    Log.d("GameActivity", "BUFFERING...");
                }

                if (state == Player.STATE_READY) {
                    Log.d("GameActivity", "!!!!!!!! VIDEO READY !!!!!!!!");
                }

                if (state == Player.STATE_ENDED) {
                    Log.d("GameActivity", "STREAM ENDED");
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                Log.d("GameActivity", "isPlaying = " + isPlaying);
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                Log.e("GameActivity", "!!!!!!!! EXOPLAYER ERROR !!!!!!!!");
                Log.e("GameActivity", "errorCode = " + error.errorCode);
                Log.e("GameActivity", "message = " + error.getMessage());
                Log.e("GameActivity", "name = " + error.getErrorCodeName());
                Log.e("GameActivity", "cause = ", error);
            }
        });

        RtspMediaSource.Factory factory = new RtspMediaSource.Factory()
                .setForceUseRtpTcp(false) // Use UDP for lower latency
                .setTimeoutMs(1000)
                .setDebugLoggingEnabled(false);

        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(Uri.parse(rtspUri))
                .setLiveConfiguration(new MediaItem.LiveConfiguration.Builder()
                        .setTargetOffsetMs(0)
                        // NOTE: this was previously 0.1f/0.2f, which tells ExoPlayer's
                        // live catch-up logic it's only allowed to run between 10%-20%
                        // playback speed - i.e. permanent slow motion whenever the
                        // live-offset adjustment engages at all, growing the gap
                        // between real game state (driven by UDP input with no such
                        // slowdown) and what's shown on screen. Live-offset speed
                        // adjustment should be a small nudge around 1.0x, not a
                        // near-freeze.
                        .setMinPlaybackSpeed(0.90f)
                        .setMaxPlaybackSpeed(2.5f)
                        .build())
                .build();

        RtspMediaSource mediaSource =
                factory.createMediaSource(mediaItem);

        player.setMediaSource(mediaSource);

        Log.d("GameActivity", "Preparing player...");

        player.prepare();

        Log.d("GameActivity", "Calling play...");

        player.play();
    }
    private void setupController(String ip, int port, byte id, byte key) {
        udpController = new UdpController(ip, port, id, key);
    }

    private void startKeepalives() {
        keepaliveHandler.removeCallbacks(lobbyKeepalive);
        keepaliveHandler.removeCallbacks(softKeepalive);
        //inputHandler.removeCallbacks(inputHeartbeat);

        keepaliveHandler.postDelayed(lobbyKeepalive, 30000);
        keepaliveHandler.postDelayed(softKeepalive, 60000);
        //inputHandler.post(inputHeartbeat);
    }

    private void stopKeepalives() {
        keepaliveHandler.removeCallbacks(lobbyKeepalive);
        keepaliveHandler.removeCallbacks(softKeepalive);
        //inputHandler.removeCallbacks(inputHeartbeat);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (updateButtons(keyCode, true)) {
            if (udpController != null) {
                udpController.sendInput(b1, b2, lx, ly, l2, rx, ry, r2);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (updateButtons(keyCode, false)) {
            if (udpController != null) {
                udpController.sendInput(b1, b2, lx, ly, l2, rx, ry, r2);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private boolean updateButtons(int keyCode, boolean down) {
        // Handle Start/Select aliases for universal gamepad support
        if (keyCode == KeyEvent.KEYCODE_MENU) keyCode = KeyEvent.KEYCODE_BUTTON_START;
        if (keyCode == KeyEvent.KEYCODE_BACK) keyCode = KeyEvent.KEYCODE_BUTTON_SELECT;

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP: setBit(5, 0, down); return true;
            case KeyEvent.KEYCODE_DPAD_DOWN: setBit(5, 1, down); return true;
            case KeyEvent.KEYCODE_DPAD_LEFT: setBit(5, 2, down); return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT: setBit(5, 3, down); return true;
            case KeyEvent.KEYCODE_BUTTON_A: setBit(5, 4, down); return true;
            case KeyEvent.KEYCODE_BUTTON_B: setBit(5, 5, down); return true;
            case KeyEvent.KEYCODE_BUTTON_Y: setBit(5, 6, down); return true; // Triangle (ID 7, Bit 6)
            case KeyEvent.KEYCODE_BUTTON_X: setBit(5, 7, down); return true; // Square (ID 8, Bit 7)
            case KeyEvent.KEYCODE_BUTTON_L1: setBit(6, 0, down); return true;
            case KeyEvent.KEYCODE_BUTTON_R1: setBit(6, 1, down); return true;
            case KeyEvent.KEYCODE_BUTTON_SELECT: setBit(6, 2, down); return true; // Select (ID 11, Bit 2)
            case KeyEvent.KEYCODE_BUTTON_START: setBit(6, 3, down); return true;  // Start (ID 12, Bit 3)
            case KeyEvent.KEYCODE_HOME: setBit(6, 4, down); return true;         // PS/Home (ID 13, Bit 4)
            case KeyEvent.KEYCODE_BUTTON_THUMBL: setBit(6, 5, down); return true; // L3 (ID 14, Bit 5)
            case KeyEvent.KEYCODE_BUTTON_THUMBR: setBit(6, 6, down); return true; // R3 (ID 15, Bit 6)
            case KeyEvent.KEYCODE_BUTTON_L2: l2 = (byte) (down ? 127 : 0); return true;
            case KeyEvent.KEYCODE_BUTTON_R2: r2 = (byte) (down ? 127 : 0); return true;
        }
        return false;
    }

    private void setBit(int byteNum, int bit, boolean val) {
        if (byteNum == 5) {
            if (val) b1 |= (1 << bit); else b1 &= ~(1 << bit);
        } else {
            if (val) b2 |= (1 << bit); else b2 &= ~(1 << bit);
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK ||
                (event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // Analog sticks
                lx = (byte) (event.getAxisValue(MotionEvent.AXIS_X) * 127);
                ly = (byte) (event.getAxisValue(MotionEvent.AXIS_Y) * 127);

                // Right stick: check both Z/RZ and RX/RY
                float z = event.getAxisValue(MotionEvent.AXIS_Z);
                float rz = event.getAxisValue(MotionEvent.AXIS_RZ);
                float rx_val = event.getAxisValue(MotionEvent.AXIS_RX);
                float ry_val = event.getAxisValue(MotionEvent.AXIS_RY);
                rx = (byte) ((Math.abs(z) > Math.abs(rx_val) ? z : rx_val) * 127);
                ry = (byte) ((Math.abs(rz) > Math.abs(ry_val) ? rz : ry_val) * 127);

                // Triggers: check BRAKE/GAS and LTRIGGER/RTRIGGER
                float brake = event.getAxisValue(MotionEvent.AXIS_BRAKE);
                float ltrigger = event.getAxisValue(MotionEvent.AXIS_LTRIGGER);
                l2 = (byte) (Math.max(brake, ltrigger) * 127);

                float gas = event.getAxisValue(MotionEvent.AXIS_GAS);
                float rtrigger = event.getAxisValue(MotionEvent.AXIS_RTRIGGER);
                r2 = (byte) (Math.max(gas, rtrigger) * 127);

                // Hat axis (D-pad) - robust range comparison
                float hatX = event.getAxisValue(MotionEvent.AXIS_HAT_X);
                float hatY = event.getAxisValue(MotionEvent.AXIS_HAT_Y);
                setBit(5, 0, hatY < -0.5f); // Up
                setBit(5, 1, hatY > 0.5f);  // Down
                setBit(5, 2, hatX < -0.5f); // Left
                setBit(5, 3, hatX > 0.5f);  // Right
                if (udpController != null) {
                    udpController.sendInput(b1, b2, lx, ly, l2, rx, ry, r2);
                }
                return true;
            }
        }
        return super.onGenericMotionEvent(event);
    }

    private long sendInputCallCount = 0;

    private void sendInput() {
        sendInputCallCount++;
        if (sendInputCallCount <= 5 || sendInputCallCount % 100 == 0) {
            Log.d("GameActivity", "sendInput() heartbeat tick #" + sendInputCallCount + ", udpController=" + udpController);
        }
        if (udpController != null) {
            udpController.sendInput(b1, b2, lx, ly, l2, rx, ry, r2);
        } else if (sendInputCallCount <= 5) {
            Log.w("GameActivity", "sendInput() called but udpController is null - nothing sent");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retryHandler.removeCallbacksAndMessages(null);
        stopKeepalives();
        if (player != null) {
            player.release();
        }
        if (udpController != null) {
            udpController.close();
        }
    }
}