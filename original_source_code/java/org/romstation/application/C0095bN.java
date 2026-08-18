package org.romstation.application;

import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.bN */
/* JADX INFO: compiled from: GameFileUploadFormDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bN.class */
public class C0095bN extends ApplicationFXMLDialog<JsonObject> {

    /* JADX INFO: renamed from: a */
    private final int f233a;

    /* JADX INFO: renamed from: b */
    private final JsonObject f234b;

    /* JADX INFO: renamed from: c */
    private final Path f235c;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField pathTextField;

    @FXML
    private PathField targetPathField;

    @FXML
    private TextField parametersTextField;

    public C0095bN(int systemID, JsonObject uploadJsonObject, Path archive) {
        this.f233a = systemID;
        this.f234b = uploadJsonObject;
        this.f235c = archive;
        load(getClass().getResource("/fxml/dialog/upload/gameFileUploadFormDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.pathTextField.setText(this.f235c.toString());
        this.targetPathField.getTextField().setEditable(false);
        if (m457a()) {
            this.targetPathField.setDisable(false);
            this.parametersTextField.setDisable(false);
        }
        if (this.f234b.get("resume") != null) {
            m456a(this.f234b.getAsJsonObject("resume"));
        }
        Button okButton = this.dialogPane.lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!m458b()) {
                event.consume();
            }
        });
    }

    /* JADX INFO: renamed from: a */
    private void m456a(JsonObject resumeObject) {
        this.nameTextField.setText(resumeObject.getAsJsonObject("file").get("name").getAsString());
        this.targetPathField.setPath(resumeObject.getAsJsonObject("file").get("target").getAsString());
        this.parametersTextField.setText(resumeObject.getAsJsonObject("file").get("parameters").getAsString());
    }

    /* JADX INFO: renamed from: a */
    private boolean m457a() {
        return this.f233a == 20 || this.f233a == 26 || this.f233a == 35 || this.f233a == 36;
    }

    /* JADX INFO: renamed from: b */
    private boolean m458b() {
        if (this.nameTextField.getText().trim().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameFileUploadFormDialog.validationError.header"), getResources().getString("gameFileUploadFormDialog.validationError.name"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (m457a() && this.targetPathField.getPath() == null) {
            new ApplicationAlert(getResources().getString("gameFileUploadFormDialog.validationError.header"), getResources().getString("gameFileUploadFormDialog.validationError.target"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void selectFileTarget(ActionEvent actionEvent) {
        C0039aL zipEntryChoiceDialog = new C0039aL(getResources().getString("gameFileUploadFormDialog.target.chooser.header"), Paths.get(this.pathTextField.getText(), new String[0]));
        zipEntryChoiceDialog.showAndWait().ifPresent(path -> {
            this.targetPathField.setPath(path.toString());
        });
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public JsonObject resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            JsonObject object = new JsonObject();
            object.addProperty("name", this.nameTextField.getText());
            object.addProperty("path", this.pathTextField.getText());
            object.addProperty("target", this.targetPathField.getPath());
            object.addProperty("parameters", this.parametersTextField.getText());
            return object;
        }
        return null;
    }
}
