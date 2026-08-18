package org.romstation.application.view.controller;

import java.util.AbstractMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.romstation.application.C0044aQ;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/MetasPaneController.class */
public class MetasPaneController {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Map.Entry<String, String>> tableView;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> keyTableColumn;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> valueTableColumn;

    @FXML
    private ResourceBundle resources;

    /* JADX INFO: renamed from: a */
    public TableView<Map.Entry<String, String>> m1308a() {
        return this.tableView;
    }

    /* JADX INFO: renamed from: b */
    public Map<String, String> m1309b() {
        return (Map) this.tableView.getItems().stream().collect(Collectors.toMap((v0) -> {
            return v0.getKey();
        }, (v0) -> {
            return v0.getValue();
        }, (k1, k2) -> {
            return k1;
        }));
    }

    @FXML
    private void initialize() {
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.keyTableColumn.setCellValueFactory(param -> {
            return new ReadOnlyStringWrapper((String) ((Map.Entry) param.getValue()).getKey());
        });
        this.valueTableColumn.setCellValueFactory(param2 -> {
            return new ReadOnlyStringWrapper((String) ((Map.Entry) param2.getValue()).getValue());
        });
    }

    @FXML
    private void add() {
        C0044aQ metaEditorDialog = new C0044aQ(new AbstractMap.SimpleEntry(null, null));
        metaEditorDialog.showAndWait().ifPresent(entry -> {
            this.tableView.getItems().add(entry);
        });
    }

    @FXML
    private void delete() {
        this.tableView.getItems().removeAll(this.tableView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void edit() {
        Map.Entry<String, String> selectedItem = (Map.Entry) this.tableView.getSelectionModel().getSelectedItem();
        C0044aQ metaEditorDialog = new C0044aQ(selectedItem);
        metaEditorDialog.showAndWait().ifPresent(entry -> {
            this.tableView.getItems().remove(selectedItem);
            this.tableView.getItems().add(entry);
            this.tableView.getSelectionModel().clearAndSelect(this.tableView.getItems().indexOf(entry));
        });
    }
}
