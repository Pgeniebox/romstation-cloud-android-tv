package org.romstation.application;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.romstation.application.view.control.ApplicationDialog;

/* JADX INFO: renamed from: org.romstation.application.au */
/* JADX INFO: compiled from: PlatinumDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/au.class */
public class C0075au extends ApplicationDialog<ButtonType> {
    public C0075au(String contentText) {
        setHeaderText(RomStation.m44d().getString("platinumDialog.header"));
        setGraphic(new Label(RomStation.m44d().getString("platinumDialog.header.platinum")));
        setContentText(contentText);
        getDialogPane().getButtonTypes().setAll(new ButtonType[]{new ButtonType(RomStation.m44d().getString("platinumDialog.button.becomePlatinum"), ButtonBar.ButtonData.OK_DONE), ButtonType.CLOSE});
        getDialogPane().setId("platinum-dialog");
    }
}
