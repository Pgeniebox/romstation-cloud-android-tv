package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import javafx.application.Platform;
import org.romstation.application.netplay.C0214b;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/Netplay.class */
@JsAccessible
public class Netplay {
    public void createManualServer() {
        Platform.runLater(C0214b::m856e);
    }

    public void createCloudServer() {
        Platform.runLater(C0214b::m857f);
    }

    public void createCloudServer(int gameId, int gameFileId) {
        Platform.runLater(() -> {
            C0214b.m858a(gameId, gameFileId);
        });
    }

    public void createAutomaticServer() {
        Platform.runLater(C0214b::m859g);
    }

    public void createDedicatedServer() {
        Platform.runLater(C0214b::m861h);
    }

    public void joinServer(int lobbyID) {
        Platform.runLater(() -> {
            C0214b.m862a(lobbyID);
        });
    }

    public void joinDedicatedServer(int serverID, boolean hasPassword) {
        Platform.runLater(() -> {
            C0214b.m867a(serverID, hasPassword);
        });
    }
}
