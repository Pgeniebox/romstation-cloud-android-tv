package org.romstation.application.task;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
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

/* JADX INFO: renamed from: org.romstation.application.task.t */
/* JADX INFO: compiled from: GameUploadTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/t.class */
public class C0252t extends Task<Void> {

    /* JADX INFO: renamed from: c */
    private int f699c;

    /* JADX INFO: renamed from: d */
    private final C0251s f700d;

    /* JADX INFO: renamed from: e */
    private C0243k f701e;

    /* JADX INFO: renamed from: f */
    private C0064ak f702f;

    /* JADX INFO: renamed from: a */
    private final int f697a = 1000;

    /* JADX INFO: renamed from: b */
    private final int f698b = 30000;

    /* JADX INFO: renamed from: g */
    private final ScheduledExecutorService f703g = Executors.newSingleThreadScheduledExecutor();

    /* JADX INFO: renamed from: h */
    private final ResourceBundle f704h = RomStation.m44d();

    public C0252t(C0251s context) {
        this.f700d = context;
    }

    /* JADX INFO: renamed from: a */
    public C0251s m1150a() {
        return this.f700d;
    }

    /* JADX INFO: renamed from: b */
    public C0064ak m1151b() {
        return this.f702f;
    }

    protected void cancelled() {
        if (this.f701e != null && this.f701e.isRunning()) {
            this.f701e.cancel(false);
        }
    }

    protected void scheduled() {
        updateTitle(this.f704h.getString("gameUploadTask.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public Void call() throws Exception {
        updateMessage(this.f704h.getString("gameUploadTask.message.initialize"));
        C0219d response = m1153d();
        JsonObject credential = m1154a(response.m967b().getAsJsonObject("upload").get("ftp").getAsString());
        try {
            this.f703g.scheduleWithFixedDelay(this::m1156e, 30000L, 30000L, TimeUnit.MILLISECONDS);
            updateMessage(this.f704h.getString("gameUploadTask.message.upload"));
            m1155a(credential, Paths.get(this.f700d.m1149c().getAsJsonObject("file").get("path").getAsString(), new String[0]).toFile());
            m1157f();
            updateMessage(this.f704h.getString("gameUploadTask.message.done"));
            return null;
        } catch (CancellationException e) {
            return null;
        } finally {
            this.f703g.shutdown();
        }
    }

    /* JADX INFO: renamed from: d */
    private C0219d m1153d() throws Exception {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/add_game.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("cover", Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(this.f700d.m1149c().get("image").getAsString(), new String[0])))).m974a("upload_id", Integer.valueOf(this.f700d.m1147a())).m974a("console_id", Integer.valueOf(this.f700d.m1149c().get("system").getAsInt())).m974a("players", Integer.valueOf(this.f700d.m1149c().get("players").getAsInt())).m974a("demo", Boolean.valueOf(this.f700d.m1149c().getAsJsonObject("type").get("demo").getAsBoolean())).m974a("hack", Boolean.valueOf(this.f700d.m1149c().getAsJsonObject("type").get("hackgame").getAsBoolean())).m974a("fangame", Boolean.valueOf(this.f700d.m1149c().getAsJsonObject("type").get("fangame").getAsBoolean())).m974a("fantrad", Boolean.valueOf(this.f700d.m1149c().getAsJsonObject("type").get("fantrad").getAsBoolean())).m974a("homebrew", Boolean.valueOf(this.f700d.m1149c().getAsJsonObject("type").get("homebrew").getAsBoolean())).m974a("release_date", Integer.valueOf(this.f700d.m1149c().get("year").getAsInt())).m974a("title", this.f700d.m1149c().get("title").getAsString()).m974a("developer", this.f700d.m1149c().get("developer").getAsString()).m974a("publisher", this.f700d.m1149c().get("publisher").isJsonNull() ? null : this.f700d.m1149c().get("publisher").getAsString()).m974a("tips_fr", this.f700d.m1149c().getAsJsonObject("tips").get("fr").getAsString()).m974a("tips_en", this.f700d.m1149c().getAsJsonObject("tips").get("en").getAsString()).m974a("description_fr", this.f700d.m1149c().getAsJsonObject("description").get("fr").getAsString()).m974a("description_en", this.f700d.m1149c().getAsJsonObject("description").get("en").getAsString()).m974a("checksum", this.f700d.m1148b()).m974a("file_name", Paths.get(this.f700d.m1149c().getAsJsonObject("file").get("path").getAsString(), new String[0]).getFileName()).m974a("label", this.f700d.m1149c().getAsJsonObject("file").get("name").getAsString()).m974a("executable", this.f700d.m1149c().getAsJsonObject("file").get("target").isJsonNull() ? null : this.f700d.m1149c().getAsJsonObject("file").get("target").getAsString()).m974a("parameters", this.f700d.m1149c().getAsJsonObject("file").get("parameters").getAsString()).m974a("genres", this.f700d.m1149c().getAsJsonArray("genres")).m974a("languages", this.f700d.m1149c().getAsJsonArray("languages")).m974a("series", this.f700d.m1149c().getAsJsonArray("series"));
        C0216a request = new C0216a(builder.m973b());
        C0219d response = request.m959a(post);
        this.f699c = response.m967b().getAsJsonObject("upload").get("id").getAsInt();
        return response;
    }

    /* JADX INFO: renamed from: a */
    private JsonObject m1154a(String cipheredString) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(C0023W.m66b(cipheredString)).getAsJsonObject();
    }

    /* JADX INFO: renamed from: a */
    private void m1155a(JsonObject credential, File file) throws Exception {
        this.f701e = new C0243k(credential.get("host").getAsString(), credential.get("username").getAsString(), credential.get("password").getAsString());
        this.f701e.m1060a(file);
        this.f701e.m1058a(String.format("%d.zip", Integer.valueOf(this.f699c)));
        this.f701e.m1062a(true);
        this.f701e.progressProperty().addListener((observableValue, oldValue, newValue) -> {
            updateProgress(this.f701e.getWorkDone(), this.f701e.getTotalWork());
        });
        ScheduledExecutorService progressService = Executors.newSingleThreadScheduledExecutor();
        this.f701e.workDoneProperty().addListener((observableValue2, previousValue, currentValue) -> {
            if (this.f702f == null) {
                this.f702f = new C0064ak(currentValue.longValue(), this.f701e.getTotalWork());
                if (!progressService.isShutdown()) {
                    progressService.scheduleWithFixedDelay(() -> {
                        this.f702f.m248h();
                    }, 1000L, 1000L, TimeUnit.MILLISECONDS);
                    return;
                }
                return;
            }
            this.f702f.m240a(currentValue.longValue() - previousValue.longValue());
        });
        this.f701e.run();
        try {
            this.f701e.get();
        } finally {
            progressService.shutdown();
        }
    }

    /* JADX INFO: renamed from: e */
    private void m1156e() {
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/update.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
            C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("upload_id", Integer.valueOf(this.f699c));
            if (this.f702f != null) {
                post.m974a("speed", Long.valueOf((long) this.f702f.m250j())).m974a("progress", Double.valueOf(this.f702f.m246f()));
            }
            C0216a request = new C0216a(builder.m973b());
            request.m959a(post);
        } catch (Exception e) {
            cancel(false);
        }
    }

    /* JADX INFO: renamed from: f */
    private void m1157f() throws Exception {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/end.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("upload_id", Integer.valueOf(this.f699c));
        C0216a request = new C0216a(builder.m973b());
        request.m959a(post);
    }
}
