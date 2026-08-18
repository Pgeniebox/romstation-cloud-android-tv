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
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.p000io.C0207a;

/* JADX INFO: renamed from: org.romstation.application.task.n */
/* JADX INFO: compiled from: GameFileDeleteTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/n.class */
public class C0246n extends Task<List<GameFile>> {

    /* JADX INFO: renamed from: a */
    private final List<GameFile> f664a;

    /* JADX INFO: renamed from: b */
    private final List<GameFile> f665b = new LinkedList();

    public C0246n(List<GameFile> gameFiles) {
        this.f664a = gameFiles;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("gameFile.delete.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<GameFile> call() throws IOException {
        EntityManager entityManager = C0081b.m309c();
        try {
            for (GameFile gameFile : this.f664a) {
                if (!isCancelled()) {
                    updateMessage(MessageFormat.format(RomStation.m44d().getString("gameFile.delete.task.message"), gameFile.getName()));
                    try {
                        entityManager.getTransaction().begin();
                        entityManager.createQuery("delete from GameFile where id = :id").setParameter("id", gameFile.getId()).executeUpdate();
                        if (gameFile.isManaged() && gameFile.getDirectory() != null) {
                            Path path = Paths.get(gameFile.getDirectory(), new String[0]);
                            if (Files.exists(path, new LinkOption[0])) {
                                C0207a.m828a(path);
                            }
                        }
                        entityManager.getTransaction().commit();
                        this.f665b.add(gameFile);
                        updateValue(this.f665b);
                        updateProgress(this.f665b.size(), this.f664a.size());
                    } catch (IOException exception) {
                        entityManager.getTransaction().rollback();
                        throw exception;
                    }
                }
            }
            entityManager.close();
            return this.f665b;
        } catch (Throwable th) {
            entityManager.close();
            throw th;
        }
    }
}
