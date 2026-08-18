package org.romstation.application.view.control.cell.entity;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.romstation.application.database.entity.Developer;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/DeveloperListCell.class */
public class DeveloperListCell extends ListCell<Developer> {
    public DeveloperListCell() {
        getStyleClass().add("developer");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Developer developer, boolean empty) {
        super.updateItem(developer, empty);
        if (empty) {
            setText(null);
        } else {
            setText(developer.getName());
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/DeveloperListCell$Factory.class */
    public static class Factory implements Callback<ListView<Developer>, ListCell<Developer>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Developer> call(ListView<Developer> param) {
            return new DeveloperListCell();
        }
    }
}
