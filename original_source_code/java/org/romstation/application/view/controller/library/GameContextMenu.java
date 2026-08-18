package org.romstation.application.view.controller.library;

import java.util.LinkedList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.romstation.application.C0135cA;
import org.romstation.application.C0136cB;
import org.romstation.application.C0178cr;
import org.romstation.application.C0179cs;
import org.romstation.application.C0184cx;
import org.romstation.application.database.entity.Game;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/GameContextMenu.class */
public class GameContextMenu {

    /* JADX INFO: renamed from: a */
    private final ObservableList<Game> f820a = FXCollections.observableList(new LinkedList());

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem launchMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private MenuItem browseMenuItem;

    @FXML
    private MenuItem linkMenuItem;

    /* JADX INFO: renamed from: a */
    public ObservableList<Game> m1507a() {
        return this.f820a;
    }

    /* JADX INFO: renamed from: b */
    public ContextMenu m1508b() {
        return this.contextMenu;
    }

    @FXML
    private void launch() {
        RomStationController.f786a.post(new C0184cx((Game) this.f820a.get(0), new String[0]));
    }

    @FXML
    private void edit() {
        RomStationController.f786a.post(new C0179cs((Game) this.f820a.get(0)));
    }

    @FXML
    private void delete() {
        RomStationController.f786a.post(new C0178cr(this.f820a));
    }

    @FXML
    private void browse() {
        RomStationController.f786a.post(new C0135cA((Game) this.f820a.get(0)));
    }

    @FXML
    private void openLinks() {
        RomStationController.f786a.post(new C0136cB((Game) this.f820a.get(0)));
    }

    @FXML
    private void initialize() {
        this.launchMenuItem.disableProperty().bind(Bindings.size(this.f820a).isNotEqualTo(1));
        this.editMenuItem.disableProperty().bind(Bindings.size(this.f820a).isNotEqualTo(1));
        this.browseMenuItem.disableProperty().bind(Bindings.size(this.f820a).isNotEqualTo(1));
        this.linkMenuItem.disableProperty().bind(Bindings.size(this.f820a).isNotEqualTo(1));
    }
}
