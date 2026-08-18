package org.romstation.application.task;

import javafx.concurrent.Task;
import org.romstation.application.C0001B;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.task.c */
/* JADX INFO: compiled from: CloudPlayerInitializationTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/c.class */
public class C0235c extends Task<Boolean> {
    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("cloudPlayerInitializationTask.header"));
        updateMessage(RomStation.m44d().getString("cloudPlayerInitializationTask.message"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Boolean call() throws Exception {
        return Boolean.valueOf(C0001B.m5b());
    }
}
