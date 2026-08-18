package org.romstation.application.view.control.cell.entity;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.romstation.application.database.entity.Developer;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/DeveloperTableCell.class */
public class DeveloperTableCell<S> extends TableCell<S, Developer> {
    public DeveloperTableCell() {
        getStyleClass().add("developer");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Developer developer, boolean empty) {
        super.updateItem(developer, empty);
        if (developer == null || empty) {
            setText(null);
        } else {
            setText(developer.getName());
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/DeveloperTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Developer>, DeveloperTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public DeveloperTableCell<S> call(TableColumn<S, Developer> param) {
            return new DeveloperTableCell<>();
        }
    }
}
