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
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.p000io.C0207a;

/* JADX INFO: renamed from: org.romstation.application.task.g */
/* JADX INFO: compiled from: EmulatorFileDeleteTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/g.class */
public class C0239g extends Task<List<EmulatorFile>> {

    /* JADX INFO: renamed from: a */
    private final List<EmulatorFile> f625a;

    /* JADX INFO: renamed from: b */
    private final List<EmulatorFile> f626b = new LinkedList();

    public C0239g(List<EmulatorFile> emulatorFiles) {
        this.f625a = emulatorFiles;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("emulatorFile.delete.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<EmulatorFile> call() throws IOException {
        EntityManager entityManager = C0081b.m309c();
        try {
            for (EmulatorFile emulatorFile : this.f625a) {
                if (!isCancelled()) {
                    updateMessage(MessageFormat.format(RomStation.m44d().getString("emulatorFile.delete.task.message"), emulatorFile.getName()));
                    try {
                        entityManager.getTransaction().begin();
                        entityManager.createQuery("delete from EmulatorFile where id = :id").setParameter("id", emulatorFile.getId()).executeUpdate();
                        if (emulatorFile.isManaged() && emulatorFile.getDirectory() != null) {
                            Path path = Paths.get(emulatorFile.getDirectory(), new String[0]);
                            if (Files.exists(path, new LinkOption[0])) {
                                C0207a.m828a(path);
                            }
                        }
                        entityManager.getTransaction().commit();
                        this.f626b.add(emulatorFile);
                        updateValue(this.f626b);
                        updateProgress(this.f626b.size(), this.f625a.size());
                    } catch (IOException exception) {
                        entityManager.getTransaction().rollback();
                        throw exception;
                    }
                }
            }
            entityManager.close();
            return this.f626b;
        } catch (Throwable th) {
            entityManager.close();
            throw th;
        }
    }
}
