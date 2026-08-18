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
import org.romstation.application.database.entity.System;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SystemsTableCell.class */
public class SystemsTableCell<S> extends TableCell<S, List<System>> {

    /* JADX INFO: renamed from: a */
    private static final C0061ah<URI, Image> f759a = new C0061ah<>(32);

    /* JADX INFO: renamed from: b */
    private HBox f760b = new HBox();

    public SystemsTableCell() {
        getStyleClass().add("systems");
        this.f760b.getStyleClass().add("container");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(List<System> systems, boolean empty) {
        super.updateItem(systems, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        this.f760b.getChildren().clear();
        systems.forEach(system -> {
            Node node = m1239a(system);
            if (node != null) {
                this.f760b.getChildren().add(node);
            }
        });
        setGraphic(this.f760b);
    }

    /* JADX INFO: renamed from: a */
    private Node m1239a(System system) {
        if (system.getGraphic() == null) {
            return null;
        }
        Image image = (Image) f759a.computeIfAbsent(system.getGraphic().getURI(), value -> {
            return new Image(value.toString(), true);
        });
        ImageView imageView = new ImageView(image);
        imageView.getStyleClass().add("system");
        Tooltip tooltip = new Tooltip(system.getName());
        Tooltip.install(imageView, tooltip);
        return imageView;
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/entity/SystemsTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, List<System>>, SystemsTableCell<S>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public SystemsTableCell<S> call(TableColumn<S, List<System>> param) {
            return new SystemsTableCell<>();
        }
    }
}
