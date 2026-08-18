package org.romstation.application;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.romstation.application.script.api.database.adapter.EmulatorAdapter;
import org.romstation.application.script.api.database.adapter.EmulatorFileAdapter;
import org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter;
import org.romstation.application.script.api.database.adapter.GameAdapter;
import org.romstation.application.script.api.database.adapter.GameFileAdapter;
import org.romstation.application.script.api.database.adapter.GameProfileAdapter;

/* JADX INFO: renamed from: org.romstation.application.V */
/* JADX INFO: compiled from: Database.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/V.class */
public class C0022V {
    public final GameAdapter game = new GameAdapter();
    public final GameFileAdapter game_file = new GameFileAdapter();
    public final GameProfileAdapter game_profile = new GameProfileAdapter();
    public final EmulatorAdapter emulator = new EmulatorAdapter();
    public final EmulatorFileAdapter emulator_file = new EmulatorFileAdapter();
    public final EmulatorProfileAdapter emulator_profile = new EmulatorProfileAdapter();

    public boolean isInitialized() {
        return C0081b.m308b();
    }

    public Object[] query(String query, Object... parameters) {
        EntityManager entityManager = C0081b.m309c();
        Query nativeQuery = entityManager.createNativeQuery(query);
        for (int i = 0; i < parameters.length; i++) {
            nativeQuery.setParameter(i + 1, parameters[i]);
        }
        Object[] objects = nativeQuery.getResultList().toArray();
        entityManager.close();
        return objects;
    }

    public int update(String query, Object... parameters) {
        EntityManager entityManager = C0081b.m309c();
        entityManager.getTransaction().begin();
        Query nativeQuery = entityManager.createNativeQuery(query);
        for (int i = 0; i < parameters.length; i++) {
            nativeQuery.setParameter(i + 1, parameters[i]);
        }
        int results = nativeQuery.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        return results;
    }
}
