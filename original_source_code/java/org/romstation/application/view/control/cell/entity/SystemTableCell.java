package org.romstation.application.view.control.cell.entity;

import java.net.URI;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.romstation.application.C0061ah;
import org.romstation.application.database.entity.System;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SystemTableCell.class */
public class SystemTableCell<S> extends TableCell<S, System> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f758a = new C0061ah<>(32);

    public SystemTableCell() {
        getStyleClass().add("system");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(System system, boolean empty) {
        super.updateItem(system, empty);
        if (system == null || empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(system.getName());
        if (system.getGraphic() == null) {
            setGraphic(null);
        } else {
            Image image = (Image) f758a.computeIfAbsent(system.getGraphic().getURI(), value -> {
                return new Image(value.toString(), true);
            });
            setGraphic(new ImageView(image));
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SystemTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, System>, SystemTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public SystemTableCell<S> call(TableColumn<S, System> param) {
            return new SystemTableCell<>();
        }
    }
}
