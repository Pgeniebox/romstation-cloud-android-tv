package org.romstation.application.view.control.cell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.romstation.application.p000io.C0207a;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/SizeTableCell.class */
public class SizeTableCell<S> extends TableCell<S, Long> {
    public SizeTableCell() {
        getStyleClass().add("size");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Long item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
        } else {
            setText(C0207a.m829a(item.longValue(), true));
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/SizeTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Long>, SizeTableCell> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public SizeTableCell call(TableColumn<S, Long> param) {
            return new SizeTableCell();
        }
    }
}
