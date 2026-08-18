package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.romstation.application.C0079ay;
import org.romstation.application.view.control.ApplicationAlert;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/Dialog.class */
@JsAccessible
public class Dialog {
    public void errorDialog(String headerText, String contentText) {
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void informationDialog(String headerText, String contentText) {
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void warningDialog(String headerText, String contentText) {
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.WARNING);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public boolean confirmationDialog(String headerText, String contentText) {
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public Object choiceDialog(String headerText, String contentText, Object value, Object... items) {
        C0079ay<Object> dialog = new C0079ay<>(headerText, contentText, value, items);
        Optional<Object> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public String textInputDialog() {
        return null;
    }

    public String passwordDialog() {
        return null;
    }
}
