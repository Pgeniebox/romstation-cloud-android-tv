package org.romstation.application.view.controller.emulators.table;

import com.google.common.eventbus.Subscribe;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.romstation.application.C0171ck;
import org.romstation.application.C0172cl;
import org.romstation.application.C0173cm;
import org.romstation.application.C0174cn;
import org.romstation.application.C0175co;
import org.romstation.application.C0177cq;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.System;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/emulators/table/EmulatorsTableViewController.class */
public class EmulatorsTableViewController {

    @FXML
    private TableView<Emulator> tableView;

    @FXML
    private TableColumn<Emulator, Integer> idColumn;

    @FXML
    private TableColumn<Emulator, String> nameColumn;

    @FXML
    private TableColumn<Emulator, Integer> filesColumn;

    @FXML
    private TableColumn<Emulator, List<System>> systemsColumn;

    @FXML
    private Button launchButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button openExplorerButton;

    @FXML
    private Button showLinksButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.tableView.setRowFactory(new EmulatorTableRow.Factory());
        this.filesColumn.setCellValueFactory(param -> {
            return new ReadOnlyObjectWrapper(Integer.valueOf(((Emulator) param.getValue()).getFiles().size()));
        });
        this.launchButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.openExplorerButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.showLinksButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1505a(C0177cq event) {
        this.tableView.getItems().setAll(event.m737a());
        this.tableView.sort();
    }

    @FXML
    private void launch(ActionEvent actionEvent) {
        RomStationController.f786a.post(new C0173cm((Emulator) this.tableView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void edit(ActionEvent actionEvent) {
        RomStationController.f786a.post(new C0172cl((Emulator) this.tableView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void delete(ActionEvent actionEvent) {
        RomStationController.f786a.post(new C0171ck(this.tableView.getSelectionModel().getSelectedItems()));
    }

    @FXML
    private void openExplorer(ActionEvent actionEvent) {
        RomStationController.f786a.post(new C0174cn((Emulator) this.tableView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void showLinks(ActionEvent actionEvent) {
        RomStationController.f786a.post(new C0175co((Emulator) this.tableView.getSelectionModel().getSelectedItem()));
    }

    /* JADX INFO: renamed from: org.romstation.application.view.controller.emulators.table.EmulatorsTableViewController$1 */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/emulators/table/EmulatorsTableViewController$1.class */
    static /* synthetic */ class C02661 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f819a = new int[KeyCode.values().length];

        static {
            try {
                f819a[KeyCode.ENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f819a[KeyCode.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (!this.tableView.getSelectionModel().isEmpty()) {
            switch (C02661.f819a[keyEvent.getCode().ordinal()]) {
                case 1:
                    RomStationController.f786a.post(new C0173cm((Emulator) this.tableView.getSelectionModel().getSelectedItem()));
                    break;
                case 2:
                    RomStationController.f786a.post(new C0171ck(this.tableView.getSelectionModel().getSelectedItems()));
                    break;
            }
        }
    }
}
