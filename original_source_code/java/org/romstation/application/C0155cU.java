package org.romstation.application;

import com.google.gson.JsonObject;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.StreamSupport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.task.C0247o;
import org.romstation.application.task.C0248p;
import org.romstation.application.view.control.ApplicationAlert;

/* JADX INFO: renamed from: org.romstation.application.cU */
/* JADX INFO: compiled from: GameDownloadManager.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cU.class */
public class C0155cU {

    /* JADX INFO: renamed from: a */
    private static final ObservableList<C0248p> f348a = FXCollections.observableList(new LinkedList());

    /* JADX INFO: renamed from: a */
    public static ObservableList<C0248p> m664a() {
        return f348a;
    }

    /* JADX INFO: renamed from: b */
    public static void m665b() {
        ApplicationAlert alert = new ApplicationAlert(RomStation.m44d().getString("downloadLimitReachedAlert.header"), RomStation.m44d().getString("downloadLimitReachedAlert.content"), Alert.AlertType.INFORMATION);
        alert.showAndWait();
    }

    /* JADX INFO: renamed from: a */
    private static JsonObject m666a(int rid) {
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/get_infos.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("gid", Integer.valueOf(rid));
            C0216a request = new C0216a(builder.m973b());
            C0219d response = request.m958b();
            return response.m967b().get("game").getAsJsonObject();
        } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        } catch (NetworkOfflineException exception2) {
            RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m667a(int gameRid, int gameFileRid) {
        if (m664a().isEmpty()) {
            JsonObject game = m666a(gameRid);
            if (game != null) {
                Optional<JsonObject> optional = StreamSupport.stream(game.getAsJsonArray("files").spliterator(), false).map((v0) -> {
                    return v0.getAsJsonObject();
                }).filter(jsonObject -> {
                    return jsonObject.get("file_id").getAsInt() == gameFileRid;
                }).findAny();
                optional.ifPresent(jsonObject2 -> {
                    m668a(game, jsonObject2);
                });
                return;
            }
            return;
        }
        m665b();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Runnable, org.romstation.application.task.p] */
    /* JADX INFO: renamed from: a */
    public static void m668a(JsonObject game, JsonObject gameFile) {
        if (m664a().isEmpty()) {
            ?? c0248p = new C0248p(new C0247o(game, gameFile));
            C0072ar dialog = new C0072ar(c0248p);
            dialog.initModality(Modality.NONE);
            new Thread((Runnable) c0248p).start();
            dialog.show();
            return;
        }
        m665b();
    }
}
