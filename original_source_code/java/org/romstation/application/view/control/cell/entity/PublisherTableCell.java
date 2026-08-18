package org.romstation.application.view.control.cell.entity;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.romstation.application.database.entity.Publisher;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/PublisherTableCell.class */
public class PublisherTableCell<S> extends TableCell<S, Publisher> {
    public PublisherTableCell() {
        getStyleClass().add("publisher");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Publisher publisher, boolean empty) {
        super.updateItem(publisher, empty);
        if (publisher == null || empty) {
            setText(null);
        } else {
            setText(publisher.getName());
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/PublisherTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Publisher>, PublisherTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public PublisherTableCell<S> call(TableColumn<S, Publisher> param) {
            return new PublisherTableCell<>();
        }
    }
}
