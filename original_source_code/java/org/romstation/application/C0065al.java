package org.romstation.application;

import javafx.scene.control.ButtonType;
import org.romstation.application.view.control.ApplicationDialog;

/* JADX INFO: renamed from: org.romstation.application.al */
/* JADX INFO: compiled from: ApplicationOfflineDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/al.class */
public class C0065al extends ApplicationDialog<ButtonType> {
    public C0065al() {
        setHeaderText(RomStation.m44d().getString("application.offline.dialog.header"));
        setContentText(RomStation.m44d().getString("application.offline.dialog.content"));
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        getDialogPane().setId("application-offline-dialog");
    }
}
