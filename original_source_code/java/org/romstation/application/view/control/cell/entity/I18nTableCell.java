package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Translation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/I18nTableCell.class */
public class I18nTableCell<S> extends TableCell<S, I18n> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f749a = new C0061ah<>(32);

    /* JADX INFO: renamed from: b */
    private HBox f750b = new HBox();

    public I18nTableCell() {
        getStyleClass().add("i18n");
        this.f750b.getStyleClass().add("container");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(I18n i18n, boolean empty) {
        super.updateItem(i18n, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        this.f750b.getChildren().clear();
        i18n.getTranslations().forEach(translation -> {
            this.f750b.getChildren().add(m1205a(translation));
        });
        setGraphic(this.f750b);
    }

    /* JADX INFO: renamed from: a */
    private Node m1205a(Translation translation) {
        Label label = new Label(translation.getString());
        if (translation.getLocale().getGraphic() != null) {
            Image image = (Image) f749a.computeIfAbsent(translation.getLocale().getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            ImageView imageView = new ImageView(image);
            imageView.getStyleClass().add("locale");
            label.setGraphic(imageView);
        }
        return label;
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/I18nTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, I18n>, I18nTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public I18nTableCell<S> call(TableColumn<S, I18n> param) {
            return new I18nTableCell<>();
        }
    }
}
