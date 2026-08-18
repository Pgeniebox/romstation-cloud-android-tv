package org.romstation.application;

import javax.websocket.CloseReason;
import org.glassfish.tyrus.client.ClientManager;

/* JADX INFO: renamed from: org.romstation.application.dm */
/* JADX INFO: compiled from: ReconnectHandler.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dm.class */
public class C0201dm extends ClientManager.ReconnectHandler {

    /* JADX INFO: renamed from: a */
    private static final int f539a = 10;

    /* JADX INFO: renamed from: b */
    private boolean f540b;

    /* JADX INFO: renamed from: a */
    public void m810a() {
        this.f540b = true;
    }

    public boolean onDisconnect(CloseReason closeReason) {
        if (this.f540b) {
            return false;
        }
        RomStation.m42b().info("Reconnecting to websocket server...");
        return true;
    }

    public boolean onConnectFailure(Exception exception) {
        if (this.f540b) {
            return false;
        }
        RomStation.m42b().warning(String.format("Connection to websocket server failed (%s), reconnecting...", exception.getMessage()));
        return true;
    }

    public long getDelay() {
        return 10L;
    }
}
