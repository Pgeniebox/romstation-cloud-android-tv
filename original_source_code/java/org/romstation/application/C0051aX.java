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
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.controller.EmulatorProfilesPaneController;
import org.romstation.application.view.controller.MetasPaneController;

/* JADX INFO: renamed from: org.romstation.application.aX */
/* JADX INFO: compiled from: EmulatorFileEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aX.class */
public class C0051aX extends ApplicationFXMLDialog<EmulatorFile> {

    /* JADX INFO: renamed from: a */
    private final EmulatorFile f80a;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private PathField directoryPathField;

    @FXML
    private EmulatorProfilesPaneController emulatorProfilesController;

    @FXML
    private MetasPaneController metasController;

    public C0051aX(EmulatorFile emulatorFile) {
        this.f80a = emulatorFile;
        load(getClass().getResource("/fxml/dialog/editor/emulator/emulatorFileEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f80a.getName());
        this.directoryPathField.setPath(this.f80a.getDirectory());
        this.directoryPathField.setDisable(this.f80a.isManaged());
        this.metasController.m1308a().getItems().setAll(this.f80a.getMetas().entrySet());
        setResizable(true);
    }

    @FXML
    private void selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String path = this.directoryPathField.getPath() != null ? this.directoryPathField.getPath() : this.f80a.getEmulator().getDirectory();
        if (path != null) {
            Path defaultPath = Paths.get(path, new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                directoryChooser.setInitialDirectory(defaultPath.toFile());
            }
        }
        File directoryPath = directoryChooser.showDialog(this.scrollPane.getScene().getWindow());
        if (directoryPath != null) {
            this.directoryPathField.setPath(directoryPath.toString());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        if (classType == getClass()) {
            return this;
        }
        try {
            return classType == EmulatorProfilesPaneController.class ? classType.getDeclaredConstructor(EmulatorFile.class).newInstance(this.f80a) : classType.newInstance();
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m151a() {
        this.f80a.setName(this.nameTextField.getText());
        this.f80a.setDirectory(this.directoryPathField.getPath());
        this.f80a.setProfiles(this.emulatorProfilesController.m1282a().getItems());
        this.f80a.setMetas(this.metasController.m1309b());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public EmulatorFile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            m151a();
            return this.f80a;
        }
        return null;
    }
}
