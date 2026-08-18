package org.romstation.application.view.control;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.nio.file.Paths;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/ApplicationAlert.class */
public class ApplicationAlert extends Alert {
    public ApplicationAlert(Alert.AlertType alertType) {
        this(null, null, alertType);
    }

    public ApplicationAlert(String headerText, String contentText, Alert.AlertType alertType) {
        super(alertType);
        setTitle("RomStation");
        setGraphic(new FontAwesomeIconView());
        setHeaderText(headerText);
        setContentText(contentText);
        getDialogPane().getScene().getStylesheets().add(RomStation.m45e());
        getDialogPane().getScene().getWindow().getIcons().add(new Image(Paths.get("images/icons/romstation.png", new String[0]).toUri().toString()));
    }
}
