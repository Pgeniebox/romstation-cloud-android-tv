package org.romstation.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.database.entity.Genre;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.Language;
import org.romstation.application.database.entity.Link;
import org.romstation.application.database.entity.Locale;
import org.romstation.application.database.entity.System;
import org.romstation.application.database.entity.Tag;
import org.romstation.application.database.entity.Translation;
import org.romstation.application.p000io.C0207a;

/* JADX INFO: renamed from: org.romstation.application.ac */
/* JADX INFO: compiled from: GameImporterTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ac.class */
public class C0056ac extends Task<List<Game>> {

    /* JADX INFO: renamed from: a */
    private final List<C0055ab> f104a;

    /* JADX INFO: renamed from: b */
    private final List<Game> f105b = new LinkedList();

    /* JADX INFO: renamed from: c */
    private EntityManager f106c;

    /* JADX INFO: renamed from: d */
    private Map<String, Locale> f107d;

    /* JADX INFO: renamed from: e */
    private JsonArray f108e;

    /* JADX INFO: renamed from: f */
    private JsonArray f109f;

    /* JADX INFO: renamed from: g */
    private JsonArray f110g;

    /* JADX INFO: renamed from: h */
    private JsonArray f111h;

    /* JADX INFO: renamed from: i */
    private JsonArray f112i;

    /* JADX INFO: renamed from: j */
    private JsonArray f113j;

