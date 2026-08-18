package org.romstation.application.netplay;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.StreamSupport;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.romstation.application.C0004E;
import org.romstation.application.C0010K;
import org.romstation.application.C0011L;
import org.romstation.application.C0060ag;
import org.romstation.application.C0067an;
import org.romstation.application.C0072ar;
import org.romstation.application.C0074at;
import org.romstation.application.C0076av;
import org.romstation.application.C0081b;
import org.romstation.application.C0088bG;
import org.romstation.application.C0114bg;
import org.romstation.application.C0117bj;
import org.romstation.application.C0119bl;
import org.romstation.application.C0120bm;
import org.romstation.application.C0153cS;
import org.romstation.application.C0155cU;
import org.romstation.application.EnumC0002C;
import org.romstation.application.EnumC0003D;
import org.romstation.application.EnumC0129bv;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.task.C0232C;
import org.romstation.application.task.C0236d;
import org.romstation.application.task.C0237e;
import org.romstation.application.task.C0240h;
import org.romstation.application.task.C0241i;
import org.romstation.application.task.C0247o;
import org.romstation.application.task.C0248p;
import org.romstation.application.task.C0253u;
import org.romstation.application.task.C0255w;
import org.romstation.application.task.EmulatorFileDownloadContextException;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ServerErrorAlert;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.vpn.C0275a;
import org.romstation.application.vpn.tap.C0279a;
import org.romstation.application.vpn.tap.TAPDriverInstallException;

/* JADX INFO: renamed from: org.romstation.application.netplay.b */
/* JADX INFO: compiled from: NetPlayManager.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/netplay/b.class */
public class C0214b {

    /* JADX INFO: renamed from: a */
    private static final ReadOnlyBooleanWrapper f562a = new ReadOnlyBooleanWrapper();

    /* JADX INFO: renamed from: b */
    private static final ReadOnlyStringWrapper f563b = new ReadOnlyStringWrapper();

    /* JADX INFO: renamed from: a */
    public static ReadOnlyBooleanProperty m849a() {
        return f562a.getReadOnlyProperty();
    }

    /* JADX INFO: renamed from: b */
    public static boolean m850b() {
        return f562a.get();
    }

    /* JADX INFO: renamed from: c */
    public static ReadOnlyStringWrapper m851c() {
        return f563b;
    }

    /* JADX INFO: renamed from: d */
    public static String m852d() {
        return f563b.get();
    }

    /* JADX INFO: renamed from: a */
    private static Optional<C0219d> m853a(EnumC0129bv type) {
        return m855a(0, (String) null, type, 0);
    }

    /* JADX INFO: renamed from: a */
    private static Optional<C0219d> m854a(GameFile gameFile, EnumC0129bv type) {
        return m855a(gameFile.getRid().intValue(), gameFile.getGame().getTitle(), type, 0);
    }

    /* JADX INFO: renamed from: a */
    private static Optional<C0219d> m855a(int gameFileId, String title, EnumC0129bv type, int masterLobbyID) {
        C0114bg createServerDialog = new C0114bg(gameFileId, title, type, masterLobbyID);
        Optional<C0215c> serverConfig = createServerDialog.showAndWait();
        if (serverConfig.isPresent()) {
            Task c0237e = new C0237e(serverConfig.get());
            C0076av<C0219d> taskDialog = new C0076av<>(c0237e);
            new Thread((Runnable) c0237e).start();
            return taskDialog.showAndWait();
        }
        return Optional.empty();
    }

    /* JADX INFO: renamed from: e */
    public static void m856e() {
        try {
            m895m();
            m894l();
            m853a(EnumC0129bv.MANUAL).ifPresent(serverResponse -> {
                JsonObject lobby = serverResponse.m967b().getAsJsonObject("lobby");
                int lobbyID = lobby.get("id").getAsInt();
                String password = lobby.get("password").isJsonNull() ? null : lobby.get("password").getAsString();
                m871a(lobbyID, password);
            });
        } catch (UnsupportedOperationException e) {
            m897o();
        } catch (AlreadyConnectedException e2) {
            m896n();
        }
    }

