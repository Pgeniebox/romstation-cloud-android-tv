package org.romstation.application;

import com.google.common.eventbus.EventBus;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/* JADX INFO: renamed from: org.romstation.application.bD */
/* JADX INFO: compiled from: PlayerContextMenuController.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bD.class */
public class C0085bD {

    /* JADX INFO: renamed from: a */
    private final EventBus f173a;

    /* JADX INFO: renamed from: b */
    private final EnumC0129bv f174b;

    /* JADX INFO: renamed from: c */
    private final boolean f175c;

    /* JADX INFO: renamed from: d */
    private final int f176d;

    /* JADX INFO: renamed from: e */
    private final boolean f177e;

    /* JADX INFO: renamed from: f */
    private C0084bC f178f;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private MenuItem kickMenuItem;

    @FXML
    private MenuItem banMenuItem;

    @FXML
    private Menu controllerMenu;

    @FXML
    private MenuItem clearPlayerControllerMenuItem;

    @FXML
    private ResourceBundle resources;

    public C0085bD(EventBus eventBus, EnumC0129bv lobbyType, boolean host) {
        this(eventBus, lobbyType, host, 0, false);
    }

    public C0085bD(EventBus eventBus, EnumC0129bv lobbyType, boolean host, int maxControllers, boolean showControllerMenu) {
        this.f173a = eventBus;
        this.f174b = lobbyType;
        this.f175c = host;
        this.f176d = maxControllers;
        this.f177e = showControllerMenu;
    }

    /* JADX INFO: renamed from: a */
    public ContextMenu m328a() {
        return this.contextMenu;
    }

    @FXML
    private void initialize() {
        this.kickMenuItem.setVisible(this.f175c);
        this.banMenuItem.setVisible(this.f175c);
        if (this.f174b == EnumC0129bv.CLOUD && this.f175c && this.f177e) {
            this.controllerMenu.setVisible(true);
            for (int i = 0; i < this.f176d; i++) {
                int port = i + 1;
                MenuItem menuItem = new MenuItem(String.format(this.resources.getString("serverLobbyDialog.playersTitledPane.contextMenu.controller.port"), Integer.valueOf(port)));
                menuItem.setOnAction(actionEvent -> {
                    this.f173a.post(new C0149cO(this.f178f, port));
                });
                menuItem.setUserData(Integer.valueOf(port));
                this.controllerMenu.getItems().add(i, menuItem);
            }
            return;
        }
        this.controllerMenu.setVisible(false);
    }

    /* JADX INFO: renamed from: a */
    public void m329a(C0084bC player) {
        this.f178f = player;
        if (player != null) {
            this.profileMenuItem.setDisable(player.m628a());
            this.kickMenuItem.setDisable(player.m320h());
            this.banMenuItem.setDisable(player.m320h());
            for (MenuItem controllerMenuItem : this.controllerMenu.getItems()) {
                if (controllerMenuItem.getUserData() != null) {
                    int port = ((Integer) controllerMenuItem.getUserData()).intValue();
                    controllerMenuItem.setDisable(player.m324k() == port);
                }
            }
            this.clearPlayerControllerMenuItem.setDisable(player.m324k() == 0);
        }
    }

    @FXML
    private void banPlayer(ActionEvent event) {
        this.f173a.post(new C0140cF(this.f178f));
    }

    @FXML
    private void kickPlayer(ActionEvent event) {
        this.f173a.post(new C0142cH(this.f178f));
    }

    @FXML
    private void showPlayerProfile(ActionEvent event) {
        this.f173a.post(new C0146cL(this.f178f));
    }

    @FXML
    private void clearPlayerController() {
        this.f173a.post(new C0141cG(this.f178f));
    }
}
