package org.romstation.application.task;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.concurrent.Task;
import org.romstation.application.C0023W;
import org.romstation.application.C0060ag;
import org.romstation.application.C0064ak;
import org.romstation.application.RomStation;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;

/* JADX INFO: renamed from: org.romstation.application.task.r */
/* JADX INFO: compiled from: GameFileUploadTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/r.class */
public class C0250r extends Task<Void> {

    /* JADX INFO: renamed from: c */
    private int f688c;

    /* JADX INFO: renamed from: d */
    private final C0249q f689d;

    /* JADX INFO: renamed from: e */
    private C0243k f690e;

    /* JADX INFO: renamed from: f */
    private C0064ak f691f;

    /* JADX INFO: renamed from: a */
    private final int f686a = 1000;

    /* JADX INFO: renamed from: b */
    private final int f687b = 30000;

    /* JADX INFO: renamed from: g */
    private final ScheduledExecutorService f692g = Executors.newSingleThreadScheduledExecutor();

    /* JADX INFO: renamed from: h */
    private final ResourceBundle f693h = RomStation.m44d();

    public C0250r(C0249q context) {
        this.f689d = context;
    }

    /* JADX INFO: renamed from: a */
    public C0249q m1136a() {
        return this.f689d;
    }

    /* JADX INFO: renamed from: b */
    public C0064ak m1137b() {
        return this.f691f;
    }

    protected void cancelled() {
        if (this.f690e != null && this.f690e.isRunning()) {
            this.f690e.cancel(false);
        }
    }

    protected void scheduled() {
        updateTitle(this.f693h.getString("gameFileUploadTask.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public Void call() throws Exception {
        updateMessage(this.f693h.getString("gameFileUploadTask.message.initialize"));
        C0219d response = m1139d();
        JsonObject credential = m1140a(response.m967b().getAsJsonObject("upload").get("ftp").getAsString());
        try {
            this.f692g.scheduleWithFixedDelay(this::m1142e, 30000L, 30000L, TimeUnit.MILLISECONDS);
            updateMessage(this.f693h.getString("gameFileUploadTask.message.upload"));
            m1141a(credential, Paths.get(this.f689d.m1135e().get("path").getAsString(), new String[0]).toFile());
            m1143f();
            updateMessage(this.f693h.getString("gameFileUploadTask.message.done"));
            return null;
        } catch (CancellationException e) {
            return null;
        } finally {
            this.f692g.shutdown();
        }
    }

    /* JADX INFO: renamed from: d */
    private C0219d m1139d() throws Exception {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/add_file.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("upload_id", Integer.valueOf(this.f689d.m1131a())).m974a("game_id", Integer.valueOf(this.f689d.m1132b())).m974a("console_id", Integer.valueOf(this.f689d.m1133c())).m974a("checksum", this.f689d.m1134d()).m974a("file_name", Paths.get(this.f689d.m1135e().get("path").getAsString(), new String[0]).getFileName()).m974a("label", this.f689d.m1135e().get("name").getAsString()).m974a("executable", this.f689d.m1135e().get("target").isJsonNull() ? null : this.f689d.m1135e().get("target").getAsString()).m974a("parameters", this.f689d.m1135e().get("parameters").getAsString());
        C0216a request = new C0216a(builder.m973b());
        C0219d response = request.m959a(post);
        this.f688c = response.m967b().getAsJsonObject("upload").get("id").getAsInt();
        return response;
    }

    /* JADX INFO: renamed from: a */
    private JsonObject m1140a(String cipheredString) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(C0023W.m66b(cipheredString)).getAsJsonObject();
    }

    /* JADX INFO: renamed from: a */
    private void m1141a(JsonObject credential, File file) throws Exception {
        this.f690e = new C0243k(credential.get("host").getAsString(), credential.get("username").getAsString(), credential.get("password").getAsString());
        this.f690e.m1060a(file);
        this.f690e.m1058a(String.format("%d.zip", Integer.valueOf(this.f688c)));
        this.f690e.m1062a(true);
        this.f690e.progressProperty().addListener((observableValue, oldValue, newValue) -> {
            updateProgress(this.f690e.getWorkDone(), this.f690e.getTotalWork());
        });
        ScheduledExecutorService progressService = Executors.newSingleThreadScheduledExecutor();
        this.f690e.workDoneProperty().addListener((observableValue2, previousValue, currentValue) -> {
            if (this.f691f == null) {
                this.f691f = new C0064ak(currentValue.longValue(), this.f690e.getTotalWork());
                if (!progressService.isShutdown()) {
                    progressService.scheduleWithFixedDelay(() -> {
                        this.f691f.m248h();
                    }, 1000L, 1000L, TimeUnit.MILLISECONDS);
                    return;
                }
                return;
            }
            this.f691f.m240a(currentValue.longValue() - previousValue.longValue());
        });
        this.f690e.run();
        try {
            this.f690e.get();
        } finally {
            progressService.shutdown();
        }
    }

    /* JADX INFO: renamed from: e */
    private void m1142e() {
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/update.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
            C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("upload_id", Integer.valueOf(this.f688c));
            if (this.f691f != null) {
                post.m974a("speed", Long.valueOf((long) this.f691f.m250j())).m974a("progress", Double.valueOf(this.f691f.m246f()));
            }
            C0216a request = new C0216a(builder.m973b());
            request.m959a(post);
        } catch (Exception e) {
            cancel(false);
        }
    }

    /* JADX INFO: renamed from: f */
    private void m1143f() throws Exception {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/end.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("upload_id", Integer.valueOf(this.f688c));
        C0216a request = new C0216a(builder.m973b());
        request.m959a(post);
    }
}
