package org.romstation.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.controller.MetasPaneController;
import org.romstation.application.view.controller.SystemsPaneController;

/* JADX INFO: renamed from: org.romstation.application.aY */
/* JADX INFO: compiled from: EmulatorProfileEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aY.class */
public class C0052aY extends ApplicationFXMLDialog<EmulatorProfile> {

    /* JADX INFO: renamed from: a */
    private final EmulatorProfile f81a;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField parametersTextField;

    @FXML
    private PathField pathField;

    @FXML
    private PathField workingDirectoryField;

    @FXML
    private MetasPaneController metasController;

    @FXML
    private SystemsPaneController systemsController;

    public C0052aY(EmulatorProfile emulatorProfile) {
        this.f81a = emulatorProfile;
        load(getClass().getResource("/fxml/dialog/editor/emulator/emulatorProfileEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f81a.getName());
        this.parametersTextField.setText(this.f81a.getParameters());
        this.pathField.setPath(this.f81a.getPath());
        this.workingDirectoryField.setPath(this.f81a.getWorkingDirectory());
        this.metasController.m1308a().getItems().setAll(this.f81a.getMetas().entrySet());
        this.systemsController.m1332a().getItems().setAll(this.f81a.getSystems());
        setResizable(true);
    }

    @FXML
    private void selectPath() {
        FileChooser fileChooser = new FileChooser();
        String path = this.pathField.getPath() != null ? this.pathField.getPath() : this.f81a.getEmulatorFile().getDirectory();
        if (path != null) {
            Path defaultPath = Paths.get(path, new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                fileChooser.setInitialDirectory(defaultPath.getParent().toFile());
                fileChooser.setInitialFileName(defaultPath.toString());
            }
        }
        File filePath = fileChooser.showOpenDialog(this.scrollPane.getScene().getWindow());
        if (filePath != null) {
            this.pathField.setPath(filePath.toString());
        }
    }

    @FXML
    private void selectWorkingDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (this.workingDirectoryField.getPath() != null) {
            Path defaultPath = Paths.get(this.workingDirectoryField.getPath(), new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                directoryChooser.setInitialDirectory(defaultPath.toFile());
            }
        }
        File path = directoryChooser.showDialog(this.scrollPane.getScene().getWindow());
        if (path != null) {
            this.workingDirectoryField.setPath(path.toString());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        if (classType == getClass()) {
            return this;
        }
        try {
            return classType.newInstance();
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m153a() {
        this.f81a.setName(this.nameTextField.getText());
        this.f81a.setParameters(this.parametersTextField.getText());
        this.f81a.setPath(this.pathField.getPath());
        this.f81a.setWorkingDirectory(this.workingDirectoryField.getPath());
        this.f81a.setMetas(this.metasController.m1309b());
        this.f81a.setSystems(this.systemsController.m1332a().getItems());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public EmulatorProfile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            m153a();
            return this.f81a;
        }
        return null;
    }
}
