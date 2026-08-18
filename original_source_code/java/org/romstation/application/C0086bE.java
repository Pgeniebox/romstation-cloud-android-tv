package org.romstation.application;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bE */
/* JADX INFO: compiled from: PlayerListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bE.class */
public class C0086bE extends ListCell<C0084bC> {

    /* JADX INFO: renamed from: a */
    private final C0085bD f179a;

    /* JADX INFO: renamed from: b */
    private final Map<C0084bC, Image> f180b;

    /* JADX INFO: renamed from: c */
    private final Map<String, Image> f181c;

    /* JADX INFO: renamed from: d */
    private final boolean f182d;

    /* JADX INFO: renamed from: e */
    private final GridPane f183e;

    /* JADX INFO: renamed from: g */
    private final Label f185g;

    /* JADX INFO: renamed from: h */
    private final HBox f186h;

    /* JADX INFO: renamed from: i */
    private final Label f187i;

    /* JADX INFO: renamed from: j */
    private final ImageView f188j;

    /* JADX INFO: renamed from: k */
    private final Label f189k;

    /* JADX INFO: renamed from: l */
    private final Tooltip f190l;

    /* JADX INFO: renamed from: m */
    private final ResourceBundle f191m = RomStation.m44d();

    /* JADX INFO: renamed from: f */
    private final ImageView f184f = new ImageView();

    public C0086bE(C0085bD contextMenuController, Map<C0084bC, Image> photoCache, Map<String, Image> flagCache, boolean showController) {
        this.f179a = contextMenuController;
        this.f180b = photoCache;
        this.f181c = flagCache;
        this.f182d = showController;
        this.f184f.getStyleClass().add("photo");
        this.f185g = new Label();
        this.f185g.getStyleClass().add("name");
        this.f186h = new HBox();
        this.f186h.getStyleClass().add("status-container");
        this.f188j = new ImageView();
        this.f190l = new Tooltip();
        Tooltip.install(this.f188j, this.f190l);
        this.f188j.getStyleClass().add("flag");
        this.f186h.getChildren().add(this.f188j);
        this.f187i = new Label(this.f191m.getString("serverLobbyDialog.playerListCell.host"), new FontAwesomeIconView());
        this.f187i.getStyleClass().add("host");
        this.f186h.getChildren().add(this.f187i);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        this.f186h.getChildren().add(region);
        this.f189k = new Label((String) null, new FontAwesomeIconView());
        this.f189k.setTooltip(new Tooltip());
        this.f189k.getStyleClass().add("controller");
        this.f186h.getChildren().add(this.f189k);
        this.f183e = new GridPane();
        this.f183e.getStyleClass().add("content");
        GridPane.setValignment(this.f184f, VPos.CENTER);
        this.f183e.getColumnConstraints().addAll(new ColumnConstraints[]{new ColumnConstraints(), new ColumnConstraints()});
        ((ColumnConstraints) this.f183e.getColumnConstraints().get(1)).setHgrow(Priority.ALWAYS);
        this.f183e.add(this.f184f, 0, 0, 1, Integer.MAX_VALUE);
        this.f183e.add(this.f185g, 1, 0);
        this.f183e.add(this.f186h, 1, 1);
        getStyleClass().add("player");
        setOnContextMenuRequested(contextMenuEvent -> {
            contextMenuController.m329a((C0084bC) getItem());
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(C0084bC player, boolean empty) {
        super.updateItem(player, empty);
        if (empty) {
            setGraphic(null);
            setContextMenu(null);
            return;
        }
        Image photoImage = this.f180b.computeIfAbsent(player, value -> {
            return new Image(value.m633f(), 40.0d, 40.0d, true, true, true);
        });
        this.f184f.setImage(photoImage);
        this.f185g.setText(player.m631d());
        this.f187i.setVisible(player.m320h());
        this.f187i.setManaged(player.m320h());
        Image flagImage = this.f181c.computeIfAbsent(player.m326l(), value2 -> {
            return new Image(value2, 16.0d, 16.0d, true, true, true);
        });
        this.f188j.setImage(flagImage);
        this.f190l.setText(player.m327m());
        this.f189k.setVisible(this.f182d && player.m322j() != null);
        this.f189k.setManaged(this.f182d && player.m322j() != null);
        this.f189k.setText(String.format(this.f191m.getString("serverLobbyDialog.playerListCell.controller"), Integer.valueOf(player.m324k())));
        this.f189k.getTooltip().setText(String.format(this.f191m.getString("serverLobbyDialog.playerListCell.controller.tooltip"), Integer.valueOf(player.m324k())));
        setGraphic(this.f183e);
        setContextMenu(this.f179a.m328a());
    }

    /* JADX INFO: renamed from: org.romstation.application.bE$a */
    /* JADX INFO: compiled from: PlayerListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bE$a.class */
    public static class a implements Callback<ListView<C0084bC>, ListCell<C0084bC>> {

        /* JADX INFO: renamed from: a */
        private final C0085bD f192a;

        /* JADX INFO: renamed from: b */
        private final Map<C0084bC, Image> f193b;

        /* JADX INFO: renamed from: c */
        private final Map<String, Image> f194c;

        /* JADX INFO: renamed from: d */
        private final boolean f195d;

        public a(C0085bD contextMenuController) {
            this(contextMenuController, false);
        }

        public a(C0085bD contextMenuController, boolean showController) {
            this.f193b = new HashMap();
            this.f194c = new HashMap();
            this.f192a = contextMenuController;
            this.f195d = showController;
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<C0084bC> call(ListView<C0084bC> param) {
            return new C0086bE(this.f192a, this.f193b, this.f194c, this.f195d);
        }
    }
}
