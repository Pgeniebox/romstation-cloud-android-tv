package org.romstation.application.view.controller.library.grid;

import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/grid/LibraryGridViewSortFieldListCell.class */
public class LibraryGridViewSortFieldListCell extends ListCell<LibraryGridViewSortField> {
    /* JADX INFO: renamed from: a */
    private String m1596a(LibraryGridViewSortField field) {
        ResourceBundle resources = RomStation.m44d();
        switch (field) {
            case ID:
                return resources.getString("library.view.grid.sort.field.id");
            case TITLE:
                return resources.getString("library.view.grid.sort.field.title");
            case SYSTEM:
                return resources.getString("library.view.grid.sort.field.system");
            case PLAYERS:
                return resources.getString("library.view.grid.sort.field.players");
            case YEAR:
                return resources.getString("library.view.grid.sort.field.year");
            case DEVELOPER:
                return resources.getString("library.view.grid.sort.field.developer");
            case PUBLISHER:
                return resources.getString("library.view.grid.sort.field.publisher");
            case FILES:
                return resources.getString("library.view.grid.sort.field.files");
            case LAST_USE:
                return resources.getString("library.view.grid.sort.field.lastUse");
            case PLAYED:
                return resources.getString("library.view.grid.sort.field.played");
            default:
                return field.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(LibraryGridViewSortField field, boolean empty) {
        super.updateItem(field, empty);
        if (empty) {
            setText(null);
        } else {
            setText(m1596a(field));
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/grid/LibraryGridViewSortFieldListCell$Factory.class */
    public static class Factory implements Callback<ListView<LibraryGridViewSortField>, ListCell<LibraryGridViewSortField>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<LibraryGridViewSortField> call(ListView<LibraryGridViewSortField> param) {
            return new LibraryGridViewSortFieldListCell();
        }
    }
}
