package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Translation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/TranslationListCell.class */
public class TranslationListCell extends ListCell<Translation> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f764a = new C0061ah<>(32);

    public TranslationListCell() {
        getStyleClass().add("translation");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Translation translation, boolean empty) {
        super.updateItem(translation, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(translation.getString());
        if (translation.getLocale().getGraphic() != null) {
            Image image = (Image) f764a.computeIfAbsent(translation.getLocale().getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        } else {
            setGraphic(null);
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/TranslationListCell$Factory.class */
    public static class Factory implements Callback<ListView<Translation>, ListCell<Translation>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Translation> call(ListView<Translation> param) {
            return new TranslationListCell();
        }
    }
}
