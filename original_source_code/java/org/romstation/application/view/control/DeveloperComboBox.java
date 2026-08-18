package org.romstation.application.view.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import org.romstation.application.C0040aM;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Developer;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/DeveloperComboBox.class */
public class DeveloperComboBox extends HBox {

    @FXML
    private ComboBox<Developer> comboBox;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private MenuItem clearMenuItem;

    @FXML
    private ResourceBundle resources;

    public DeveloperComboBox() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/control/developerComboBox.fxml"));
            fxmlLoader.setResources(RomStation.m44d());
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    public ComboBox<Developer> getComboBox() {
        return this.comboBox;
    }

    @FXML
    private void initialize() {
        this.editMenuItem.disableProperty().bind(this.comboBox.valueProperty().isNull());
        this.clearMenuItem.disableProperty().bind(this.comboBox.valueProperty().isNull());
    }

    @FXML
    private void create() {
        C0040aM dialog = new C0040aM(new Developer());
        Optional<Developer> optional = dialog.showAndWait();
        optional.ifPresent(developer -> {
            this.comboBox.getItems().add(developer);
            this.comboBox.setValue(developer);
        });
    }

    @FXML
    private void edit() {
        C0040aM dialog = new C0040aM((Developer) this.comboBox.getSelectionModel().getSelectedItem());
        Optional<Developer> optional = dialog.showAndWait();
        optional.ifPresent(developer -> {
            this.comboBox.getItems().setAll(new LinkedList(this.comboBox.getItems()));
            this.comboBox.getSelectionModel().select(developer);
        });
    }

    @FXML
    private void clear() {
        this.comboBox.getSelectionModel().clearSelection();
    }
}
