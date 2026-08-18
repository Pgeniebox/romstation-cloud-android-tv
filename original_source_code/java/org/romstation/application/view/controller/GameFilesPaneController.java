package org.romstation.application.view.controller;

import java.awt.Desktop;
import java.io.File;
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
import org.romstation.application.C0073as;
import org.romstation.application.C0076av;
import org.romstation.application.C0108ba;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.task.C0246n;
import org.romstation.application.view.control.ApplicationAlert;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/GameFilesPaneController.class */
public class GameFilesPaneController {

    /* JADX INFO: renamed from: a */
    private final Game f782a;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button explorerButton;

    @FXML
    private Button copyButton;

    @FXML
    private TableView<GameFile> tableView;

    @FXML
    private TableColumn<GameFile, Integer> idTableColumn;

    @FXML
    private TableColumn<GameFile, String> nameTableColumn;

    @FXML
    private TableColumn<GameFile, Integer> profilesTableColumn;

    @FXML
    private ResourceBundle resources;

    public GameFilesPaneController(Game game) {
        this.f782a = game;
    }

    /* JADX INFO: renamed from: a */
    public TableView<GameFile> m1284a() {
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
            return new ReadOnlyObjectWrapper(Integer.valueOf(((GameFile) param.getValue()).getProfiles().size()));
        });
        this.tableView.getItems().setAll(this.f782a.getFiles());
    }

    @FXML
    private void create() {
        GameFile gameFile = new GameFile();
        gameFile.setGame(this.f782a);
        C0108ba dialog = new C0108ba(gameFile);
        Optional<GameFile> optional = dialog.showAndWait();
        ObservableList items = this.tableView.getItems();
        items.getClass();
        optional.ifPresent((v1) -> {
            r1.add(v1);
        });
    }

    @FXML
    private void importFile() {
        C0073as dialog = new C0073as(this.f782a);
        Optional optionalShowAndWait = dialog.showAndWait();
        ObservableList items = this.tableView.getItems();
        items.getClass();
        optionalShowAndWait.ifPresent((v1) -> {
            r1.add(v1);
        });
    }

    @FXML
    private void delete() {
        ApplicationAlert alert = new ApplicationAlert(this.resources.getString("gameFile.delete.alert.header"), this.resources.getString("gameFile.delete.alert.content"), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Task c0246n = new C0246n(this.tableView.getSelectionModel().getSelectedItems());
            Thread thread = new Thread((Runnable) c0246n);
            C0076av<List<GameFile>> dialog = new C0076av<>(c0246n, "delete");
            thread.start();
            Optional<List<GameFile>> dialogResult = dialog.showAndWait();
            ObservableList items = this.tableView.getItems();
            items.getClass();
            dialogResult.ifPresent((v1) -> {
                r1.removeAll(v1);
            });
        }
    }

    @FXML
    private void edit() {
        C0108ba dialog = new C0108ba((GameFile) this.tableView.getSelectionModel().getSelectedItem());
        Optional<GameFile> optional = dialog.showAndWait();
        optional.ifPresent(emulatorFile -> {
            this.tableView.refresh();
        });
    }

    @FXML
    private void openExplorer() {
        GameFile selectedItem = (GameFile) this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem.getDirectory() != null) {
            try {
                Desktop.getDesktop().open(new File(selectedItem.getDirectory()));
            } catch (Exception exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
        }
    }

    @FXML
    private void copy() {
        this.tableView.getItems().add(new GameFile((GameFile) this.tableView.getSelectionModel().getSelectedItem()));
    }
}
