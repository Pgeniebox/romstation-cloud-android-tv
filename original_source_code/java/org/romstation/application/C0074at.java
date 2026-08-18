package org.romstation.application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.at */
/* JADX INFO: compiled from: PasswordInputDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/at.class */
public class C0074at extends ApplicationFXMLDialog<String> {

    @FXML
    private PasswordField passwordField;

    public C0074at(String headerText) {
        this(headerText, null);
    }

    public C0074at(String headerText, String text) {
        load(getClass().getResource("/fxml/dialog/passwordInputDialog.fxml"));
        setHeaderText(headerText);
        this.passwordField.setText(text);
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            this.passwordField.requestFocus();
        });
    }

    /* JADX INFO: renamed from: a */
    public PasswordField m292a() {
        return this.passwordField;
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
            return this.passwordField.getText();
        }
        return null;
    }
}
