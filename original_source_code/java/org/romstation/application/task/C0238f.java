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
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.p000io.C0207a;

/* JADX INFO: renamed from: org.romstation.application.task.f */
/* JADX INFO: compiled from: EmulatorDeleteTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/f.class */
public class C0238f extends Task<List<Emulator>> {

    /* JADX INFO: renamed from: a */
    private final List<Emulator> f623a;

    /* JADX INFO: renamed from: b */
    private final List<Emulator> f624b = new LinkedList();

    public C0238f(List<Emulator> emulators) {
        this.f623a = emulators;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("emulator.delete.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<Emulator> call() throws IOException {
        EntityManager entityManager = C0081b.m309c();
        try {
            for (Emulator emulator : this.f623a) {
                if (!isCancelled()) {
                    String message = RomStation.m44d().getString("emulator.delete.task.message");
                    if (emulator.getName() != null) {
                        message = MessageFormat.format(message, emulator.getName());
                    }
                    updateMessage(message);
                    try {
                        entityManager.getTransaction().begin();
                        Emulator entity = (Emulator) entityManager.merge(emulator);
                        entityManager.remove(entity);
                        if (emulator.isManaged() && emulator.getDirectory() != null) {
                            Path path = Paths.get(emulator.getDirectory(), new String[0]);
                            if (Files.exists(path, new LinkOption[0])) {
                                C0207a.m828a(path);
                            }
                        }
                        entityManager.getTransaction().commit();
                        this.f624b.add(emulator);
                        updateValue(this.f624b);
                        updateProgress(this.f624b.size(), this.f623a.size());
                    } catch (IOException exception) {
                        entityManager.getTransaction().rollback();
                        throw exception;
                    }
                }
            }
            entityManager.close();
            return this.f624b;
        } catch (Throwable th) {
            entityManager.close();
            throw th;
        }
    }
}
