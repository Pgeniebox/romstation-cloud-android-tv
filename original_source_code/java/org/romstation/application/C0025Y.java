package org.romstation.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.Link;
import org.romstation.application.database.entity.System;
import org.romstation.application.task.C0233a;

/* JADX INFO: renamed from: org.romstation.application.Y */
/* JADX INFO: compiled from: EmulatorImporterTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/Y.class */
public class C0025Y extends Task<List<Emulator>> {

    /* JADX INFO: renamed from: a */
    private final List<C0024X> f34a;

    /* JADX INFO: renamed from: b */
    private final List<Emulator> f35b = new LinkedList();

    /* JADX INFO: renamed from: c */
    private Pattern f36c = Pattern.compile("/[^/]+\\)");

    /* JADX INFO: renamed from: d */
    private Pattern f37d = Pattern.compile("%(?<cmd>\\w+)%");

    /* JADX INFO: renamed from: e */
    private Pattern f38e = Pattern.compile("(?<key>-{1,2}\\w+)=\"(?<value>[^\"]*)\"");

    /* JADX INFO: renamed from: f */
    private EntityManager f39f;

    /* JADX INFO: renamed from: g */
    private JsonArray f40g;

    /* JADX INFO: renamed from: h */
    private JsonArray f41h;

    /* JADX INFO: renamed from: i */
    private JsonArray f42i;

