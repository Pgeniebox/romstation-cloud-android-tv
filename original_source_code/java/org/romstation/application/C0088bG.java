package org.romstation.application;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.StreamSupport;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.netplay.C0213a;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.task.C0235c;
import org.romstation.application.task.C0258z;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.ServerErrorAlert;
import org.romstation.application.view.controller.ApplicationView;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.view.controller.library.LibraryController;
import org.romstation.application.vpn.EnumC0278d;
import org.romstation.application.vpn.InterfaceC0276b;

/* JADX INFO: renamed from: org.romstation.application.bG */
/* JADX INFO: compiled from: ServerLobbyDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bG.class */
public class C0088bG extends ApplicationFXMLDialog<Void> implements InterfaceC0276b {

    /* JADX INFO: renamed from: a */
    private static final int f197a = 30;

    /* JADX INFO: renamed from: b */
    private static final int f198b = 250;

    /* JADX INFO: renamed from: c */
    private Future<Session> f199c;

    /* JADX INFO: renamed from: d */
    private C0201dm f200d;

    /* JADX INFO: renamed from: e */
    private C0158cX f201e;

    /* JADX INFO: renamed from: f */
    private final C0213a f202f;

    /* JADX INFO: renamed from: g */
    private C0084bC f203g;

    /* JADX INFO: renamed from: h */
    private final JsonObject f204h;

    /* JADX INFO: renamed from: i */
    private final EventBus f205i;

    /* JADX INFO: renamed from: j */
    private final int f206j;

    /* JADX INFO: renamed from: k */
    private final int f207k;

    /* JADX INFO: renamed from: l */
    private final boolean f208l;

    /* JADX INFO: renamed from: m */
    private final EnumC0129bv f209m;

    /* JADX INFO: renamed from: n */
    private ScheduledExecutorService f210n;

    /* JADX INFO: renamed from: o */
    private final BooleanProperty f211o = new SimpleBooleanProperty();

    /* JADX INFO: renamed from: p */
    private final BooleanProperty f212p = new SimpleBooleanProperty();

    /* JADX INFO: renamed from: q */
    private final BooleanProperty f213q = new SimpleBooleanProperty();

    /* JADX INFO: renamed from: r */
    private final BooleanProperty f214r = new SimpleBooleanProperty();

    /* JADX INFO: renamed from: s */
    private final BooleanProperty f215s = new SimpleBooleanProperty();

    /* JADX INFO: renamed from: t */
    private final boolean f216t;

    /* JADX INFO: renamed from: u */
    private final String f217u;

    /* JADX INFO: renamed from: v */
    private final int f218v;

    /* JADX INFO: renamed from: w */
    private int f219w;

    /* JADX INFO: renamed from: x */
    private long f220x;

    /* JADX INFO: renamed from: y */
    private C0085bD f221y;

    /* JADX INFO: renamed from: z */
    private C0127bt f222z;

    /* JADX INFO: renamed from: A */
    private long f223A;

    /* JADX INFO: renamed from: B */
    private AudioClip f224B;

    /* JADX INFO: renamed from: C */
    private AudioClip f225C;

    /* JADX INFO: renamed from: D */
    private AudioClip f226D;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ImageView gameCoverImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label privateStatusLabel;

    @FXML
    private Label lockedStatusLabel;

    @FXML
    private Label liveStatusLabel;

    @FXML
    private Label serverStoppedStatusLabel;

    @FXML
    private Menu lobbyOptionsMenu;

    @FXML
    private Menu dedicatedServerOptionsMenu;

    @FXML
    private CheckMenuItem liveMenuItem;

    @FXML
    private CheckMenuItem lockMenuItem;

    @FXML
    private MenuItem clearPasswordMenuItem;

    @FXML
    private CheckMenuItem soundNotificationsMenuItem;

    @FXML
    private MenuItem startDedicatedServerMenuItem;

    @FXML
    private MenuItem restartDedicatedServerMenuItem;

    @FXML
    private MenuItem stopDedicatedServerMenuItem;

    @FXML
    private ButtonBar launchButtonBar;

    @FXML
    private Button openCloudClientButton;

    @FXML
    private HBox hostIPContainer;

    @FXML
    private TextField hostIPTextField;

    @FXML
    private HBox dedicatedServerAddressContainer;

    @FXML
    private TextField dedicatedServerAddressTextField;

    @FXML
    private Accordion membersAccordion;

    @FXML
    private TitledPane playersTitledPane;

    @FXML
    private Button banPlayerButton;

    @FXML
    private Button kickPlayerButton;

    @FXML
    private Button playerProfileButton;

    @FXML
    private MenuButton controllerMenuButton;

    @FXML
    private MenuItem clearPlayerControllerMenuItem;

    @FXML
    private TitledPane blacklistTitledPane;

    @FXML
    private Button unbanPlayerButton;

    @FXML
    private Button bannedPlayerProfileButton;

    @FXML
    private ListView<C0084bC> playersListView;

    @FXML
    private ListView<C0131bx> blacklistListView;

    @FXML
    private ListView<AbstractC0132by> messagesListView;

    @FXML
    private TextField messageTextField;

    @FXML
    private ResourceBundle resources;

