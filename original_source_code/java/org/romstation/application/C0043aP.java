package org.romstation.application;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.romstation.application.database.entity.Link;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aP */
/* JADX INFO: compiled from: LinkEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aP.class */
public class C0043aP extends ApplicationFXMLDialog<Link> {

    /* JADX INFO: renamed from: a */
    private final Link f72a;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private CheckBox externalCheckBox;

    public C0043aP(Link link) {
        this.f72a = link;
        load(getClass().getResource("/fxml/dialog/editor/linkEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f72a.getName());
        this.locationTextField.setText(this.f72a.getLocation());
        this.externalCheckBox.setSelected(this.f72a.isExternal());
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Link resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            this.f72a.setName(this.nameTextField.getText());
            this.f72a.setLocation(this.locationTextField.getText());
            this.f72a.setExternal(this.externalCheckBox.isSelected());
            return this.f72a;
        }
        return null;
    }
}
