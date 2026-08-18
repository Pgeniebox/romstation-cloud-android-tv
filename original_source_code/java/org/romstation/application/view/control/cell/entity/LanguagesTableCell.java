package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Language;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LanguagesTableCell.class */
public class LanguagesTableCell<S> extends TableCell<S, List<Language>> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f753a = new C0061ah<>(32);

    /* JADX INFO: renamed from: b */
    private HBox f754b = new HBox();

    public LanguagesTableCell() {
        getStyleClass().add("languages");
        this.f754b.getStyleClass().add("container");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(List<Language> languages, boolean empty) {
        super.updateItem(languages, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        this.f754b.getChildren().clear();
        languages.forEach(language -> {
            Node node = m1216a(language);
            if (node != null) {
                this.f754b.getChildren().add(node);
            }
        });
        setGraphic(this.f754b);
    }

    /* JADX INFO: renamed from: a */
    private Node m1216a(Language language) {
        if (language.getGraphic() == null) {
            return null;
        }
        Image image = (Image) f753a.computeIfAbsent(language.getGraphic().getURI(), value -> {
            return new Image(value.toString(), true);
        });
        ImageView imageView = new ImageView(image);
        imageView.getStyleClass().add("language");
        Tooltip tooltip = new Tooltip(language.getName().getDefaultString());
        Tooltip.install(imageView, tooltip);
        return imageView;
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/LanguagesTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, List<Language>>, LanguagesTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public LanguagesTableCell<S> call(TableColumn<S, List<Language>> param) {
            return new LanguagesTableCell<>();
        }
    }
}