    public C0088bG(C0213a credential, JsonObject lobbyObject) {
        this.f202f = credential;
        this.f204h = lobbyObject;
        this.f206j = lobbyObject.get("id").getAsInt();
        this.f207k = lobbyObject.get("linked_id").getAsInt();
        this.f209m = EnumC0129bv.m627a(lobbyObject.get("type").getAsInt());
        this.f216t = lobbyObject.get("is_host").getAsBoolean();
        this.f218v = lobbyObject.get("slots").getAsInt();
        this.f217u = lobbyObject.get("host_ip").getAsString();
        this.f211o.setValue(Boolean.valueOf(lobbyObject.get("locked").getAsBoolean()));
        this.f212p.setValue(Boolean.valueOf(lobbyObject.get("password").getAsBoolean()));
        this.f208l = lobbyObject.get("linked").getAsBoolean();
        this.f220x = lobbyObject.get("last_update").getAsLong();
        this.f205i = new EventBus();
        switch (this.f209m) {
            case DEDICATED:
                this.f214r.setValue(Boolean.valueOf(lobbyObject.get("dedicated_server").getAsJsonObject().get("running").getAsBoolean()));
                break;
            case CLOUD:
                this.f213q.setValue(Boolean.valueOf(lobbyObject.get("live").getAsBoolean()));
                this.f219w = lobbyObject.getAsJsonObject("cloud").getAsJsonObject("controller").get("ports").getAsInt();
                break;
        }
        load(getClass().getResource("/fxml/dialog/netplay/lobby/serverLobbyDialog.fxml"));
    }

    /* JADX INFO: renamed from: a */
    public String m337a() {
        return this.f217u;
    }

