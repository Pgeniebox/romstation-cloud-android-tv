package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Genre;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/GenreListCell.class */
public class GenreListCell extends ListCell<Genre> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f746a = new C0061ah<>(32);

    public GenreListCell() {
        getStyleClass().add("genre");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Genre genre, boolean empty) {
        super.updateItem(genre, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(genre.getName().getDefaultString());
        if (genre.getGraphic() != null) {
            Image image = (Image) f746a.computeIfAbsent(genre.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        } else {
            setGraphic(null);
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/GenreListCell$Factory.class */
    public static class Factory implements Callback<ListView<Genre>, ListCell<Genre>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Genre> call(ListView<Genre> param) {
            return new GenreListCell();
        }
    }
}
