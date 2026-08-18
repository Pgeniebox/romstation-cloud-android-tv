package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.Image;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/ImageTableCell.class */
public class ImageTableCell<S> extends TableCell<S, Image> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, javafx.scene.image.Image> f751a = new C0061ah<>(32);

    public ImageTableCell() {
        getStyleClass().add("image");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Image imageEntity, boolean empty) {
        super.updateItem(imageEntity, empty);
        if (empty || imageEntity == null) {
            setGraphic(null);
        } else {
            javafx.scene.image.Image image = (javafx.scene.image.Image) f751a.computeIfAbsent(imageEntity.getURI(), value -> {
                return new javafx.scene.image.Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/ImageTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Image>, ImageTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ImageTableCell<S> call(TableColumn<S, Image> param) {
            return new ImageTableCell<>();
        }
    }
}
