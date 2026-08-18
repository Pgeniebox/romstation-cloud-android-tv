package org.romstation.application.view.controller.emulators;

import java.util.LinkedList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.romstation.application.C0171ck;
import org.romstation.application.C0172cl;
import org.romstation.application.C0173cm;
import org.romstation.application.C0174cn;
import org.romstation.application.C0175co;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/emulators/EmulatorContextMenuController.class */
public class EmulatorContextMenuController {

    /* JADX INFO: renamed from: a */
    private final ObservableList<Emulator> f814a = FXCollections.observableList(new LinkedList());

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem launchMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private MenuItem explorerMenuItem;

    @FXML
    private MenuItem linkMenuItem;

    /* JADX INFO: renamed from: a */
    public ObservableList<Emulator> m1470a() {
        return this.f814a;
    }

    /* JADX INFO: renamed from: b */
    public ContextMenu m1471b() {
        return this.contextMenu;
    }

    @FXML
    private void initialize() {
        this.launchMenuItem.disableProperty().bind(Bindings.size(this.f814a).isNotEqualTo(1));
        this.editMenuItem.disableProperty().bind(Bindings.size(this.f814a).isNotEqualTo(1));
        this.explorerMenuItem.disableProperty().bind(Bindings.size(this.f814a).isNotEqualTo(1));
        this.linkMenuItem.disableProperty().bind(Bindings.size(this.f814a).isNotEqualTo(1));
    }

    @FXML
    private void launchEmulator() {
        RomStationController.f786a.post(new C0173cm((Emulator) this.f814a.get(0)));
    }

    @FXML
    private void editEmulator() {
        RomStationController.f786a.post(new C0172cl((Emulator) this.f814a.get(0)));
    }

    @FXML
    private void deleteEmulator() {
        RomStationController.f786a.post(new C0171ck(this.f814a));
    }

    @FXML
    private void openExplorer() {
        RomStationController.f786a.post(new C0174cn((Emulator) this.f814a.get(0)));
    }

    @FXML
    private void openLinks() {
        RomStationController.f786a.post(new C0175co((Emulator) this.f814a.get(0)));
    }
}
