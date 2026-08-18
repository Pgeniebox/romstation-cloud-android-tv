package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Genre;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/GenresTableCell.class */
public class GenresTableCell<S> extends TableCell<S, List<Genre>> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f747a = new C0061ah<>(32);

    /* JADX INFO: renamed from: b */
    private HBox f748b = new HBox();

    public GenresTableCell() {
        getStyleClass().add("genres");
        this.f748b.getStyleClass().add("container");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(List<Genre> genres, boolean empty) {
        super.updateItem(genres, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        this.f748b.getChildren().clear();
        genres.forEach(genre -> {
            this.f748b.getChildren().add(m1200a(genre));
        });
        setGraphic(this.f748b);
    }

    /* JADX INFO: renamed from: a */
    private Node m1200a(Genre genre) {
        ImageView imageView = null;
        if (genre.getGraphic() != null) {
            Image image = (Image) f747a.computeIfAbsent(genre.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            imageView = new ImageView(image);
        }
        Label label = new Label(genre.getName().getDefaultString(), imageView);
        label.getStyleClass().add("genre");
        return label;
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/GenresTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, List<Genre>>, GenresTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public GenresTableCell<S> call(TableColumn<S, List<Genre>> param) {
            return new GenresTableCell<>();
        }
    }
}
