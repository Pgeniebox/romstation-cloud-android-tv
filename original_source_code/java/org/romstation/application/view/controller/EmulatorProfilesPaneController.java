package org.romstation.application.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.romstation.application.C0052aY;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.System;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/EmulatorProfilesPaneController.class */
public class EmulatorProfilesPaneController {

    /* JADX INFO: renamed from: a */
    private final EmulatorFile f781a;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button copyButton;

    @FXML
    private TableView<EmulatorProfile> tableView;

    @FXML
    private TableColumn<EmulatorProfile, Integer> idTableColumn;

    @FXML
    private TableColumn<EmulatorProfile, String> nameTableColumn;

    @FXML
    private TableColumn<EmulatorProfile, List<System>> systemsTableColumn;

    @FXML
    private ResourceBundle resources;

    public EmulatorProfilesPaneController(EmulatorFile emulatorFile) {
        this.f781a = emulatorFile;
    }

    /* JADX INFO: renamed from: a */
    public TableView<EmulatorProfile> m1282a() {
        return this.tableView;
    }

    @FXML
    private void initialize() {
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.copyButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.tableView.getItems().setAll(this.f781a.getProfiles());
    }

    @FXML
    private void add() {
        EmulatorProfile emulatorProfile = new EmulatorProfile();
        emulatorProfile.setEmulatorFile(this.f781a);
        C0052aY dialog = new C0052aY(emulatorProfile);
        Optional<EmulatorProfile> optional = dialog.showAndWait();
        ObservableList items = this.tableView.getItems();
        items.getClass();
        optional.ifPresent((v1) -> {
            r1.add(v1);
        });
    }

    @FXML
    private void delete() {
        this.tableView.getItems().removeAll(this.tableView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void edit() {
        C0052aY dialog = new C0052aY((EmulatorProfile) this.tableView.getSelectionModel().getSelectedItem());
        Optional<EmulatorProfile> optional = dialog.showAndWait();
        optional.ifPresent(emulatorProfile -> {
            this.tableView.refresh();
        });
    }

    @FXML
    private void copy() {
        this.tableView.getItems().add(new EmulatorProfile((EmulatorProfile) this.tableView.getSelectionModel().getSelectedItem()));
    }
}
