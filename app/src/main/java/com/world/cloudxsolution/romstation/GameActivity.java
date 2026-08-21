package com.world.cloudxsolution.romstation;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.world.cloudxsolution.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameActivity extends AppCompatActivity {

    private LibVLC libVLC;
    private MediaPlayer mediaPlayer;
    private VLCVideoLayout vlcVideoLayout;
    private List<String> selectedVlcOptions = new ArrayList<>();
    private HashMap<String, Object> selectedApiOptions = new HashMap<>();
    private GamepadMapper gamepadMapper = new GamepadMapper();
    private boolean isLearningMapping = false;
    private int learningInputId = -1;
    private AlertDialog mappingCaptureDialog;

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

    // NOTE: MotionEvent.AXIS_SIZE is NOT "the number of axes" - it's the axis constant
    // for touch/tool size (value 3). Using it as a loop bound only scans axes 0-2
    // (X, Y, PRESSURE) and silently skips AXIS_Z / AXIS_RZ (right stick), AXIS_HAT_X/Y,
    // AXIS_BRAKE / AXIS_GAS, etc. Enumerate the axes we actually care about instead.
    private static final int[] GAMEPAD_AXES = {
            MotionEvent.AXIS_X, MotionEvent.AXIS_Y,
            MotionEvent.AXIS_Z, MotionEvent.AXIS_RZ,
            MotionEvent.AXIS_RX, MotionEvent.AXIS_RY,
            MotionEvent.AXIS_HAT_X, MotionEvent.AXIS_HAT_Y,
            MotionEvent.AXIS_LTRIGGER, MotionEvent.AXIS_RTRIGGER,
            MotionEvent.AXIS_BRAKE, MotionEvent.AXIS_GAS,
            MotionEvent.AXIS_THROTTLE, MotionEvent.AXIS_RUDDER, MotionEvent.AXIS_WHEEL
    };

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
        setContentView(R.layout.activity_game_rom);

        vlcVideoLayout = findViewById(R.id.vlc_layout);

        ArrayList<String> options = new ArrayList<>();
        options.add("-vvv");
        libVLC = new LibVLC(this, options);
        mediaPlayer = new MediaPlayer(libVLC);

        api = new RomStationApi(this);
        loadSavedSettings();

        String gameFileId = getIntent().getStringExtra("game_file_id");
        String gameTitle = getIntent().getStringExtra("game_title");

        if (gameFileId != null) {
            Log.d("GameActivity", "Initializing session for gameFileId: " + gameFileId + ", title: " + gameTitle);
            initSession(gameFileId, gameTitle != null ? gameTitle : "Game Session");
        } else {
            Log.w("GameActivity", "game_file_id is null!");
        }
    }

    private void saveCurrentSettings() {
        SharedPreferences prefs = getSharedPreferences("lobby_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("api_options", gson.toJson(selectedApiOptions));
        editor.putString("vlc_options", gson.toJson(selectedVlcOptions));
        editor.putString("gamepad_buttons", gson.toJson(gamepadMapper.buttonToIdMap));
        editor.putString("gamepad_axes", gson.toJson(gamepadMapper.axisToIdMap));
        editor.apply();
        Log.d("GameActivity", "Settings saved to SharedPreferences");
    }

    private void loadSavedSettings() {
        SharedPreferences prefs = getSharedPreferences("lobby_prefs", MODE_PRIVATE);
        String apiJson = prefs.getString("api_options", null);
        String vlcJson = prefs.getString("vlc_options", null);
        String btnJson = prefs.getString("gamepad_buttons", null);
        String axisJson = prefs.getString("gamepad_axes", null);

        if (apiJson != null) {
            selectedApiOptions = gson.fromJson(apiJson, new TypeToken<HashMap<String, Object>>(){}.getType());
        }
        if (vlcJson != null) {
            selectedVlcOptions = gson.fromJson(vlcJson, new TypeToken<List<String>>(){}.getType());
        }
        if (btnJson != null) {
            gamepadMapper.buttonToIdMap = gson.fromJson(btnJson, new TypeToken<HashMap<Integer, Integer>>(){}.getType());
        }
        if (axisJson != null) {
            gamepadMapper.axisToIdMap = gson.fromJson(axisJson, new TypeToken<HashMap<String, Integer>>(){}.getType());
        }
        Log.d("GameActivity", "Settings loaded from SharedPreferences");
    }

    private void initSession(String gameFileId, String title) {
        // Step 1: Basic Options
        View basicView = LayoutInflater.from(this).inflate(R.layout.dialog_lobby_options, null);
        Spinner spinnerLang = basicView.findViewById(R.id.spinner_language);
        Spinner spinnerReg = basicView.findViewById(R.id.spinner_region);
        Spinner spinnerFps = basicView.findViewById(R.id.spinner_framerate);
        Spinner spinnerRes = basicView.findViewById(R.id.spinner_resolution);
        Spinner spinnerBitrate = basicView.findViewById(R.id.spinner_bitrate);
        basicView.findViewById(R.id.vlc_options_container).setVisibility(View.GONE);

        Integer[] langOptions = new Integer[]{0, 1, 2, 3};
        Integer[] regOptions = new Integer[]{0, 1, 2, 3};
        Integer[] fpsOptions = new Integer[]{30, 60};
        Integer[] resOptions = new Integer[]{0, 1, 2};
        Integer[] bitOptions = new Integer[]{5000, 10000, 15000, 25000, 30000};

        spinnerLang.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, langOptions));
        spinnerReg.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, regOptions));
        spinnerFps.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fpsOptions));
        spinnerRes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resOptions));
        spinnerBitrate.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bitOptions));

        // Restore saved values or use defaults
        setSpinnerValue(spinnerLang, selectedApiOptions.get("language"), 1);
        setSpinnerValue(spinnerReg, selectedApiOptions.get("region"), 0);
        setSpinnerValue(spinnerFps, selectedApiOptions.get("framerate"), 30);
        setSpinnerValue(spinnerRes, selectedApiOptions.get("resolution"), 0);
        setSpinnerValue(spinnerBitrate, selectedApiOptions.get("bitrate"), 15000);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Step 1: Lobby Basic Setup")
                .setView(basicView)
                .setCancelable(false)
                .setPositiveButton("Next", (d, which) -> {
                    selectedApiOptions.put("language", spinnerLang.getSelectedItem());
                    selectedApiOptions.put("region", spinnerReg.getSelectedItem());
                    selectedApiOptions.put("framerate", spinnerFps.getSelectedItem());
                    selectedApiOptions.put("resolution", spinnerRes.getSelectedItem());
                    selectedApiOptions.put("bitrate", spinnerBitrate.getSelectedItem());
                    showAdvancedOptionsDialog(gameFileId, title);
                })
                .setNeutralButton("Skip", (d, which) -> {
                    if (selectedApiOptions.isEmpty()) {
                        // If no saved options, apply defaults from the UI before skipping
                        selectedApiOptions.put("language", spinnerLang.getSelectedItem());
                        selectedApiOptions.put("region", spinnerReg.getSelectedItem());
                        selectedApiOptions.put("framerate", spinnerFps.getSelectedItem());
                        selectedApiOptions.put("resolution", spinnerRes.getSelectedItem());
                        selectedApiOptions.put("bitrate", spinnerBitrate.getSelectedItem());
                    }
                    createLobbyWithRetry(gameFileId, title, 1);
                })
                .setNegativeButton("Gamepad Mapping", (d, which) -> showGamepadMappingDialog())
                .create();

        dialog.setOnShowListener(d -> {
            Button skipButton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
            if (skipButton != null) {
                skipButton.setFocusable(true);
                skipButton.setFocusableInTouchMode(true);
                skipButton.requestFocus();
            }
        });
        dialog.show();
    }

    private void showGamepadMappingDialog() {
        String[] items = new String[25];
        for (int id = 1; id <= 25; id++) {
            String mappingDesc = "Not mapped";

            // Search button maps
            for (Map.Entry<Integer, Integer> entry : gamepadMapper.buttonToIdMap.entrySet()) {
                if (entry.getValue() == id) {
                    mappingDesc = KeyEvent.keyCodeToString(entry.getKey());
                    break;
                }
            }
            // Search axis maps
            for (Map.Entry<String, Integer> entry : gamepadMapper.axisToIdMap.entrySet()) {
                if (entry.getValue() == id) {
                    mappingDesc = entry.getKey();
                    break;
                }
            }

            items[id - 1] = GamepadMapper.getInputName(id) + " -> " + mappingDesc;
        }

        new AlertDialog.Builder(this)
                .setTitle("Gamepad Mapping")
                .setItems(items, (dialog, which) -> {
                    startCapture(which + 1);
                })
                .setPositiveButton("Done", (dialog, which) -> saveCurrentSettings())
                .setNegativeButton("Reset Defaults", (dialog, which) -> {
                    gamepadMapper.resetToDefaults();
                    showGamepadMappingDialog();
                })
                .show();
    }

    private void startCapture(int inputId) {
        learningInputId = inputId;
        isLearningMapping = true;
        mappingCaptureDialog = new AlertDialog.Builder(this)
                .setTitle("Mapping: " + GamepadMapper.getInputName(inputId))
                .setMessage("Press a button or move an axis on your gamepad...")
                .setNegativeButton("Cancel", (dialog, which) -> isLearningMapping = false)
                .create();

        mappingCaptureDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (isLearningMapping && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode != KeyEvent.KEYCODE_BACK) {
                    if ((event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD ||
                            (event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK) {

                        // Clear existing mapping for this ID
                        removeMappingById(learningInputId);
                        gamepadMapper.buttonToIdMap.put(keyCode, learningInputId);

                        isLearningMapping = false;
                        mappingCaptureDialog.dismiss();
                        showGamepadMappingDialog();
                        return true;
                    }
                }
            }
            return false;
        });

        mappingCaptureDialog.show();

        if (mappingCaptureDialog.getWindow() != null) {
            mappingCaptureDialog.getWindow().getDecorView().setOnGenericMotionListener((v, event) -> {
                if (isLearningMapping) {
                    for (int i : GAMEPAD_AXES) {
                        float val = event.getAxisValue(i);
                        if (Math.abs(val) > 0.5f) {
                            if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK ||
                                    (event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {

                                removeMappingById(learningInputId);
                                String axisKey = i + ":" + (val > 0 ? "1" : "-1");
                                gamepadMapper.axisToIdMap.put(axisKey, learningInputId);

                                isLearningMapping = false;
                                mappingCaptureDialog.dismiss();
                                showGamepadMappingDialog();
                                return true;
                            }
                        }
                    }
                }
                return false;
            });
        }
    }

    private void removeMappingById(int id) {
        gamepadMapper.buttonToIdMap.values().removeIf(v -> v == id);
        gamepadMapper.axisToIdMap.values().removeIf(v -> v == id);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    private void setSpinnerValue(Spinner spinner, Object value, Object defaultValue) {
        Object finalValue = value != null ? value : defaultValue;
        if (finalValue instanceof Double) finalValue = ((Double) finalValue).intValue();
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(finalValue.toString())) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void showAdvancedOptionsDialog(String gameFileId, String title) {
        View advancedView = LayoutInflater.from(this).inflate(R.layout.dialog_lobby_options, null);
        advancedView.findViewById(R.id.spinner_language).setVisibility(View.GONE);
        advancedView.findViewById(R.id.spinner_region).setVisibility(View.GONE);
        advancedView.findViewById(R.id.spinner_framerate).setVisibility(View.GONE);
        advancedView.findViewById(R.id.spinner_resolution).setVisibility(View.GONE);
        advancedView.findViewById(R.id.spinner_bitrate).setVisibility(View.GONE);

        LinearLayout container = advancedView.findViewById(R.id.vlc_options_container);
        container.removeAllViews();

        TextView apiHeader = new TextView(this);
        apiHeader.setText("--- API Advanced Parameters ---");
        apiHeader.setTypeface(null, android.graphics.Typeface.BOLD);
        container.addView(apiHeader);

        String[] apiBaseOptions = {"description=io", "password=tyu", "slots=1", "locked=1", "live=0", "instantiated=0", "cloud=1", "master_lobby_id=0"};
        for (String opt : apiBaseOptions) addOptionRow(container, opt, "api_");

        TextView vlcHeader = new TextView(this);
        vlcHeader.setText("\n--- VLC Performance Options ---");
        vlcHeader.setTypeface(null, android.graphics.Typeface.BOLD);
        container.addView(vlcHeader);

        String[] vlcBaseOptions = {":sout-avcodec-noise-reduction=3",":sout-avcodec-hurry-up",":avcodec-threads=2",":avcodec-skiploopfilter=0",":avcodec-fast",":netsync-master",":no-gnutls-system-trust",":gl=wgl",":yuv-yuv4mpeg2",":network-synchronisation",":clock-synchro=0",":network-caching=0", ":live-caching=300", ":clock-jitter=0",":no-quiet-synchro", ":rtsp-tcp", ":drop-late-frames", ":no-skip-frames",":rtsp-frame-buffer-size=10000", ":avcodec-hw=any"};
        for (String opt : vlcBaseOptions) addOptionRow(container, opt, "vlc_");

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Step 2: Advanced Tuning")
                .setView(advancedView)
                .setCancelable(false)
                .setPositiveButton("Start Game", (d, which) -> {
                    collectOptionsFromContainer(container);
                    saveCurrentSettings();
                    createLobbyWithRetry(gameFileId, title, 1);
                })
                .setNeutralButton("Skip", (d, which) -> {
                    createLobbyWithRetry(gameFileId, title, 1);
                })
                .setNegativeButton("Back", (d, which) -> initSession(gameFileId, title))
                .create();

        dialog.setOnShowListener(d -> {
            Button skipButton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
            if (skipButton != null) {
                skipButton.setFocusable(true);
                skipButton.setFocusableInTouchMode(true);
                skipButton.requestFocus();
            }
        });
        dialog.show();
    }

    private void collectOptionsFromContainer(LinearLayout container) {
        selectedVlcOptions.clear();
        for (int i = 0; i < container.getChildCount(); i++) {
            View row = container.getChildAt(i);
            CheckBox cb = row.findViewWithTag("check");
            if (cb != null && cb.isChecked()) {
                TextView tvKey = row.findViewWithTag("key");
                EditText etVal = row.findViewWithTag("val");
                String prefix = (String) row.getTag();
                String finalKey = tvKey.getText().toString();
                String finalVal = etVal != null ? etVal.getText().toString() : null;

                if ("api_".equals(prefix)) {
                    if (finalVal != null) {
                        try { selectedApiOptions.put(finalKey, Integer.parseInt(finalVal)); }
                        catch (NumberFormatException e) { selectedApiOptions.put(finalKey, finalVal); }
                    } else { selectedApiOptions.put(finalKey, 1); }
                } else if ("vlc_".equals(prefix)) {
                    selectedVlcOptions.add(finalVal != null ? finalKey + "=" + finalVal : finalKey);
                }
            }
        }
    }

    private void addOptionRow(LinearLayout container, String option, String typePrefix) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 4, 0, 4);
        row.setTag(typePrefix);

        CheckBox cb = new CheckBox(this);
        cb.setTag("check");

        TextView tvKey = new TextView(this);
        tvKey.setTag("key");

        String key, val = null;
        if (option.contains("=")) {
            String[] parts = option.split("=", 2);
            key = parts[0];
            val = parts[1];
        } else {
            key = option;
        }
        tvKey.setText(key);

        // Check if we have a saved value for this key
        boolean isVlc = "vlc_".equals(typePrefix);
        boolean enabled = false;
        if (isVlc) {
            for (String saved : selectedVlcOptions) {
                if (saved.startsWith(key)) {
                    enabled = true;
                    if (saved.contains("=")) val = saved.split("=", 2)[1];
                    break;
                }
            }
        } else {
            Object saved = selectedApiOptions.get(key);
            if (saved != null) {
                enabled = true;
                val = String.valueOf(saved);
                if (saved instanceof Double) val = String.valueOf(((Double) saved).intValue());
            }
        }

        // If not found in saved, use the default from the 'option' string
        cb.setChecked(enabled || (selectedVlcOptions.isEmpty() && selectedApiOptions.size() <= 5)); // Heuristic for first run

        if (val != null) {
            EditText etVal = new EditText(this);
            etVal.setTag("val");
            etVal.setText(val);
            etVal.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            row.addView(cb);
            row.addView(tvKey);
            row.addView(etVal);
        } else {
            tvKey.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            row.addView(cb);
            row.addView(tvKey);
        }
        container.addView(row);
    }

    private void createLobbyWithRetry(String gameFileId, String title, int attempt) {
        Log.d("GameActivity", "Calling createLobby... attempt " + attempt + "/" + MAX_CREATE_LOBBY_ATTEMPTS);
        api.createLobby(gameFileId, title, selectedApiOptions, new RequestNetwork.RequestListener() {
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
                                    () -> checkAndStartStream(streamUri),
                                    2000
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

    private static final int MAX_RETRIES = 18; // 18 attempts * 5s = 90 seconds max wait
    private static final int RETRY_DELAY_MS = 5000;
    private int retryAttempts = 0;

    private void checkAndStartStream(String streamUri) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            boolean isReady = false;

            while (retryAttempts < MAX_RETRIES && !isReady) {
                retryAttempts++;
                Log.d("GameActivity", "Pinging RTSP stream... Attempt " + retryAttempts + "/" + MAX_RETRIES);

                if (pingRtspUri(streamUri)) {
                    isReady = true;
                    Log.d("GameActivity", "RTSP stream active and returning 200 OK!");
                } else {
                    Log.d("GameActivity", "Stream not ready yet (404/Offline). Waiting " + (RETRY_DELAY_MS / 1000) + "s...");
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            final boolean readyResult = isReady;
            mainHandler.post(() -> {
                if (readyResult) {
                    setupStream(streamUri);
                    //mediaPlayer.play();
                } else {
                    Log.e("GameActivity", "RTSP stream failed to initialize after maximum retries.");
                }
            });
        });
    }

    private boolean pingRtspUri(String uriString) {
        try {
            URI uri = new URI(uriString);
            String host = uri.getHost();
            int port = (uri.getPort() == -1) ? 8554 : uri.getPort();

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), 3000);
                socket.setSoTimeout(3000);

                OutputStream out = socket.getOutputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Send RTSP DESCRIBE request to verify path status
                String request = "DESCRIBE " + uriString + " RTSP/1.0\r\n" +
                        "CSeq: 1\r\n" +
                        "User-Agent: LibVLC/3.0\r\n\r\n";

                out.write(request.getBytes(StandardCharsets.UTF_8));
                out.flush();

                String responseLine = in.readLine();
                return responseLine != null && responseLine.contains("200");
            }
        } catch (Exception e) {
            Log.w("GameActivity", "RTSP ping attempt failed: " + e.getMessage());
            return false;
        }
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



    private void setupStream(String rtspUri) {
        Log.d("GameActivity", "=================================");
        Log.d("GameActivity", "RTSP START (VLC)");
        Log.d("GameActivity", "URI = " + rtspUri);
        for (String opt : selectedVlcOptions) {
            Log.d("GameActivity", "VLC Option: " + opt);
        }
        Log.d("GameActivity", "=================================");
        mediaPlayer.setAspectRatio("16/9");
        mediaPlayer.setVideoScale(MediaPlayer.ScaleType.SURFACE_FILL);
        mediaPlayer.attachViews(vlcVideoLayout, null, false, false);

        Media media = new Media(libVLC, Uri.parse(rtspUri));

        for (String opt : selectedVlcOptions) {
            media.addOption(opt);
        }
        media.addOption(":rtsp-host="+ Uri.parse(rtspUri).getHost());
        media.addOption(":rtsp-port="+ Uri.parse(rtspUri).getPort());
        media.addOption(":fullscreen");
        media.addOption(":no-keyboard-events");
        media.addOption(":no-mouse-events");
        media.addOption("no-disable-screensaver");
        media.addOption(":high-priority");
        media.addOption(":sout-display-delay=0");
        media.addOption(":gnutls-priorities=PERFORMANCE");
        ///--gnutls-priorities={PERFORMANCE,NORMAL,SECURE128,SECURE256}

        //media.addOption(":gl=wgl");
        /// -V, --vout={any,direct3d11,direct3d9,glwin32,gl,directdraw,wingdi,caca,vdummy,vmem,flaschen,yuv,vdummy,none}
        ///      --rate=<float>             Playback speed
        ///--mtu=<integer>            MTU of the network interface
        /// --rtsp-host=<string>       RTSP server address
        ///       --rtsp-port=<integer [1..65535]>
        ///      --codec=<string>           Preferred decoders list
        media.setHWDecoderEnabled(true, true);
        mediaPlayer.setMedia(media);
        media.release();

        Log.d("GameActivity", "Calling play...");
        mediaPlayer.play();
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
        if (handleBinding(keyCode, true)) {
            sendInput();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (handleBinding(keyCode, false)) {
            sendInput();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private boolean handleBinding(int keyCode, boolean down) {
        Integer id = gamepadMapper.buttonToIdMap.get(keyCode);
        if (id != null) {
            updateStateFromId(id, down ? (byte) 127 : 0);
            return true;
        }
        return false;
    }

    private void updateStateFromId(int id, byte value) {
        if (id <= 8) { // Byte 5
            setBit(5, id - 1, value > 0);
        } else if (id <= 15) { // Byte 6
            setBit(6, id - 9, value > 0);
        } else {
            switch (id) {
                case 16: lx = (byte)-value; break; // Left Stick Left
                case 17: lx = value; break;        // Left Stick Right
                case 18: ly = (byte)-value; break; // Left Stick Up
                case 19: ly = value; break;        // Left Stick Down
                case 20: l2 = value; break;        // L2
                case 21: rx = (byte)-value; break; // Right Stick Left
                case 22: rx = value; break;        // Right Stick Right
                case 23: ry = (byte)-value; break; // Right Stick Up
                case 24: ry = value; break;        // Right Stick Down
                case 25: r2 = value; break;        // R2
            }
        }
    }

    private boolean updateButtons(int keyCode, boolean down) {
        return false; // Deprecated by mapper
    }

    private void setBit(int byteNum, int bit, boolean val) {
        if (byteNum == 5) {
            if (val) b1 |= (1 << bit); else b1 &= ~(1 << bit);
        } else {
            if (val) b2 |= (1 << bit); else b2 &= ~(1 << bit);
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if (isLearningMapping) {
            for (int i : GAMEPAD_AXES) {
                float val = event.getAxisValue(i);
                if (Math.abs(val) > 0.5f) {
                    if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK ||
                            (event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {

                        removeMappingById(learningInputId);
                        String axisKey = i + ":" + (val > 0 ? "1" : "-1");
                        gamepadMapper.axisToIdMap.put(axisKey, learningInputId);

                        isLearningMapping = false;
                        if (mappingCaptureDialog != null) mappingCaptureDialog.dismiss();
                        showGamepadMappingDialog();
                        return true;
                    }
                }
            }
        }

        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK ||
                (event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {

                for (int i : GAMEPAD_AXES) {
                    float val = event.getAxisValue(i);

                    Integer idPos = gamepadMapper.axisToIdMap.get(i + ":1");
                    Integer idNeg = gamepadMapper.axisToIdMap.get(i + ":-1");

                    // idPos and idNeg both ultimately write the same underlying byte
                    // (lx/ly/rx/ry/etc). They must be applied so whichever direction is
                    // actually active is written LAST, otherwise the "resting" direction's
                    // zero value always overwrites the active one. Clear the inactive side
                    // first, then apply the active side.
                    if (val >= 0) {
                        if (idNeg != null) updateStateFromId(idNeg, (byte) 0);
                        if (idPos != null) {
                            byte out = (idPos <= 15) ? (val > 0.5f ? (byte) 127 : 0) : (byte) (val * 127);
                            updateStateFromId(idPos, out);
                        }
                    } else {
                        if (idPos != null) updateStateFromId(idPos, (byte) 0);
                        if (idNeg != null) {
                            byte out = (idNeg <= 15) ? (val < -0.5f ? (byte) 127 : 0) : (byte) (-val * 127);
                            updateStateFromId(idNeg, out);
                        }
                    }
                }
                sendInput();
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
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (libVLC != null) {
            libVLC.release();
        }
        if (udpController != null) {
            udpController.close();
        }
    }

}