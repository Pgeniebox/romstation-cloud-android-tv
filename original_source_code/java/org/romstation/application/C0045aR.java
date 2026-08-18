package org.romstation.application;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.romstation.application.database.entity.Publisher;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aR */
/* JADX INFO: compiled from: PublisherEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aR.class */
public class C0045aR extends ApplicationFXMLDialog<Publisher> {

    /* JADX INFO: renamed from: a */
    private final Publisher f74a;

    @FXML
    private TextField nameTextField;

    @FXML
    private ResourceBundle resources;

    public C0045aR(Publisher publisher) {
        this.f74a = publisher;
        load(getClass().getResource("/fxml/dialog/editor/publisherEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f74a.getName());
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Publisher resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            this.f74a.setName(this.nameTextField.getText());
            return this.f74a;
        }
        return null;
    }
}
