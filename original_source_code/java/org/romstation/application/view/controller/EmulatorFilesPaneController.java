package org.romstation.application.view.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.romstation.application.C0051aX;
import org.romstation.application.C0068ao;
import org.romstation.application.C0076av;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.task.C0239g;
import org.romstation.application.view.control.ApplicationAlert;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/EmulatorFilesPaneController.class */
public class EmulatorFilesPaneController {

    /* JADX INFO: renamed from: a */
    private final Emulator f780a;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button explorerButton;

    @FXML
    private Button copyButton;

    @FXML
    private TableView<EmulatorFile> tableView;

    @FXML
    private TableColumn<EmulatorFile, Integer> idTableColumn;

    @FXML
    private TableColumn<EmulatorFile, String> nameTableColumn;

    @FXML
    private TableColumn<EmulatorFile, Integer> profilesTableColumn;

    @FXML
    private ResourceBundle resources;

    public EmulatorFilesPaneController(Emulator emulator) {
        this.f780a = emulator;
    }

    /* JADX INFO: renamed from: a */
    public TableView<EmulatorFile> m1279a() {
        return this.tableView;
    }

    @FXML
    private void initialize() {
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.explorerButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.copyButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.profilesTableColumn.setCellValueFactory(param -> {
            return new ReadOnlyObjectWrapper(Integer.valueOf(((EmulatorFile) param.getValue()).getProfiles().size()));
        });
        this.tableView.getItems().setAll(this.f780a.getFiles());
    }

    @FXML
    private void create() {
        EmulatorFile emulatorFile = new EmulatorFile();
        emulatorFile.setEmulator(this.f780a);
        C0051aX dialog = new C0051aX(emulatorFile);
        Optional<EmulatorFile> optional = dialog.showAndWait();
        ObservableList items = this.tableView.getItems();
        items.getClass();
        optional.ifPresent((v1) -> {
            r1.add(v1);
        });
    }

    @FXML
    private void importFile() {
        C0068ao dialog = new C0068ao(this.f780a);
        Optional optionalShowAndWait = dialog.showAndWait();
        ObservableList items = this.tableView.getItems();
        items.getClass();
        optionalShowAndWait.ifPresent((v1) -> {
            r1.add(v1);
        });
    }

    @FXML
    private void delete() {
        ApplicationAlert alert = new ApplicationAlert(this.resources.getString("emulatorFile.delete.alert.header"), this.resources.getString("emulatorFile.delete.alert.content"), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Task c0239g = new C0239g(this.tableView.getSelectionModel().getSelectedItems());
            Thread thread = new Thread((Runnable) c0239g);
            C0076av<List<EmulatorFile>> dialog = new C0076av<>(c0239g, "delete");
            thread.start();
            Optional<List<EmulatorFile>> dialogResult = dialog.showAndWait();
            ObservableList items = this.tableView.getItems();
            items.getClass();
            dialogResult.ifPresent((v1) -> {
                r1.removeAll(v1);
            });
        }
    }

    @FXML
    private void edit() {
        C0051aX dialog = new C0051aX((EmulatorFile) this.tableView.getSelectionModel().getSelectedItem());
        Optional<EmulatorFile> optional = dialog.showAndWait();
        optional.ifPresent(emulatorFile -> {
            this.tableView.refresh();
        });
    }

    @FXML
    private void openExplorer() {
        EmulatorFile selectedItem = (EmulatorFile) this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem.getDirectory() != null) {
            try {
                Desktop.getDesktop().open(new File(selectedItem.getDirectory()));
            } catch (IOException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
        }
    }

    @FXML
    private void copy() {
        this.tableView.getItems().add(new EmulatorFile((EmulatorFile) this.tableView.getSelectionModel().getSelectedItem()));
    }
}
