package org.romstation.application;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bu */
/* JADX INFO: compiled from: BannedMemberListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bu.class */
public class C0128bu extends ListCell<C0131bx> {

    /* JADX INFO: renamed from: a */
    private final C0127bt f301a;

    /* JADX INFO: renamed from: b */
    private final Map<C0131bx, Image> f302b;

    /* JADX INFO: renamed from: d */
    private final ImageView f304d = new ImageView();

    /* JADX INFO: renamed from: e */
    private final Label f305e = new Label();

    /* JADX INFO: renamed from: c */
    private final GridPane f303c = new GridPane();

    public C0128bu(C0127bt contextMenuController, Map<C0131bx, Image> cache) {
        this.f301a = contextMenuController;
        this.f302b = cache;
        this.f303c.add(this.f304d, 0, 0);
        this.f303c.add(this.f305e, 1, 0);
        getStyleClass().add("banned-member");
        this.f303c.getStyleClass().add("content");
        this.f304d.getStyleClass().add("photo");
        this.f305e.getStyleClass().add("name");
        setOnContextMenuRequested(contextMenuEvent -> {
            contextMenuController.m621a((C0131bx) getItem());
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(C0131bx member, boolean empty) {
        super.updateItem(member, empty);
        if (empty) {
            setGraphic(null);
            setContextMenu(null);
            return;
        }
        this.f305e.setText(member.m631d());
        Image image = this.f302b.computeIfAbsent(member, value -> {
            return new Image(value.m633f(), 32.0d, 32.0d, true, true, true);
        });
        this.f304d.setImage(image);
        setGraphic(this.f303c);
        setContextMenu(this.f301a.m620a());
    }

    /* JADX INFO: renamed from: org.romstation.application.bu$a */
    /* JADX INFO: compiled from: BannedMemberListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bu$a.class */
    public static class a implements Callback<ListView<C0131bx>, ListCell<C0131bx>> {

        /* JADX INFO: renamed from: a */
        private final C0127bt f306a;

        /* JADX INFO: renamed from: b */
        private final Map<C0131bx, Image> f307b = new HashMap();

        public a(C0127bt contextMenuController) {
            this.f306a = contextMenuController;
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<C0131bx> call(ListView<C0131bx> param) {
            return new C0128bu(this.f306a, this.f307b);
        }
    }
}
