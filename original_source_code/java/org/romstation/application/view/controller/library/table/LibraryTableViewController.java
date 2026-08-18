package org.romstation.application.view.controller.library.table;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.romstation.application.C0135cA;
import org.romstation.application.C0136cB;
import org.romstation.application.C0139cE;
import org.romstation.application.C0166cf;
import org.romstation.application.C0178cr;
import org.romstation.application.C0179cs;
import org.romstation.application.C0184cx;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Game;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.view.controller.library.LibraryController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/table/LibraryTableViewController.class */
public class LibraryTableViewController {

    @FXML
    private TableView<Game> tableView;

    @FXML
    private TableColumn<Game, Integer> filesTableColumn;

    @FXML
    private Button launchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button openExplorerButton;

    @FXML
    private Button showLinksButton;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(RomStation.m43c().getProperty("library.tableView.sort"));
        jsonArray.forEach(element -> {
            JsonObject object = element.getAsJsonObject();
            this.tableView.getColumns().stream().filter(column -> {
                try {
                    return column.getUserData() == LibraryTableViewColumn.valueOf(object.get("column").getAsString());
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }).findFirst().ifPresent(column2 -> {
                column2.setSortType(TableColumn.SortType.valueOf(object.get("order").getAsString()));
                this.tableView.getSortOrder().add(column2);
            });
        });
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.tableView.setRowFactory(new LibraryTableViewGameTableRow.Factory(LibraryController.m1509a()));
        this.filesTableColumn.setCellValueFactory(param -> {
            return new ReadOnlyObjectWrapper(Integer.valueOf(((Game) param.getValue()).getFiles().size()));
        });
        this.launchButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.openExplorerButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.showLinksButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
    }

    @FXML
    private void launch() {
        RomStationController.f786a.post(new C0184cx((Game) this.tableView.getSelectionModel().getSelectedItem(), new String[0]));
    }

    @FXML
    private void edit() {
        RomStationController.f786a.post(new C0179cs((Game) this.tableView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void delete() {
        RomStationController.f786a.post(new C0178cr(this.tableView.getSelectionModel().getSelectedItems()));
    }

    @FXML
    private void openExplorer() {
        RomStationController.f786a.post(new C0135cA((Game) this.tableView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void showLinks() {
        RomStationController.f786a.post(new C0136cB((Game) this.tableView.getSelectionModel().getSelectedItem()));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1599a(C0139cE event) {
        this.tableView.getItems().setAll(event.m646a());
        this.tableView.sort();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1600a(C0166cf event) {
        JsonArray jsonArray = new JsonArray();
        for (TableColumn<Game, ?> column : this.tableView.getSortOrder()) {
            JsonObject element = new JsonObject();
            element.addProperty("column", ((LibraryTableViewColumn) column.getUserData()).name());
            element.addProperty("order", column.getSortType().name());
            jsonArray.add(element);
        }
        RomStation.m43c().setProperty("library.tableView.sort", jsonArray.toString());
    }

    /* JADX INFO: renamed from: org.romstation.application.view.controller.library.table.LibraryTableViewController$1 */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/table/LibraryTableViewController$1.class */
    static /* synthetic */ class C02701 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f846a = new int[KeyCode.values().length];

        static {
            try {
                f846a[KeyCode.ENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f846a[KeyCode.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (!this.tableView.getSelectionModel().isEmpty()) {
            switch (C02701.f846a[keyEvent.getCode().ordinal()]) {
                case 1:
                    RomStationController.f786a.post(new C0184cx((Game) this.tableView.getSelectionModel().getSelectedItem(), new String[0]));
                    break;
                case 2:
                    RomStationController.f786a.post(new C0178cr(this.tableView.getSelectionModel().getSelectedItems()));
                    break;
            }
        }
    }
}
