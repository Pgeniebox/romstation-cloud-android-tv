package org.romstation.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.romstation.application.database.entity.Script;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.aS */
/* JADX INFO: compiled from: ScriptEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aS.class */
public class C0046aS extends ApplicationFXMLDialog<Script> {

    /* JADX INFO: renamed from: a */
    private final Script f75a;

    @FXML
    private PathField pathField;

    @FXML
    private ResourceBundle resources;

    public C0046aS() {
        this(new Script());
    }

    public C0046aS(Script script) {
        this.f75a = script;
        load(getClass().getResource("/fxml/dialog/editor/scriptEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.pathField.setPath(this.f75a.getPath());
    }

    @FXML
    private void selectPath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        if (this.pathField.getPath() != null) {
            Path defaultPath = Paths.get(this.pathField.getPath(), new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                fileChooser.setInitialDirectory(defaultPath.getParent().toFile());
                fileChooser.setInitialFileName(defaultPath.toString());
            }
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(this.resources.getString("scriptEditorDialog.path.extensionFilters"), new String[]{"*.rb"}));
        File path = fileChooser.showOpenDialog(getDialogPane().getScene().getWindow());
        if (path != null) {
            this.pathField.setPath(path.toString());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Script resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            if (this.pathField.getPath() != null && this.pathField.getPath().isEmpty()) {
                this.f75a.setPath(null);
            } else {
                this.f75a.setPath(this.pathField.getPath());
            }
            return this.f75a;
        }
        return null;
    }
}
