package org.romstation.application.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import org.romstation.application.C0081b;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Game;
import org.romstation.application.p000io.C0207a;

/* JADX INFO: renamed from: org.romstation.application.task.m */
/* JADX INFO: compiled from: GameDeleteTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/m.class */
public class C0245m extends Task<List<Game>> {

    /* JADX INFO: renamed from: a */
    private final List<Game> f662a;

    /* JADX INFO: renamed from: b */
    private final List<Game> f663b = new LinkedList();

    public C0245m(List<Game> games) {
        this.f662a = games;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("game.delete.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<Game> call() throws IOException {
        EntityManager entityManager = C0081b.m309c();
        try {
            for (Game game : this.f662a) {
                if (!isCancelled()) {
                    String message = RomStation.m44d().getString("game.delete.task.message");
                    if (game.getTitle() != null) {
                        message = MessageFormat.format(message, game.getTitle());
                    }
                    updateMessage(message);
                    try {
                        entityManager.getTransaction().begin();
                        Game entity = (Game) entityManager.merge(game);
                        entityManager.remove(entity);
                        if (game.isManaged() && game.getDirectory() != null) {
                            Path path = Paths.get(game.getDirectory(), new String[0]);
                            if (Files.exists(path, new LinkOption[0])) {
                                C0207a.m828a(path);
                            }
                        }
                        entityManager.getTransaction().commit();
                        this.f663b.add(game);
                        updateValue(this.f663b);
                        updateProgress(this.f663b.size(), this.f662a.size());
                    } catch (IOException exception) {
                        entityManager.getTransaction().rollback();
                        throw exception;
                    }
                }
            }
            entityManager.close();
            return this.f663b;
        } catch (Throwable th) {
            entityManager.close();
            throw th;
        }
    }
}
