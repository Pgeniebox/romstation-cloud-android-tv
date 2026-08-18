package org.romstation.application;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aw */
/* JADX INFO: compiled from: TextAreaDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aw.class */
public class C0077aw extends ApplicationFXMLDialog<String> {

    @FXML
    private TextArea textArea;

    public C0077aw(String headerText) {
        this(headerText, null);
    }

    public C0077aw(String headerText, String text) {
        load(getClass().getResource("/fxml/dialog/textAreaDialog.fxml"));
        setHeaderText(headerText);
        this.textArea.setText(text);
    }

    @FXML
    private void initialize() {
    }

    /* JADX INFO: renamed from: a */
    public TextArea m299a() {
        return this.textArea;
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
            return this.textArea.getText();
        }
        return null;
    }
}
