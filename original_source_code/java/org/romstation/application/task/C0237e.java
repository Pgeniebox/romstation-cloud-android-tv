package org.romstation.application.task;

import java.net.MalformedURLException;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import org.romstation.application.C0060ag;
import org.romstation.application.EnumC0129bv;
import org.romstation.application.RomStation;
import org.romstation.application.netplay.C0215c;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;

/* JADX INFO: renamed from: org.romstation.application.task.e */
/* JADX INFO: compiled from: CreateServerTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/e.class */
public class C0237e extends Task<C0219d> {

    /* JADX INFO: renamed from: a */
    private static final int f621a = 255;

    /* JADX INFO: renamed from: b */
    private final C0215c f622b;

    public C0237e(C0215c config) {
        this.f622b = config;
    }

    protected void scheduled() {
        ResourceBundle i18n = RomStation.m44d();
        updateTitle(i18n.getString("netplayCreateServerTask.title"));
        updateMessage(i18n.getString("netplayCreateServerTask.message"));
    }

    /* JADX INFO: renamed from: a */
    private String m1013a(String string) {
        return (string == null || string.length() <= f621a) ? string : string.substring(0, f621a);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0219d call() throws ServerResponseException, MalformedURLException, NetworkOfflineException, InvalidServerResponseException {
        C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/create_lobby.php");
        urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g postQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("title", m1013a(this.f622b.m929c())).m974a("description", m1013a(this.f622b.m931d())).m974a("password", m1013a(this.f622b.m933e())).m974a("slots", Integer.valueOf(this.f622b.m935f())).m974a("game_file_id", Integer.valueOf(this.f622b.m937g())).m974a("locked", Integer.valueOf(this.f622b.m939h() ? 1 : 0));
        if (this.f622b.m925a() == EnumC0129bv.CLOUD) {
            postQuery.m974a("live", Integer.valueOf(this.f622b.m941i() ? 1 : 0)).m974a("instantiated", Integer.valueOf(this.f622b.m943j() ? 1 : 0)).m974a("cloud", 1).m974a("master_lobby_id", Integer.valueOf(this.f622b.m927b())).m974a("language", Integer.valueOf(this.f622b.m945k())).m974a("region", Integer.valueOf(this.f622b.m947l())).m974a("framerate", Integer.valueOf(this.f622b.m949m())).m974a("resolution", Integer.valueOf(this.f622b.m951n().m608a())).m974a("bitrate", Integer.valueOf(this.f622b.m953o()));
        }
        C0216a httpRequest = new C0216a(urlBuilder.m973b());
        return httpRequest.m959a(postQuery);
    }
}
