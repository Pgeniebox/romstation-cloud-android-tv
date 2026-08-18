package org.romstation.application;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.ay */
/* JADX INFO: compiled from: ComboBoxChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ay.class */
public class C0079ay<T> extends ApplicationFXMLDialog<T> {

    /* JADX INFO: renamed from: a */
    private final T f155a;

    /* JADX INFO: renamed from: b */
    private final T[] f156b;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label contentLabel;

    @FXML
    private ComboBox<T> comboBox;

    public C0079ay(String headerText, String contentText, T value, T... items) {
        this.f155a = value;
        this.f156b = items;
        load(getClass().getResource("/fxml/dialog/choice/comboBoxChoiceDialog.fxml"));
        setHeaderText(headerText);
        this.contentLabel.setText(contentText);
    }

    @FXML
    private void initialize() {
        if (this.f156b != null) {
            this.comboBox.getItems().setAll(this.f156b);
        }
        if (!this.comboBox.getItems().contains(this.f155a)) {
            this.comboBox.getSelectionModel().selectFirst();
        } else {
            this.comboBox.getSelectionModel().select(this.f155a);
        }
    }

    /* JADX INFO: renamed from: a */
    public ComboBox<T> m304a() {
        return this.comboBox;
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected T resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return (T) this.comboBox.getValue();
        }
        return null;
    }
}
