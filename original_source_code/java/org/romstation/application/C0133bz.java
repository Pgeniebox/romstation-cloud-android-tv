package org.romstation.application;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.VPos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.romstation.application.view.controller.ApplicationView;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: renamed from: org.romstation.application.bz */
/* JADX INFO: compiled from: MessageListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bz.class */
public class C0133bz extends ListCell<AbstractC0132by> implements InterfaceC0083bB {

    /* JADX INFO: renamed from: a */
    private final Map<C0084bC, Image> f315a;

    /* JADX INFO: renamed from: b */
    private final GridPane f316b;

    /* JADX INFO: renamed from: c */
    private final ImageView f317c = new ImageView();

    /* JADX INFO: renamed from: d */
    private final Label f318d = new Label();

    /* JADX INFO: renamed from: e */
    private final Label f319e = new Label();

    /* JADX INFO: renamed from: f */
    private final Tooltip f320f = new Tooltip();

    /* JADX INFO: renamed from: g */
    private final TextFlow f321g;

    public C0133bz(Map<C0084bC, Image> cache) {
        this.f315a = cache;
        this.f319e.setTooltip(this.f320f);
        this.f321g = new TextFlow();
        this.f316b = new GridPane();
        GridPane.setValignment(this.f317c, VPos.TOP);
        GridPane.setHgrow(this.f319e, Priority.ALWAYS);
        this.f316b.add(this.f317c, 0, 0, 1, Integer.MAX_VALUE);
        this.f316b.add(this.f318d, 1, 0);
        this.f316b.add(this.f319e, 2, 0);
        this.f316b.add(this.f321g, 1, 1, Integer.MAX_VALUE, 1);
        getStyleClass().add("message");
        this.f316b.getStyleClass().add("player-content");
        this.f317c.getStyleClass().add("photo");
        this.f318d.getStyleClass().add("name");
        this.f319e.getStyleClass().add("timestamp");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(AbstractC0132by message, boolean empty) {
        super.updateItem(message, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        long timestamp = Instant.ofEpochSecond(message.m634a()).toEpochMilli();
        if (message instanceof C0087bF) {
            C0087bF playerMessage = (C0087bF) message;
            Image image = this.f315a.computeIfAbsent(playerMessage.m336c(), player -> {
                return new Image(playerMessage.m336c().m633f(), 32.0d, 32.0d, true, true, true);
            });
            this.f317c.setImage(image);
            this.f318d.setText(playerMessage.m336c().m631d());
            this.f319e.setText(String.format("%tT", Long.valueOf(timestamp)));
            this.f320f.setText(String.format("%tc", Long.valueOf(timestamp)));
            this.f321g.getChildren().setAll(C0082bA.m311a(message.m635b(), this));
            setGraphic(this.f316b);
            return;
        }
        TextFlow graphic = new TextFlow();
        graphic.getStyleClass().add(message instanceof C0089bH ? "system" : "motd");
        graphic.getChildren().setAll(C0082bA.m311a(String.format("%tT: %s", Long.valueOf(timestamp), message.m635b()), this));
        setGraphic(graphic);
    }

    @Override // org.romstation.application.InterfaceC0083bB
    /* JADX INFO: renamed from: a */
    public void mo318a(Hyperlink hyperlink) {
        RomStationController.f786a.post(new C0152cR(hyperlink.getText(), true));
        RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
    }

    /* JADX INFO: renamed from: org.romstation.application.bz$a */
    /* JADX INFO: compiled from: MessageListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bz$a.class */
    public static class a implements Callback<ListView<AbstractC0132by>, ListCell<AbstractC0132by>> {

        /* JADX INFO: renamed from: a */
        private final Map<C0084bC, Image> f322a = new HashMap();

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<AbstractC0132by> call(ListView<AbstractC0132by> param) {
            return new C0133bz(this.f322a);
        }
    }
}
