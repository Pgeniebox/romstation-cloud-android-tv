package org.romstation.application.task;

import com.google.gson.JsonObject;
import java.net.MalformedURLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.romstation.application.C0004E;
import org.romstation.application.C0023W;
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

/* JADX INFO: renamed from: org.romstation.application.task.y */
/* JADX INFO: compiled from: SoftwareUpdateTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/y.class */
public class C0257y extends Task<Void> {

    /* JADX INFO: renamed from: a */
    private final C0060ag f722a;

    /* JADX INFO: renamed from: b */
    private JsonObject f723b;

    /* JADX INFO: renamed from: c */
    private final ScheduledExecutorService f724c = Executors.newSingleThreadScheduledExecutor();

    /* JADX INFO: renamed from: d */
    private static final int f725d = 60;

    public C0257y(C0060ag software) {
        this.f722a = software;
    }

    /* JADX INFO: renamed from: a */
    public C0060ag m1170a() {
        return this.f722a;
    }

    protected void cancelled() {
        this.f724c.shutdown();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public Void call() throws Exception {
        ScheduledFuture<?> future = this.f724c.scheduleAtFixedRate(this::m1172c, 60L, 60L, TimeUnit.SECONDS);
        future.get();
        return null;
    }

    /* JADX INFO: renamed from: c */
    public void m1172c() {
        if (this.f723b == null) {
            this.f723b = C0004E.m14g();
        }
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/soft/update.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("arch", Integer.valueOf(C0004E.m11d().m6a()));
            C0222g post = new C0222g().m974a("auth", this.f722a.m236f()).m974a("cpi", C0023W.m65a(this.f723b.toString()));
            C0216a request = new C0216a(builder.m973b());
            C0219d response = request.m959a(post);
            this.f722a.m231a(response.m967b().getAsJsonObject("soft").get("id").getAsInt());
            RomStation.m43c().setProperty("application.id", String.valueOf(this.f722a.m230c()));
        } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            if ((exception instanceof ServerResponseException) && ((ServerResponseException) exception).m955a().m965a() == -100) {
                Platform.exit();
            }
        } catch (NetworkOfflineException exception2) {
            RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
        }
    }
}