    public C0056ac(List<C0055ab> games) {
        this.f104a = games;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("legacyGameImporterTask.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<Game> call() throws IOException {
        Game game;
        this.f106c = C0081b.m309c();
        this.f107d = (Map) this.f106c.createNamedQuery(Locale.f492a, Locale.class).getResultList().stream().collect(Collectors.toMap((v0) -> {
            return v0.getTag();
        }, Function.identity()));
        try {
            for (C0055ab legacyGame : this.f104a) {
                updateMessage(MessageFormat.format(RomStation.m44d().getString("legacyGameImporterTask.message"), legacyGame.getTitle()));
                if (isCancelled()) {
                    break;
                }
                try {
                    game = (Game) this.f106c.createQuery("select game from Game game where game.rid = :rid", Game.class).setParameter("rid", Integer.valueOf(legacyGame.getRsId())).setMaxResults(1).getSingleResult();
                } catch (NoResultException e) {
                    game = m174a(legacyGame);
                }
                if (legacyGame.getFilename() != null && !legacyGame.getFilename().isEmpty()) {
                    GameFile gameFile = m175a(game, legacyGame);
                    gameFile.getProfiles().add(m176a(gameFile, legacyGame));
                    game.getFiles().add(gameFile);
                }
                this.f106c.getTransaction().begin();
                this.f106c.persist(game);
                this.f106c.getTransaction().commit();
                this.f105b.add(game);
                updateValue(this.f105b);
                updateProgress(this.f105b.size(), this.f104a.size());
            }
            this.f106c.close();
            return this.f105b;
        } catch (Throwable th) {
            this.f106c.close();
            throw th;
        }
    }

    /* JADX INFO: renamed from: a */
    private Game m174a(C0055ab legacyGame) throws IOException {
        Game game = new Game();
        if (legacyGame.getRsId() != 0) {
            game.setRid(Integer.valueOf(legacyGame.getRsId()));
        }
        if (legacyGame.getTitle() != null) {
            game.setTitle(legacyGame.getTitle().replaceFirst("(?i)\\(Dis(c|k|que) \\d\\)", "").trim());
        }
        if (legacyGame.getConsole() != null && !legacyGame.getConsole().isEmpty()) {
            game.setSystem(m178b(legacyGame.getConsole()));
        }
        if (legacyGame.getRegion() != null && !legacyGame.getRegion().isEmpty()) {
            game.setLanguages(m180b(legacyGame.getRegion().split(" - ")));
        }
        if (legacyGame.getGenre() != null && !legacyGame.getGenre().isEmpty()) {
            game.setGenres(m179a(legacyGame.getGenre().split(" - ")));
        }
        if (legacyGame.getPlayers() != 0) {
            game.setPlayers(Integer.valueOf(legacyGame.getPlayers()));
        }
        if (legacyGame.getDate() != 0) {
            game.setYear(Integer.valueOf(legacyGame.getDate()));
        }
        if (legacyGame.getDeveloper() != null && !legacyGame.getDeveloper().isEmpty()) {
            game.setDeveloper(m181c(legacyGame.getDeveloper()));
        }
        if (legacyGame.getPublisher() != null && !legacyGame.getPublisher().isEmpty()) {
            game.setPublisher(m182d(legacyGame.getPublisher()));
        }
        if (legacyGame.getTags() != null && !legacyGame.getTags().isEmpty()) {
            game.setTags(m183c(legacyGame.getTags().split(",")));
        }
        if (legacyGame.getUrl() != null && !legacyGame.getUrl().isEmpty()) {
            game.getLinks().add(new Link("URL", legacyGame.getUrl()));
        }
        if (legacyGame.getRsId() != 0) {
            String name = C0207a.m831a(MessageFormat.format("{0} - {1,number,#}", game.getTitle(), Integer.valueOf(legacyGame.getRsId())));
            Path target = Paths.get(RomStation.m43c().getProperty("path.games"), "downloads", name);
            Files.createDirectories(target, new FileAttribute[0]);
            game.setDirectory(target.toString());
            game.setManaged(true);
        } else if (legacyGame.isLink()) {
            if (legacyGame.getFilename() != null) {
                game.setDirectory(legacyGame.getDirectory().toString());
            }
        } else {
            String name2 = C0207a.m831a(MessageFormat.format("{0} - {1,number,#}", game.getTitle(), Integer.valueOf(legacyGame.getId())));
            Path target2 = Paths.get(RomStation.m43c().getProperty("path.games"), "legacy", name2);
            Files.createDirectories(target2, new FileAttribute[0]);
            game.setDirectory(target2.toString());
            game.setManaged(true);
        }
        if (legacyGame.getScreenshot() != null && !legacyGame.getScreenshot().isEmpty()) {
            Path source = Paths.get(legacyGame.getScreenshot(), new String[0]);
            if (legacyGame.isLink()) {
                game.setGraphic(new Image(source));
            } else if (Files.exists(source, new LinkOption[0])) {
                Path target3 = Paths.get(game.getDirectory(), "images", source.getFileName().toString());
                Files.createDirectories(target3, new FileAttribute[0]);
                Files.move(source, target3, StandardCopyOption.REPLACE_EXISTING);
                game.setGraphic(new Image(target3));
            }
        }
        if (legacyGame.getLastUse() != 0) {
            game.setLastUse(Long.valueOf(legacyGame.getLastUse()));
        }
        return game;
    }

    /* JADX INFO: renamed from: a */
    private GameFile m175a(Game game, C0055ab legacyGame) throws IOException {
        Path target;
        GameFile gameFile = new GameFile();
        int gameFileRid = 0;
        if (legacyGame.getRsId() != 0) {
            if (Files.isDirectory(Paths.get(legacyGame.getFilename(), new String[0]), new LinkOption[0])) {
                gameFileRid = m177a(String.valueOf(legacyGame.getRsId()));
            } else {
                gameFileRid = m177a(Paths.get(legacyGame.getFilename(), new String[0]).getFileName().toString().split("\\.")[0]);
            }
            if (gameFileRid != 0) {
                gameFile.setRid(Integer.valueOf(gameFileRid));
            }
        }
        gameFile.setName(legacyGame.getTitle());
        if (!game.isManaged() || legacyGame.isLink()) {
            gameFile.setDirectory(legacyGame.getDirectory().toString());
        } else {
            Path source = Paths.get(legacyGame.getFilename(), new String[0]);
            if (gameFileRid != 0) {
                target = Paths.get(game.getDirectory(), "files", String.format("%d - %d", Integer.valueOf(gameFileRid), Long.valueOf(Instant.now().getEpochSecond())));
            } else {
                target = Paths.get(game.getDirectory(), "files", String.format("%d - %d", Integer.valueOf(legacyGame.getId()), Long.valueOf(Instant.now().getEpochSecond())));
            }
            Files.createDirectories(target, new FileAttribute[0]);
            if (Files.isRegularFile(source, new LinkOption[0])) {
                Files.move(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            }
            gameFile.setDirectory(target.toString());
            gameFile.setManaged(true);
        }
        gameFile.setGame(game);
        gameFile.getMetas().put("legacy", "{\"id\": " + legacyGame.getId() + "}");
        return gameFile;
    }

    /* JADX INFO: renamed from: a */
    private GameProfile m176a(GameFile gameFile, C0055ab legacyGame) {
        GameProfile gameProfile = new GameProfile();
        gameProfile.setName("default");
        Path directory = Paths.get(gameFile.getDirectory(), new String[0]);
        Path file = Paths.get(legacyGame.getFilename(), new String[0]);
        if (legacyGame.getExecutable() != null && !legacyGame.getExecutable().isEmpty() && Files.isDirectory(file, new LinkOption[0])) {
            gameProfile.setPath(directory.resolve(legacyGame.getRelativeExecutable()).toString());
        } else {
            gameProfile.setPath(directory.resolve(file.getFileName()).toString());
        }
        gameProfile.setParameters(legacyGame.getParameters());
        gameProfile.setGameFile(gameFile);
        return gameProfile;
    }

    /* JADX INFO: renamed from: a */
    private int m177a(String value) throws IOException {
        if (this.f113j == null) {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/importer/legacy/games_files.json"));
            Throwable th = null;
            try {
                try {
                    JsonParser jsonParser = new JsonParser();
                    this.f113j = jsonParser.parse(reader).getAsJsonArray();
                    if (reader != null) {
                        if (0 != 0) {
                            try {
                                reader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            reader.close();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    throw th3;
                }
            } catch (Throwable th4) {
                if (reader != null) {
                    if (th != null) {
                        try {
                            reader.close();
                        } catch (Throwable th5) {
                            th.addSuppressed(th5);
                        }
                    } else {
                        reader.close();
                    }
                }
                throw th4;
            }
        }
        Optional<JsonElement> optional = StreamSupport.stream(this.f113j.spliterator(), false).filter(jsonElement -> {
            return jsonElement.getAsJsonObject().get("rename").getAsString().split("\\.")[0].equals(value);
        }).findAny();
        return ((Integer) optional.map(jsonElement2 -> {
            return Integer.valueOf(jsonElement2.getAsJsonObject().get("id").getAsInt());
        }).orElse(0)).intValue();
    }

    /* JADX INFO: renamed from: b */
    private System m178b(String value) throws IOException {
        if (this.f108e == null) {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/importer/legacy/systems.json"));
            Throwable th = null;
            try {
                try {
                    JsonParser jsonParser = new JsonParser();
                    this.f108e = jsonParser.parse(reader).getAsJsonArray();
                    if (reader != null) {
                        if (0 != 0) {
                            try {
                                reader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            reader.close();
                        }
                    }
                } catch (Throwable th3) {
                    if (reader != null) {
                        if (th != null) {
                            try {
                                reader.close();
                            } catch (Throwable th4) {
                                th.addSuppressed(th4);
                            }
                        } else {
                            reader.close();
                        }
                    }
                    throw th3;
                }
            } catch (Throwable th5) {
                th = th5;
                throw th5;
            }
        }
        Optional<JsonElement> optional = StreamSupport.stream(this.f108e.spliterator(), false).filter(jsonElement -> {
            for (JsonElement alias : jsonElement.getAsJsonObject().get("aliases").getAsJsonArray()) {
                if (alias.getAsString().equals(value)) {
                    return true;
                }
            }
            return false;
        }).findAny();
        if (optional.isPresent()) {
            JsonObject jsonObject = optional.get().getAsJsonObject();
            try {
                return (System) this.f106c.createQuery("select system from System system where system.rid = :rid", System.class).setParameter("rid", Integer.valueOf(jsonObject.get("id").getAsInt())).getSingleResult();
            } catch (NoResultException e) {
                return new System(Integer.valueOf(jsonObject.get("id").getAsInt()), jsonObject.get("name").getAsString(), new Image(jsonObject.get("graphic").getAsString()));
            }
        }
        try {
            return (System) this.f106c.createQuery("select system from System system where system.name like :name", System.class).setParameter("name", value).setMaxResults(1).getSingleResult();
        } catch (NoResultException e2) {
            return new System(null, value, null);
        }
    }

    /* JADX INFO: renamed from: a */
    private List<Genre> m179a(String[] values) throws IOException {
        LinkedList linkedList = new LinkedList();
        if (this.f109f == null) {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/importer/legacy/genres.json"));
            Throwable th = null;
            try {
                try {
                    JsonParser jsonParser = new JsonParser();
                    this.f109f = jsonParser.parse(reader).getAsJsonArray();
                    if (reader != null) {
                        if (0 != 0) {
                            try {
                                reader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            reader.close();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    throw th3;
                }
            } catch (Throwable th4) {
                if (reader != null) {
                    if (th != null) {
                        try {
                            reader.close();
                        } catch (Throwable th5) {
                            th.addSuppressed(th5);
                        }
                    } else {
                        reader.close();
                    }
                }
                throw th4;
            }
        }
        for (String value : values) {
            Optional<JsonElement> optional = StreamSupport.stream(this.f109f.spliterator(), false).filter(jsonElement -> {
                return jsonElement.getAsJsonObject().get("en").getAsString().equals(value) || jsonElement.getAsJsonObject().get("fr").getAsString().equals(value);
            }).findAny();
            if (optional.isPresent()) {
                JsonObject jsonObject = optional.get().getAsJsonObject();
                try {
                    linkedList.add(this.f106c.createQuery("select genre from Genre genre where genre.rid = :rid", Genre.class).setParameter("rid", Integer.valueOf(jsonObject.get("id").getAsInt())).getSingleResult());
                } catch (NoResultException e) {
                    I18n i18n = new I18n();
                    i18n.getTranslations().add(new Translation(this.f107d.get("en"), jsonObject.get("en").getAsString(), i18n));
                    i18n.getTranslations().add(new Translation(this.f107d.get("fr"), jsonObject.get("fr").getAsString(), i18n));
                    linkedList.add(new Genre(Integer.valueOf(jsonObject.get("id").getAsInt()), i18n));
                }
            } else {
                try {
                    linkedList.add(this.f106c.createQuery("select genre from Genre genre join genre.name.translations translation where translation.string like :string", Genre.class).setParameter("string", value).setMaxResults(1).getSingleResult());
                } catch (NoResultException e2) {
                    I18n i18n2 = new I18n();
                    i18n2.getTranslations().add(new Translation(this.f107d.get("en"), value, i18n2));
                    i18n2.getTranslations().add(new Translation(this.f107d.get("fr"), value, i18n2));
                    linkedList.add(new Genre(null, i18n2));
                }
            }
        }
        return linkedList;
    }

    /* JADX INFO: renamed from: b */
    private List<Language> m180b(String[] values) throws IOException {
        LinkedList linkedList = new LinkedList();
        if (this.f110g == null) {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/importer/legacy/languages.json"));
            Throwable th = null;
            try {
                try {
                    JsonParser jsonParser = new JsonParser();
                    this.f110g = jsonParser.parse(reader).getAsJsonArray();
                    if (reader != null) {
                        if (0 != 0) {
                            try {
                                reader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            reader.close();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    throw th3;
                }
            } catch (Throwable th4) {
                if (reader != null) {
                    if (th != null) {
                        try {
                            reader.close();
                        } catch (Throwable th5) {
                            th.addSuppressed(th5);
                        }
                    } else {
                        reader.close();
                    }
                }
                throw th4;
            }
        }
        for (String value : values) {
            Optional<JsonElement> optional = StreamSupport.stream(this.f110g.spliterator(), false).filter(jsonElement -> {
                return jsonElement.getAsJsonObject().get("en").getAsString().equals(value) || jsonElement.getAsJsonObject().get("fr").getAsString().equals(value);
            }).findAny();
            if (optional.isPresent()) {
                JsonObject jsonObject = optional.get().getAsJsonObject();
                try {
                    linkedList.add(this.f106c.createQuery("select language from Language language where language.rid = :rid", Language.class).setParameter("rid", Integer.valueOf(jsonObject.get("id").getAsInt())).getSingleResult());
                } catch (NoResultException e) {
                    I18n i18n = new I18n();
                    i18n.getTranslations().add(new Translation(this.f107d.get("en"), jsonObject.get("en").getAsString(), i18n));
                    i18n.getTranslations().add(new Translation(this.f107d.get("fr"), jsonObject.get("fr").getAsString(), i18n));
                    linkedList.add(new Language(Integer.valueOf(jsonObject.get("id").getAsInt()), i18n, new Image(jsonObject.get("graphic").getAsString())));
                }
            } else {
                try {
                    linkedList.add(this.f106c.createQuery("select language from Language language join language.name.translations translation where translation.string like :string", Language.class).setParameter("string", value).setMaxResults(1).getSingleResult());
                } catch (NoResultException e2) {
                    I18n i18n2 = new I18n();
                    i18n2.getTranslations().add(new Translation(this.f107d.get("fr"), value, i18n2));
                    i18n2.getTranslations().add(new Translation(this.f107d.get("en"), value, i18n2));
                    linkedList.add(new Language(null, i18n2));
                }
            }
        }
        return linkedList;
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0066 */
    /* JADX INFO: renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.romstation.application.database.entity.Developer m181c(java.lang.String r7) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.C0056ac.m181c(java.lang.String):org.romstation.application.database.entity.Developer");
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0066 */
    /* JADX INFO: renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.romstation.application.database.entity.Publisher m182d(java.lang.String r7) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.C0056ac.m182d(java.lang.String):org.romstation.application.database.entity.Publisher");
    }

    /* JADX INFO: renamed from: c */
    private List<Tag> m183c(String[] values) {
        LinkedList linkedList = new LinkedList();
        for (String value : values) {
            try {
                linkedList.add(this.f106c.createQuery("select tag from Tag tag left join tag.name.translations translation where translation.string like :string", Tag.class).setParameter("string", value).setMaxResults(1).getSingleResult());
            } catch (NoResultException e) {
                I18n i18n = new I18n();
                i18n.getTranslations().add(new Translation(this.f107d.get("en"), value, i18n));
                i18n.getTranslations().add(new Translation(this.f107d.get("fr"), value, i18n));
                linkedList.add(new Tag(i18n));
            }
        }
        return linkedList;
    }
}
