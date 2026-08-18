package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Tag;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/TagListCell.class */
public class TagListCell extends ListCell<Tag> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f761a = new C0061ah<>(32);

    public TagListCell() {
        getStyleClass().add("tag");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Tag tag, boolean empty) {
        super.updateItem(tag, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(tag.getName().getDefaultString());
        if (tag.getGraphic() != null) {
            Image image = (Image) f761a.computeIfAbsent(tag.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        } else {
            setGraphic(null);
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/TagListCell$Factory.class */
    public static class Factory implements Callback<ListView<Tag>, ListCell<Tag>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Tag> call(ListView<Tag> param) {
            return new TagListCell();
        }
    }
}
