package org.romstation.application;

import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.romstation.application.task.C0230A;
import org.romstation.application.task.C0258z;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: renamed from: org.romstation.application.cW */
/* JADX INFO: compiled from: SystemLauncherManager.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cW.class */
public class C0157cW {

    /* JADX INFO: renamed from: a */
    private static ObservableList<C0230A> f350a = FXCollections.observableList(new LinkedList());

    /* JADX INFO: renamed from: a */
    public static ObservableList<C0230A> m679a() {
        return f350a;
    }

    /* JADX INFO: renamed from: b */
    public static boolean m680b() {
        return !f350a.isEmpty();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, java.lang.Runnable, org.romstation.application.task.A] */
    /* JADX INFO: renamed from: a */
    public static C0230A m681a(C0258z context) {
        ?? c0230a = new C0230A(context);
        c0230a.setOnRunning(workerStateEvent -> {
            RomStationController.f786a.post(new C0105bX(context));
        });
        c0230a.setOnSucceeded(workerStateEvent2 -> {
            RomStationController.f786a.post(new C0106bY(context));
        });
        Thread thread = new Thread((Runnable) c0230a);
        thread.setDaemon(true);
        thread.start();
        f350a.add((Object) c0230a);
        return c0230a;
    }
}
