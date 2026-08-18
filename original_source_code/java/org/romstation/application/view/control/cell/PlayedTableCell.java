package org.romstation.application.view.control.cell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/PlayedTableCell.class */
public class PlayedTableCell<S> extends TableCell<S, Long> {
    public PlayedTableCell() {
        getStyleClass().add("played");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Long time, boolean empty) {
        super.updateItem(time, empty);
        if (empty || time == null) {
            setText(null);
            return;
        }
        if (time.longValue() == 0) {
            setText(null);
            return;
        }
        if (time.longValue() >= 86400) {
            setText(String.format(RomStation.m44d().getString("playedTableCell.format.days"), Long.valueOf(time.longValue() / 86400), Long.valueOf((time.longValue() / 3600) % 24), Long.valueOf((time.longValue() / 60) % 60), Long.valueOf(time.longValue() % 60)));
            return;
        }
        if (time.longValue() >= 3600) {
            setText(String.format(RomStation.m44d().getString("playedTableCell.format.hours"), Long.valueOf((time.longValue() / 3600) % 24), Long.valueOf((time.longValue() / 60) % 60), Long.valueOf(time.longValue() % 60)));
        } else if (time.longValue() >= 60) {
            setText(String.format(RomStation.m44d().getString("playedTableCell.format.minutes"), Long.valueOf((time.longValue() / 60) % 60), Long.valueOf(time.longValue() % 60)));
        } else {
            setText(String.format(RomStation.m44d().getString("playedTableCell.format.seconds"), Long.valueOf(time.longValue() % 60)));
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/PlayedTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Long>, PlayedTableCell> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public PlayedTableCell call(TableColumn<S, Long> param) {
            return new PlayedTableCell();
        }
    }
}
