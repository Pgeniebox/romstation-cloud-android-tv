package org.romstation.application.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.jruby.RubyObject;
import org.jruby.javasupport.JavaUtil;
import org.romstation.application.C0004E;
import org.romstation.application.C0013N;
import org.romstation.application.C0157cW;
import org.romstation.application.EnumC0003D;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Script;

/* JADX INFO: renamed from: org.romstation.application.task.A */
/* JADX INFO: compiled from: SystemLauncherTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/A.class */
public class C0230A extends Task<Integer> {

    /* JADX INFO: renamed from: a */
    private static final File f600a;

    /* JADX INFO: renamed from: b */
    private final C0258z f601b;

    /* JADX INFO: renamed from: c */
    private String f602c;

    /* JADX INFO: renamed from: d */
    private String f603d;

    /* JADX INFO: renamed from: e */
    private String f604e;

    /* JADX INFO: renamed from: f */
    private final List<RubyObject> f605f = new LinkedList();

    /* JADX INFO: renamed from: g */
    private Process f606g;

    static {
        f600a = new File(C0004E.m10c() == EnumC0003D.WINDOWS ? "NUL" : "/dev/null");
    }

    public C0230A(C0258z context) {
        this.f601b = context;
    }

    public C0258z getContext() {
        return this.f601b;
    }

    public String getExecutable() {
        return this.f602c;
    }

    public void setExecutable(String executable) {
        this.f602c = executable;
    }

    public String getParameters() {
        return this.f603d;
    }

    public void setParameters(String parameters) {
        this.f603d = parameters;
    }

    public String getWorkingDirectory() {
        return this.f604e;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.f604e = workingDirectory;
    }

    /* JADX INFO: renamed from: b */
    private void m987b() {
        List<Script> scripts = new LinkedList<>();
        scripts.addAll(getContext().getEmulatorProfile().getEmulatorFile().getEmulator().getScripts());
        scripts.addAll(getContext().getEmulatorProfile().getEmulatorFile().getScripts());
        scripts.addAll(getContext().getEmulatorProfile().getScripts());
        if (this.f601b.getGameProfile() != null) {
            scripts.addAll(getContext().getGameProfile().getGameFile().getGame().getScripts());
            scripts.addAll(getContext().getGameProfile().getGameFile().getScripts());
            scripts.addAll(getContext().getGameProfile().getScripts());
        }
        scripts.forEach(script -> {
            try {
                this.f605f.add(C0013N.m39a(script));
            } catch (Exception exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
        });
    }

    /* JADX INFO: renamed from: c */
    private void m988c() {
        this.f602c = this.f601b.getEmulatorProfile().getPath();
        this.f603d = this.f601b.eval(this.f601b.getEmulatorProfile().getParameters());
        this.f604e = this.f601b.eval(this.f601b.getEmulatorProfile().getWorkingDirectory());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Integer call() {
        m987b();
        m990d();
        m992f();
        m988c();
        m991e();
        m993g();
        Path emulatorProfilePath = Paths.get(this.f602c, new String[0]).toAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
        processBuilder.directory(Paths.get(this.f604e, new String[0]).toFile());
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectInput(f600a);
        processBuilder.redirectOutput(f600a);
        List<String> commands = processBuilder.command();
        switch (C0004E.m10c()) {
            case WINDOWS:
                commands.add("cmd");
                commands.add("/c");
                commands.add(String.format("\"%s %s\"", String.format("\"%s\"", emulatorProfilePath), Optional.ofNullable(this.f603d).orElse("")));
                break;
            case MAC_OS:
                commands.add("bash");
                commands.add("-c");
                if (Files.isDirectory(emulatorProfilePath, new LinkOption[0])) {
                    commands.add(String.format("open \"%s\" --new --wait-apps --args %s", emulatorProfilePath, Optional.ofNullable(this.f603d).orElse("")));
                } else {
                    commands.add(String.format("\"%s\" %s", emulatorProfilePath, Optional.ofNullable(this.f603d).orElse("")));
                }
                break;
        }
        try {
            this.f606g = processBuilder.start();
            m994h();
            return Integer.valueOf(this.f606g.waitFor());
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        } finally {
            if (this.f606g != null) {
                m995i();
            }
        }
    }

    @Deprecated
    /* JADX INFO: renamed from: d */
    private void m990d() {
        this.f605f.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_pre_init")) {
                if (Platform.isFxApplicationThread()) {
                    rubyObject.callMethod("on_pre_init", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
                    return;
                }
                try {
                    FutureTask<Void> futureTask = new FutureTask<>(() -> {
                        rubyObject.callMethod("on_pre_init", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
                    }, null);
                    Platform.runLater(futureTask);
                    futureTask.get();
                } catch (InterruptedException | ExecutionException exception) {
                    RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                }
            }
        });
    }

    @Deprecated
    /* JADX INFO: renamed from: e */
    private void m991e() {
        this.f605f.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_post_init")) {
                if (Platform.isFxApplicationThread()) {
                    rubyObject.callMethod("on_post_init", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
                    return;
                }
                try {
                    FutureTask<Void> futureTask = new FutureTask<>(() -> {
                        rubyObject.callMethod("on_post_init", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
                    }, null);
                    Platform.runLater(futureTask);
                    futureTask.get();
                } catch (InterruptedException | ExecutionException exception) {
                    RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                }
            }
        });
    }

    /* JADX INFO: renamed from: f */
    private void m992f() {
        this.f605f.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_init")) {
                rubyObject.callMethod("on_init", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
            }
        });
        if (C0013N.m38a().system.on_init != null) {
            C0013N.m38a().system.on_init.accept(this);
        }
    }

    /* JADX INFO: renamed from: g */
    private void m993g() {
        this.f605f.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_ready")) {
                rubyObject.callMethod("on_ready", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
            }
        });
        if (C0013N.m38a().system.on_ready != null) {
            C0013N.m38a().system.on_ready.accept(this);
        }
    }

    /* JADX INFO: renamed from: h */
    private void m994h() {
        this.f605f.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_start")) {
                rubyObject.callMethod("on_start", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
            }
        });
        if (C0013N.m38a().system.on_start != null) {
            C0013N.m38a().system.on_start.accept(this);
        }
    }

    /* JADX INFO: renamed from: i */
    private void m995i() {
        this.f605f.forEach(rubyObject -> {
            if (rubyObject.respondsTo("on_stop")) {
                rubyObject.callMethod("on_stop", JavaUtil.convertJavaToRuby(rubyObject.getRuntime(), this));
            }
        });
        if (C0013N.m38a().system.on_stop != null) {
            C0013N.m38a().system.on_stop.accept(this);
        }
    }

    protected void cancelled() {
        if (this.f606g != null) {
            this.f606g.destroyForcibly();
        }
    }

    protected void done() {
        C0157cW.m679a().remove(this);
    }
}
