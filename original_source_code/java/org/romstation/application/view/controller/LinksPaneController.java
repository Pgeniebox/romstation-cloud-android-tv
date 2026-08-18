package org.romstation.application.view.controller;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import org.romstation.application.C0043aP;
import org.romstation.application.database.entity.Link;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/LinksPaneController.class */
public class LinksPaneController {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Link> tableView;

    @FXML
    private TableColumn<Link, Boolean> externalTableColumn;

    @FXML
    private ResourceBundle resources;

    /* JADX INFO: renamed from: a */
    public TableView<Link> m1303a() {
        return this.tableView;
    }

    @FXML
    private void initialize() {
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.externalTableColumn.setCellFactory(column -> {
            return new CheckBoxTableCell(index -> {
                return new SimpleBooleanProperty(((Link) this.tableView.getItems().get(index.intValue())).isExternal());
            });
        });
    }

    @FXML
    private void add() {
        C0043aP dialog = new C0043aP(new Link());
        Optional<Link> result = dialog.showAndWait();
        result.ifPresent(link -> {
            this.tableView.getItems().add(link);
        });
    }

    @FXML
    private void edit() {
        C0043aP dialog = new C0043aP((Link) this.tableView.getSelectionModel().getSelectedItem());
        Optional<Link> result = dialog.showAndWait();
        result.ifPresent(link -> {
            this.tableView.refresh();
        });
    }

    @FXML
    private void delete() {
        this.tableView.getItems().removeAll(this.tableView.getSelectionModel().getSelectedItems());
    }
}
