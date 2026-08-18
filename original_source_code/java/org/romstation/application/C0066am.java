package org.romstation.application;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.romstation.application.view.control.ApplicationDialog;

/* JADX INFO: renamed from: org.romstation.application.am */
/* JADX INFO: compiled from: ApplicationUpdateDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/am.class */
public class C0066am extends ApplicationDialog<ButtonType> {
    public C0066am() {
        setHeaderText(RomStation.m44d().getString("application.update.dialog.header"));
        setContentText(RomStation.m44d().getString("application.update.dialog.content"));
        getDialogPane().getButtonTypes().setAll(new ButtonType[]{new ButtonType(RomStation.m44d().getString("application.update.dialog.button.update"), ButtonBar.ButtonData.OK_DONE), new ButtonType(RomStation.m44d().getString("application.update.dialog.button.offline"), ButtonBar.ButtonData.CANCEL_CLOSE)});
        getDialogPane().setId("application-update-dialog");
    }
}