    public C0025Y(List<C0024X> emulators) {
        this.f34a = emulators;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("legacyEmulatorImporterTask.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<Emulator> call() throws Exception {
        this.f39f = C0081b.m309c();
        try {
            for (C0024X legacyEmulator : this.f34a) {
                updateMessage(MessageFormat.format(RomStation.m44d().getString("legacyEmulatorImporterTask.message"), legacyEmulator.getName()));
                if (isCancelled()) {
                    break;
                }
                Emulator emulator = m69a(legacyEmulator);
                EmulatorFile emulatorFile = m70a(emulator, legacyEmulator);
                if (!legacyEmulator.getProfiles().isEmpty()) {
                    emulatorFile.getProfiles().add(m71a(emulatorFile, legacyEmulator.getProfiles().get(0)));
                    for (C0026Z legacyEmulatorProfile : legacyEmulator.getProfiles()) {
                        emulatorFile.getProfiles().add(m72b(emulatorFile, legacyEmulatorProfile));
                    }
                }
                emulator.getFiles().add(emulatorFile);
                this.f39f.getTransaction().begin();
                this.f39f.persist(emulator);
                this.f39f.getTransaction().commit();
                this.f35b.add(emulator);
                updateValue(this.f35b);
                updateProgress(this.f35b.size(), this.f34a.size());
            }
            this.f39f.close();
            return this.f35b;
        } catch (Throwable th) {
            this.f39f.close();
            throw th;
        }
    }

    /* JADX INFO: renamed from: a */
    private Emulator m69a(C0024X legacyEmulator) throws IOException {
        if (this.f40g == null) {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/importer/legacy/emulators.json"));
            Throwable th = null;
            try {
                try {
                    JsonParser jsonParser = new JsonParser();
                    this.f40g = jsonParser.parse(reader).getAsJsonArray();
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
        Optional<JsonElement> optional = StreamSupport.stream(this.f40g.spliterator(), false).filter(jsonElement -> {
            for (JsonElement alias : jsonElement.getAsJsonObject().get("aliases").getAsJsonArray()) {
                if (alias.getAsString().equals(legacyEmulator.getName())) {
                    return true;
                }
            }
            return false;
        }).findAny();
        if (optional.isPresent()) {
            JsonObject jsonObject = optional.get().getAsJsonObject();
            try {
                return (Emulator) this.f39f.createQuery("select emulator from Emulator emulator where emulator.rid = :rid", Emulator.class).setParameter("rid", Integer.valueOf(jsonObject.get("id").getAsInt())).getSingleResult();
            } catch (NoResultException e) {
                Emulator emulator = new Emulator();
                emulator.setRid(Integer.valueOf(jsonObject.get("id").getAsInt()));
                emulator.setName(jsonObject.get("name").getAsString());
                for (JsonElement jsonElement2 : jsonObject.getAsJsonArray("links")) {
                    JsonObject jsonLinkObject = jsonElement2.getAsJsonObject();
                    emulator.getLinks().add(new Link(jsonLinkObject.get("name").getAsString(), jsonLinkObject.get("location").getAsString(), Boolean.valueOf(jsonLinkObject.get("external").getAsBoolean())));
                }
                Path path = Paths.get(RomStation.m43c().getProperty("path.emulators"), "downloads", emulator.getName());
                Files.createDirectories(path, new FileAttribute[0]);
                emulator.setDirectory(path.toString());
                emulator.setManaged(true);
                return emulator;
            }
        }
        try {
            return (Emulator) this.f39f.createQuery("select emulator from Emulator emulator where emulator.name like :name", Emulator.class).setParameter("name", legacyEmulator.getName()).setMaxResults(1).getSingleResult();
        } catch (NoResultException e2) {
            Emulator emulator2 = new Emulator();
            emulator2.setName(legacyEmulator.getName());
            emulator2.setDirectory(Files.createDirectories(Paths.get(RomStation.m43c().getProperty("path.emulators"), "legacy", legacyEmulator.getName()), new FileAttribute[0]).toString());
            emulator2.setManaged(true);
            return emulator2;
        }
    }

    /* JADX INFO: renamed from: a */
    private EmulatorFile m70a(Emulator emulator, C0024X legacyEmulator) throws IOException {
        JsonObject jsonObject = m74b(legacyEmulator);
        EmulatorFile emulatorFile = new EmulatorFile();
        emulatorFile.setEmulator(emulator);
        emulatorFile.getMetas().put("legacy", "{\"name\": \"" + legacyEmulator.getName() + "\"}");
        if (jsonObject != null) {
            emulatorFile.setRid(Integer.valueOf(jsonObject.get("id").getAsInt()));
            emulatorFile.setName(jsonObject.get("name").getAsString());
        } else {
            emulatorFile.setName(legacyEmulator.getName() + " (RomStation 1.0)");
        }
        Path source = legacyEmulator.getDirectory();
        Path target = Paths.get(emulator.getDirectory(), "files", String.format("%s - %d", emulatorFile.getName(), Long.valueOf(Instant.now().getEpochSecond())));
        Files.createDirectories(target, new FileAttribute[0]);
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        emulatorFile.setDirectory(target.toString());
        emulatorFile.setManaged(true);
        return emulatorFile;
    }

    /* JADX INFO: renamed from: a */
    private EmulatorProfile m71a(EmulatorFile emulatorFile, C0026Z legacyEmulatorProfile) {
        EmulatorProfile emulatorProfile = new EmulatorProfile();
        emulatorProfile.setName(emulatorFile.getEmulator().getName());
        emulatorProfile.setPath(Paths.get(emulatorFile.getDirectory(), new String[0]).resolve(legacyEmulatorProfile.m83c()).toString());
        emulatorProfile.setWorkingDirectory("${emulator.file.directory}");
        emulatorProfile.setEmulatorFile(emulatorFile);
        return emulatorProfile;
    }

    /* JADX INFO: renamed from: b */
    private EmulatorProfile m72b(EmulatorFile emulatorFile, C0026Z legacyEmulatorProfile) throws IOException {
        EmulatorProfile emulatorProfile = new EmulatorProfile();
        emulatorProfile.setName(legacyEmulatorProfile.m81b());
        emulatorProfile.setPath(Paths.get(emulatorFile.getDirectory(), new String[0]).resolve(legacyEmulatorProfile.m83c()).toString());
        emulatorProfile.setWorkingDirectory("${emulator.file.directory}");
        emulatorProfile.setEmulatorFile(emulatorFile);
        emulatorProfile.getSystems().add(m73a(legacyEmulatorProfile.m81b()));
        if (legacyEmulatorProfile.m86e() != null) {
            String parameters = m76c(m75b(legacyEmulatorProfile.m86e()));
            Matcher matcher = this.f36c.matcher(parameters);
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            jsonObject.add("commands", jsonArray);
            while (matcher.find()) {
                String group = matcher.group();
                switch (group) {
                    case "/unzip()":
                    case "/unrar()":
                        String meta = emulatorProfile.getMetas().getOrDefault("unpack", "{\"formats\": [], \"extensions\": [\".iso\", \".cso\", \".pbp\", \".elf\", \".prx\", \".cue\", \".ccd\", \".toc\", \".m3u\", \".mdf\", \".nrg\", \".bin\", \".img\", \".dol\", \".gcm\", \".tgc\", \".wbfs\", \".ciso\", \".gcz\", \".wad\", \".dff\", \".chd\", \".gdi\", \".cdr\", \".cdi\", \".nds\", \".sub\", \".mds\", \".ecm\", \".gba\"]}");
                        JsonParser parser = new JsonParser();
                        JsonObject metaJson = parser.parse(meta).getAsJsonObject();
                        metaJson.getAsJsonArray("formats").add(group.equals("/unzip()") ? ".zip" : ".rar");
                        emulatorProfile.getMetas().put("unpack", metaJson.toString());
                        break;
                    default:
                        jsonArray.add(group);
                        break;
                }
                parameters = matcher.replaceFirst("").trim();
                matcher.reset(parameters);
            }
            emulatorProfile.setParameters(parameters);
            emulatorProfile.getMetas().put("legacy", jsonObject.toString());
        }
        return emulatorProfile;
    }

    /* JADX INFO: renamed from: a */
    private System m73a(String value) throws IOException {
        if (this.f41h == null) {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/importer/legacy/systems.json"));
            Throwable th = null;
            try {
                try {
                    JsonParser jsonParser = new JsonParser();
                    this.f41h = jsonParser.parse(reader).getAsJsonArray();
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
        Optional<JsonElement> optional = StreamSupport.stream(this.f41h.spliterator(), false).filter(jsonElement -> {
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
                return (System) this.f39f.createQuery("select system from System system where system.rid = :rid", System.class).setParameter("rid", Integer.valueOf(jsonObject.get("id").getAsInt())).getSingleResult();
            } catch (NoResultException e) {
                return new System(Integer.valueOf(jsonObject.get("id").getAsInt()), jsonObject.get("name").getAsString(), new Image(jsonObject.get("graphic").getAsString()));
            }
        }
        try {
            return (System) this.f39f.createQuery("select system from System system where system.name like :name", System.class).setParameter("name", value).setMaxResults(1).getSingleResult();
        } catch (NoResultException e2) {
            return new System(null, value, null);
        }
    }

    /* JADX INFO: renamed from: b */
    private JsonObject m74b(C0024X emulator) throws IOException {
        if (this.f42i == null) {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/importer/legacy/emulators_files.json"));
            Throwable th = null;
            try {
                try {
                    JsonParser jsonParser = new JsonParser();
                    this.f42i = jsonParser.parse(reader).getAsJsonArray();
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
        for (C0026Z emulatorProfile : emulator.getProfiles()) {
            try {
                C0233a task = new C0233a(Paths.get(emulatorProfile.m84d(), new String[0]));
                task.run();
                String crc32 = (String) task.get();
                for (JsonElement jsonElement : this.f42i) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.get("crc32").getAsString().equals(crc32)) {
                        return jsonObject;
                    }
                }
            } catch (InterruptedException | ExecutionException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: b */
    private String m75b(String parameters) {
        Matcher matcher = this.f37d.matcher(parameters);
        while (matcher.find()) {
            parameters = parameters.replace(matcher.group(), "${" + matcher.group("cmd").toLowerCase() + "}");
        }
        return parameters;
    }

    /* JADX INFO: renamed from: c */
    private String m76c(String parameters) {
        Matcher matcher = this.f38e.matcher(parameters);
        while (matcher.find()) {
            parameters = parameters.replace(matcher.group(), "\"" + matcher.group("key") + "=" + matcher.group("value") + "\"");
        }
        return parameters;
    }
}
