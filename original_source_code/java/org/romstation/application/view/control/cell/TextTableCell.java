package org.romstation.application.view.control.cell;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/TextTableCell.class */
public class TextTableCell<S> extends TableCell<S, String> {

    /* JADX INFO: renamed from: a */
    private final Label f744a;

    /* JADX INFO: renamed from: b */
    private final Tooltip f745b;

    public TextTableCell() {
        getStyleClass().add("text");
        this.f744a = new Label();
        this.f745b = new Tooltip();
        this.f744a.setTooltip(this.f745b);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(String text, boolean empty) {
        super.updateItem(text, empty);
        if (empty || text == null) {
            setGraphic(null);
            return;
        }
        String string = text.replaceAll("\\r?\\n", "");
        this.f744a.setText(string);
        this.f745b.setText(text);
        setGraphic(this.f744a);
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/TextTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, String>, TextTableCell> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public TextTableCell call(TableColumn<S, String> param) {
            return new TextTableCell();
        }
    }
}
