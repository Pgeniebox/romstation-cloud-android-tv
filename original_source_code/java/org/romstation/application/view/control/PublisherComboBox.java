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
import org.romstation.application.C0045aR;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Publisher;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/PublisherComboBox.class */
public class PublisherComboBox extends HBox {

    @FXML
    private ComboBox<Publisher> comboBox;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private MenuItem clearMenuItem;

    @FXML
    private ResourceBundle resources;

    public PublisherComboBox() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/control/publisherComboBox.fxml"));
            fxmlLoader.setResources(RomStation.m44d());
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    public ComboBox<Publisher> getComboBox() {
        return this.comboBox;
    }

    @FXML
    private void initialize() {
        this.editMenuItem.disableProperty().bind(this.comboBox.valueProperty().isNull());
        this.clearMenuItem.disableProperty().bind(this.comboBox.valueProperty().isNull());
    }

    @FXML
    private void create() {
        C0045aR dialog = new C0045aR(new Publisher());
        Optional<Publisher> optional = dialog.showAndWait();
        optional.ifPresent(publisher -> {
            this.comboBox.getItems().add(publisher);
            this.comboBox.setValue(publisher);
        });
    }

    @FXML
    private void edit() {
        C0045aR dialog = new C0045aR((Publisher) this.comboBox.getSelectionModel().getSelectedItem());
        Optional<Publisher> optional = dialog.showAndWait();
        optional.ifPresent(publisher -> {
            this.comboBox.getItems().setAll(new LinkedList(this.comboBox.getItems()));
            this.comboBox.getSelectionModel().select(publisher);
        });
    }

    @FXML
    private void clear() {
        this.comboBox.getSelectionModel().clearSelection();
    }
}
