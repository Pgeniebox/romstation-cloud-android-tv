package org.romstation.application.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.romstation.application.C0004E;
import org.romstation.application.C0023W;
import org.romstation.application.C0060ag;
import org.romstation.application.C0064ak;
import org.romstation.application.C0081b;
import org.romstation.application.C0155cU;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Developer;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.database.entity.Genre;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.Language;
import org.romstation.application.database.entity.Link;
import org.romstation.application.database.entity.Locale;
import org.romstation.application.database.entity.Publisher;
import org.romstation.application.database.entity.Script;
import org.romstation.application.database.entity.Series;
import org.romstation.application.database.entity.System;
import org.romstation.application.database.entity.Translation;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.p000io.C0207a;
import org.romstation.application.p000io.ChecksumException;

/* JADX INFO: renamed from: org.romstation.application.task.p */
/* JADX INFO: compiled from: GameFileDownloadTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/p.class */
public class C0248p extends Task<GameFile> {

    /* JADX INFO: renamed from: c */
    private final C0247o f671c;

    /* JADX INFO: renamed from: g */
    private C0064ak f675g;

    /* JADX INFO: renamed from: h */
    private C0254v f676h;

    /* JADX INFO: renamed from: i */
    private C0233a f677i;

    /* JADX INFO: renamed from: j */
    private C0231B f678j;

    /* JADX INFO: renamed from: k */
    private Path f679k;

    /* JADX INFO: renamed from: l */
    private EntityManager f680l;

    /* JADX INFO: renamed from: a */
    private final int f669a = 60000;

    /* JADX INFO: renamed from: b */
    private final int f670b = 1000;

    /* JADX INFO: renamed from: d */
    private final ScheduledExecutorService f672d = Executors.newSingleThreadScheduledExecutor();

    /* JADX INFO: renamed from: e */
    private final ReadOnlyStringWrapper f673e = new ReadOnlyStringWrapper();

    /* JADX INFO: renamed from: f */
    private final ReadOnlyBooleanWrapper f674f = new ReadOnlyBooleanWrapper();

    public C0248p(C0247o context) {
        this.f671c = context;
    }

    /* JADX INFO: renamed from: a */
    public C0247o m1073a() {
        return this.f671c;
    }

    /* JADX INFO: renamed from: b */
    public ReadOnlyStringWrapper m1074b() {
        return this.f673e;
    }

    /* JADX INFO: renamed from: c */
    public String m1075c() {
        return this.f673e.get();
    }

    /* JADX INFO: renamed from: d */
    public ReadOnlyBooleanProperty m1076d() {
        return this.f674f.getReadOnlyProperty();
    }

    /* JADX INFO: renamed from: e */
    public boolean m1077e() {
        return this.f674f.get();
    }

    /* JADX INFO: renamed from: f */
    public C0064ak m1078f() {
        return this.f675g;
    }

    public void run() {
        C0155cU.m664a().add(this);
        super.run();
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("game.download.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public GameFile call() throws Exception {
        GameFile gameFile = null;
        m1080h();
        if (m1077e()) {
            long timestamp = System.currentTimeMillis();
            while (m1077e() && !isCancelled()) {
                if (System.currentTimeMillis() - timestamp >= 60000) {
                    m1081i();
                    timestamp = System.currentTimeMillis();
                } else {
                    Thread.sleep(1L);
                }
            }
        }
        if (!isCancelled()) {
            this.f672d.scheduleWithFixedDelay(this::m1082j, 60000L, 60000L, TimeUnit.MILLISECONDS);
            try {
                m1086m();
                m1087n();
                gameFile = m1107p();
                m1083k();
                updateMessage(RomStation.m44d().getString("game.download.task.message.done"));
            } catch (InterruptedException | CancellationException e) {
            } finally {
                this.f672d.shutdown();
            }
        }
        m1084l();
        return gameFile;
    }

    protected void cancelled() {
        if (this.f676h != null && this.f676h.isRunning()) {
            this.f676h.cancel(false);
        }
        if (this.f677i != null && this.f677i.isRunning()) {
            this.f677i.cancel(false);
        }
        if (this.f678j != null && this.f678j.isRunning()) {
            this.f678j.cancel(false);
        }
    }

    protected void done() {
        C0155cU.m664a().remove(this);
    }

    /* JADX INFO: renamed from: h */
    private void m1080h() throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/download/start.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("gfid", Integer.valueOf(this.f671c.m1071c().get("file_id").getAsInt()));
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
        C0216a request = new C0216a(builder.m973b());
        C0219d response = request.m959a(post);
        if (response.m967b().get("queue").getAsJsonObject().get("wait").getAsInt() == 1) {
            this.f674f.set(true);
            m1085a(response.m967b().get("queue").getAsJsonObject().get("position").getAsInt());
        } else {
            this.f671c.m1068a(response.m967b().get("download").getAsJsonObject());
        }
        if (!response.m967b().get("ad").isJsonNull()) {
            this.f673e.set(C0217b.m961b() + response.m967b().get("ad").getAsString());
        }
    }

