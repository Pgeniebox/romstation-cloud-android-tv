package com.world.cloudxsolution.romstation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.webkit.CookieManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;

public class RomStationApi {

    private final Activity activity;
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    private static String memberId = "0";
    private static String sessionKey = null;
    private static String phpsessid = null;
    private static int softId = 0;

    private static final String VERSION = "229";
    private static final String OS = "1";
    private static final String ARCH = "2";

    public RomStationApi(Activity activity) {
        this.activity = activity;
    }

    private String getUrlWithVersion(String baseUrl) {
        return baseUrl + "?v=" + VERSION + "&os=" + OS + "&arch=" + ARCH;
    }

    public void setSession(String memberIds, String sessionKey) {
        Log.d("RomStationApi", "Session set: memberId=" + memberIds + ", sessionKey=" + sessionKey);
        RomStationApi.memberId = memberIds;
        RomStationApi.sessionKey = sessionKey;
    }

    public void setLoginContext(String phpsessid, int memberID) {
        RomStationApi.phpsessid = phpsessid;
        RomStationApi.memberId = String.valueOf(memberID);
    }

    public String getMemberId() {
        return memberId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public boolean hasSoftwareId() {
        return softId > 0;
    }

    public void setSoftwareId(int softId) {
        RomStationApi.softId = softId;
        Log.d("RomStationApi", "Software ID set: " + softId);
    }

    private void setRomStationHeaders(RequestNetwork request) {
        String cookies = CookieManager.getInstance().getCookie("https://www.romstation.fr/");
        if (cookies != null && !cookies.isEmpty()) {
            HashMap<String, Object> headers = new HashMap<>();
            headers.put("Cookie", cookies);
            request.setHeaders(headers);
            Log.d("RomStationApi", "Using Cookie header for RomStation request");
        }
    }

    private String buildAuthJson() {
        HashMap<String, Object> authJson = new HashMap<>();
        authJson.put("soft_id", softId);
        authJson.put("soft_raw_uid", getAndroidId());
        authJson.put("soft_uid", Utils.md5(getAndroidId()));
        authJson.put("phpsessid", phpsessid);
        authJson.put("member_id", Integer.parseInt(memberId));
        authJson.put("member_session", sessionKey);
        return gson.toJson(authJson);
    }

    private String buildDeviceProfileJson() {
        HashMap<String, Object> profile = new HashMap<>();
        profile.put("manufacturer", Build.MANUFACTURER);
        profile.put("brand", Build.BRAND);
        profile.put("model", Build.MODEL);
        profile.put("device", Build.DEVICE);
        profile.put("product", Build.PRODUCT);
        profile.put("android", Build.VERSION.RELEASE);
        profile.put("sdk", Build.VERSION.SDK_INT);
        profile.put("arch", System.getProperty("os.arch"));
        return gson.toJson(profile);
    }

    @SuppressLint("HardwareIds")
    public String getAndroidId() {
        return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public void startSoftware(RequestNetwork.RequestListener listener) {
        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        request.setParams(new HashMap<>(), RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.GET, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/soft/start.php"), "soft_start", listener);
    }

    public void updateSoftware(RequestNetwork.RequestListener listener) {
        String auth = buildAuthJson();
        String authEncrypted = SecurityUtils.encrypt(auth);
        String cpiEncrypted = SecurityUtils.encrypt(buildDeviceProfileJson());
        Log.d("RomStationApi", "Soft Update Auth JSON: " + auth);

        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put("auth", authEncrypted);
        params.put("cpi", cpiEncrypted);

        request.setParams(params, RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.POST, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/soft/update.php"), "soft_update", listener);
    }

    public void login(String phpsessid, int memberID,RequestNetwork.RequestListener listener) {
        setLoginContext(phpsessid, memberID);

        String auth = buildAuthJson();
        String authEncrypted = SecurityUtils.encrypt(auth);
        Log.d("RomStationApi", "Login Auth JSON: " + auth);

        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put("auth", authEncrypted);

        request.setParams(params, RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.POST, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/account/login.php"), "login", listener);
    }

    public void getGameInfo(String gameId, RequestNetwork.RequestListener listener) {
        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put("gid", gameId);

        request.setParams(params, RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.GET, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/game/get_infos.php"), "get_infos", listener);
    }

    public void createLobby(String gameFileId, String title, HashMap<String, Object> dynamicParams, RequestNetwork.RequestListener listener) {
        String auth = buildAuthJson();
        String authEncrypted = SecurityUtils.encrypt(auth);
        Log.d("RomStationApi", "Create Lobby Auth JSON: " + auth);

        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        
        HashMap<String, Object> params = new HashMap<>();
        params.put("auth", authEncrypted);
        params.put("title", title);
        params.put("game_file_id", gameFileId);
        
        // Add all dynamic parameters from UI
        if (dynamicParams != null) {
            params.putAll(dynamicParams);
        }

        request.setParams(params, RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.POST, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/multiplayer/create_lobby.php"), "create_lobby", listener);
    }

    public void getCredential(String lobbyId, String password, RequestNetwork.RequestListener listener) {
        String auth = buildAuthJson();
        String authEncrypted = SecurityUtils.encrypt(auth);
        Log.d("RomStationApi", "Get Credential Auth JSON: " + auth);

        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put("auth", authEncrypted);
        params.put("lobby_id", lobbyId);
        params.put("password", password);

        request.setParams(params, RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.POST, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/multiplayer/get_credential.php"), "get_credential", listener);
    }

    public void joinLobby(String lobbyId, String credentialId, RequestNetwork.RequestListener listener) {
        String auth = buildAuthJson();
        String authEncrypted = SecurityUtils.encrypt(auth);
        Log.d("RomStationApi", "Join Lobby Auth JSON: " + auth);

        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put("auth", authEncrypted);
        params.put("lobby_id", lobbyId);
        params.put("credential_id", credentialId);

        request.setParams(params, RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.POST, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/multiplayer/join_lobby.php"), "join_lobby", listener);
    }

    public void updateLobby(String lobbyId, String credentialId, long lastUpdate, RequestNetwork.RequestListener listener) {
        String auth = buildAuthJson();
        String authEncrypted = SecurityUtils.encrypt(auth);

        RequestNetwork request = new RequestNetwork(activity);
        setRomStationHeaders(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put("auth", authEncrypted);
        params.put("lobby_id", lobbyId);
        params.put("credential_id", credentialId);
        params.put("polling", 1);
        params.put("last_update", lastUpdate);

        request.setParams(params, RequestNetworkController.REQUEST_PARAM);
        request.startRequestNetwork(RequestNetworkController.POST, getUrlWithVersion("https://www.romstation.fr/romstation/scripts/multiplayer/update.php"), "update_lobby", listener);
    }
}
