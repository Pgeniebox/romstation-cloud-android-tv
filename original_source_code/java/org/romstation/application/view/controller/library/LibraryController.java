package org.romstation.application.view.controller.library;

import com.google.common.eventbus.Subscribe;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import org.romstation.application.C0004E;
import org.romstation.application.C0028aA;
import org.romstation.application.C0029aB;
import org.romstation.application.C0032aE;
import org.romstation.application.C0053aZ;
import org.romstation.application.C0055ab;
import org.romstation.application.C0056ac;
import org.romstation.application.C0057ad;
import org.romstation.application.C0060ag;
import org.romstation.application.C0067an;
import org.romstation.application.C0071aq;
import org.romstation.application.C0076av;
import org.romstation.application.C0080az;
import org.romstation.application.C0081b;
import org.romstation.application.C0105bX;
import org.romstation.application.C0106bY;
import org.romstation.application.C0111bd;
import org.romstation.application.C0112be;
import org.romstation.application.C0135cA;
import org.romstation.application.C0136cB;
import org.romstation.application.C0139cE;
import org.romstation.application.C0152cR;
import org.romstation.application.C0156cV;
import org.romstation.application.C0157cW;
import org.romstation.application.C0165ce;
import org.romstation.application.C0166cf;
import org.romstation.application.C0167cg;
import org.romstation.application.C0168ch;
import org.romstation.application.C0170cj;
import org.romstation.application.C0178cr;
import org.romstation.application.C0179cs;
import org.romstation.application.C0184cx;
import org.romstation.application.C0185cy;
import org.romstation.application.C0186cz;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.database.entity.Link;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.task.C0240h;
import org.romstation.application.task.C0241i;
import org.romstation.application.task.C0244l;
import org.romstation.application.task.C0245m;
import org.romstation.application.task.C0258z;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.SearchField;
import org.romstation.application.view.controller.ApplicationView;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.view.controller.library.filters.LibraryFiltersController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/LibraryController.class */
public class LibraryController {

    /* JADX INFO: renamed from: a */
    private static GameContextMenu f821a;

    /* JADX INFO: renamed from: b */
    private Timeline f822b;

    @FXML
    private BorderPane root;

    @FXML
    private Label searchResultLabel;

    @FXML
    private ToggleGroup viewToggleGroup;

    @FXML
    private ToggleButton tableViewToggle;

    @FXML
    private ToggleButton gridViewToggle;

    @FXML
    private SearchField titleSearchField;

