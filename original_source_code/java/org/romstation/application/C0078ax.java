package org.romstation.application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.ax */
/* JADX INFO: compiled from: TextInputDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ax.class */
public class C0078ax extends ApplicationFXMLDialog<String> {

    @FXML
    private TextField textField;

    public C0078ax(String headerText) {
        this(headerText, null);
    }

    public C0078ax(String headerText, String text) {
        load(getClass().getResource("/fxml/dialog/textInputDialog.fxml"));
        setHeaderText(headerText);
        this.textField.setText(text);
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            this.textField.requestFocus();
        });
    }

    /* JADX INFO: renamed from: a */
    public TextField m301a() {
        return this.textField;
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public String resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return this.textField.getText();
        }
        return null;
    }
}
