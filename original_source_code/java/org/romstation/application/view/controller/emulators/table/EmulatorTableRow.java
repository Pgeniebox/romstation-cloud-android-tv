package org.romstation.application.view.controller.emulators.table;

import java.io.IOException;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import org.romstation.application.C0173cm;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.view.controller.emulators.EmulatorContextMenuController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/emulators/table/EmulatorTableRow.class */
public class EmulatorTableRow extends TableRow<Emulator> {
    public EmulatorTableRow(EmulatorContextMenuController controller) {
        setOnContextMenuRequested(event -> {
            controller.m1470a().setAll(getTableView().getSelectionModel().getSelectedItems());
        });
        contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(itemProperty())).then(controller.m1471b()).otherwise((ContextMenu) null));
        setOnMouseClicked(event2 -> {
            if (event2.getButton().equals(MouseButton.PRIMARY) && event2.getClickCount() == 2) {
                RomStationController.f786a.post(new C0173cm((Emulator) getItem()));
            }
        });
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/emulators/table/EmulatorTableRow$Factory.class */
    public static class Factory implements Callback<TableView<Emulator>, TableRow<Emulator>> {

        /* JADX INFO: renamed from: a */
        private EmulatorContextMenuController f818a;

        public Factory() {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/emulators/contextMenu.fxml"));
            fxmlLoader.setResources(RomStation.m44d());
            try {
                fxmlLoader.load();
                this.f818a = (EmulatorContextMenuController) fxmlLoader.getController();
            } catch (IOException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            }
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public TableRow<Emulator> call(TableView<Emulator> param) {
            return new EmulatorTableRow(this.f818a);
        }
    }
}
