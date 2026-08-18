package org.romstation.application.task;

import java.io.File;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import org.romstation.application.C0081b;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.database.entity.System;

/* JADX INFO: renamed from: org.romstation.application.task.l */
/* JADX INFO: compiled from: GameBulkImportTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/l.class */
public class C0244l extends Task<List<Game>> {

    /* JADX INFO: renamed from: a */
    private final System f659a;

    /* JADX INFO: renamed from: b */
    private final List<File> f660b;

    /* JADX INFO: renamed from: c */
    private final List<Game> f661c = new LinkedList();

    public C0244l(System system, List<File> files) {
        this.f659a = system;
        this.f660b = files;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("gameBulkImportTask.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<Game> call() {
        EntityManager entityManager = C0081b.m309c();
        try {
            for (File file : this.f660b) {
                updateMessage(MessageFormat.format(RomStation.m44d().getString("gameBulkImportTask.message"), file.getName()));
                if (isCancelled()) {
                    break;
                }
                Game game = new Game();
                game.setTitle(file.getName());
                game.setSystem(this.f659a);
                game.setDirectory(file.getParent());
                GameFile gameFile = new GameFile();
                gameFile.setName(file.getName());
                gameFile.setDirectory(file.getParent());
                gameFile.setGame(game);
                GameProfile gameProfile = new GameProfile();
                gameProfile.setName(file.getName());
                gameProfile.setPath(file.toString());
                gameProfile.setGameFile(gameFile);
                game.getFiles().add(gameFile);
                gameFile.getProfiles().add(gameProfile);
                entityManager.getTransaction().begin();
                entityManager.merge(game);
                entityManager.getTransaction().commit();
                this.f661c.add(game);
                updateValue(this.f661c);
                updateProgress(this.f661c.size(), this.f660b.size());
            }
            entityManager.close();
            return this.f661c;
        } catch (Throwable th) {
            entityManager.close();
            throw th;
        }
    }
}