    @FXML
    private LibraryFiltersController filtersController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        switch (LibraryView.valueOf(RomStation.m43c().getProperty("library.view"))) {
            case TABLE_VIEW:
                this.viewToggleGroup.selectToggle(this.tableViewToggle);
                break;
            case GRID_VIEW:
                this.viewToggleGroup.selectToggle(this.gridViewToggle);
                break;
        }
        this.viewToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.viewToggleGroup.selectToggle(oldValue);
            }
        });
        this.titleSearchField.textProperty().addListener((observable2, oldValue2, newValue2) -> {
            this.f822b.playFromStart();
        });
        this.f822b = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(250.0d), event -> {
            this.filtersController.m1548a(this.titleSearchField.getText());
        }, new KeyValue[0])});
    }

    /* JADX INFO: renamed from: a */
    public static GameContextMenu m1509a() {
        if (f821a == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(LibraryController.class.getResource("/fxml/library/contextMenu.fxml"));
            fxmlLoader.setResources(RomStation.m44d());
            try {
                fxmlLoader.load();
                f821a = (GameContextMenu) fxmlLoader.getController();
            } catch (IOException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            }
        }
        return f821a;
    }

    /* JADX INFO: renamed from: a */
    private void m1510a(Game game, String... arguments) {
        List<GameProfile> gameProfiles = (List) game.getFiles().stream().flatMap(gameFile -> {
            return gameFile.getProfiles().stream();
        }).collect(Collectors.toList());
        switch (gameProfiles.size()) {
            case 0:
                break;
            case 1:
                m1512a(gameProfiles.get(0), arguments);
                break;
            default:
                C0029aB dialog = new C0029aB(gameProfiles);
                dialog.showAndWait().ifPresent(gameProfile -> {
                    m1512a(gameProfile, arguments);
                });
                break;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1511a(GameFile gameFile, String... arguments) {
        switch (gameFile.getProfiles().size()) {
            case 0:
                break;
            case 1:
                m1512a(gameFile.getProfiles().get(0), arguments);
                break;
            default:
                C0029aB dialog = new C0029aB(gameFile.getProfiles());
                dialog.showAndWait().ifPresent(gameProfile -> {
                    m1512a(gameProfile, arguments);
                });
                break;
        }
    }

    /* JADX WARN: Type inference failed for: r0v50, types: [java.lang.Runnable, org.romstation.application.task.i] */
    /* JADX INFO: renamed from: a */
    private void m1512a(GameProfile gameProfile, String... arguments) {
        Game game = gameProfile.getGameFile().getGame();
        if (game.getSystem() == null) {
        }
        EntityManager entityManager = C0081b.m309c();
        List<EmulatorProfile> emulatorProfiles = entityManager.createQuery("select profile from EmulatorProfile profile where :system member of profile.systems", EmulatorProfile.class).setParameter("system", game.getSystem()).getResultList();
        entityManager.close();
        if (game.getSystem().getRid() != null && emulatorProfiles.isEmpty()) {
            try {
                C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/emulator/get_file.php");
                builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("arch", Integer.valueOf(C0004E.m11d().m6a())).m974a("system", game.getSystem().getRid());
                C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
                C0216a request = new C0216a(builder.m973b());
                C0219d serverResponse = request.m959a(post);
                C0240h context = new C0240h(serverResponse.m967b());
                C0080az emulatorFileDownloadConfirmationDialog = new C0080az(context);
                Optional<ButtonType> result = emulatorFileDownloadConfirmationDialog.showAndWait();
                if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                    ?? c0241i = new C0241i(context);
                    Thread thread = new Thread((Runnable) c0241i);
                    C0067an emulatorFileDownloadDialog = new C0067an(c0241i);
                    thread.start();
                    Optional<EmulatorFile> taskResult = emulatorFileDownloadDialog.showAndWait();
                    if (taskResult.isPresent()) {
                        emulatorProfiles.addAll((Collection) taskResult.get().getEmulator().getFiles().stream().flatMap(emulatorFile -> {
                            return emulatorFile.getProfiles().stream();
                        }).filter(emulatorProfile -> {
                            return emulatorProfile.getSystems().contains(game.getSystem());
                        }).collect(Collectors.toList()));
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } catch (NetworkOfflineException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            } catch (Exception exception2) {
                RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
            }
        }
        switch (emulatorProfiles.size()) {
            case 0:
                ApplicationAlert alert = new ApplicationAlert(this.resources.getString("emulator.missing.alert.header"), this.resources.getString("emulator.missing.alert.content"), Alert.AlertType.WARNING);
                alert.showAndWait();
                break;
            case 1:
                C0157cW.m681a(new C0258z(emulatorProfiles.get(0), gameProfile, arguments));
                break;
            default:
                C0028aA dialog = new C0028aA(emulatorProfiles, gameProfile.getEmulatorProfile());
                dialog.showAndWait().ifPresent(emulatorProfile2 -> {
                    C0157cW.m681a(new C0258z(emulatorProfile2, gameProfile, arguments));
                });
                break;
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1513a(C0167cg event) {
        this.root.setVisible(event.m730a() == ApplicationView.LIBRARY);
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1514a(C0139cE event) {
        EntityManager entityManager = C0081b.m309c();
        long count = ((Long) entityManager.createNamedQuery(Game.f439a, Long.class).getSingleResult()).longValue();
        entityManager.close();
        String message = MessageFormat.format(this.resources.getString("library.search.result"), Integer.valueOf(event.m646a().size()), Long.valueOf(count));
        this.searchResultLabel.setText(message);
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1515a(C0184cx event) {
        m1510a(event.m744a(), event.m745b());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1516a(C0185cy event) {
        m1511a(event.m746a(), event.m747b());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1517a(C0186cz event) {
        m1512a(event.m748a(), event.m749b());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1518a(C0105bX event) {
        C0258z context = event.m552a();
        if (context.getGameProfile() != null) {
            Game game = context.getGameProfile().getGameFile().getGame();
            game.setLastUse(Long.valueOf(Instant.now().getEpochSecond()));
            GameProfile gameProfile = context.getGameProfile();
            gameProfile.setEmulatorProfile(context.getEmulatorProfile());
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.createQuery("update Game set lastUse = :timestamp where id = :id").setParameter("timestamp", game.getLastUse()).setParameter("id", game.getId()).executeUpdate();
            entityManager.createQuery("update GameProfile set emulatorProfile = :profile where id = :id").setParameter("profile", gameProfile.getEmulatorProfile()).setParameter("id", gameProfile.getId()).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1519a(C0106bY event) {
        C0258z context = event.m553a();
        if (context.getGameProfile() != null) {
            Game game = context.getGameProfile().getGameFile().getGame();
            long played = Instant.now().getEpochSecond() - game.getLastUse().longValue();
            if (game.getPlayed() == null) {
                game.setPlayed(Long.valueOf(played));
            } else {
                game.setPlayed(Long.valueOf(game.getPlayed().longValue() + played));
            }
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.createQuery("update Game set played = :timestamp where id = :id").setParameter("timestamp", game.getPlayed()).setParameter("id", game.getId()).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
            if (game.getRid() != null) {
                new Thread(() -> {
                    try {
                        C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/set_played.php");
                        builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("gid", game.getRid()).m974a("efid", context.getEmulatorProfile().getEmulatorFile().getRid()).m974a("t", Long.valueOf(played));
                        C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
                        C0216a request = new C0216a(builder.m973b());
                        request.m959a(post);
                    } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception) {
                        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                    } catch (NetworkOfflineException exception2) {
                        RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                    }
                }).start();
            }
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1520a(C0179cs event) {
        C0053aZ dialog = new C0053aZ(event.m739a());
        dialog.showAndWait().ifPresent(game -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.merge(game);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        });
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1521a(C0178cr event) {
        Alert alert = new ApplicationAlert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(this.resources.getString("game.delete.alert.header"));
        alert.setContentText(this.resources.getString("game.delete.alert.content"));
        Optional<ButtonType> alertResult = alert.showAndWait();
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            Task c0245m = new C0245m(event.m738a());
            Thread thread = new Thread((Runnable) c0245m);
            C0076av<List<Game>> dialog = new C0076av<>(c0245m, "delete");
            thread.start();
            Optional<List<Game>> dialogResult = dialog.showAndWait();
            dialogResult.ifPresent(items -> {
                if (!items.isEmpty()) {
                    RomStationController.f786a.post(new C0170cj());
                }
            });
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1522a(C0135cA event) {
        Game game = event.m643a();
        if (game != null && game.getDirectory() != null) {
            try {
                Desktop.getDesktop().open(new File(game.getDirectory()));
            } catch (IOException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1523a(C0136cB event) {
        Game game = event.m644a();
        if (game != null) {
            C0032aE dialog = new C0032aE(game.getLinks());
            Optional<Link> optional = dialog.showAndWait();
            optional.ifPresent(link -> {
                if (link.isExternal()) {
                    try {
                        Desktop.getDesktop().browse(new URI(link.getLocation()));
                        return;
                    } catch (Exception exception) {
                        RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                        return;
                    }
                }
                RomStationController.f786a.post(new C0152cR(link.getLocation(), true));
                RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
            });
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1524a(C0165ce event) {
        Path root = Paths.get("", new String[0]).toAbsolutePath().getParent();
        if (Files.exists(root.resolve("database.sqlite"), new LinkOption[0]) && Files.notExists(root.resolve("imported"), new LinkOption[0]) && Files.notExists(root.resolve("games_imported"), new LinkOption[0])) {
            try {
                try {
                    C0057ad gameScannerTask = new C0057ad(root);
                    gameScannerTask.run();
                    List<C0055ab> games = (List) gameScannerTask.get();
                    if (!games.isEmpty()) {
                        Task c0056ac = new C0056ac(games);
                        Thread thread = new Thread((Runnable) c0056ac);
                        C0076av<List<Game>> taskDialog = new C0076av<>(c0056ac);
                        thread.start();
                        taskDialog.showAndWait();
                    }
                    try {
                        Files.createFile(root.resolve("games_imported"), new FileAttribute[0]);
                    } catch (IOException exception) {
                        RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                    }
                } catch (Throwable th) {
                    try {
                        Files.createFile(root.resolve("games_imported"), new FileAttribute[0]);
                    } catch (IOException exception2) {
                        RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                    }
                    throw th;
                }
            } catch (InterruptedException | ExecutionException exception3) {
                RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                try {
                    Files.createFile(root.resolve("games_imported"), new FileAttribute[0]);
                } catch (IOException exception4) {
                    RomStation.m42b().log(Level.WARNING, exception4.getMessage(), (Throwable) exception4);
                }
            }
        }
        this.filtersController.m1551b();
        this.filtersController.m1547a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1525a(C0166cf event) {
        if (this.viewToggleGroup.getSelectedToggle() == this.tableViewToggle) {
            RomStation.m43c().setProperty("library.view", LibraryView.TABLE_VIEW.name());
        } else {
            RomStation.m43c().setProperty("library.view", LibraryView.GRID_VIEW.name());
        }
    }

    @FXML
    private void create() {
        C0053aZ gameEditorDialog = new C0053aZ(new Game());
        gameEditorDialog.showAndWait().ifPresent(entity -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        });
    }

    @FXML
    private void bulkImport() {
        C0071aq dialog = new C0071aq();
        dialog.showAndWait().ifPresent(result -> {
            Task c0244l = new C0244l(result.m274a(), result.m275b());
            Thread thread = new Thread((Runnable) c0244l);
            C0076av<List<Game>> taskDialog = new C0076av<>(c0244l);
            thread.start();
            Optional<List<Game>> dialogResult = taskDialog.showAndWait();
            dialogResult.ifPresent(items -> {
                if (!items.isEmpty()) {
                    RomStationController.f786a.post(new C0170cj());
                }
            });
        });
    }

    @FXML
    private void download() {
        RomStationController.f786a.post(new C0152cR(C0217b.m961b() + "/games", true));
        RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
    }

    @FXML
    private void upload() {
        C0156cV.m672b();
    }

    @FXML
    private void importLegacy() {
        C0112be romstationDirectoryChooserDialog = new C0112be();
        romstationDirectoryChooserDialog.showAndWait().ifPresent(path -> {
            try {
                C0057ad gameScannerTask = new C0057ad(path);
                gameScannerTask.run();
                List<C0055ab> games = (List) gameScannerTask.get();
                C0111bd gameImporterDialog = new C0111bd(games);
                gameImporterDialog.showAndWait().ifPresent(items -> {
                    Task c0056ac = new C0056ac(items);
                    Thread thread = new Thread((Runnable) c0056ac);
                    C0076av<List<Game>> taskDialog = new C0076av<>(c0056ac);
                    thread.start();
                    Optional<List<Game>> dialogResult = taskDialog.showAndWait();
                    dialogResult.ifPresent(importedGames -> {
                        if (!importedGames.isEmpty()) {
                            RomStationController.f786a.post(new C0170cj());
                        }
                    });
                });
            } catch (Exception exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            }
        });
    }

    /* JADX INFO: renamed from: b */
    public Node m1526b() {
        return this.root;
    }
}
