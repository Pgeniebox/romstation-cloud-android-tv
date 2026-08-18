package org.romstation.application;

import java.util.AbstractMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aQ */
/* JADX INFO: compiled from: MetaEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aQ.class */
public class C0044aQ extends ApplicationFXMLDialog<Map.Entry<String, String>> {

    /* JADX INFO: renamed from: a */
    private final Map.Entry<String, String> f73a;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField keyTextField;

    @FXML
    private TextArea valueTextArea;

    @FXML
    private ResourceBundle resources;

    public C0044aQ(Map.Entry<String, String> entry) {
        this.f73a = entry;
        load(getClass().getResource("/fxml/dialog/editor/metaEditorDialog.fxml"));
        setResizable(true);
    }

    @FXML
    private void initialize() {
        this.keyTextField.setText(this.f73a.getKey());
        this.valueTextArea.setText(this.f73a.getValue());
        this.dialogPane.lookupButton(ButtonType.OK).disableProperty().bind(this.keyTextField.textProperty().isEmpty());
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Map.Entry<String, String> resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return new AbstractMap.SimpleEntry(this.keyTextField.getText(), this.valueTextArea.getText());
        }
        return null;
    }
}
