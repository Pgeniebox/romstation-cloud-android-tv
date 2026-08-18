package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Language;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LanguageListCell.class */
public class LanguageListCell extends ListCell<Language> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f752a = new C0061ah<>(32);

    public LanguageListCell() {
        getStyleClass().add("language");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Language language, boolean empty) {
        super.updateItem(language, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(language.getName().getDefaultString());
        if (language.getGraphic() != null) {
            Image image = (Image) f752a.computeIfAbsent(language.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        } else {
            setGraphic(null);
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LanguageListCell$Factory.class */
    public static class Factory implements Callback<ListView<Language>, ListCell<Language>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Language> call(ListView<Language> param) {
            return new LanguageListCell();
        }
    }
}
