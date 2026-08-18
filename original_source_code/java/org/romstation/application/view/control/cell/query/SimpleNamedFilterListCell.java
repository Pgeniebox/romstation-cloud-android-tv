package org.romstation.application.view.control.cell.query;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0187d;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/query/SimpleNamedFilterListCell.class */
public class SimpleNamedFilterListCell<T> extends ListCell<C0187d> {
    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(C0187d namedFilter, boolean empty) {
        super.updateItem(namedFilter, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (namedFilter.m755e() == null) {
                setGraphic(null);
            } else {
                setGraphic(new ImageView(namedFilter.m755e().getURI().toString()));
            }
            setText(namedFilter.m752c());
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/query/SimpleNamedFilterListCell$Factory.class */
    public static class Factory<T> implements Callback<ListView<C0187d>, ListCell<C0187d>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<C0187d> call(ListView<C0187d> param) {
            return new SimpleNamedFilterListCell();
        }
    }
}
