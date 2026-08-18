package org.romstation.application.view.controller;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import org.romstation.application.C0046aS;
import org.romstation.application.database.entity.Script;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/ScriptsPaneController.class */
public class ScriptsPaneController {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Script> tableView;

    @FXML
    private ResourceBundle resources;

    /* JADX INFO: renamed from: a */
    public TableView<Script> m1323a() {
        return this.tableView;
    }

    @FXML
    private void initialize() {
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    @FXML
    private void add() {
        C0046aS dialog = new C0046aS(new Script());
        Optional<Script> result = dialog.showAndWait();
        result.ifPresent(script -> {
            this.tableView.getItems().add(script);
        });
    }

    @FXML
    private void edit() {
        C0046aS dialog = new C0046aS((Script) this.tableView.getSelectionModel().getSelectedItem());
        Optional<Script> result = dialog.showAndWait();
        result.ifPresent(script -> {
            this.tableView.refresh();
        });
    }

    @FXML
    private void delete() {
        this.tableView.getItems().removeAll(this.tableView.getSelectionModel().getSelectedItems());
    }
}
