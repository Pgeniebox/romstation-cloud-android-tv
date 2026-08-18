package org.romstation.application.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.teamdev.jxbrowser.js.JsAccessible;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.romstation.application.C0067an;
import org.romstation.application.C0081b;
import org.romstation.application.C0155cU;
import org.romstation.application.C0156cV;
import org.romstation.application.C0184cx;
import org.romstation.application.C0185cy;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.task.C0240h;
import org.romstation.application.task.C0241i;
import org.romstation.application.task.EmulatorFileDownloadContextException;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/Library.class */
@JsAccessible
public class Library {
    public static final Set<Consumer<Game>> onGameDeleted = new HashSet();
    public static final Set<Consumer<GameFile>> onGameFileDeleted = new HashSet();
    public static List<TableColumn<Game, Object>> tableColumns = new LinkedList();

    public void onGameDeleted(Consumer<Game> consumer) {
        onGameDeleted.add(consumer);
    }

    public void onGameFileDeleted(Consumer<GameFile> consumer) {
        onGameFileDeleted.add(consumer);
    }

    public void addTableColumn(TableColumn tableColumn) {
        tableColumns.add(tableColumn);
    }

    public void launchGame(int rid) {
        EntityManager entityManager = C0081b.m309c();
        try {
            Game game = (Game) entityManager.createNamedQuery(Game.f440b, Game.class).setParameter("rid", Integer.valueOf(rid)).getSingleResult();
            Platform.runLater(() -> {
                RomStationController.f786a.post(new C0184cx(game, new String[0]));
            });
        } catch (NoResultException e) {
        } finally {
            entityManager.close();
        }
    }

    public void launchGameFile(int rid) {
        EntityManager entityManager = C0081b.m309c();
        try {
            GameFile gameFile = (GameFile) entityManager.createNamedQuery(GameFile.f461a, GameFile.class).setParameter("rid", Integer.valueOf(rid)).getSingleResult();
            Platform.runLater(() -> {
                RomStationController.f786a.post(new C0185cy(gameFile, new String[0]));
            });
        } catch (NoResultException e) {
        } finally {
            entityManager.close();
        }
    }

    public void downloadGameFile(int gameRid, int gameFileRid) {
        Platform.runLater(() -> {
            C0155cU.m667a(gameRid, gameFileRid);
        });
    }

    public void uploadGameFile(int gameID, int systemID) {
        Platform.runLater(() -> {
            C0156cV.m673a(gameID, systemID);
        });
    }

    public void downloadEmulatorFile(int rid) {
        Platform.runLater(() -> {
            try {
                ?? c0241i = new C0241i(new C0240h(rid));
                Thread thread = new Thread((Runnable) c0241i);
                C0067an dialog = new C0067an(c0241i);
                thread.start();
                dialog.showAndWait();
            } catch (NetworkOfflineException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            } catch (EmulatorFileDownloadContextException exception2) {
                RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
            }
        });
    }

    public boolean hasDownloadableContent(int rid, String string) {
        JsonParser parser = new JsonParser();
        JsonArray<JsonElement> json = parser.parse(string).getAsJsonArray();
        EntityManager entityManager = C0081b.m309c();
        try {
            Game game = (Game) entityManager.createNamedQuery(Game.f440b, Game.class).setParameter("rid", Integer.valueOf(rid)).getSingleResult();
            for (JsonElement element : json) {
                if (game.getFiles().stream().noneMatch(file -> {
                    return Objects.equals(file.getRid(), Integer.valueOf(element.getAsInt()));
                })) {
                    return true;
                }
            }
            return false;
        } catch (NoResultException e) {
            return true;
        } finally {
            entityManager.close();
        }
    }
}
