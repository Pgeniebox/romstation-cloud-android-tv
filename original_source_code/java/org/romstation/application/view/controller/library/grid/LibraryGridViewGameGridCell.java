package org.romstation.application.view.controller.library.grid;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.ResourceBundle;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.romstation.application.C0061ah;
import org.romstation.application.C0062ai;
import org.romstation.application.C0184cx;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Game;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.view.controller.library.GameContextMenu;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/grid/LibraryGridViewGameGridCell.class */
public class LibraryGridViewGameGridCell extends GridCell<Game> {

    /* JADX INFO: renamed from: a */
    private final C0061ah<Game, C0062ai> f830a;

    /* JADX INFO: renamed from: c */
    private final VBox f832c;

    /* JADX INFO: renamed from: d */
    private final Label f833d;

    /* JADX INFO: renamed from: f */
    private final StackPane f835f;

    /* JADX INFO: renamed from: g */
    private final ImageView f836g;

    /* JADX INFO: renamed from: h */
    private final ImageView f837h;

    /* JADX INFO: renamed from: i */
    private final HBox f838i;

    /* JADX INFO: renamed from: j */
    private final Button f839j;

    /* JADX INFO: renamed from: k */
    private final Button f840k;

    /* JADX INFO: renamed from: b */
    private final ResourceBundle f831b = RomStation.m44d();

    /* JADX INFO: renamed from: e */
    private final StackPane f834e = new StackPane();

    public LibraryGridViewGameGridCell(GameContextMenu contextMenuController, C0061ah<Game, C0062ai> cache) {
        this.f830a = cache;
        this.f834e.getStyleClass().add("content-container");
        VBox.setVgrow(this.f834e, Priority.ALWAYS);
        this.f835f = new StackPane();
        this.f835f.setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        this.f835f.getStyleClass().add("images-container");
        this.f834e.getChildren().add(this.f835f);
        this.f836g = new ImageView();
        this.f836g.fitWidthProperty().bind(this.f834e.widthProperty());
        this.f836g.fitHeightProperty().bind(this.f834e.heightProperty());
        this.f836g.setPreserveRatio(true);
        this.f836g.getStyleClass().add("default");
        this.f835f.getChildren().add(this.f836g);
        this.f837h = new ImageView();
        this.f837h.fitWidthProperty().bind(this.f834e.widthProperty());
        this.f837h.fitHeightProperty().bind(this.f834e.heightProperty());
        this.f837h.setPreserveRatio(true);
        this.f837h.getStyleClass().add("cover");
        this.f835f.getChildren().add(this.f837h);
        this.f839j = new Button(this.f831b.getString("game.gridCell.launch"), new FontAwesomeIconView());
        this.f839j.setOnAction(event -> {
            RomStationController.f786a.post(new C0184cx((Game) getItem(), new String[0]));
        });
        this.f839j.getStyleClass().add("launch");
        this.f840k = new Button((String) null, new FontAwesomeIconView());
        this.f840k.setOnAction(event2 -> {
            contextMenuController.m1507a().setAll(new Game[]{(Game) getItem()});
            contextMenuController.m1508b().show(this.f840k, Side.RIGHT, 0.0d, 0.0d);
        });
        this.f840k.getStyleClass().add("options");
        this.f838i = new HBox(new Node[]{this.f839j, this.f840k});
        this.f838i.getStyleClass().add("controls-container");
        this.f835f.getChildren().add(this.f838i);
        this.f833d = new Label();
        this.f833d.getStyleClass().add("title");
        this.f832c = new VBox(new Node[]{this.f834e, this.f833d});
        this.f832c.getStyleClass().add("root-container");
        this.f832c.setOnMouseClicked(event3 -> {
            if (event3.getButton().equals(MouseButton.PRIMARY) && event3.getClickCount() == 2) {
                RomStationController.f786a.post(new C0184cx((Game) getItem(), new String[0]));
            }
        });
        getStyleClass().add("game");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Game game, boolean empty) {
        super.updateItem(game, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
            return;
        }
        C0062ai cachedImage = null;
        if (game.getGraphic() != null) {
            cachedImage = this.f830a.get(game);
            if (cachedImage == null || !cachedImage.m239b().equals(game.getGraphic().getPath())) {
                cachedImage = new C0062ai(new Image(game.getGraphic().getURI().toString()), game.getGraphic().getPath());
                this.f830a.put(game, cachedImage);
            }
        }
        if (cachedImage != null && !cachedImage.m238a().isError()) {
            this.f837h.setImage(cachedImage.m238a());
            this.f836g.setVisible(false);
            this.f836g.setManaged(false);
            this.f837h.setVisible(true);
            this.f837h.setManaged(true);
        } else {
            this.f836g.setVisible(true);
            this.f836g.setManaged(true);
            this.f837h.setVisible(false);
            this.f837h.setManaged(false);
        }
        this.f833d.setText(game.getTitle());
        setGraphic(this.f832c);
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/grid/LibraryGridViewGameGridCell$Factory.class */
    public static class Factory implements Callback<GridView<Game>, GridCell<Game>> {

        /* JADX INFO: renamed from: a */
        private final GameContextMenu f841a;

        /* JADX INFO: renamed from: b */
        private final C0061ah<Game, C0062ai> f842b = new C0061ah<>(100);

        public Factory(GameContextMenu contextMenuController) {
            this.f841a = contextMenuController;
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public GridCell<Game> call(GridView<Game> param) {
            return new LibraryGridViewGameGridCell(this.f841a, this.f842b);
        }
    }
}
