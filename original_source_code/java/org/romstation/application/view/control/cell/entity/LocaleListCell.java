package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Locale;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LocaleListCell.class */
public class LocaleListCell extends ListCell<Locale> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f755a = new C0061ah<>(32);

    public LocaleListCell() {
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
            Image image = (Image) f755a.computeIfAbsent(locale.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        } else {
            setGraphic(null);
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LocaleListCell$Factory.class */
    public static class Factory implements Callback<ListView<Locale>, ListCell<Locale>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Locale> call(ListView<Locale> param) {
            return new LocaleListCell();
        }
    }
}
