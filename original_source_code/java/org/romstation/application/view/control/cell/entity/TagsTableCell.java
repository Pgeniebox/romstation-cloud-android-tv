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
import org.romstation.application.database.entity.Tag;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/TagsTableCell.class */
public class TagsTableCell<S> extends TableCell<S, List<Tag>> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f762a = new C0061ah<>(32);

    /* JADX INFO: renamed from: b */
    private HBox f763b = new HBox();

    public TagsTableCell() {
        getStyleClass().add("tags");
        this.f763b.getStyleClass().add("container");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(List<Tag> tags, boolean empty) {
        super.updateItem(tags, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        this.f763b.getChildren().clear();
        tags.forEach(tag -> {
            this.f763b.getChildren().add(m1247a(tag));
        });
        setGraphic(this.f763b);
    }

    /* JADX INFO: renamed from: a */
    private Node m1247a(Tag tag) {
        ImageView imageView = null;
        if (tag.getGraphic() != null) {
            Image image = (Image) f762a.computeIfAbsent(tag.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            imageView = new ImageView(image);
        }
        Label label = new Label(tag.getName().getDefaultString(), imageView);
        label.getStyleClass().add("tag");
        return label;
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/TagsTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, List<Tag>>, TagsTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public TagsTableCell<S> call(TableColumn<S, List<Tag>> param) {
            return new TagsTableCell<>();
        }
    }
}
