package org.romstation.application;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.freedesktop.gstreamer.Gst;
import org.jruby.Ruby;
import org.jruby.RubyObject;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/RomStation.class */
public class RomStation extends Application {

    /* JADX INFO: renamed from: a */
    public static final int f15a = 229;

    /* JADX INFO: renamed from: b */
    public static final String f16b = "2.9.2";

    /* JADX INFO: renamed from: c */
    private static final Logger f17c = Logger.getLogger("global");

    /* JADX INFO: renamed from: d */
    private static Properties f18d;

    /* JADX INFO: renamed from: e */
    private static ResourceBundle f19e;

    /* JADX INFO: renamed from: f */
    private static RomStation f20f;

    /* JADX INFO: renamed from: g */
    private Thread f21g;

    /* JADX INFO: renamed from: h */
    private FileLock f22h;

    /* JADX INFO: renamed from: i */
    private Exception f23i;

    /* JADX INFO: renamed from: j */
    private RomStationController f24j;

    /* JADX INFO: renamed from: k */
    private final List<RubyObject> f25k = new LinkedList();

    public static void main(String[] args) {
        launch(args);
    }

    public RomStation() {
        f20f = this;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized RomStation m41a() {
        return f20f;
    }

    /* JADX INFO: renamed from: b */
    public static Logger m42b() {
        return f17c;
    }

    /* JADX INFO: renamed from: c */
    public static Properties m43c() {
        return f18d;
    }

    /* JADX INFO: renamed from: d */
    public static ResourceBundle m44d() {
        return f19e;
    }

    /* JADX INFO: renamed from: e */
    public static String m45e() {
        return Paths.get("themes", f18d.getProperty("application.theme"), "theme.css").toUri().toString();
    }

    public void init() {
        try {
            m51g();
            this.f21g = new Thread(this::m46f);
            Runtime.getRuntime().addShutdownHook(this.f21g);
            f18d = m48a(Paths.get("config/romstation.properties", new String[0]));
            Locale.setDefault(Locale.forLanguageTag(f18d.getProperty("application.locale")));
            f19e = m50a(Paths.get("i18n", new String[0]), Locale.getDefault());
            Path currentDirectory = Paths.get("", new String[0]).toAbsolutePath();
            if (!Files.isWritable(currentDirectory)) {
                throw new AccessDeniedException(currentDirectory.toString());
            }
            Path applicationUserDataPath = Files.createDirectories(C0004E.m9b(), new FileAttribute[0]);
            RandomAccessFile randomAccessFile = new RandomAccessFile(applicationUserDataPath.resolve("romstation.lck").toFile(), "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            this.f22h = fileChannel.tryLock();
            if (this.f22h == null) {
                throw new ApplicationAlreadyRunningException();
            }
            m52h();
            ExecutorService executorService = Executors.newCachedThreadPool();
            Future<?> scriptsFuture = executorService.submit(this::m53i);
            Future<?> databaseFuture = executorService.submit(C0081b::m307a);
            Future<?> browserFuture = executorService.submit(C0027a::m90a);
            Future<?> viewFuture = executorService.submit(this::m54j);
            executorService.shutdown();
            scriptsFuture.get();
            databaseFuture.get();
            browserFuture.get();
            viewFuture.get();
            m56l();
        } catch (Exception exception) {
            this.f23i = exception;
        }
    }

    public void start(Stage stage) {
        try {
            if (this.f23i != null) {
                throw this.f23i;
            }
            m57m();
            RomStationController.f786a.post(new C0165ce());
            this.f24j.m1315a(stage);
            stage.show();
        } catch (Exception exception) {
            m47a(exception);
            System.exit(0);
        }
    }

    public void stop() {
        Runtime runtime;
        try {
            m58n();
            RomStationController.f786a.post(new C0166cf());
            m49b(Paths.get("config/romstation.properties", new String[0]));
            runtime = Runtime.getRuntime();
        } catch (Exception exception) {
            m47a(exception);
            runtime = Runtime.getRuntime();
        } finally {
            Runtime.getRuntime().removeShutdownHook(this.f21g);
            m46f();
        }
    }

    /* JADX INFO: renamed from: f */
    private void m46f() {
        if (Ruby.isGlobalRuntimeReady()) {
            Ruby.getGlobalRuntime().tearDown();
        }
        if (C0027a.m92c()) {
            C0027a.m93d();
        }
        if (C0081b.m308b()) {
            C0081b.m310d();
        }
        if (C0001B.m3a()) {
            Gst.deinit();
        }
        if (this.f22h != null) {
            try {
                this.f22h.acquiredBy().close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private void m47a(Exception exception) {
        f17c.log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        if (f19e != null) {
            if (exception instanceof ApplicationAlreadyRunningException) {
                ApplicationAlert alert = new ApplicationAlert(f19e.getString("applicationAlreadyRunningAlert.header"), f19e.getString("applicationAlreadyRunningAlert.content"), Alert.AlertType.ERROR);
                alert.showAndWait();
            } else if (exception instanceof AccessDeniedException) {
                ApplicationAlert alert2 = new ApplicationAlert(f19e.getString("writePermissionErrorAlert.header"), f19e.getString("writePermissionErrorAlert.content"), Alert.AlertType.ERROR);
                alert2.showAndWait();
            } else {
                C0069ap exceptionDialog = new C0069ap(exception);
                exceptionDialog.showAndWait();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private Properties m48a(Path path) throws IOException {
        Properties defaults = new Properties();
        defaults.load(getClass().getResourceAsStream("/config/romstation.properties"));
        Properties properties = new Properties(defaults);
        try {
            InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
            Throwable th = null;
            try {
                properties.load(inputStream);
                if (inputStream != null) {
                    if (0 != 0) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            } catch (Throwable th3) {
                if (inputStream != null) {
                    if (0 != 0) {
                        try {
                            inputStream.close();
                        } catch (Throwable th4) {
                            th.addSuppressed(th4);
                        }
                    } else {
                        inputStream.close();
                    }
                }
                throw th3;
            }
        } catch (Exception exception) {
            m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            if (Locale.getDefault().getLanguage().equals("fr")) {
                properties.setProperty("application.locale", "fr");
            }
        }
        return properties;
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x004a */
    /* JADX INFO: renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void m49b(java.nio.file.Path r5) throws java.io.IOException {
        /*
            r4 = this;
            r0 = r5
            java.nio.file.Path r0 = r0.getParent()
            r1 = 0
            java.nio.file.attribute.FileAttribute[] r1 = new java.nio.file.attribute.FileAttribute[r1]
            java.nio.file.Path r0 = java.nio.file.Files.createDirectories(r0, r1)
            r0 = r5
            r1 = 0
            java.nio.file.OpenOption[] r1 = new java.nio.file.OpenOption[r1]
            java.io.OutputStream r0 = java.nio.file.Files.newOutputStream(r0, r1)
            r6 = r0
            r0 = 0
            r7 = r0
            java.util.Properties r0 = org.romstation.application.RomStation.f18d     // Catch: java.lang.Throwable -> L42 java.lang.Throwable -> L4a
            r1 = r6
            r2 = 0
            r0.store(r1, r2)     // Catch: java.lang.Throwable -> L42 java.lang.Throwable -> L4a
            r0 = r6
            if (r0 == 0) goto L6d
            r0 = r7
            if (r0 == 0) goto L3b
            r0 = r6
            r0.close()     // Catch: java.lang.Throwable -> L30
            goto L6d
        L30:
            r8 = move-exception
            r0 = r7
            r1 = r8
            r0.addSuppressed(r1)
            goto L6d
        L3b:
            r0 = r6
            r0.close()
            goto L6d
        L42:
            r8 = move-exception
            r0 = r8
            r7 = r0
            r0 = r8
            throw r0     // Catch: java.lang.Throwable -> L4a
        L4a:
            r9 = move-exception
            r0 = r6
            if (r0 == 0) goto L6a
            r0 = r7
            if (r0 == 0) goto L66
            r0 = r6
            r0.close()     // Catch: java.lang.Throwable -> L5b
            goto L6a
        L5b:
            r10 = move-exception
            r0 = r7
            r1 = r10
            r0.addSuppressed(r1)
            goto L6a
        L66:
            r0 = r6
            r0.close()
        L6a:
            r0 = r9
            throw r0
        L6d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.RomStation.m49b(java.nio.file.Path):void");
    }

    /* JADX INFO: renamed from: a */
    private ResourceBundle m50a(Path path, Locale locale) throws MalformedURLException, MissingResourceException {
        URL[] urls = {path.toUri().toURL()};
        ClassLoader loader = new URLClassLoader(urls);
        return ResourceBundle.getBundle("romstation", locale, loader);
    }

    /* JADX INFO: renamed from: g */
    private void m51g() {
        try {
            FileHandler fileHandler = new FileHandler("romstation.log");
            fileHandler.setFormatter(new SimpleFormatter());
            f17c.addHandler(fileHandler);
            f17c.setLevel(Level.WARNING);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: h */
    private void m52h() {
        switch (C0004E.m10c()) {
            case WINDOWS:
                try {
                    Files.move(Paths.get("../_Updater.exe", new String[0]), Paths.get("../Updater.exe", new String[0]), StandardCopyOption.REPLACE_EXISTING);
                    break;
                } catch (NoSuchFileException e) {
                } catch (IOException exception) {
                    m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                }
                try {
                    Files.move(Paths.get("_Updater.jar", new String[0]), Paths.get("Updater.jar", new String[0]), StandardCopyOption.REPLACE_EXISTING);
                } catch (NoSuchFileException e2) {
                    return;
                } catch (IOException exception2) {
                    m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
                    return;
                }
                break;
            case MAC_OS:
                try {
                    Files.move(Paths.get("Updater.app/Contents/MacOS/_Updater", new String[0]), Paths.get("Updater.app/Contents/MacOS/Updater", new String[0]), StandardCopyOption.REPLACE_EXISTING);
                    break;
                } catch (NoSuchFileException e3) {
                } catch (IOException exception3) {
                    m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                }
                try {
                    Files.move(Paths.get("Updater.app/Contents/Java/_Updater.jar", new String[0]), Paths.get("Updater.app/Contents/Java/Updater.jar", new String[0]), StandardCopyOption.REPLACE_EXISTING);
                } catch (NoSuchFileException e4) {
                    return;
                } catch (IOException exception4) {
                    m42b().log(Level.SEVERE, exception4.getMessage(), (Throwable) exception4);
                }
                break;
        }
    }

    /* JADX INFO: renamed from: i */
    private void m53i() {
        try {
            Files.find(Paths.get("scripts", new String[0]), 2, (path, basicFileAttributes) -> {
                return path.endsWith("init.rb");
            }, new FileVisitOption[0]).forEach(path2 -> {
                try {
                    this.f25k.add(C0013N.m40a(path2));
                } catch (Exception exception) {
                    m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                }
            });
        } catch (IOException exception) {
            m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
        m55k();
    }

    /* JADX INFO: renamed from: j */
    private boolean m54j() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/romstation.fxml"));
        fxmlLoader.setResources(f19e);
        fxmlLoader.load();
        this.f24j = (RomStationController) fxmlLoader.getController();
        return true;
    }

    /* JADX INFO: renamed from: k */
    private void m55k() {
        this.f25k.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_init")) {
                rubyObject.callMethod("on_init");
            }
        });
        if (C0013N.m38a().runtime.on_init != null) {
            C0013N.m38a().runtime.on_init.run();
        }
    }

    /* JADX INFO: renamed from: l */
    private void m56l() {
        this.f25k.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_ready")) {
                rubyObject.callMethod("on_ready");
            }
        });
        if (C0013N.m38a().runtime.on_ready != null) {
            C0013N.m38a().runtime.on_ready.run();
        }
    }

    /* JADX INFO: renamed from: m */
    private void m57m() {
        this.f25k.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_start")) {
                rubyObject.callMethod("on_start");
            }
        });
        if (C0013N.m38a().runtime.on_start != null) {
            C0013N.m38a().runtime.on_start.run();
        }
    }

    /* JADX INFO: renamed from: n */
    private void m58n() {
        this.f25k.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_stop")) {
                rubyObject.callMethod("on_stop");
            }
        });
        if (C0013N.m38a().runtime.on_stop != null) {
            C0013N.m38a().runtime.on_stop.run();
        }
    }
}
