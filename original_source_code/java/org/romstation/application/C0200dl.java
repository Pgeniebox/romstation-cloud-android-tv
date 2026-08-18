package org.romstation.application;

import com.google.common.eventbus.EventBus;
import java.nio.ByteBuffer;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.Session;

/* JADX INFO: renamed from: org.romstation.application.dl */
/* JADX INFO: compiled from: LobbyEndpoint.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dl.class */
@ClientEndpoint
public class C0200dl {

    /* JADX INFO: renamed from: a */
    private final EventBus f538a;

    public C0200dl(EventBus eventBus) {
        this.f538a = eventBus;
    }

    @OnMessage
    /* JADX INFO: renamed from: a */
    public void m809a(Session session, ByteBuffer byteBuffer) {
        if (byteBuffer.get(0) == 9) {
            this.f538a.post(new C0148cN());
        }
    }
}
