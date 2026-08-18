package org.romstation.application.task;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.concurrent.Task;
import org.romstation.application.RomStation;
import org.romstation.application.vpn.C0275a;
import org.romstation.application.vpn.EnumC0278d;
import org.romstation.application.vpn.InterfaceC0276b;

/* JADX INFO: renamed from: org.romstation.application.task.C */
/* JADX INFO: compiled from: VpnConnectionTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/C.class */
public class C0232C extends Task<C0275a> implements InterfaceC0276b {

    /* JADX INFO: renamed from: a */
    private final Path f611a;

    /* JADX INFO: renamed from: b */
    private final Path f612b;

    /* JADX INFO: renamed from: c */
    private final String f613c;

    /* JADX INFO: renamed from: d */
    private final ResourceBundle f614d;

    /* JADX INFO: renamed from: e */
    private C0275a f615e;

    /* JADX INFO: renamed from: f */
    private Throwable f616f;

    public C0232C(Path cert, Path pass) {
        this(cert, pass, null);
    }

    public C0232C(Path cert, Path pass, String deviceNode) {
        this.f611a = cert;
        this.f612b = pass;
        this.f613c = deviceNode;
        this.f614d = RomStation.m44d();
    }

    protected void scheduled() {
        updateTitle(this.f614d.getString("vpnConnectionTask.title"));
        updateMessage(this.f614d.getString("vpnConnectionTask.state.initialization"));
    }

    protected void cancelled() {
        if (this.f615e != null && this.f615e.m1649e()) {
            try {
                this.f615e.m1650f();
            } catch (IOException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0275a call() throws Exception {
        this.f615e = new C0275a(this.f611a, this.f612b, this.f613c);
        this.f615e.m1646a(this);
        this.f615e.m1647d();
        while (!isCancelled()) {
            try {
                if (this.f616f != null) {
                    throw ((Exception) this.f616f);
                }
                if (this.f615e.m1644b() == EnumC0278d.CONNECTED) {
                    break;
                }
                Thread.sleep(100L);
            } catch (Throwable th) {
                this.f615e.m1646a((InterfaceC0276b) null);
                throw th;
            }
        }
        this.f615e.m1646a((InterfaceC0276b) null);
        if (this.f615e.m1644b() == EnumC0278d.CONNECTED) {
            return this.f615e;
        }
        return null;
    }

    @Override // org.romstation.application.vpn.InterfaceC0276b
    /* JADX INFO: renamed from: a */
    public void mo390a(EnumC0278d state, String description) {
        switch (state) {
            case CONNECTING:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.connecting"));
                break;
            case WAIT:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.wait"));
                break;
            case AUTH:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.auth"));
                break;
            case GET_CONFIG:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.getConfig"));
                break;
            case ASSIGN_IP:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.assignIP"));
                break;
            case ADD_ROUTES:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.addRoutes"));
                break;
            case CONNECTED:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.connected"));
                break;
            case RECONNECTING:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.reconnecting"));
                break;
            case EXITING:
                updateMessage(this.f614d.getString("vpnConnectionTask.state.exiting"));
                break;
        }
    }

    @Override // org.romstation.application.vpn.InterfaceC0276b
    /* JADX INFO: renamed from: a */
    public void mo391a(Throwable throwable) {
        this.f616f = throwable;
    }
}
