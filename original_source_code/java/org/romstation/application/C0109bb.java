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
import javafx.stage.FileChooser;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.controller.MetasPaneController;

/* JADX INFO: renamed from: org.romstation.application.bb */
/* JADX INFO: compiled from: GameProfileEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bb.class */
public class C0109bb extends ApplicationFXMLDialog<GameProfile> {

    /* JADX INFO: renamed from: a */
    private final GameProfile f271a;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField parametersTextField;

    @FXML
    private PathField pathField;

    @FXML
    private MetasPaneController metasController;

    public C0109bb(GameProfile gameProfile) {
        this.f271a = gameProfile;
        load(getClass().getResource("/fxml/dialog/editor/game/gameProfileEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f271a.getName());
        this.parametersTextField.setText(this.f271a.getParameters());
        this.pathField.setPath(this.f271a.getPath());
        this.metasController.m1308a().getItems().setAll(this.f271a.getMetas().entrySet());
        setResizable(true);
    }

    @FXML
    private void selectPath() {
        FileChooser fileChooser = new FileChooser();
        String path = this.pathField.getPath() != null ? this.pathField.getPath() : this.f271a.getGameFile().getDirectory();
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
    private void m556a() {
        this.f271a.setName(this.nameTextField.getText());
        this.f271a.setParameters(this.parametersTextField.getText());
        this.f271a.setPath(this.pathField.getPath());
        this.f271a.setMetas(this.metasController.m1309b());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public GameProfile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            m556a();
            return this.f271a;
        }
        return null;
    }
}
