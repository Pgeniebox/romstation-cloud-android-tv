package org.romstation.application.view.control;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.nio.file.Paths;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/ApplicationDialog.class */
public abstract class ApplicationDialog<T> extends Dialog<T> {
    public ApplicationDialog() {
        setTitle("RomStation");
        setGraphic(new FontAwesomeIconView());
        getDialogPane().getScene().getStylesheets().add(RomStation.m45e());
        getDialogPane().getScene().getWindow().getIcons().add(new Image(Paths.get("images/icons/romstation.png", new String[0]).toUri().toString()));
    }
}
