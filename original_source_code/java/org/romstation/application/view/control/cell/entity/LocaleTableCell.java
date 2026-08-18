package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Locale;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LocaleTableCell.class */
public class LocaleTableCell<S> extends TableCell<S, Locale> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f756a = new C0061ah<>(32);

    public LocaleTableCell() {
        getStyleClass().add("locale");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Locale locale, boolean empty) {
        super.updateItem(locale, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(locale.getName().getDefaultString());
        if (locale.getGraphic() != null) {
            Image image = (Image) f756a.computeIfAbsent(locale.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LocaleTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Locale>, LocaleTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public LocaleTableCell<S> call(TableColumn<S, Locale> param) {
            return new LocaleTableCell<>();
        }
    }
}
