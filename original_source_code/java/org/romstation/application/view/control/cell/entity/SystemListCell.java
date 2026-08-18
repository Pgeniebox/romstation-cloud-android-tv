package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.System;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SystemListCell.class */
public class SystemListCell extends ListCell<System> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f757a = new C0061ah<>(32);

    public SystemListCell() {
        getStyleClass().add("system");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(System system, boolean empty) {
        super.updateItem(system, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(system.getName());
        if (system.getGraphic() != null) {
            Image image = (Image) f757a.computeIfAbsent(system.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        } else {
            setGraphic(null);
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SystemListCell$Factory.class */
    public static class Factory implements Callback<ListView<System>, ListCell<System>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<System> call(ListView<System> param) {
            return new SystemListCell();
        }
    }
}
