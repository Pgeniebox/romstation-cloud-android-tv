package org.romstation.application.view.controller;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.romstation.application.C0109bb;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.database.entity.GameProfile;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/GameProfilesPaneController.class */
public class GameProfilesPaneController {

    /* JADX INFO: renamed from: a */
    private final GameFile f783a;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button copyButton;

    @FXML
    private TableView<GameProfile> tableView;

    @FXML
    private TableColumn<GameProfile, Integer> idTableColumn;

    @FXML
    private TableColumn<GameProfile, String> nameTableColumn;

    @FXML
    private ResourceBundle resources;

    public GameProfilesPaneController(GameFile gameFile) {
        this.f783a = gameFile;
    }

    /* JADX INFO: renamed from: a */
    public TableView<GameProfile> m1287a() {
        return this.tableView;
    }

    @FXML
    private void initialize() {
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.copyButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.tableView.getItems().setAll(this.f783a.getProfiles());
    }

    @FXML
    private void add() {
        GameProfile gameProfile = new GameProfile();
        gameProfile.setGameFile(this.f783a);
        C0109bb dialog = new C0109bb(gameProfile);
        Optional<GameProfile> optional = dialog.showAndWait();
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
        C0109bb dialog = new C0109bb((GameProfile) this.tableView.getSelectionModel().getSelectedItem());
        Optional<GameProfile> optional = dialog.showAndWait();
        optional.ifPresent(emulatorProfile -> {
            this.tableView.refresh();
        });
    }

    @FXML
    private void copy() {
        this.tableView.getItems().add(new GameProfile((GameProfile) this.tableView.getSelectionModel().getSelectedItem()));
    }
}
