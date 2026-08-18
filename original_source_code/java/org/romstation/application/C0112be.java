package org.romstation.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.be */
/* JADX INFO: compiled from: RomStationDirectoryChooserDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/be.class */
public class C0112be extends ApplicationFXMLDialog<Path> {

    @FXML
    private DialogPane root;

    @FXML
    private PathField pathField;

    @FXML
    private Label successLabel;

    @FXML
    private Label errorLabel;

    public C0112be() {
        load(getClass().getResource("/fxml/dialog/importer/legacy/romstationDirectoryChooserDialog.fxml"));
    }

    @FXML
    private void initialize() {
        Path[] paths = {Paths.get("C:/RomStation", new String[0]), Paths.get("C:/Program Files (x86)/RomStation", new String[0])};
        for (Path path : paths) {
            if (m570a(path)) {
                this.pathField.setPath(path.toString());
                this.successLabel.setVisible(true);
                break;
            }
        }
        if (this.pathField.getPath() == null) {
            this.root.lookupButton(ButtonType.OK).setDisable(true);
        }
        this.pathField.getTextField().setEditable(false);
    }

    @FXML
    private void chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (this.pathField.getPath() != null) {
            directoryChooser.setInitialDirectory(Paths.get(this.pathField.getPath(), new String[0]).toFile());
        }
        File directory = directoryChooser.showDialog(getDialogPane().getScene().getWindow());
        if (directory != null) {
            this.pathField.setPath(directory.toString());
            if (m570a(directory.toPath())) {
                this.successLabel.setVisible(true);
                this.errorLabel.setVisible(false);
                getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            } else {
                this.successLabel.setVisible(false);
                this.errorLabel.setVisible(true);
                getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private boolean m570a(Path path) {
        return Files.exists(path.resolve("RomStation.exe"), new LinkOption[0]) && Files.exists(path.resolve("database.sqlite"), new LinkOption[0]);
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Path resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK && this.pathField.getPath() != null) {
            return Paths.get(this.pathField.getPath(), new String[0]);
        }
        return null;
    }
}
