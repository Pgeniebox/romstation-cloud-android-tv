package org.romstation.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.controller.SystemsPaneController;

/* JADX INFO: renamed from: org.romstation.application.ao */
/* JADX INFO: compiled from: EmulatorFileImportDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ao.class */
public class C0068ao extends ApplicationFXMLDialog<EmulatorFile> {

    /* JADX INFO: renamed from: a */
    private final Emulator f145a;

    @FXML
    private DialogPane root;

    @FXML
    private TextField nameTextField;

    @FXML
    private PathField pathField;

    @FXML
    private TextField parametersTextField;

    @FXML
    private SystemsPaneController systemsController;

    public C0068ao(Emulator emulator) {
        this.f145a = emulator;
        load(getClass().getResource("/fxml/dialog/emulatorFileImportDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.pathField.getTextField().setEditable(false);
        this.root.lookupButton(ButtonType.OK).disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.pathField.getTextField().textProperty().isEmpty()));
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
        File path = fileChooser.showOpenDialog(getDialogPane().getScene().getWindow());
        if (path != null) {
            this.pathField.setPath(path.toString());
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public EmulatorFile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            EmulatorFile emulatorFile = new EmulatorFile();
            emulatorFile.setEmulator(this.f145a);
            emulatorFile.setName(this.nameTextField.getText());
            emulatorFile.setDirectory(Paths.get(this.pathField.getPath(), new String[0]).getParent().toString());
            EmulatorProfile defaultProfile = new EmulatorProfile();
            defaultProfile.setEmulatorFile(emulatorFile);
            defaultProfile.setName(emulatorFile.getName());
            defaultProfile.setPath(this.pathField.getPath());
            if (this.systemsController.m1332a().getItems().isEmpty()) {
                defaultProfile.setParameters(this.parametersTextField.getText());
            }
            defaultProfile.setWorkingDirectory("${emulator.file.directory}");
            emulatorFile.getProfiles().add(defaultProfile);
            this.systemsController.m1332a().getItems().forEach(system -> {
                EmulatorProfile systemProfile = new EmulatorProfile();
                systemProfile.setEmulatorFile(emulatorFile);
                systemProfile.setName(system.getName());
                systemProfile.getSystems().add(system);
                systemProfile.setPath(this.pathField.getPath());
                systemProfile.setParameters(this.parametersTextField.getText());
                systemProfile.setWorkingDirectory("${emulator.file.directory}");
                emulatorFile.getProfiles().add(systemProfile);
            });
            return emulatorFile;
        }
        return null;
    }
}
