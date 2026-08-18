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
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.controller.EmulatorFilesPaneController;
import org.romstation.application.view.controller.LinksPaneController;
import org.romstation.application.view.controller.MetasPaneController;

/* JADX INFO: renamed from: org.romstation.application.aW */
/* JADX INFO: compiled from: EmulatorEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aW.class */
public class C0050aW extends ApplicationFXMLDialog<Emulator> {

    /* JADX INFO: renamed from: a */
    private final Emulator f79a;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private PathField directoryPathField;

    @FXML
    private EmulatorFilesPaneController emulatorFilesController;

    @FXML
    private LinksPaneController linksController;

    @FXML
    private MetasPaneController metasController;

    public C0050aW(Emulator emulator) {
        this.f79a = emulator;
        load(getClass().getResource("/fxml/dialog/editor/emulator/emulatorEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f79a.getName());
        this.directoryPathField.setPath(this.f79a.getDirectory());
        this.directoryPathField.setDisable(this.f79a.isManaged());
        this.linksController.m1303a().getItems().setAll(this.f79a.getLinks());
        this.metasController.m1308a().getItems().setAll(this.f79a.getMetas().entrySet());
        setResizable(true);
    }

    @FXML
    private void selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (this.directoryPathField.getPath() != null) {
            Path defaultPath = Paths.get(this.directoryPathField.getPath(), new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                directoryChooser.setInitialDirectory(defaultPath.toFile());
            }
        }
        File path = directoryChooser.showDialog(this.scrollPane.getScene().getWindow());
        if (path != null) {
            this.directoryPathField.setPath(path.toString());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        if (classType == getClass()) {
            return this;
        }
        try {
            return classType == EmulatorFilesPaneController.class ? classType.getDeclaredConstructor(Emulator.class).newInstance(this.f79a) : classType.newInstance();
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m149a() {
        this.f79a.setName(this.nameTextField.getText());
        this.f79a.setDirectory(this.directoryPathField.getPath());
        this.f79a.setFiles(this.emulatorFilesController.m1279a().getItems());
        this.f79a.setLinks(this.linksController.m1303a().getItems());
        this.f79a.setMetas(this.metasController.m1309b());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Emulator resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            m149a();
            return this.f79a;
        }
        return null;
    }
}
