package org.romstation.application.view.control.cell.entity;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.romstation.application.database.entity.Series;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SeriesListCell.class */
public class SeriesListCell extends ListCell<Series> {
    public SeriesListCell() {
        getStyleClass().add("series");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Series series, boolean empty) {
        super.updateItem(series, empty);
        if (empty) {
            setText(null);
        } else {
            setText(series.getName());
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SeriesListCell$Factory.class */
    public static class Factory implements Callback<ListView<Series>, ListCell<Series>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Series> call(ListView<Series> param) {
            return new SeriesListCell();
        }
    }
}
