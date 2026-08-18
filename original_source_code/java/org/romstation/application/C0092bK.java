package org.romstation.application;

import com.google.common.eventbus.Subscribe;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.bK */
/* JADX INFO: compiled from: PathsSettingsController.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bK.class */
public class C0092bK {

    @FXML
    private GridPane root;

    @FXML
    private PathField gamesPathField;

    @FXML
    private PathField emulatorsPathField;

    @FXML
    private PathField screenshotsPathField;

    @FXML
    private void initialize() {
        C0093bL.f230a.register(this);
        this.gamesPathField.setPath(RomStation.m43c().getProperty("path.games"));
        this.emulatorsPathField.setPath(RomStation.m43c().getProperty("path.emulators"));
        this.screenshotsPathField.setPath(RomStation.m43c().getProperty("path.screenshots"));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m446a(C0104bW event) {
        RomStation.m43c().setProperty("path.games", this.gamesPathField.getPath());
        RomStation.m43c().setProperty("path.emulators", this.emulatorsPathField.getPath());
        RomStation.m43c().setProperty("path.screenshots", this.screenshotsPathField.getPath());
    }

    /* JADX INFO: renamed from: a */
    private void m447a(PathField pathField) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (pathField.getPath() != null) {
            Path defaultPath = Paths.get(pathField.getPath(), new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                directoryChooser.setInitialDirectory(defaultPath.toFile());
            }
        }
        File path = directoryChooser.showDialog(this.root.getScene().getWindow());
        if (path != null) {
            pathField.setPath(path.toString());
        }
    }

    @FXML
    private void selectGamesPath(ActionEvent actionEvent) {
        m447a(this.gamesPathField);
    }

    @FXML
    private void selectEmulatorsPath(ActionEvent actionEvent) {
        m447a(this.emulatorsPathField);
    }

    @FXML
    private void selectScreenshotsPath(ActionEvent event) {
        m447a(this.screenshotsPathField);
    }
}
