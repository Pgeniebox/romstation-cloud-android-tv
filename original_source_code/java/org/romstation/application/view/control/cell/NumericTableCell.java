package org.romstation.application.view.control.cell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/NumericTableCell.class */
public class NumericTableCell<S> extends TableCell<S, Number> {
    public NumericTableCell() {
        getStyleClass().add("numeric");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Number number, boolean empty) {
        super.updateItem(number, empty);
        if (empty || number == null) {
            setText(null);
        } else {
            setText(number.toString());
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/NumericTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Number>, NumericTableCell> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public NumericTableCell call(TableColumn<S, Number> param) {
            return new NumericTableCell();
        }
    }
}