    /* JADX INFO: renamed from: f */
    public static void m857f() {
        try {
            m894l();
            C0119bl gameChoiceDialog = new C0119bl(EnumC0129bv.CLOUD);
            gameChoiceDialog.showAndWait().ifPresent(gameId -> {
                try {
                    JsonObject game = m879c(gameId.intValue());
                    m881a(game, true).ifPresent(gameFile -> {
                        m855a(gameFile.get("file_id").getAsInt(), game.get("title").getAsString(), EnumC0129bv.CLOUD, 0).ifPresent(serverResponse -> {
                            JsonObject lobby = serverResponse.m967b().getAsJsonObject("lobby");
                            int lobbyID = lobby.get("id").getAsInt();
                            String password = lobby.get("password").isJsonNull() ? null : lobby.get("password").getAsString();
                            m871a(lobbyID, password);
                        });
                    });
                } catch (MalformedURLException | InvalidServerResponseException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                } catch (NetworkOfflineException exception2) {
                    RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                } catch (ServerResponseException exception3) {
                    RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                    ServerErrorAlert alert = new ServerErrorAlert(exception3);
                    alert.showAndWait();
                }
            });
        } catch (AlreadyConnectedException e) {
            m896n();
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m858a(int gameId, int gameFileId) {
        try {
            m894l();
            try {
                try {
                    try {
                        JsonObject game = m879c(gameId);
                        m882a(game, gameFileId).ifPresent(gameFile -> {
                            m855a(gameFile.get("file_id").getAsInt(), game.get("title").getAsString(), EnumC0129bv.CLOUD, 0).ifPresent(serverResponse -> {
                                JsonObject lobby = serverResponse.m967b().getAsJsonObject("lobby");
                                int lobbyID = lobby.get("id").getAsInt();
                                String password = lobby.get("password").isJsonNull() ? null : lobby.get("password").getAsString();
                                m871a(lobbyID, password);
                            });
                        });
                    } catch (ServerResponseException exception) {
                        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                        ServerErrorAlert alert = new ServerErrorAlert(exception);
                        alert.showAndWait();
                    }
                } catch (NetworkOfflineException exception2) {
                    RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                }
            } catch (MalformedURLException | InvalidServerResponseException exception3) {
                RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
            }
        } catch (AlreadyConnectedException e) {
            m896n();
        }
    }

    /* JADX INFO: renamed from: g */
    public static void m859g() {
        try {
            m895m();
            m894l();
            C0119bl dialog = new C0119bl(EnumC0129bv.AUTOMATIC);
            dialog.showAndWait().ifPresent(gameID -> {
                m885a(gameID.intValue(), (Consumer<GameFile>) C0214b::m860a);
            });
        } catch (UnsupportedOperationException e) {
            m897o();
        } catch (AlreadyConnectedException e2) {
            m896n();
        }
    }

    /* JADX INFO: renamed from: a */
    private static void m860a(GameFile gameFile) {
        try {
            m894l();
            Optional<EmulatorFile> entity = m890h(gameFile.getGame().getSystem().getRid().intValue());
            entity.ifPresent(emulatorFile -> {
                m854a(gameFile, EnumC0129bv.AUTOMATIC).ifPresent(serverResponse -> {
                    JsonObject lobby = serverResponse.m967b().getAsJsonObject("lobby");
                    int lobbyID = lobby.get("id").getAsInt();
                    String password = lobby.get("password").isJsonNull() ? null : lobby.get("password").getAsString();
                    m872a(lobbyID, password, gameFile, emulatorFile);
                });
            });
        } catch (AlreadyConnectedException e) {
            m896n();
        }
    }

    /* JADX INFO: renamed from: h */
    public static void m861h() {
        C0117bj dialog = new C0117bj();
        dialog.showAndWait().ifPresent(id -> {
            Task c0236d = new C0236d(id.intValue());
            C0076av<C0219d> taskDialog = new C0076av<>(c0236d);
            new Thread((Runnable) c0236d).start();
            taskDialog.showAndWait().ifPresent(serverResponse -> {
                RomStationController.f786a.post(new C0153cS(C0217b.m961b() + serverResponse.m967b().getAsJsonObject("server").get("url").getAsString()));
            });
        });
    }

    /* JADX INFO: renamed from: a */
    public static void m862a(int lobbyID) {
        String password;
        try {
            try {
                m894l();
                C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/get_lobby_infos.php");
                urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
                C0222g urlQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("lobby_id", Integer.valueOf(lobbyID)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("arch", Integer.valueOf(C0004E.m11d().m6a()));
                C0216a httpRequest = new C0216a(urlBuilder.m973b());
                C0219d serverResponse = httpRequest.m959a(urlQuery);
                JsonObject lobby = serverResponse.m967b().getAsJsonObject("lobby");
                if (lobby.get("password").getAsBoolean()) {
                    C0074at dialog = new C0074at(RomStation.m44d().getString("netplayServerPasswordAlert.header"));
                    Optional<String> result = dialog.showAndWait();
                    if (!result.isPresent()) {
                        return;
                    }
                    m877c(lobbyID, result.get());
                    password = result.get();
                } else {
                    password = null;
                }
                switch (EnumC0129bv.m627a(lobby.get("type").getAsInt())) {
                    case MANUAL:
                        m895m();
                        m863a(lobby, password);
                        break;
                    case CLOUD:
                        m864b(lobby, password);
                        break;
                    case AUTOMATIC:
                    case AUTOMATIC_VPN:
                        m895m();
                        m865c(lobby, password);
                        break;
                }
            } catch (UnsupportedOperationException e) {
                m897o();
            } catch (AlreadyConnectedException e2) {
                m896n();
            } catch (NetworkOfflineException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            } catch (ServerResponseException exception2) {
                RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
                ServerErrorAlert alert = new ServerErrorAlert(exception2);
                alert.showAndWait();
            }
        } catch (MalformedURLException | InvalidServerResponseException exception3) {
            RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
        }
    }

    /* JADX INFO: renamed from: a */
    private static void m863a(JsonObject lobby, String password) {
        m871a(lobby.get("id").getAsInt(), password);
    }

    /* JADX INFO: renamed from: b */
    private static void m864b(JsonObject lobby, String password) {
        try {
            m894l();
            int lobbyID = lobby.get("id").getAsInt();
            if (lobby.get("linked").getAsBoolean()) {
                m855a(lobby.get("game_file_id").getAsInt(), (String) null, EnumC0129bv.CLOUD, lobbyID).ifPresent(serverResponse -> {
                    m871a(lobbyID, password);
                });
            } else {
                m871a(lobby.get("id").getAsInt(), password);
            }
        } catch (AlreadyConnectedException e) {
            m896n();
        }
    }

    /* JADX INFO: renamed from: c */
    private static void m865c(JsonObject lobby, String password) {
        m886a(lobby.get("game_id").getAsInt(), lobby.get("game_file_id").getAsInt(), (Consumer<GameFile>) gameFile -> {
            m866a(lobby, password, gameFile);
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static void m866a(JsonObject lobby, String password, GameFile gameFile) {
        try {
            m894l();
            Optional<EmulatorFile> entity = m890h(gameFile.getGame().getSystem().getRid().intValue());
            entity.ifPresent(emulatorFile -> {
                m872a(lobby.get("id").getAsInt(), password, gameFile, emulatorFile);
            });
        } catch (AlreadyConnectedException e) {
            m896n();
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m867a(int id, boolean hasPassword) {
        String password;
        try {
            try {
                m894l();
                C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/get_dedicated_infos.php");
                urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
                C0222g urlQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("server_id", Integer.valueOf(id)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("arch", Integer.valueOf(C0004E.m11d().m6a()));
                C0216a httpRequest = new C0216a(urlBuilder.m973b());
                C0219d serverResponse = httpRequest.m959a(urlQuery);
                JsonObject dedicatedServer = serverResponse.m967b().getAsJsonObject("dedicated");
                if (dedicatedServer.get("password").getAsBoolean()) {
                    C0074at dialog = new C0074at(RomStation.m44d().getString("netplayServerPasswordAlert.header"));
                    Optional<String> result = dialog.showAndWait();
                    if (!result.isPresent()) {
                        return;
                    }
                    m878a(id, result.get(), true);
                    password = result.get();
                } else {
                    password = null;
                }
                if (dedicatedServer.get("game_file_id").getAsInt() == 0) {
                    m868d(dedicatedServer, password);
                } else {
                    m895m();
                    String str = password;
                    m886a(dedicatedServer.get("game_id").getAsInt(), dedicatedServer.get("game_file_id").getAsInt(), (Consumer<GameFile>) gameFile -> {
                        m869b(dedicatedServer, str, gameFile);
                    });
                }
            } catch (UnsupportedOperationException e) {
                m897o();
            } catch (AlreadyConnectedException e2) {
                m896n();
            } catch (NetworkOfflineException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            } catch (ServerResponseException exception2) {
                RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
                ServerErrorAlert alert = new ServerErrorAlert(exception2);
                alert.showAndWait();
            }
        } catch (MalformedURLException | InvalidServerResponseException exception3) {
            RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
        }
    }

    /* JADX INFO: renamed from: d */
    private static void m868d(JsonObject dedicatedServer, String password) {
        m870a(dedicatedServer, password, (GameFile) null, (EmulatorFile) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static void m869b(JsonObject dedicatedServer, String password, GameFile gameFile) {
        try {
            m894l();
            Optional<EmulatorFile> entity = m890h(gameFile.getGame().getSystem().getRid().intValue());
            entity.ifPresent(emulatorFile -> {
                m870a(dedicatedServer, password, gameFile, emulatorFile);
            });
        } catch (AlreadyConnectedException e) {
            m896n();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static void m870a(JsonObject dedicatedServer, String password, GameFile gameFile, EmulatorFile emulatorFile) {
        Task c0255w = new C0255w(dedicatedServer.get("id").getAsInt(), password);
        C0076av<C0219d> dialog = new C0076av<>(c0255w);
        new Thread((Runnable) c0255w).start();
        dialog.showAndWait().ifPresent(serverResponse -> {
            JsonObject lobby = serverResponse.m967b().getAsJsonObject("lobby");
            m872a(lobby.get("id").getAsInt(), password, gameFile, emulatorFile);
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static void m871a(int lobbyID, String password) {
        m872a(lobbyID, password, (GameFile) null, (EmulatorFile) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static void m872a(int lobbyID, String password, GameFile gameFile, EmulatorFile emulatorFile) {
        boolean success = false;
        C0213a credential = null;
        try {
            try {
                try {
                    try {
                        credential = m873b(lobbyID, password).orElse(null);
                        if (credential != null) {
                            m875a(lobbyID, credential, gameFile, emulatorFile);
                            success = true;
                        }
                        if (success) {
                            return;
                        }
                        m876b(lobbyID);
                        if (credential == null || credential.m848b() == null || !credential.m848b().m1649e()) {
                            return;
                        }
                        try {
                            credential.m848b().m1650f();
                        } catch (IOException exception) {
                            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                        }
                    } catch (IOException | InvalidServerResponseException exception2) {
                        RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
                        if (success) {
                            return;
                        }
                        m876b(lobbyID);
                        if (credential == null || credential.m848b() == null || !credential.m848b().m1649e()) {
                            return;
                        }
                        try {
                            credential.m848b().m1650f();
                        } catch (IOException exception3) {
                            RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                        }
                    } catch (NetworkOfflineException exception4) {
                        RomStation.m42b().log(Level.WARNING, exception4.getMessage(), (Throwable) exception4);
                        if (success) {
                            return;
                        }
                        m876b(lobbyID);
                        if (credential == null || credential.m848b() == null || !credential.m848b().m1649e()) {
                            return;
                        }
                        try {
                            credential.m848b().m1650f();
                        } catch (IOException exception5) {
                            RomStation.m42b().log(Level.SEVERE, exception5.getMessage(), (Throwable) exception5);
                        }
                    }
                } catch (TAPDriverInstallException exception6) {
                    RomStation.m42b().log(Level.SEVERE, exception6.getMessage(), (Throwable) exception6);
                    Platform.runLater(() -> {
                        ApplicationAlert alert = new ApplicationAlert(RomStation.m44d().getString("tapDeviceInstallErrorAlert.header"), RomStation.m44d().getString("tapDeviceInstallErrorAlert.content"), Alert.AlertType.ERROR);
                        alert.showAndWait();
                    });
                    if (success) {
                        return;
                    }
                    m876b(lobbyID);
                    if (credential == null || credential.m848b() == null || !credential.m848b().m1649e()) {
                        return;
                    }
                    try {
                        credential.m848b().m1650f();
                    } catch (IOException exception7) {
                        RomStation.m42b().log(Level.SEVERE, exception7.getMessage(), (Throwable) exception7);
                    }
                }
            } catch (ServerResponseException exception8) {
                RomStation.m42b().log(Level.SEVERE, exception8.getMessage(), (Throwable) exception8);
                Platform.runLater(() -> {
                    ServerErrorAlert alert = new ServerErrorAlert(exception8);
                    alert.showAndWait();
                });
                if (success) {
                    return;
                }
                m876b(lobbyID);
                if (credential == null || credential.m848b() == null || !credential.m848b().m1649e()) {
                    return;
                }
                try {
                    credential.m848b().m1650f();
                } catch (IOException exception9) {
                    RomStation.m42b().log(Level.SEVERE, exception9.getMessage(), (Throwable) exception9);
                }
            }
        } catch (Throwable th) {
            if (!success) {
                m876b(lobbyID);
                if (credential != null && credential.m848b() != null && credential.m848b().m1649e()) {
                    try {
                        credential.m848b().m1650f();
                    } catch (IOException exception10) {
                        RomStation.m42b().log(Level.SEVERE, exception10.getMessage(), (Throwable) exception10);
                    }
                }
            }
            throw th;
        }
    }

    /* JADX INFO: renamed from: b */
    private static Optional<C0213a> m873b(int lobbyID, String password) throws TAPDriverInstallException, IOException {
        Task c0253u = new C0253u(lobbyID, password);
        C0076av<C0219d> taskDialog = new C0076av<>(c0253u);
        new Thread((Runnable) c0253u).start();
        C0219d serverResponse = (C0219d) taskDialog.showAndWait().orElse(null);
        if (serverResponse != null) {
            JsonObject credential = serverResponse.m967b().getAsJsonObject("credential");
            if (credential.get("vpn").getAsBoolean()) {
                String guid = m891i().orElse(null);
                if (guid == null) {
                    ApplicationAlert alert = new ApplicationAlert(RomStation.m44d().getString("tapDeviceInstallAlert.header"), RomStation.m44d().getString("tapDeviceInstallAlert.content"), Alert.AlertType.CONFIRMATION);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        C0011L device = C0279a.m1653a();
                        guid = C0279a.m1654a(device.m34a());
                        RomStation.m43c().setProperty("tapDevice.id", device.m34a());
                        RomStation.m43c().setProperty("tapDevice.guid", guid);
                    } else {
                        return Optional.empty();
                    }
                }
                Path certPath = Files.createTempFile("romstation-ca", ".crt", new FileAttribute[0]);
                Files.write(certPath, Base64.getDecoder().decode(credential.get("cert").getAsString().getBytes()), new OpenOption[0]);
                Path passPath = Files.createTempFile("romstation-ca-pass", null, new FileAttribute[0]);
                Files.write(passPath, (credential.get("login").getAsString() + "\n" + credential.get("password").getAsString()).getBytes(), new OpenOption[0]);
                Task c0232c = new C0232C(certPath, passPath, guid);
                C0076av<C0275a> dialog = new C0076av<>(c0232c);
                new Thread((Runnable) c0232c).start();
                Optional<C0275a> result2 = dialog.showAndWait();
                if (result2.isPresent()) {
                    if (!m892j()) {
                        m893k();
                    }
                    return Optional.of(new C0213a(credential.get("id").getAsInt(), result2.get()));
                }
                return Optional.empty();
            }
            return Optional.of(new C0213a(credential.get("id").getAsInt()));
        }
        return Optional.empty();
    }

    /* JADX INFO: renamed from: a */
    private static void m874a(int lobbyID, C0213a credential) throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        m875a(lobbyID, credential, (GameFile) null, (EmulatorFile) null);
    }

    /* JADX INFO: renamed from: a */
    private static void m875a(int lobbyID, C0213a credential, GameFile gameFile, EmulatorFile emulatorFile) throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/join_lobby.php");
        urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g urlQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("lobby_id", Integer.valueOf(lobbyID)).m974a("credential_id", Integer.valueOf(credential.m847a()));
        if (credential.m848b() != null) {
            urlQuery.m974a("ip_vpn", credential.m848b().m1643a());
        }
        if (gameFile != null) {
            urlQuery.m974a("gfid", gameFile.getRid());
        }
        if (emulatorFile != null) {
            urlQuery.m974a("efid", emulatorFile.getRid());
        }
        C0216a httpRequest = new C0216a(urlBuilder.m973b());
        C0219d serverResponse = httpRequest.m959a(urlQuery);
        if (serverResponse.m967b().has("redirect_url")) {
            RomStationController.f786a.post(new C0153cS(C0217b.m961b() + serverResponse.m967b().get("redirect_url").getAsString()));
        }
        JsonObject lobby = serverResponse.m967b().getAsJsonObject("lobby");
        f563b.set(lobby.get("host_ip").getAsString());
        C0088bG dialog = new C0088bG(credential, lobby);
        dialog.initModality(Modality.NONE);
        dialog.showingProperty().addListener((observableValue, previousValue, value) -> {
            f562a.set(value.booleanValue());
        });
        dialog.show();
    }

    /* JADX INFO: renamed from: b */
    private static void m876b(int lobbyID) {
        try {
            C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/quit.php");
            urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
            C0222g urlQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("lobby_id", Integer.valueOf(lobbyID));
            C0216a httpRequest = new C0216a(urlBuilder.m973b());
            httpRequest.m959a(urlQuery);
        } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        } catch (NetworkOfflineException exception2) {
            RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
        }
    }

    /* JADX INFO: renamed from: c */
    private static void m877c(int lobbyID, String password) throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        m878a(lobbyID, password, false);
    }

    /* JADX INFO: renamed from: a */
    private static void m878a(int lobbyID, String password, boolean isDedicatedServer) throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/check_password.php");
        urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g postQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("id", Integer.valueOf(lobbyID)).m974a("password", password).m974a("dedicated_server", Integer.valueOf(isDedicatedServer ? 1 : 0));
        C0216a httpRequest = new C0216a(urlBuilder.m973b());
        httpRequest.m959a(postQuery);
    }

    /* JADX INFO: renamed from: c */
    private static JsonObject m879c(int gameID) throws ServerResponseException, MalformedURLException, NetworkOfflineException, InvalidServerResponseException {
        C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/get_infos.php");
        urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("gid", Integer.valueOf(gameID));
        C0216a request = new C0216a(urlBuilder.m973b());
        return request.m958b().m967b().getAsJsonObject("game");
    }

    /* JADX INFO: renamed from: a */
    private static Optional<JsonObject> m880a(JsonObject game) {
        return m881a(game, false);
    }

    /* JADX INFO: renamed from: a */
    private static Optional<JsonObject> m881a(JsonObject game, boolean cloud) {
        JsonObject[] files = (JsonObject[]) StreamSupport.stream(game.getAsJsonArray("files").spliterator(), false).map((v0) -> {
            return v0.getAsJsonObject();
        }).filter(jsonObject -> {
            return jsonObject.get("status").getAsInt() == 1;
        }).filter(jsonObject2 -> {
            if (cloud) {
                return jsonObject2.get("cloud").getAsInt() == 1 && jsonObject2.get("cloud_state").getAsInt() == 4;
            }
            return C0004E.m12e() != EnumC0002C.X86 || jsonObject2.get("x64_only").getAsInt() == 0;
        }).toArray(x$0 -> {
            return new JsonObject[x$0];
        });
        switch (files.length) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(files[0]);
            default:
                return new C0120bm(files).showAndWait();
        }
    }

    /* JADX INFO: renamed from: a */
    private static Optional<JsonObject> m882a(JsonObject game, int gameFileID) {
        switch (game.getAsJsonArray("files").size()) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(game.getAsJsonArray("files").get(0).getAsJsonObject());
            default:
                return StreamSupport.stream(game.getAsJsonArray("files").spliterator(), false).map((v0) -> {
                    return v0.getAsJsonObject();
                }).filter(object -> {
                    return object.get("file_id").getAsInt() == gameFileID;
                }).findAny();
        }
    }

    /* JADX INFO: renamed from: d */
    private static GameFile m883d(int gameFileID) {
        EntityManager entityManager = C0081b.m309c();
        try {
            return (GameFile) entityManager.createNamedQuery(GameFile.f461a, GameFile.class).setParameter("rid", Integer.valueOf(gameFileID)).getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Runnable, org.romstation.application.task.p] */
    /* JADX INFO: renamed from: a */
    public static void m884a(JsonObject game, JsonObject gameFile, Consumer<GameFile> onFinish) {
        if (C0155cU.m664a().isEmpty()) {
            ?? c0248p = new C0248p(new C0247o(game, gameFile));
            C0072ar dialog = new C0072ar(c0248p);
            dialog.resultProperty().addListener((observableValue, previousValue, value) -> {
                onFinish.accept(value);
            });
            dialog.m277a(true);
            dialog.initModality(Modality.NONE);
            new Thread((Runnable) c0248p).start();
            dialog.show();
            return;
        }
        C0155cU.m665b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static void m885a(int gameID, Consumer<GameFile> consumer) {
        try {
            JsonObject game = m879c(gameID);
            m880a(game).ifPresent(gameFile -> {
                try {
                    consumer.accept(m883d(gameFile.get("file_id").getAsInt()));
                } catch (NoResultException e) {
                    m884a(game, gameFile, (Consumer<GameFile>) consumer);
                }
            });
        } catch (MalformedURLException | InvalidServerResponseException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        } catch (NetworkOfflineException exception2) {
            RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
        } catch (ServerResponseException exception3) {
            RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
            ServerErrorAlert alert = new ServerErrorAlert(exception3);
            alert.showAndWait();
        }
    }

    /* JADX INFO: renamed from: a */
    private static void m886a(int gameID, int gameFileID, Consumer<GameFile> consumer) {
        try {
            consumer.accept(m883d(gameFileID));
        } catch (NoResultException e) {
            try {
                JsonObject game = m879c(gameID);
                m882a(game, gameFileID).ifPresent(gameFile -> {
                    m884a(game, gameFile, (Consumer<GameFile>) consumer);
                });
            } catch (MalformedURLException | InvalidServerResponseException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            } catch (NetworkOfflineException exception2) {
                RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
            } catch (ServerResponseException exception3) {
                RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                ServerErrorAlert alert = new ServerErrorAlert(exception3);
                alert.showAndWait();
            }
        }
    }

    /* JADX INFO: renamed from: e */
    private static int m887e(int systemID) throws ServerResponseException, MalformedURLException, NetworkOfflineException, InvalidServerResponseException {
        C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/get_emulator.php");
        urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g postQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("arch", Integer.valueOf(C0004E.m11d().m6a())).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("console_id", Integer.valueOf(systemID));
        C0216a httpRequest = new C0216a(urlBuilder.m973b());
        return httpRequest.m959a(postQuery).m967b().get("emulator_file_id").getAsInt();
    }

    /* JADX INFO: renamed from: f */
    private static EmulatorFile m888f(int emulatorFileID) {
        EntityManager entityManager = C0081b.m309c();
        try {
            return (EmulatorFile) entityManager.createNamedQuery(EmulatorFile.f423a, EmulatorFile.class).setParameter("rid", Integer.valueOf(emulatorFileID)).getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Runnable, org.romstation.application.task.i] */
    /* JADX INFO: renamed from: g */
    private static Optional<EmulatorFile> m889g(int id) throws NetworkOfflineException, EmulatorFileDownloadContextException {
        ?? c0241i = new C0241i(new C0240h(id));
        C0067an dialog = new C0067an(c0241i);
        new Thread((Runnable) c0241i).start();
        return dialog.showAndWait();
    }

    /* JADX INFO: renamed from: h */
    private static Optional<EmulatorFile> m890h(int systemID) {
        try {
            try {
                int emulatorFileID = m887e(systemID);
                try {
                    return Optional.of(m888f(emulatorFileID));
                } catch (NoResultException e) {
                    return m889g(emulatorFileID);
                }
            } catch (NetworkOfflineException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                return Optional.empty();
            } catch (ServerResponseException exception2) {
                RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
                ServerErrorAlert alert = new ServerErrorAlert(exception2);
                alert.showAndWait();
                return Optional.empty();
            }
        } catch (MalformedURLException | InvalidServerResponseException | EmulatorFileDownloadContextException exception3) {
            RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
            return Optional.empty();
        }
    }

    /* JADX INFO: renamed from: i */
    private static Optional<String> m891i() {
        String tapDeviceID = RomStation.m43c().getProperty("tapDevice.id");
        String tapDeviceGUID = RomStation.m43c().getProperty("tapDevice.guid");
        if (tapDeviceGUID != null && tapDeviceID != null && tapDeviceID.startsWith("ROOT\\NET\\")) {
            List<C0011L> devices = C0010K.m31a("@" + tapDeviceID);
            if (!devices.isEmpty()) {
                return Optional.of(tapDeviceGUID);
            }
        }
        return Optional.empty();
    }

    /* JADX INFO: renamed from: j */
    private static boolean m892j() {
        ProcessBuilder processBuilder = new ProcessBuilder("openvpn/check_firewall_rules.bat");
        processBuilder.inheritIO();
        try {
            return processBuilder.start().waitFor() == 0;
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return false;
        }
    }

    /* JADX INFO: renamed from: k */
    private static void m893k() {
        ProcessBuilder processBuilder = new ProcessBuilder("elevate.exe", "-c", "-w", Paths.get("openvpn", "add_firewall_rules.bat").toAbsolutePath().toString());
        processBuilder.inheritIO();
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: l */
    private static void m894l() throws AlreadyConnectedException {
        if (m850b()) {
            throw new AlreadyConnectedException();
        }
    }

    /* JADX INFO: renamed from: m */
    private static void m895m() throws UnsupportedOperationException {
        if (C0004E.m10c() != EnumC0003D.WINDOWS) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: renamed from: n */
    private static void m896n() {
        ResourceBundle i18n = RomStation.m44d();
        ApplicationAlert alert = new ApplicationAlert(i18n.getString("netplayAlreadyConnectedAlert.header"), i18n.getString("netplayAlreadyConnectedAlert.content"), Alert.AlertType.ERROR);
        alert.showAndWait();
    }

    /* JADX INFO: renamed from: o */
    private static void m897o() {
        ResourceBundle i18n = RomStation.m44d();
        ApplicationAlert alert = new ApplicationAlert(i18n.getString("featureNotYetAvailableAlert.header"), i18n.getString("featureNotYetAvailableAlert.content"), Alert.AlertType.ERROR);
        alert.showAndWait();
    }
}