    /* JADX INFO: renamed from: i */
    private void m1081i() throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/download/update.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("gfid", Integer.valueOf(this.f671c.m1071c().get("file_id").getAsInt()));
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
        C0216a request = new C0216a(builder.m973b());
        C0219d response = request.m959a(post);
        if (response.m967b().get("queue").getAsJsonObject().get("wait").getAsInt() == 1) {
            m1085a(response.m967b().get("queue").getAsJsonObject().get("position").getAsInt());
        } else {
            this.f674f.set(false);
            this.f671c.m1068a(response.m967b().get("download").getAsJsonObject());
        }
    }

    /* JADX INFO: renamed from: j */
    private void m1082j() {
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/download/update.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("gfid", Integer.valueOf(this.f671c.m1071c().get("file_id").getAsInt()));
            if (this.f675g != null && !this.f676h.isDone()) {
                builder.m972a().m974a("running", 1).m974a("speed", Long.valueOf((long) this.f675g.m250j())).m974a("progress", Double.valueOf(this.f675g.m246f()));
            }
            C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
            C0216a request = new C0216a(builder.m973b());
            request.m959a(post);
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: k */
    private void m1083k() throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/download/set_downloaded.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("gfid", this.f671c.m1071c().get("file_id").getAsString());
        if (this.f675g != null) {
            builder.m972a().m974a("speed", Long.valueOf((long) this.f675g.m250j()));
        }
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
        C0216a request = new C0216a(builder.m973b());
        request.m959a(post);
    }

    /* JADX INFO: renamed from: l */
    private void m1084l() throws ServerResponseException, NetworkOfflineException, MalformedURLException, InvalidServerResponseException {
        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/download/end.php");
        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a()));
        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
        C0216a request = new C0216a(builder.m973b());
        request.m959a(post);
    }

    /* JADX INFO: renamed from: a */
    private void m1085a(int queuePosition) {
        updateMessage(String.format(RomStation.m44d().getString("game.download.task.message.queue"), Integer.valueOf(queuePosition)));
    }

    /* JADX INFO: renamed from: m */
    private void m1086m() throws Exception {
        updateMessage(RomStation.m44d().getString("game.download.task.message.downloading"));
        this.f679k = Paths.get("cache/downloads/games", this.f671c.m1071c().get("name").getAsString());
        Files.createDirectories(this.f679k.getParent(), new FileAttribute[0]);
        JsonParser parser = new JsonParser();
        String protocol = this.f671c.m1067a().get("protocol").getAsString();
        JsonObject credential = parser.parse(C0023W.m66b(this.f671c.m1067a().get(protocol).getAsString())).getAsJsonObject();
        this.f676h = new C0254v(credential.get("url").getAsString(), this.f679k);
        this.f676h.m1163a(true);
        this.f676h.m1165b(true);
        this.f676h.m1167a(new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("token", credential.get("token").getAsString()).toString());
        this.f676h.progressProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(this.f676h.getWorkDone(), this.f676h.getTotalWork());
        });
        ScheduledExecutorService progressService = Executors.newSingleThreadScheduledExecutor();
        this.f676h.workDoneProperty().addListener((observableValue, previousValue, currentValue) -> {
            if (this.f675g == null) {
                this.f675g = new C0064ak(currentValue.longValue(), this.f676h.getTotalWork());
                if (!progressService.isShutdown()) {
                    progressService.scheduleWithFixedDelay(() -> {
                        this.f675g.m248h();
                    }, 1000L, 1000L, TimeUnit.MILLISECONDS);
                    return;
                }
                return;
            }
            this.f675g.m240a(currentValue.longValue() - previousValue.longValue());
        });
        this.f676h.run();
        try {
            this.f676h.get();
        } finally {
            progressService.shutdown();
        }
    }

    /* JADX INFO: renamed from: n */
    private void m1087n() throws Exception {
        this.f677i = new C0233a(this.f679k);
        this.f677i.progressProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(this.f677i.getWorkDone(), this.f677i.getTotalWork());
        });
        updateMessage(RomStation.m44d().getString("game.download.task.message.verifying"));
        this.f677i.run();
        String localChecksum = (String) this.f677i.get();
        String serverChecksum = this.f671c.m1071c().get("crc32").getAsString();
        if (!serverChecksum.equals(localChecksum)) {
            Files.delete(this.f679k);
            throw new ChecksumException("Checksum invalid");
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1088a(Path target) throws ExecutionException, InterruptedException, IOException {
        this.f678j = new C0231B(this.f679k, target);
        this.f678j.progressProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(this.f678j.getWorkDone(), this.f678j.getTotalWork());
        });
        updateMessage(RomStation.m44d().getString("game.download.task.message.unpacking"));
        this.f678j.run();
        this.f678j.get();
        Files.delete(this.f679k);
    }

    /* JADX INFO: renamed from: a */
    private Game m1089a(JsonObject json) throws Exception {
        Game game = new Game();
        game.setRid(Integer.valueOf(json.get("id").getAsInt()));
        game.setTitle(json.get("title").getAsString());
        game.setPlayers(Integer.valueOf(json.get("players").getAsInt()));
        game.setYear(Integer.valueOf(json.get("release_date").getAsInt()));
        game.setLinks(m1092a(json.getAsJsonArray("links")));
        game.setSystem(m1094c(json.getAsJsonObject("system")));
        game.setDescription(m1096d(json.getAsJsonObject("description")));
        game.getLanguages().addAll(m1098b(json.getAsJsonArray("languages")));
        game.getGenres().addAll(m1100c(json.getAsJsonArray("genres")));
        game.setDeveloper(m1102h(json.getAsJsonObject("developer")));
        game.setPublisher(m1104j(json.getAsJsonObject("publisher")));
        game.getSeries().addAll(m1106d(json.getAsJsonArray("series")));
        game.setManaged(true);
        String dirName = C0207a.m831a(MessageFormat.format("{0} - {1,number,#}", game.getTitle(), game.getRid()));
        Path dir = Paths.get(RomStation.m43c().getProperty("path.games"), "downloads", dirName);
        Files.createDirectories(dir, new FileAttribute[0]);
        game.setDirectory(dir.toString());
        Path graphicOutput = dir.resolve("images/cover.png");
        Files.createDirectories(graphicOutput.getParent(), new FileAttribute[0]);
        URL graphicInput = new URL(C0217b.m961b() + json.get("cover").getAsString());
        InputStream stream = graphicInput.openStream();
        Throwable th = null;
        try {
            try {
                Files.copy(stream, graphicOutput, StandardCopyOption.REPLACE_EXISTING);
                game.setGraphic(new Image(graphicOutput));
                if (stream != null) {
                    if (0 != 0) {
                        try {
                            stream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        stream.close();
                    }
                }
                if (!json.get("meta").isJsonNull()) {
                    if (json.getAsJsonObject("meta").has("metas")) {
                        json.getAsJsonObject("meta").getAsJsonArray("metas").forEach(jsonElement -> {
                            JsonObject object = jsonElement.getAsJsonObject();
                            game.getMetas().put(object.get("key").getAsString(), object.get("value").getAsString());
                        });
                    }
                    if (json.getAsJsonObject("meta").has("scripts")) {
                        json.getAsJsonObject("meta").getAsJsonArray("scripts").forEach(jsonElement2 -> {
                            game.getScripts().add(new Script(Paths.get(game.getDirectory(), jsonElement2.getAsString()).toString()));
                        });
                    }
                }
                return game;
            } catch (Throwable th3) {
                th = th3;
                throw th3;
            }
        } catch (Throwable th4) {
            if (stream != null) {
                if (th != null) {
                    try {
                        stream.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                } else {
                    stream.close();
                }
            }
            throw th4;
        }
    }

    /* JADX INFO: renamed from: a */
    private GameFile m1090a(Game game) throws ExecutionException, InterruptedException, IOException {
        JsonObject metaJsonObject = this.f671c.m1071c().getAsJsonObject("meta");
        Path directoryPath = Paths.get(game.getDirectory(), "files", this.f671c.m1071c().get("file_id").getAsString());
        if (this.f671c.m1071c().get("deflate").getAsBoolean()) {
            if (Files.exists(directoryPath, new LinkOption[0])) {
                C0207a.m828a(directoryPath);
            }
            m1088a(directoryPath);
        } else {
            Files.createDirectories(directoryPath, new FileAttribute[0]);
            Files.move(this.f679k, directoryPath.resolve(this.f671c.m1071c().get("name").getAsString()), StandardCopyOption.REPLACE_EXISTING);
        }
        GameFile gameFile = new GameFile();
        gameFile.setRid(Integer.valueOf(this.f671c.m1071c().get("file_id").getAsInt()));
        gameFile.setName(this.f671c.m1071c().get("label").getAsString());
        gameFile.setDirectory(directoryPath.toString());
        gameFile.setGame(game);
        gameFile.setManaged(true);
        if (metaJsonObject.has("metas")) {
            metaJsonObject.getAsJsonArray("metas").forEach(jsonElement -> {
                JsonObject object = jsonElement.getAsJsonObject();
                gameFile.getMetas().put(object.get("key").getAsString(), object.get("value").getAsString());
            });
        }
        if (metaJsonObject.has("scripts")) {
            metaJsonObject.getAsJsonArray("scripts").forEach(jsonElement2 -> {
                gameFile.getScripts().add(new Script(Paths.get(gameFile.getDirectory(), jsonElement2.getAsString()).toString()));
            });
        }
        metaJsonObject.getAsJsonArray("profiles").forEach(jsonElement3 -> {
            gameFile.getProfiles().add(m1091a(gameFile, jsonElement3.getAsJsonObject()));
        });
        return gameFile;
    }

    /* JADX INFO: renamed from: a */
    private GameProfile m1091a(GameFile gameFile, JsonObject profileObject) {
        GameProfile gameProfile = new GameProfile();
        gameProfile.setName(profileObject.get("name").getAsString().replace("${gameFile.name}", gameFile.getName()));
        if (profileObject.get("target").isJsonNull() || profileObject.get("target").getAsString().isEmpty()) {
            gameProfile.setPath(Paths.get(gameFile.getDirectory(), this.f671c.m1071c().get("name").getAsString()).toString());
        } else {
            gameProfile.setPath(Paths.get(gameFile.getDirectory(), profileObject.get("target").getAsString()).toString());
        }
        if (profileObject.has("parameters")) {
            gameProfile.setParameters(profileObject.get("parameters").isJsonNull() ? null : profileObject.get("parameters").getAsString());
        }
        gameProfile.setGameFile(gameFile);
        if (profileObject.has("metas")) {
            profileObject.getAsJsonArray("metas").forEach(jsonElement -> {
                JsonObject object = jsonElement.getAsJsonObject();
                gameProfile.getMetas().put(object.get("key").getAsString(), object.get("value").getAsString());
            });
        }
        if (profileObject.has("scripts")) {
            profileObject.getAsJsonArray("scripts").forEach(jsonElement2 -> {
                gameProfile.getScripts().add(new Script(Paths.get(gameFile.getDirectory(), jsonElement2.getAsString()).toString()));
            });
        }
        return gameProfile;
    }

    /* JADX INFO: renamed from: a */
    private List<Link> m1092a(JsonArray jsonArray) {
        List<Link> links = new LinkedList<>();
        jsonArray.forEach(element -> {
            JsonObject jsonObject = element.getAsJsonObject();
            Link link = new Link();
            link.setName(jsonObject.get("name").getAsString());
            link.setExternal(jsonObject.get("external").getAsBoolean());
            if (link.isExternal()) {
                link.setLocation(jsonObject.get("location").getAsString());
            } else {
                link.setLocation(C0217b.m961b() + jsonObject.get("location").getAsString());
            }
            links.add(link);
        });
        return links;
    }

    /* JADX INFO: renamed from: b */
    private System m1093b(JsonObject jsonObject) throws Exception {
        Path output = Paths.get("images/systems", new String[0]).resolve(Paths.get(jsonObject.get("image").getAsString(), new String[0]).getFileName());
        URL url = new URL(C0217b.m961b() + jsonObject.get("image").getAsString());
        InputStream stream = url.openStream();
        Throwable th = null;
        try {
            Files.copy(stream, output, StandardCopyOption.REPLACE_EXISTING);
            if (stream != null) {
                if (0 != 0) {
                    try {
                        stream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    stream.close();
                }
            }
            System system = new System();
            system.setRid(Integer.valueOf(jsonObject.get("id").getAsInt()));
            system.setName(jsonObject.get("name").getAsString());
            system.setGraphic(new Image(output));
            return system;
        } catch (Throwable th3) {
            if (stream != null) {
                if (0 != 0) {
                    try {
                        stream.close();
                    } catch (Throwable th4) {
                        th.addSuppressed(th4);
                    }
                } else {
                    stream.close();
                }
            }
            throw th3;
        }
    }

    /* JADX INFO: renamed from: c */
    private System m1094c(JsonObject jsonObject) throws Exception {
        try {
            return (System) this.f680l.createNamedQuery(System.f509c, System.class).setParameter("rid", Integer.valueOf(jsonObject.get("id").getAsInt())).getSingleResult();
        } catch (NoResultException e) {
            return m1093b(jsonObject);
        }
    }

    /* JADX INFO: renamed from: o */
    private Map<String, Locale> m1095o() {
        return (Map) this.f680l.createNamedQuery(Locale.f492a, Locale.class).getResultList().stream().collect(Collectors.toMap((v0) -> {
            return v0.getTag();
        }, Function.identity()));
    }

    /* JADX INFO: renamed from: d */
    private I18n m1096d(JsonObject jsonObject) {
        I18n i18n = new I18n();
        m1095o().forEach((tag, locale) -> {
            JsonElement element = jsonObject.get(tag);
            if (element != null && !element.isJsonNull() && !element.getAsString().isEmpty()) {
                i18n.getTranslations().add(new Translation(locale, element.getAsString(), i18n));
            }
        });
        return i18n;
    }

    /* JADX INFO: renamed from: e */
    private Language m1097e(JsonObject jsonObject) {
        Map<String, Locale> locales = m1095o();
        I18n i18n = new I18n();
        i18n.getTranslations().add(new Translation(locales.get("fr"), jsonObject.get("fr").getAsString(), i18n));
        i18n.getTranslations().add(new Translation(locales.get("en"), jsonObject.get("en").getAsString(), i18n));
        return new Language(Integer.valueOf(jsonObject.get("id").getAsInt()), i18n, new Image(Paths.get("images/flags", jsonObject.get("code").getAsString() + ".png")));
    }

    /* JADX INFO: renamed from: b */
    private List<Language> m1098b(JsonArray jsonArray) {
        List<Language> result = new LinkedList<>();
        List<Language> entities = this.f680l.createNamedQuery(Language.f485b, Language.class).getResultList();
        jsonArray.forEach(e -> {
            JsonObject jsonObject = e.getAsJsonObject();
            Optional<Language> optional = entities.stream().filter(language -> {
                return Objects.equals(language.getRid(), Integer.valueOf(jsonObject.get("id").getAsInt()));
            }).findAny();
            Language language2 = optional.orElseGet(() -> {
                return m1097e(jsonObject);
            });
            result.add(language2);
        });
        return result;
    }

    /* JADX INFO: renamed from: f */
    private Genre m1099f(JsonObject jsonObject) {
        Map<String, Locale> locales = m1095o();
        I18n i18n = new I18n();
        i18n.getTranslations().add(new Translation(locales.get("fr"), jsonObject.get("fr").getAsString(), i18n));
        i18n.getTranslations().add(new Translation(locales.get("en"), jsonObject.get("en").getAsString(), i18n));
        return new Genre(Integer.valueOf(jsonObject.get("id").getAsInt()), i18n);
    }

    /* JADX INFO: renamed from: c */
    private List<Genre> m1100c(JsonArray jsonArray) {
        List<Genre> result = new LinkedList<>();
        List<Genre> entities = this.f680l.createNamedQuery(Genre.f477b, Genre.class).getResultList();
        jsonArray.forEach(e -> {
            JsonObject jsonObject = e.getAsJsonObject();
            Optional<Genre> optional = entities.stream().filter(genre -> {
                return Objects.equals(genre.getRid(), Integer.valueOf(jsonObject.get("id").getAsInt()));
            }).findAny();
            Genre genre2 = optional.orElseGet(() -> {
                return m1099f(jsonObject);
            });
            result.add(genre2);
        });
        return result;
    }

    /* JADX INFO: renamed from: g */
    private Developer m1101g(JsonObject jsonObject) {
        return new Developer(Integer.valueOf(jsonObject.get("id").getAsInt()), jsonObject.get("name").getAsString());
    }

    /* JADX INFO: renamed from: h */
    private Developer m1102h(JsonObject jsonObject) {
        int rid = jsonObject.get("id").getAsInt();
        if (rid == 0) {
            return null;
        }
        try {
            return (Developer) this.f680l.createNamedQuery(Developer.f410c, Developer.class).setParameter("rid", Integer.valueOf(rid)).getSingleResult();
        } catch (NoResultException e) {
            return m1101g(jsonObject);
        }
    }

    /* JADX INFO: renamed from: i */
    private Publisher m1103i(JsonObject jsonObject) {
        return new Publisher(Integer.valueOf(jsonObject.get("id").getAsInt()), jsonObject.get("name").getAsString());
    }

    /* JADX INFO: renamed from: j */
    private Publisher m1104j(JsonObject jsonObject) {
        int rid = jsonObject.get("id").getAsInt();
        if (rid == 0) {
            return null;
        }
        try {
            return (Publisher) this.f680l.createNamedQuery(Publisher.f499c, Publisher.class).setParameter("rid", Integer.valueOf(rid)).getSingleResult();
        } catch (NoResultException e) {
            return m1103i(jsonObject);
        }
    }

    /* JADX INFO: renamed from: k */
    private Series m1105k(JsonObject jsonObject) {
        return new Series(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString());
    }

    /* JADX INFO: renamed from: d */
    private List<Series> m1106d(JsonArray jsonArray) {
        List<Series> result = new LinkedList<>();
        List<Series> entities = this.f680l.createNamedQuery(Series.f504b, Series.class).getResultList();
        jsonArray.forEach(e -> {
            JsonObject jsonObject = e.getAsJsonObject();
            Optional<Series> optional = entities.stream().filter(series -> {
                return Objects.equals(series.getRid(), Integer.valueOf(jsonObject.get("id").getAsInt()));
            }).findAny();
            Series series2 = optional.orElseGet(() -> {
                return m1105k(jsonObject);
            });
            result.add(series2);
        });
        return result;
    }

    /* JADX INFO: renamed from: p */
    private GameFile m1107p() throws Exception {
        Game game;
        try {
            this.f680l = C0081b.m309c();
            try {
                game = (Game) this.f680l.createNamedQuery(Game.f440b, Game.class).setParameter("rid", Integer.valueOf(this.f671c.m1069b().get("id").getAsInt())).getSingleResult();
            } catch (NoResultException e) {
                game = m1089a(this.f671c.m1069b());
            }
            GameFile gameFile = m1090a(game);
            game.getFiles().add(gameFile);
            this.f680l.getTransaction().begin();
            this.f680l.persist(game);
            this.f680l.getTransaction().commit();
            if (this.f680l != null) {
                this.f680l.close();
            }
            return gameFile;
        } catch (Throwable th) {
            if (this.f680l != null) {
                this.f680l.close();
            }
            throw th;
        }
    }
}