    @FXML
    private void initialize() {
        this.f205i.register(this);
        if (this.f204h.get("game").isJsonNull()) {
            this.gameCoverImageView.setVisible(false);
            this.gameCoverImageView.setManaged(false);
        } else {
            this.gameCoverImageView.setImage(new Image(C0217b.m961b() + this.f204h.getAsJsonObject("game").get("cover_url").getAsString(), true));
        }
        this.titleLabel.setText(this.f204h.get("title").getAsString());
        if (!this.f204h.get("description").isJsonNull()) {
            this.descriptionLabel.setText(this.f204h.get("description").getAsString());
        }
        if (this.f209m == EnumC0129bv.MANUAL) {
            this.hostIPTextField.setText(this.f217u);
        } else {
            this.hostIPContainer.setVisible(false);
            this.hostIPContainer.setManaged(false);
        }
        if (this.f209m == EnumC0129bv.DEDICATED) {
            this.dedicatedServerAddressTextField.setText(this.f204h.get("dedicated_server").getAsJsonObject().get("address").getAsString());
        } else {
            this.dedicatedServerAddressContainer.setVisible(false);
            this.dedicatedServerAddressContainer.setManaged(false);
        }
        if (this.f209m != EnumC0129bv.MANUAL) {
            m383n();
        }
        this.soundNotificationsMenuItem.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("lobby.soundNotifications")));
        this.f226D = new AudioClip(Paths.get("sounds", "notification.wav").toUri().toString());
        this.f224B = new AudioClip(Paths.get("sounds", "doorbell.wav").toUri().toString());
        this.f225C = new AudioClip(Paths.get("sounds", "door_closing.wav").toUri().toString());
        this.privateStatusLabel.visibleProperty().bind(this.f212p);
        this.privateStatusLabel.managedProperty().bind(this.f212p);
        this.lockMenuItem.setSelected(this.f211o.getValue().booleanValue());
        this.lockedStatusLabel.visibleProperty().bind(this.f211o);
        this.lockedStatusLabel.managedProperty().bind(this.f211o);
        this.liveMenuItem.setSelected(this.f213q.getValue().booleanValue());
        this.liveMenuItem.setVisible(this.f209m == EnumC0129bv.CLOUD);
        this.liveStatusLabel.visibleProperty().bind(this.f213q);
        this.liveStatusLabel.managedProperty().bind(this.f213q);
        if (this.f209m == EnumC0129bv.DEDICATED) {
            this.serverStoppedStatusLabel.visibleProperty().bind(this.f214r.not());
            this.serverStoppedStatusLabel.managedProperty().bind(this.f214r.not());
        } else {
            this.serverStoppedStatusLabel.setVisible(false);
            this.serverStoppedStatusLabel.setManaged(false);
        }
        this.clearPasswordMenuItem.disableProperty().bind(this.f212p.not());
        this.startDedicatedServerMenuItem.disableProperty().bind(this.f214r);
        this.restartDedicatedServerMenuItem.disableProperty().bind(this.f214r.not().or(this.f215s));
        this.stopDedicatedServerMenuItem.disableProperty().bind(this.f214r.not().or(this.f215s));
        this.f210n = Executors.newSingleThreadScheduledExecutor();
        this.f210n.scheduleWithFixedDelay(() -> {
            m344a(true);
        }, 30L, 30L, TimeUnit.SECONDS);
        this.playersListView.getItems().addListener(change -> {
            this.playersTitledPane.setText(String.format(this.resources.getString("serverLobbyDialog.playersTitledPane.title"), Integer.valueOf(change.getList().size()), Integer.valueOf(this.f218v)));
        });
        this.playersListView.getSelectionModel().selectedItemProperty().addListener((observableValue, previousValue, currentValue) -> {
            this.playerProfileButton.setDisable(currentValue == null || currentValue.m628a());
            this.banPlayerButton.setDisable(currentValue == null || currentValue.m320h());
            this.kickPlayerButton.setDisable(currentValue == null || currentValue.m320h());
            this.controllerMenuButton.setDisable(currentValue == null);
            if (currentValue != null) {
                m340a(currentValue);
            }
        });
        m338b();
        this.playersListView.setCellFactory(new C0086bE.a(this.f221y, !this.f208l));
        m342a(this.f204h.getAsJsonArray("members"));
        if (this.f209m == EnumC0129bv.CLOUD && this.f216t && !this.f208l) {
            this.controllerMenuButton.setVisible(true);
            for (int i = 0; i < this.f219w; i++) {
                int port = i + 1;
                MenuItem menuItem = new MenuItem(String.format(this.resources.getString("serverLobbyDialog.playersTitledPane.toolbar.controller.port"), Integer.valueOf(port)));
                menuItem.setOnAction(actionEvent -> {
                    m379d(port);
                });
                menuItem.setUserData(Integer.valueOf(port));
                this.controllerMenuButton.getItems().add(i, menuItem);
            }
        } else {
            this.controllerMenuButton.setVisible(false);
        }
        this.blacklistTitledPane.setText(String.format(this.resources.getString("serverLobbyDialog.blacklistTitledPane.title"), 0));
        this.blacklistListView.getItems().addListener(change2 -> {
            this.blacklistTitledPane.setText(String.format(this.resources.getString("serverLobbyDialog.blacklistTitledPane.title"), Integer.valueOf(change2.getList().size())));
        });
        this.unbanPlayerButton.disableProperty().bind(Bindings.size(this.blacklistListView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.blacklistListView.getSelectionModel().selectedItemProperty().addListener((observableValue2, previousValue2, currentValue2) -> {
            this.bannedPlayerProfileButton.setDisable(currentValue2 == null || currentValue2.m628a());
        });
        m341d();
        this.blacklistListView.setCellFactory(new C0128bu.a(this.f222z));
        this.messagesListView.setCellFactory(new C0133bz.a());
        this.messagesListView.getSelectionModel().selectedItemProperty().addListener((observableValue3, previous, current) -> {
            if (current instanceof C0087bF) {
                this.playersListView.getSelectionModel().select(((C0087bF) current).m336c());
            }
        });
        this.messagesListView.getItems().addListener(change3 -> {
            while (change3.next()) {
                if (change3.wasAdded()) {
                    for (AbstractC0132by message : change3.getAddedSubList()) {
                        this.f205i.post(new C0143cI(message));
                    }
                }
            }
        });
        for (JsonElement element : this.f204h.getAsJsonArray("motd")) {
            this.messagesListView.getItems().add(new C0130bw(element.getAsString()));
        }
        if (this.f216t) {
            m343b(this.f204h.getAsJsonArray("banned_members"));
            if (this.f209m != EnumC0129bv.DEDICATED) {
                this.dedicatedServerOptionsMenu.setVisible(false);
            }
        } else {
            this.lobbyOptionsMenu.setVisible(false);
            this.dedicatedServerOptionsMenu.setVisible(false);
            this.banPlayerButton.setVisible(false);
            this.kickPlayerButton.setVisible(false);
            this.playersTitledPane.setCollapsible(false);
            this.membersAccordion.getPanes().remove(this.blacklistTitledPane);
        }
        if (this.f202f.m848b() != null) {
            this.f202f.m848b().m1646a(this);
        }
        if (this.f209m == EnumC0129bv.CLOUD) {
            this.openCloudClientButton.setVisible(true);
            this.openCloudClientButton.setManaged(true);
        }
        try {
            this.f200d = new C0201dm();
            ClientManager clientManager = ClientManager.createClient();
            clientManager.getProperties().put("org.glassfish.tyrus.client.ClientManager.ReconnectHandler", this.f200d);
            this.f199c = clientManager.asyncConnectToServer(new C0200dl(this.f205i), new URI(this.f204h.get("ws_server").getAsString()));
        } catch (DeploymentException | URISyntaxException e) {
            RomStation.m42b().log(Level.SEVERE, e.getMessage(), (Throwable) e);
        }
        this.dialogPane.lookupButton(ButtonType.CLOSE).setText(this.resources.getString("serverLobbyDialog.disconnect"));
        setOnCloseRequest(dialogEvent -> {
            RomStation.m43c().setProperty("lobby.soundNotifications", String.valueOf(this.soundNotificationsMenuItem.isSelected()));
            m364k();
        });
        setResizable(true);
    }

    /* JADX INFO: renamed from: b */
    private void m338b() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(LibraryController.class.getResource("/fxml/dialog/netplay/lobby/playerContextMenu.fxml"));
            fxmlLoader.setControllerFactory(type -> {
                return new C0085bD(this.f205i, this.f209m, this.f216t, this.f219w, !this.f208l);
            });
            fxmlLoader.setResources(this.resources);
            fxmlLoader.load();
            this.f221y = (C0085bD) fxmlLoader.getController();
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: c */
    private void m339c() {
        C0084bC player = (C0084bC) this.playersListView.getSelectionModel().getSelectedItem();
        if (player != null) {
            m340a(player);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m340a(C0084bC player) {
        for (MenuItem controllerMenuItem : this.controllerMenuButton.getItems()) {
            if (controllerMenuItem.getUserData() != null) {
                int port = ((Integer) controllerMenuItem.getUserData()).intValue();
                controllerMenuItem.setDisable(player.m324k() == port);
            }
        }
        this.clearPlayerControllerMenuItem.setDisable(player.m324k() == 0);
    }

    /* JADX INFO: renamed from: d */
    private void m341d() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(LibraryController.class.getResource("/fxml/dialog/netplay/lobby/bannedMemberContextMenu.fxml"));
            fxmlLoader.setControllerFactory(type -> {
                return new C0127bt(this.f205i);
            });
            fxmlLoader.setResources(this.resources);
            fxmlLoader.load();
            this.f222z = (C0127bt) fxmlLoader.getController();
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m342a(JsonArray array) {
        array.forEach(jsonElement -> {
            C0084bC player = new C0084bC(jsonElement.getAsJsonObject());
            if (player.m319g() == this.f202f.m847a()) {
                this.f203g = player;
            }
            this.playersListView.getItems().add(player);
        });
    }

    /* JADX INFO: renamed from: b */
    private void m343b(JsonArray array) {
        array.forEach(jsonElement -> {
            this.blacklistListView.getItems().add(new C0131bx(jsonElement.getAsJsonObject()));
        });
    }

    /* JADX INFO: renamed from: a */
    private synchronized void m344a(boolean polling) {
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/update.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
            C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("lobby_id", Integer.valueOf(this.f206j)).m974a("credential_id", Integer.valueOf(this.f202f.m847a())).m974a("polling", Integer.valueOf(polling ? 1 : 0)).m974a("last_update", Long.valueOf(this.f220x));
            C0216a request = new C0216a(builder.m973b());
            C0219d serverResponse = request.m959a(post);
            serverResponse.m967b().getAsJsonArray("actions").forEach(jsonElement -> {
                JsonObject jsonObject = (JsonObject) jsonElement;
                switch (jsonObject.get("name").getAsString()) {
                    case "add_member":
                        m345a(jsonObject.getAsJsonObject("value"));
                        break;
                    case "remove_member":
                        m346a(jsonObject.get("value").getAsInt());
                        break;
                    case "message":
                        m347b(jsonObject.getAsJsonObject("value"));
                        break;
                    case "system_message":
                        m348c(jsonObject.getAsJsonObject("value"));
                        break;
                    case "update_title":
                        m349a(jsonObject.get("value").getAsString());
                        break;
                    case "update_description":
                        m350b(jsonObject.get("value").getAsString());
                        break;
                    case "lock":
                        m351b(jsonObject.get("value").getAsBoolean());
                        break;
                    case "password":
                        m352c(jsonObject.get("value").getAsBoolean());
                        break;
                    case "live":
                        m353d(jsonObject.get("value").getAsBoolean());
                        break;
                    case "dedicated_server_started":
                        m354e();
                        break;
                    case "dedicated_server_restarting":
                        m355f();
                        break;
                    case "dedicated_server_restarted":
                        m356g();
                        break;
                    case "dedicated_server_stopping":
                        m357h();
                        break;
                    case "dedicated_server_stopped":
                        m358i();
                        break;
                    case "ban":
                        m360c(jsonObject.get("value").getAsInt());
                        break;
                    case "kick":
                        m359b(jsonObject.get("value").getAsInt());
                        break;
                    case "unban":
                        m361d(jsonObject.getAsJsonObject("value"));
                        break;
                    case "controllers":
                        m362c(jsonObject.getAsJsonArray("value"));
                        break;
                    case "restart_stream":
                        m363j();
                        break;
                }
            });
            this.f220x = serverResponse.m967b().get("last_update").getAsLong();
        } catch (MalformedURLException | InvalidServerResponseException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        } catch (NetworkOfflineException exception2) {
            RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
        } catch (ServerResponseException exception3) {
            RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
            Platform.runLater(() -> {
                close();
                ServerErrorAlert alert = new ServerErrorAlert(exception3);
                alert.showAndWait();
            });
        }
    }

    /* JADX INFO: renamed from: a */
    private void m345a(JsonObject object) {
        Platform.runLater(() -> {
            C0084bC player = new C0084bC(object);
            this.playersListView.getItems().add(player);
            this.messagesListView.getItems().add(new C0089bH(String.format(this.resources.getString("serverLobbyDialog.message.player.joined"), player.m631d())));
            if (this.soundNotificationsMenuItem.isSelected()) {
                this.f224B.play();
            }
        });
    }

    /* JADX INFO: renamed from: a */
    private void m346a(int id) {
        Platform.runLater(() -> {
            Iterator<C0084bC> iterator = this.playersListView.getItems().iterator();
            while (iterator.hasNext()) {
                C0084bC player = iterator.next();
                if (player.m319g() == id) {
                    iterator.remove();
                    this.messagesListView.getItems().add(new C0089bH(String.format(this.resources.getString("serverLobbyDialog.message.player.leaved"), player.m631d())));
                    if (this.soundNotificationsMenuItem.isSelected()) {
                        this.f225C.play();
                        return;
                    }
                    return;
                }
            }
        });
    }

    /* JADX INFO: renamed from: b */
    private void m347b(JsonObject object) {
        Platform.runLater(() -> {
            int id = object.getAsJsonObject("from").get("id").getAsInt();
            this.playersListView.getItems().stream().filter(player -> {
                return player.m319g() == id;
            }).findAny().ifPresent(player2 -> {
                AbstractC0132by message = new C0087bF(player2, object.get("timestamp").getAsLong(), object.get("message").getAsString());
                this.messagesListView.getItems().add(message);
                this.messagesListView.scrollTo(message);
                if (this.soundNotificationsMenuItem.isSelected()) {
                    this.f226D.play();
                }
            });
        });
    }

    /* JADX INFO: renamed from: c */
    private void m348c(JsonObject object) {
        Platform.runLater(() -> {
            String text;
            if (Locale.getDefault().toLanguageTag().equals("fr")) {
                text = object.getAsJsonObject("message").get("fr").getAsString();
            } else {
                text = object.getAsJsonObject("message").get("en").getAsString();
            }
            this.messagesListView.getItems().add(new C0089bH(object.get("timestamp").getAsLong(), text));
        });
    }

    /* JADX INFO: renamed from: a */
    private void m349a(String title) {
        Platform.runLater(() -> {
            this.titleLabel.setText(title);
            this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.title.updated")));
        });
    }

    /* JADX INFO: renamed from: b */
    private void m350b(String description) {
        Platform.runLater(() -> {
            this.descriptionLabel.setText(description);
            this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.description.updated")));
        });
    }

    /* JADX INFO: renamed from: b */
    private void m351b(boolean locked) {
        Platform.runLater(() -> {
            this.f211o.setValue(Boolean.valueOf(locked));
            this.lockMenuItem.setSelected(locked);
            if (locked) {
                this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.locked")));
            } else {
                this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.unlocked")));
            }
        });
    }

    /* JADX INFO: renamed from: c */
    private void m352c(boolean enabled) {
        Platform.runLater(() -> {
            this.f212p.setValue(Boolean.valueOf(enabled));
            if (enabled) {
                this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.password.enabled")));
            } else {
                this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.password.disabled")));
            }
        });
    }

    /* JADX INFO: renamed from: d */
    private void m353d(boolean live) {
        Platform.runLater(() -> {
            this.f213q.setValue(Boolean.valueOf(live));
            this.liveMenuItem.setSelected(live);
            if (live) {
                this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.live.online")));
            } else {
                this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.live.offline")));
            }
        });
    }

    /* JADX INFO: renamed from: e */
    private void m354e() {
        Platform.runLater(() -> {
            this.f214r.setValue(true);
            this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.dedicatedServer.started")));
        });
    }

    /* JADX INFO: renamed from: f */
    private void m355f() {
        this.f215s.setValue(true);
        Platform.runLater(() -> {
            this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.dedicatedServer.restarting")));
        });
    }

    /* JADX INFO: renamed from: g */
    private void m356g() {
        this.f215s.setValue(false);
        Platform.runLater(() -> {
            this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.dedicatedServer.restarted")));
        });
    }

    /* JADX INFO: renamed from: h */
    private void m357h() {
        this.f215s.setValue(true);
        Platform.runLater(() -> {
            this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.dedicatedServer.stopping")));
        });
    }

    /* JADX INFO: renamed from: i */
    private void m358i() {
        this.f215s.setValue(false);
        Platform.runLater(() -> {
            this.f214r.setValue(false);
            this.messagesListView.getItems().add(new C0089bH(this.resources.getString("serverLobbyDialog.message.dedicatedServer.stopped")));
        });
    }

    /* JADX INFO: renamed from: b */
    private void m359b(int id) {
        Platform.runLater(() -> {
            if (id == this.f202f.m847a()) {
                close();
                ApplicationAlert alert = new ApplicationAlert(this.resources.getString("netplayKickedAlert.header"), this.resources.getString("netplayKickedAlert.content"), Alert.AlertType.INFORMATION);
                alert.showAndWait();
                return;
            }
            Iterator<C0084bC> iterator = this.playersListView.getItems().iterator();
            while (iterator.hasNext()) {
                C0084bC player = iterator.next();
                if (player.m319g() == id) {
                    iterator.remove();
                    this.messagesListView.getItems().add(new C0089bH(String.format(this.resources.getString("serverLobbyDialog.message.player.kicked"), player.m631d())));
                    return;
                }
            }
        });
    }

    /* JADX INFO: renamed from: c */
    private void m360c(int id) {
        Platform.runLater(() -> {
            Iterator<C0084bC> iterator = this.playersListView.getItems().iterator();
            while (iterator.hasNext()) {
                C0084bC player = iterator.next();
                if (player.m319g() == id) {
                    iterator.remove();
                    if (this.f216t) {
                        this.blacklistListView.getItems().add(player);
                    }
                    this.messagesListView.getItems().add(new C0089bH(String.format(this.resources.getString("serverLobbyDialog.message.player.banned"), player.m631d())));
                    return;
                }
            }
        });
    }

    /* JADX INFO: renamed from: d */
    private void m361d(JsonObject jsonObject) {
        Platform.runLater(() -> {
            int softID = jsonObject.get("sid").getAsInt();
            int memberID = jsonObject.get("mid").getAsInt();
            Iterator<C0131bx> iterator = this.blacklistListView.getItems().iterator();
            while (iterator.hasNext()) {
                C0131bx member = iterator.next();
                if (member.m629b() == softID && member.m630c() == memberID) {
                    iterator.remove();
                    this.messagesListView.getItems().add(new C0089bH(String.format(this.resources.getString("serverLobbyDialog.message.player.unbanned"), member.m631d())));
                    return;
                }
            }
        });
    }

    /* JADX INFO: renamed from: c */
    private void m362c(JsonArray jsonArray) {
        Platform.runLater(() -> {
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement jsonElement = (JsonElement) it.next();
                JsonObject controller = jsonElement.getAsJsonObject();
                int playerId = controller.get("credential_id").getAsInt();
                byte controllerId = controller.get("controller_id").getAsByte();
                byte controllerKey = controller.get("controller_key").getAsByte();
                int controllerPort = controller.get("controller_port").getAsInt();
                for (C0084bC player : this.playersListView.getItems()) {
                    if (player.m319g() == playerId && player.m324k() != controllerPort) {
                        if (controllerPort == 0) {
                            this.messagesListView.getItems().add(new C0089bH(String.format(this.resources.getString("serverLobbyDialog.message.player.controller.clear"), Integer.valueOf(player.m324k()), player.m631d())));
                            player.m323a((C0188da) null);
                        } else {
                            this.messagesListView.getItems().add(new C0089bH(String.format(this.resources.getString("serverLobbyDialog.message.player.controller.update"), Integer.valueOf(controllerPort), player.m631d())));
                            player.m323a(new C0188da(controllerId, controllerKey));
                        }
                        player.m325a(controllerPort);
                        if (player.m319g() == this.f202f.m847a() && this.f201e != null) {
                            this.f201e.m685a(player.m322j());
                            break;
                        }
                        break;
                    }
                }
            }
            this.playersListView.refresh();
            m339c();
        });
    }

    /* JADX INFO: renamed from: j */
    private void m363j() {
        Platform.runLater(() -> {
            if (this.f201e != null && this.f201e.m686b().isShowing()) {
                this.f201e.m689e();
            }
        });
    }

    /* JADX INFO: renamed from: k */
    private void m364k() {
        this.f210n.shutdown();
        if (this.f199c != null) {
            this.f200d.m810a();
            if (this.f199c.isDone()) {
                try {
                    Session session = this.f199c.get();
                    if (session.isOpen()) {
                        session.close();
                    }
                } catch (IOException | InterruptedException | ExecutionException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                }
            }
        }
        this.f205i.unregister(this);
        if (this.f202f.m848b() != null) {
            this.f202f.m848b().m1646a((InterfaceC0276b) null);
            if (this.f202f.m848b().m1649e()) {
                try {
                    this.f202f.m848b().m1650f();
                } catch (IOException exception2) {
                    RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
                }
            }
        }
        if (this.f201e != null && this.f201e.m686b().isShowing()) {
            this.f201e.close();
        }
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/quit.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
            C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("lobby_id", Integer.valueOf(this.f206j)).m974a("credential_id", Integer.valueOf(this.f202f.m847a()));
            C0216a request = new C0216a(builder.m973b());
            request.m959a(post);
        } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception3) {
            RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
        } catch (NetworkOfflineException exception4) {
            RomStation.m42b().log(Level.WARNING, exception4.getMessage(), (Throwable) exception4);
        }
    }

    /* JADX INFO: renamed from: a */
    private String m365a(String string, int maxLength) {
        return (string == null || string.length() <= maxLength) ? string : string.substring(0, maxLength);
    }

    /* JADX INFO: renamed from: l */
    private boolean m366l() {
        return System.currentTimeMillis() - this.f223A >= 250;
    }

    /* JADX INFO: renamed from: m */
    private void m367m() {
        this.f223A = System.currentTimeMillis();
    }

    @FXML
    private void onGameCoverClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            RomStationController.f786a.post(new C0152cR(C0217b.m961b() + this.f204h.getAsJsonObject("game").get("url").getAsString(), true));
            RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
        }
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        String text = this.messageTextField.getText();
        if (text != null && !text.isEmpty()) {
            m369c(this.messageTextField.getText());
            this.messageTextField.clear();
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m368a(C0145cK event) {
        m369c(event.m653a());
    }

    /* JADX INFO: renamed from: c */
    private void m369c(String message) {
        if (m366l()) {
            new Thread(() -> {
                try {
                    C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/post_chat_message.php");
                    builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
                    C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("lobby_id", Integer.valueOf(this.f206j)).m974a("credential_id", Integer.valueOf(this.f202f.m847a())).m974a("message", m365a(message, 512));
                    C0216a request = new C0216a(builder.m973b());
                    request.m959a(post);
                } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                } catch (NetworkOfflineException exception2) {
                    RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                }
            }).start();
            m367m();
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m370a(C0144cJ event) {
        m372a(event.m651a(), event.m652b());
    }

    /* JADX INFO: renamed from: d */
    private void m371d(String command) {
        m372a(command, (Object) null);
    }

    /* JADX INFO: renamed from: a */
    private void m372a(String command, Object value) {
        if (m366l()) {
            new Thread(() -> {
                try {
                    C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/multiplayer/lobby_cmd.php");
                    builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
                    C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("lobby_id", Integer.valueOf(this.f207k == 0 ? this.f206j : this.f207k)).m974a("action", command).m974a("value", value);
                    C0216a request = new C0216a(builder.m973b());
                    request.m959a(post);
                } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                } catch (NetworkOfflineException exception2) {
                    RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                }
            }).start();
            m367m();
        }
    }

    @FXML
    private void live(ActionEvent event) {
        boolean live = this.liveMenuItem.isSelected();
        this.liveMenuItem.setSelected(!live);
        m372a("live", Boolean.valueOf(live));
    }

    @FXML
    private void editPassword(ActionEvent event) {
        C0074at passwordInputDialog = new C0074at(this.resources.getString("serverLobbyDialog.dialog.editLobbyPassword"));
        passwordInputDialog.showAndWait().ifPresent(this::m373e);
    }

    @FXML
    private void clearPassword(ActionEvent event) {
        m373e((String) null);
    }

    /* JADX INFO: renamed from: e */
    private void m373e(String password) {
        m372a("password", m365a(password, 255));
    }

    @FXML
    private void lock(ActionEvent event) {
        boolean locked = this.lockMenuItem.isSelected();
        this.lockMenuItem.setSelected(!locked);
        m372a("lock", Boolean.valueOf(locked));
    }

    @FXML
    public void editTitle(ActionEvent event) {
        C0078ax textInputDialog = new C0078ax(this.resources.getString("serverLobbyDialog.dialog.editLobbyTitle"), this.titleLabel.getText());
        textInputDialog.showAndWait().ifPresent(title -> {
            m372a("title", m365a(title, 255));
        });
    }

    @FXML
    public void editDescription(ActionEvent event) {
        C0078ax textInputDialog = new C0078ax(this.resources.getString("serverLobbyDialog.dialog.editLobbyDescription"), this.descriptionLabel.getText());
        textInputDialog.showAndWait().ifPresent(description -> {
            m372a("description", m365a(description, 512));
        });
    }

    @FXML
    private void startDedicatedServer() {
        m371d("start_dedicated_server");
    }

    @FXML
    private void restartDedicatedServer() {
        m371d("restart_dedicated_server");
    }

    @FXML
    private void stopDedicatedServer() {
        m371d("stop_dedicated_server");
    }

    @FXML
    private void openDedicatedServerCommandPrompt() {
        C0078ax dialog = new C0078ax(this.resources.getString("serverLobbyDialog.dialog.dedicatedServerCommandPrompt"));
        dialog.getDialogPane().getStyleClass().add("command-prompt");
        dialog.showAndWait().ifPresent(command -> {
            if (!command.isEmpty()) {
                m372a("send_dedicated_server_command", command);
            }
        });
    }

    @FXML
    private void openDedicatedServerFileManager() {
        String url = this.f204h.get("dedicated_server").getAsJsonObject().get("ftp").getAsString();
        switch (C0004E.m10c()) {
            case WINDOWS:
                ProcessBuilder processBuilder = new ProcessBuilder("filezilla/filezilla.exe", url);
                processBuilder.directory(Paths.get("filezilla", new String[0]).toFile());
                processBuilder.inheritIO();
                try {
                    processBuilder.start();
                } catch (IOException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                }
                break;
        }
    }

    @FXML
    private void copyHostIP(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(this.hostIPTextField.getText());
        clipboard.setContent(clipboardContent);
    }

    @FXML
    private void copyDedicatedServerAddress(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(this.dedicatedServerAddressTextField.getText());
        clipboard.setContent(clipboardContent);
    }

    @FXML
    private void banPlayer(ActionEvent event) {
        C0084bC player = (C0084bC) this.playersListView.getSelectionModel().getSelectedItem();
        m374b(player);
    }

    /* JADX INFO: renamed from: b */
    private void m374b(C0084bC player) {
        m372a("ban", Integer.valueOf(player.m319g()));
    }

    @FXML
    private void kickPlayer(ActionEvent event) {
        C0084bC player = (C0084bC) this.playersListView.getSelectionModel().getSelectedItem();
        m375c(player);
    }

    /* JADX INFO: renamed from: c */
    private void m375c(C0084bC player) {
        m372a("kick", Integer.valueOf(player.m319g()));
    }

    @FXML
    private void showPlayerProfile(ActionEvent event) {
        C0084bC player = (C0084bC) this.playersListView.getSelectionModel().getSelectedItem();
        m376a((C0131bx) player);
    }

    /* JADX INFO: renamed from: a */
    private void m376a(C0131bx member) {
        RomStationController.f786a.post(new C0152cR(member.m632e(), true));
        RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
    }

    @FXML
    private void unbanMember(ActionEvent event) {
        C0131bx member = (C0131bx) this.blacklistListView.getSelectionModel().getSelectedItem();
        m377b(member);
    }

    /* JADX INFO: renamed from: b */
    private void m377b(C0131bx member) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mid", Integer.valueOf(member.m630c()));
        jsonObject.addProperty("sid", Integer.valueOf(member.m629b()));
        m372a("unban", jsonObject);
    }

    @FXML
    private void showMemberProfile(ActionEvent event) {
        C0131bx member = (C0131bx) this.blacklistListView.getSelectionModel().getSelectedItem();
        m376a(member);
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m378a(C0149cO event) {
        m380a(event.m656a(), event.m657b());
    }

    /* JADX INFO: renamed from: d */
    private void m379d(int port) {
        m380a((C0084bC) this.playersListView.getSelectionModel().getSelectedItem(), port);
    }

    /* JADX INFO: renamed from: a */
    private void m380a(C0084bC player, int port) {
        if (player.m324k() != port) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("credential_id", Integer.valueOf(player.m319g()));
            jsonObject.addProperty("controller_port", Integer.valueOf(port));
            m372a("set_controller", jsonObject);
        }
    }

    @FXML
    private void clearPlayerController() {
        m382d((C0084bC) this.playersListView.getSelectionModel().getSelectedItem());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m381a(C0141cG event) {
        m382d(event.m648a());
    }

    /* JADX INFO: renamed from: d */
    private void m382d(C0084bC player) {
        m380a(player, 0);
    }

    /* JADX INFO: renamed from: n */
    private void m383n() {
        JsonObject launchJsonObject = this.f204h.getAsJsonObject("launch");
        for (JsonElement profileJsonElement : launchJsonObject.getAsJsonArray("profiles")) {
            JsonObject profileJsonObject = profileJsonElement.getAsJsonObject();
            Button button = new Button((String) null, new FontAwesomeIconView());
            button.getStyleClass().add("launch");
            if (profileJsonObject.get("tag").getAsString().equals("server")) {
                button.setText(this.resources.getString("serverLobbyDialog.launch.server"));
                button.getStyleClass().add("server");
            } else {
                button.setText(this.resources.getString("serverLobbyDialog.launch"));
            }
            button.setOnAction(event -> {
                m384a(launchJsonObject.getAsJsonObject("game_file").get("id").getAsInt(), profileJsonObject);
            });
            this.launchButtonBar.getButtons().add(button);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m384a(int gameFileID, JsonObject profile) {
        EntityManager entityManager = C0081b.m309c();
        try {
            switch (profile.get("type").getAsString()) {
                case "emulator":
                    GameProfile gameProfile = (GameProfile) entityManager.createQuery("select game_profile from GameProfile game_profile where game_profile.gameFile.rid = :rid", GameProfile.class).setParameter("rid", Integer.valueOf(gameFileID)).setMaxResults(1).getSingleResult();
                    EmulatorProfile emulatorProfile = (EmulatorProfile) entityManager.createQuery("select emulator_profile from EmulatorProfile emulator_profile join emulator_profile.metas meta where value(meta) like :guid", EmulatorProfile.class).setParameter("guid", profile.get("guid").getAsString()).setMaxResults(1).getSingleResult();
                    for (JsonElement metaElement : profile.getAsJsonArray("metas")) {
                        JsonObject metaObject = metaElement.getAsJsonObject();
                        emulatorProfile.getMetas().put(metaObject.get("key").getAsString(), metaObject.get("value").getAsString());
                    }
                    String[] args = (String[]) StreamSupport.stream(profile.getAsJsonArray("args").spliterator(), false).map((v0) -> {
                        return v0.getAsString();
                    }).toArray(x$0 -> {
                        return new String[x$0];
                    });
                    C0157cW.m681a(new C0258z(emulatorProfile, gameProfile, args));
                    break;
                case "game":
                    GameProfile gameProfile2 = (GameProfile) entityManager.createQuery("select game_profile from GameProfile game_profile join game_profile.metas meta where value(meta) like :guid", GameProfile.class).setParameter("guid", profile.get("guid").getAsString()).setMaxResults(1).getSingleResult();
                    for (JsonElement metaElement2 : profile.getAsJsonArray("metas")) {
                        JsonObject metaObject2 = metaElement2.getAsJsonObject();
                        gameProfile2.getMetas().put(metaObject2.get("key").getAsString(), metaObject2.get("value").getAsString());
                    }
                    String[] args2 = (String[]) StreamSupport.stream(profile.getAsJsonArray("args").spliterator(), false).map((v0) -> {
                        return v0.getAsString();
                    }).toArray(x$1 -> {
                        return new String[x$1];
                    });
                    RomStationController.f786a.post(new C0186cz(gameProfile2, args2));
                    break;
            }
        } catch (NoResultException e) {
            RomStation.m42b().log(Level.WARNING, e.getMessage(), e);
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        } finally {
            entityManager.close();
        }
    }

    @FXML
    private void openCloudClient() {
        if (this.f201e == null || !this.f201e.m686b().isShowing()) {
            if (!C0001B.m3a()) {
                Task c0235c = new C0235c();
                C0076av<Boolean> dialog = new C0076av<>(c0235c);
                new Thread((Runnable) c0235c).start();
                if (!((Boolean) dialog.showAndWait().orElse(false)).booleanValue()) {
                    return;
                }
            }
            try {
                this.f201e = new C0158cX(this.f205i, this.f204h.getAsJsonObject("game").get("title").getAsString(), new Image(C0217b.m961b() + this.f204h.getAsJsonObject("game").get("system_icon").getAsString()), this.f204h.getAsJsonObject("cloud"));
                this.f201e.m685a(this.f203g.m322j());
                this.f201e.m687c();
                this.f201e.m686b().show();
                return;
            } catch (IOException exception) {
                RomStation.m42b().log(Level.SEVERE, "cloud player creation failed", (Throwable) exception);
                return;
            }
        }
        this.f201e.m686b().setIconified(false);
        this.f201e.m686b().toFront();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m385a(C0148cN event) {
        m344a(false);
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m386a(C0140cF event) {
        m374b(event.m647a());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m387a(C0142cH event) {
        m375c(event.m649a());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m388a(C0146cL event) {
        m376a(event.m654a());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m389a(C0147cM event) {
        m377b(event.m655a());
    }

    @Override // org.romstation.application.vpn.InterfaceC0276b
    /* JADX INFO: renamed from: a */
    public void mo390a(EnumC0278d state, String description) {
        if (state == EnumC0278d.EXITING) {
            close();
            Platform.runLater(() -> {
                ApplicationAlert alert = new ApplicationAlert(this.resources.getString("vpnConnectionLostAlert.header"), String.format(this.resources.getString("vpnConnectionLostAlert.content"), description), Alert.AlertType.ERROR);
                alert.showAndWait();
            });
        }
    }

    @Override // org.romstation.application.vpn.InterfaceC0276b
    /* JADX INFO: renamed from: a */
    public void mo391a(Throwable throwable) {
        close();
        Platform.runLater(() -> {
            C0069ap dialog = new C0069ap(throwable);
            dialog.showAndWait();
        });
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Void resultConverter(ButtonType buttonType) {
        return null;
    }
}
