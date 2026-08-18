package org.romstation.application;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;

/* JADX INFO: renamed from: org.romstation.application.T */
/* JADX INFO: compiled from: Runtime.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/T.class */
public class C0020T {
    public Runnable on_init;
    public Runnable on_ready;
    public Runnable on_start;
    public Runnable on_stop;

    public String[] getParameters() {
        return (String[]) RomStation.m41a().getParameters().getRaw().toArray(new String[0]);
    }

    public int getBuild() {
        return RomStation.f15a;
    }

    public String getVersion() {
        return RomStation.f16b;
    }

    public Properties getSettings() {
        return RomStation.m43c();
    }

    public boolean isFxApplicationThread() {
        return Platform.isFxApplicationThread();
    }

    public void runLater(Runnable runnable) {
        Platform.runLater(runnable);
    }

    public void runAndWait(Runnable runnable) throws ExecutionException, InterruptedException {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
            return;
        }
        FutureTask<Void> futureTask = new FutureTask<>(runnable, null);
        Platform.runLater(futureTask);
        futureTask.get();
    }

    public void exit() {
        Platform.exit();
    }
}
