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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.romstation.application.C0023W;
import org.romstation.application.C0060ag;
import org.romstation.application.C0064ak;
import org.romstation.application.C0081b;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.Link;
import org.romstation.application.database.entity.Script;
import org.romstation.application.database.entity.System;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0222g;
import org.romstation.application.p000io.C0207a;
import org.romstation.application.p000io.ChecksumException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: renamed from: org.romstation.application.task.i */
/* JADX INFO: compiled from: EmulatorFileDownloadTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/i.class */
public class C0241i extends Task<EmulatorFile> {

    /* JADX INFO: renamed from: b */
    private C0064ak f632b;

    /* JADX INFO: renamed from: c */
    private C0254v f633c;

    /* JADX INFO: renamed from: d */
    private AbstractC0234b f634d;

    /* JADX INFO: renamed from: e */
    private C0231B f635e;

    /* JADX INFO: renamed from: f */
    private Path f636f;

    /* JADX INFO: renamed from: g */
    private EntityManager f637g;

    /* JADX INFO: renamed from: i */
    private final C0240h f639i;

    /* JADX INFO: renamed from: j */
    private XPath f640j;

    /* JADX INFO: renamed from: a */
    private final int f631a = 1000;

    /* JADX INFO: renamed from: h */
    private final List<System> f638h = new LinkedList();

    public C0241i(C0240h context) {
        this.f639i = context;
        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.f640j = xPathFactory.newXPath();
    }

    /* JADX INFO: renamed from: a */
    public C0240h m1023a() {
        return this.f639i;
    }

