package org.romstation.application.view.controller.library.table;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import org.romstation.application.C0184cx;
import org.romstation.application.database.entity.Game;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.view.controller.library.GameContextMenu;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/table/LibraryTableViewGameTableRow.class */
public class LibraryTableViewGameTableRow extends TableRow<Game> {
    public LibraryTableViewGameTableRow(GameContextMenu contextMenuController) {
        setOnContextMenuRequested(event -> {
            contextMenuController.m1507a().setAll(getTableView().getSelectionModel().getSelectedItems());
        });
        contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(itemProperty())).then(contextMenuController.m1508b()).otherwise((ContextMenu) null));
        setOnMouseClicked(event2 -> {
            if (event2.getButton().equals(MouseButton.PRIMARY) && event2.getClickCount() == 2) {
                RomStationController.f786a.post(new C0184cx((Game) getItem(), new String[0]));
            }
        });
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/table/LibraryTableViewGameTableRow$Factory.class */
    public static class Factory implements Callback<TableView<Game>, TableRow<Game>> {

        /* JADX INFO: renamed from: a */
        private final GameContextMenu f847a;

        public Factory(GameContextMenu contextMenuController) {
            this.f847a = contextMenuController;
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public TableRow<Game> call(TableView<Game> param) {
            return new LibraryTableViewGameTableRow(this.f847a);
        }
    }
}
