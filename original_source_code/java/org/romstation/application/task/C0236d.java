package org.romstation.application.task;

import java.net.MalformedURLException;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import org.romstation.application.C0060ag;
import org.romstation.application.RomStation;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;

/* JADX INFO: renamed from: org.romstation.application.task.d */
/* JADX INFO: compiled from: CreateDedicatedServerTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/d.class */
public class C0236d extends Task<C0219d> {

    /* JADX INFO: renamed from: a */
    private final int f620a;

    public C0236d(int id) {
        this.f620a = id;
    }

    protected void scheduled() {
        ResourceBundle i18n = RomStation.m44d();
        updateTitle(i18n.getString("netplayCreateDedicatedServerTask.title"));
        updateMessage(i18n.getString("netplayCreateDedicatedServerTask.message"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0219d call() throws ServerResponseException, MalformedURLException, NetworkOfflineException, InvalidServerResponseException {
        C0221f urlBuilder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/create_dedicated_server.php");
        urlBuilder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g urlQuery = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("template_id", Integer.valueOf(this.f620a));
        C0216a httpRequest = new C0216a(urlBuilder.m973b());
        return httpRequest.m959a(urlQuery);
    }
}