    /* JADX INFO: renamed from: b */
    public C0064ak m1024b() {
        return this.f632b;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("emulator.download.task.title"));
    }

    protected void cancelled() {
        if (this.f633c != null && this.f633c.isRunning()) {
            this.f633c.cancel(false);
        }
        if (this.f634d != null && this.f634d.isRunning()) {
            this.f634d.cancel(false);
        }
        if (this.f635e != null && this.f635e.isRunning()) {
            this.f635e.cancel(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public EmulatorFile call() throws Exception {
        try {
            m1026d();
            m1027e();
            EmulatorFile emulatorFile = m1042f();
            updateValue(emulatorFile);
            return emulatorFile;
        } catch (InterruptedException | CancellationException e) {
            return null;
        }
    }

    /* JADX INFO: renamed from: d */
    private void m1026d() throws Exception {
        updateMessage(RomStation.m44d().getString("emulator.download.task.message.downloading"));
        this.f636f = Paths.get("cache/downloads/emulators", this.f640j.evaluate("/EmulatorFile/@id", this.f639i.m1022d()));
        Files.createDirectories(this.f636f.getParent(), new FileAttribute[0]);
        JsonParser parser = new JsonParser();
        String protocol = this.f639i.m1019a().get("protocol").getAsString();
        JsonObject credential = parser.parse(C0023W.m66b(this.f639i.m1019a().get(protocol).getAsString())).getAsJsonObject();
        this.f633c = new C0254v(credential.get("url").getAsString(), this.f636f);
        this.f633c.m1163a(true);
        this.f633c.m1165b(true);
        this.f633c.m1167a(new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("file_path", this.f639i.m1019a().getAsJsonObject("file").get("path").getAsString()).toString());
        this.f633c.progressProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(this.f633c.getWorkDone(), this.f633c.getTotalWork());
        });
        ScheduledExecutorService progressService = Executors.newSingleThreadScheduledExecutor();
        this.f633c.workDoneProperty().addListener((observableValue, previousValue, currentValue) -> {
            if (this.f632b == null) {
                this.f632b = new C0064ak(currentValue.longValue(), this.f633c.getTotalWork());
                if (!progressService.isShutdown()) {
                    progressService.scheduleWithFixedDelay(() -> {
                        this.f632b.m248h();
                    }, 1000L, 1000L, TimeUnit.MILLISECONDS);
                    return;
                }
                return;
            }
            this.f632b.m240a(currentValue.longValue() - previousValue.longValue());
        });
        this.f633c.run();
        try {
            this.f633c.get();
        } finally {
            progressService.shutdown();
        }
    }

    /* JADX INFO: renamed from: e */
    private void m1027e() throws Exception {
        this.f634d = new C0233a(this.f636f);
        this.f634d.progressProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(this.f634d.getWorkDone(), this.f634d.getTotalWork());
        });
        updateMessage(RomStation.m44d().getString("emulator.download.task.message.verifying"));
        this.f634d.run();
        String localChecksum = (String) this.f634d.get();
        String serverChecksum = this.f639i.m1019a().getAsJsonObject("file").get("crc32").getAsString();
        if (!serverChecksum.equals(localChecksum)) {
            Files.delete(this.f636f);
            throw new ChecksumException("Checksum invalid");
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1028a(Path source, Path target) throws Exception {
        this.f635e = new C0231B(source, target);
        this.f635e.progressProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(this.f635e.getWorkDone(), this.f635e.getTotalWork());
        });
        updateMessage(RomStation.m44d().getString("emulator.download.task.message.unpacking"));
        this.f635e.run();
        this.f635e.get();
        Files.delete(source);
    }

    /* JADX INFO: renamed from: a */
    private System m1029a(JsonObject jsonObject) throws Exception {
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

    /* JADX INFO: renamed from: a */
    private void m1030a(JsonArray jsonArray) throws Exception {
        List<System> entities = this.f637g.createNamedQuery(System.f508b, System.class).getResultList();
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JsonElement element = (JsonElement) it.next();
            JsonObject jsonObject = element.getAsJsonObject();
            Optional<System> optional = entities.stream().filter(system -> {
                return Objects.equals(system.getRid(), Integer.valueOf(jsonObject.get("id").getAsInt()));
            }).findAny();
            this.f638h.add(optional.isPresent() ? optional.get() : m1029a(jsonObject));
        }
    }

    /* JADX INFO: renamed from: a */
    private List<System> m1031a(Element element) {
        List<System> result = new LinkedList<>();
        NodeList nodeList = element.getElementsByTagName("System");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element systemElement = (Element) nodeList.item(i);
            Optional<System> optional = this.f638h.stream().filter(system -> {
                return Objects.equals(system.getRid(), Integer.valueOf(Integer.parseInt(systemElement.getAttribute("id"))));
            }).findAny();
            result.getClass();
            optional.ifPresent((v1) -> {
                r1.add(v1);
            });
        }
        return result;
    }

    /* JADX INFO: renamed from: a */
    private List<Script> m1032a(Path root, Element element) {
        List<Script> scripts = new LinkedList<>();
        NodeList nodeList = element.getElementsByTagName("Script");
        for (int i = 0; i < nodeList.getLength(); i++) {
            scripts.add(m1033b(root, (Element) nodeList.item(i)));
        }
        return scripts;
    }

    /* JADX INFO: renamed from: b */
    private Script m1033b(Path root, Element element) {
        Script script = new Script();
        script.setPath(root.resolve(element.getAttribute("path")).toString());
        return script;
    }

    /* JADX INFO: renamed from: b */
    private Map<String, String> m1034b(Element element) {
        Map<String, String> metas = new HashMap<>();
        NodeList nodeList = element.getElementsByTagName("Meta");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element meta = (Element) nodeList.item(i);
            metas.put(meta.getAttribute("key"), meta.hasAttribute("value") ? meta.getAttribute("value") : null);
        }
        return metas;
    }

    /* JADX INFO: renamed from: c */
    private List<Link> m1035c(Element element) {
        List<Link> links = new LinkedList<>();
        NodeList nodeList = element.getElementsByTagName("Link");
        for (int i = 0; i < nodeList.getLength(); i++) {
            links.add(m1036d((Element) nodeList.item(i)));
        }
        return links;
    }

    /* JADX INFO: renamed from: d */
    private Link m1036d(Element element) {
        Link link = new Link();
        link.setName(element.getAttribute("name"));
        link.setExternal(Boolean.parseBoolean(element.getAttribute("external")));
        if (link.isExternal()) {
            link.setLocation(element.getAttribute("location"));
        } else {
            link.setLocation(C0217b.m961b() + element.getAttribute("location"));
        }
        return link;
    }

    /* JADX INFO: renamed from: a */
    private Image m1037a(Path output, String imagePath, String rename) {
        Path target = output.resolve(rename + imagePath.substring(imagePath.lastIndexOf(46)));
        try {
            URL url = new URL(C0217b.m961b() + imagePath);
            try {
                InputStream stream = url.openStream();
                Throwable th = null;
                try {
                    try {
                        Files.copy(stream, target, StandardCopyOption.REPLACE_EXISTING);
                        Image image = new Image(target);
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
                        return image;
                    } catch (Throwable th3) {
                        if (stream != null) {
                            if (th != null) {
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
                } catch (Throwable th5) {
                    th = th5;
                    throw th5;
                }
            } catch (IOException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                return null;
            }
        } catch (MalformedURLException exception2) {
            RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
            return null;
        }
    }

    /* JADX INFO: renamed from: e */
    private Emulator m1038e(Element element) throws IOException {
        Path dir = Paths.get(RomStation.m43c().getProperty("path.emulators"), "downloads", element.getAttribute("directory"));
        Emulator emulator = new Emulator();
        emulator.setRid(Integer.valueOf(element.getAttribute("id")));
        emulator.setName(element.getAttribute("name"));
        emulator.setDirectory(dir.toString());
        Path graphicOutputPath = dir.resolve("images");
        Files.createDirectories(graphicOutputPath, new FileAttribute[0]);
        emulator.setGraphic(m1037a(graphicOutputPath, element.getAttribute("image"), emulator.getName()));
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == 1) {
                switch (node.getNodeName()) {
                    case "Links":
                        emulator.setLinks(m1035c((Element) node));
                        break;
                    case "Metas":
                        emulator.setMetas(m1034b((Element) node));
                        break;
                }
            }
        }
        emulator.setManaged(true);
        return emulator;
    }

    /* JADX INFO: renamed from: a */
    private EmulatorFile m1039a(Emulator emulator, Element element) {
        EmulatorFile emulatorFile = new EmulatorFile();
        emulatorFile.setRid(Integer.valueOf(element.getAttribute("id")));
        emulatorFile.setName(element.getAttribute("name"));
        Path fileOutputPath = Paths.get(emulator.getDirectory(), "files", element.getAttribute("directory"));
        emulatorFile.setDirectory(fileOutputPath.toString());
        emulatorFile.setEmulator(emulator);
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == 1) {
                switch (node.getNodeName()) {
                    case "EmulatorProfiles":
                        emulatorFile.setProfiles(m1040a(emulatorFile, (Element) node));
                        break;
                    case "Scripts":
                        emulatorFile.setScripts(m1032a(fileOutputPath, (Element) node));
                        break;
                    case "Metas":
                        emulatorFile.setMetas(m1034b((Element) node));
                        break;
                }
            }
        }
        emulatorFile.setManaged(true);
        return emulatorFile;
    }

    /* JADX INFO: renamed from: a */
    private List<EmulatorProfile> m1040a(EmulatorFile emulatorFile, Element element) {
        List<EmulatorProfile> profiles = new LinkedList<>();
        NodeList nodeList = element.getElementsByTagName("EmulatorProfile");
        for (int i = 0; i < nodeList.getLength(); i++) {
            profiles.add(m1041b(emulatorFile, (Element) nodeList.item(i)));
        }
        return profiles;
    }

    /* JADX INFO: renamed from: b */
    private EmulatorProfile m1041b(EmulatorFile emulatorFile, Element element) {
        EmulatorProfile emulatorProfile = new EmulatorProfile();
        emulatorProfile.setName(element.getAttribute("name"));
        if (element.hasAttribute("parameters")) {
            emulatorProfile.setParameters(element.getAttribute("parameters"));
        }
        emulatorProfile.setPath(Paths.get(emulatorFile.getDirectory(), element.getAttribute("path")).toString());
        emulatorProfile.setWorkingDirectory(element.getAttribute("working_directory"));
        emulatorProfile.setEmulatorFile(emulatorFile);
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == 1) {
                switch (node.getNodeName()) {
                    case "Systems":
                        emulatorProfile.setSystems(m1031a((Element) node));
                        break;
                    case "Scripts":
                        emulatorProfile.setScripts(m1032a(Paths.get(emulatorFile.getDirectory(), new String[0]), (Element) node));
                        break;
                    case "Metas":
                        emulatorProfile.setMetas(m1034b((Element) node));
                        break;
                }
            }
        }
        return emulatorProfile;
    }

    /* JADX INFO: renamed from: f */
    private EmulatorFile m1042f() throws Exception {
        Emulator emulator;
        try {
            this.f637g = C0081b.m309c();
            m1030a(this.f639i.m1020b());
            try {
                emulator = (Emulator) this.f637g.createNamedQuery(Emulator.f414c, Emulator.class).setParameter("rid", Integer.valueOf(((Number) this.f640j.evaluate("/Emulator/@id", this.f639i.m1021c(), XPathConstants.NUMBER)).intValue())).getSingleResult();
            } catch (NoResultException e) {
                emulator = m1038e(this.f639i.m1021c().getDocumentElement());
            }
            EmulatorFile emulatorFile = m1039a(emulator, this.f639i.m1022d().getDocumentElement());
            emulator.getFiles().add(emulatorFile);
            Path emulatorFileDirectory = Paths.get(emulatorFile.getDirectory(), new String[0]);
            if (Files.exists(emulatorFileDirectory, new LinkOption[0])) {
                C0207a.m828a(emulatorFileDirectory);
            }
            m1028a(this.f636f, emulatorFileDirectory);
            this.f637g.getTransaction().begin();
            this.f637g.persist(emulator);
            this.f637g.getTransaction().commit();
            if (this.f637g != null) {
                this.f637g.close();
            }
            return emulatorFile;
        } catch (Throwable th) {
            if (this.f637g != null) {
                this.f637g.close();
            }
            throw th;
        }
    }
}
