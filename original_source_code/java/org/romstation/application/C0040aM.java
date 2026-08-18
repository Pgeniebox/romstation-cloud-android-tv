package org.romstation.application;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.romstation.application.database.entity.Developer;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aM */
/* JADX INFO: compiled from: DeveloperEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aM.class */
public class C0040aM extends ApplicationFXMLDialog<Developer> {

    /* JADX INFO: renamed from: a */
    private final Developer f69a;

    @FXML
    private TextField nameTextField;

    @FXML
    private ResourceBundle resources;

    public C0040aM(Developer developer) {
        this.f69a = developer;
        load(getClass().getResource("/fxml/dialog/editor/developerEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f69a.getName());
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Developer resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            this.f69a.setName(this.nameTextField.getText());
            return this.f69a;
        }
        return null;
    }
}
