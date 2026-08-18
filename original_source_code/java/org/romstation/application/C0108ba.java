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
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.controller.GameProfilesPaneController;
import org.romstation.application.view.controller.MetasPaneController;

/* JADX INFO: renamed from: org.romstation.application.ba */
/* JADX INFO: compiled from: GameFileEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ba.class */
public class C0108ba extends ApplicationFXMLDialog<GameFile> {

    /* JADX INFO: renamed from: a */
    private final GameFile f270a;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private PathField directoryPathField;

    @FXML
    private GameProfilesPaneController gameProfilesController;

    @FXML
    private MetasPaneController metasController;

    public C0108ba(GameFile gameFile) {
        this.f270a = gameFile;
        load(getClass().getResource("/fxml/dialog/editor/game/gameFileEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.nameTextField.setText(this.f270a.getName());
        this.directoryPathField.setPath(this.f270a.getDirectory());
        this.directoryPathField.setDisable(this.f270a.isManaged());
        this.metasController.m1308a().getItems().setAll(this.f270a.getMetas().entrySet());
        setResizable(true);
    }

    @FXML
    private void selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String path = this.directoryPathField.getPath() != null ? this.directoryPathField.getPath() : this.f270a.getGame().getDirectory();
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
            return classType == GameProfilesPaneController.class ? classType.getDeclaredConstructor(GameFile.class).newInstance(this.f270a) : classType.newInstance();
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m554a() {
        this.f270a.setName(this.nameTextField.getText());
        this.f270a.setDirectory(this.directoryPathField.getPath());
        this.f270a.setProfiles(this.gameProfilesController.m1287a().getItems());
        this.f270a.setMetas(this.metasController.m1309b());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public GameFile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            m554a();
            return this.f270a;
        }
        return null;
    }
}
