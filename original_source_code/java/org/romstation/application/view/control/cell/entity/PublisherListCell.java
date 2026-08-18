package org.romstation.application.view.control.cell.entity;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.romstation.application.database.entity.Publisher;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/PublisherListCell.class */
public class PublisherListCell extends ListCell<Publisher> {
    public PublisherListCell() {
        getStyleClass().add("publisher");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Publisher publisher, boolean empty) {
        super.updateItem(publisher, empty);
        if (empty) {
            setText(null);
        } else {
            setText(publisher.getName());
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/PublisherListCell$Factory.class */
    public static class Factory implements Callback<ListView<Publisher>, ListCell<Publisher>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Publisher> call(ListView<Publisher> param) {
            return new PublisherListCell();
        }
    }
}
