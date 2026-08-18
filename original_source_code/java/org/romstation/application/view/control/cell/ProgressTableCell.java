package org.romstation.application.view.control.cell;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.romstation.application.AbstractC0199dk;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/ProgressTableCell.class */
public class ProgressTableCell extends TableCell<AbstractC0199dk, Double> {

    /* JADX INFO: renamed from: b */
    private final ProgressBar f742b = new ProgressBar();

    /* JADX INFO: renamed from: c */
    private final Label f743c = new Label();

    /* JADX INFO: renamed from: a */
    private final StackPane f741a = new StackPane(new Node[]{this.f742b, this.f743c});

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Double value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        this.f743c.setText(String.format("%.0f%%", Double.valueOf(value.doubleValue() * 100.0d)));
        this.f742b.setProgress(value.doubleValue());
        setGraphic(this.f741a);
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/ProgressTableCell$Factory.class */
    public static class Factory implements Callback<TableColumn<AbstractC0199dk, Double>, ProgressTableCell> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ProgressTableCell call(TableColumn<AbstractC0199dk, Double> param) {
            return new ProgressTableCell();
        }
    }
}
