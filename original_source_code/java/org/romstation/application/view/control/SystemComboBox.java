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
import org.romstation.application.C0048aU;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.System;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/SystemComboBox.class */
public class SystemComboBox extends HBox {

    @FXML
    private ComboBox<System> comboBox;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private MenuItem clearMenuItem;

    @FXML
    private ResourceBundle resources;

    public SystemComboBox() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/control/systemComboBox.fxml"));
            fxmlLoader.setResources(RomStation.m44d());
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    public ComboBox<System> getComboBox() {
        return this.comboBox;
    }

    @FXML
    private void initialize() {
        this.editMenuItem.disableProperty().bind(this.comboBox.valueProperty().isNull());
        this.clearMenuItem.disableProperty().bind(this.comboBox.valueProperty().isNull());
    }

    @FXML
    private void create() {
        C0048aU dialog = new C0048aU(new System());
        Optional<System> optional = dialog.showAndWait();
        optional.ifPresent(system -> {
            this.comboBox.getItems().add(system);
            this.comboBox.setValue(system);
        });
    }

    @FXML
    private void edit() {
        C0048aU dialog = new C0048aU((System) this.comboBox.getSelectionModel().getSelectedItem());
        Optional<System> optional = dialog.showAndWait();
        optional.ifPresent(system -> {
            this.comboBox.getItems().setAll(new LinkedList(this.comboBox.getItems()));
            this.comboBox.getSelectionModel().select(system);
        });
    }

    @FXML
    private void clear() {
        this.comboBox.getSelectionModel().clearSelection();
    }
}
