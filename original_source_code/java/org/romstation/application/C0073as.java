package org.romstation.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.as */
/* JADX INFO: compiled from: GameFileImportDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/as.class */
public class C0073as extends ApplicationFXMLDialog<GameFile> {

    /* JADX INFO: renamed from: a */
    private final Game f153a;

    @FXML
    private DialogPane root;

    @FXML
    private TextField nameTextField;

    @FXML
    private PathField pathField;

    public C0073as(Game game) {
        this.f153a = game;
        load(getClass().getResource("/fxml/dialog/gameFileImportDialog.fxml"));
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
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public GameFile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            GameFile gameFile = new GameFile();
            gameFile.setGame(this.f153a);
            gameFile.setName(this.nameTextField.getText());
            gameFile.setDirectory(Paths.get(this.pathField.getPath(), new String[0]).getParent().toString());
            GameProfile defaultProfile = new GameProfile();
            defaultProfile.setGameFile(gameFile);
            defaultProfile.setName(gameFile.getName());
            defaultProfile.setPath(this.pathField.getPath());
            gameFile.getProfiles().add(defaultProfile);
            return gameFile;
        }
        return null;
    }
}
