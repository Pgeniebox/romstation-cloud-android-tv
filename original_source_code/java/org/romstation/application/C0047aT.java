package org.romstation.application;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.romstation.application.database.entity.Series;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aT */
/* JADX INFO: compiled from: SeriesEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aT.class */
public class C0047aT extends ApplicationFXMLDialog<Series> {

    /* JADX INFO: renamed from: a */
    private final Series f76a;

    @FXML
    private TextField nameTextField;

    @FXML
    private ResourceBundle resources;

    public C0047aT(Series series) {
        this.f76a = series;
        load(getClass().getResource("/fxml/dialog/editor/seriesEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f76a.getName());
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Series resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            this.f76a.setName(this.nameTextField.getText());
            return this.f76a;
        }
        return null;
    }
}
