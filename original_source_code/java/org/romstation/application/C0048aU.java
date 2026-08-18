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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.System;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.aU */
/* JADX INFO: compiled from: SystemEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aU.class */
public class C0048aU extends ApplicationFXMLDialog<System> {

    /* JADX INFO: renamed from: a */
    private final System f77a;

    @FXML
    private TextField nameTextField;

    @FXML
    private PathField graphicPathField;

    @FXML
    private ResourceBundle resources;

    public C0048aU() {
        this(new System());
    }

    public C0048aU(System system) {
        this.f77a = system;
        load(getClass().getResource("/fxml/dialog/editor/systemEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f77a.getName());
        if (this.f77a.getGraphic() != null) {
            this.graphicPathField.setPath(this.f77a.getGraphic().getPath());
        }
    }

    @FXML
    private void selectPath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        if (this.graphicPathField.getPath() != null) {
            Path defaultPath = Paths.get(this.graphicPathField.getPath(), new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                fileChooser.setInitialDirectory(defaultPath.getParent().toFile());
                fileChooser.setInitialFileName(defaultPath.toString());
            }
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(this.resources.getString("systemEditorDialog.graphic.extensionFilters"), new String[]{"*.bmp", "*.gif", "*.png", "*.jpg"}));
        File path = fileChooser.showOpenDialog(getDialogPane().getScene().getWindow());
        if (path != null) {
            this.graphicPathField.setPath(path.toString());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public System resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            this.f77a.setName(this.nameTextField.getText());
            if (this.graphicPathField.getPath() == null || this.graphicPathField.getPath().isEmpty()) {
                this.f77a.setGraphic(null);
            } else {
                this.f77a.setGraphic(new Image(this.graphicPathField.getPath()));
            }
            return this.f77a;
        }
        return null;
    }
}
