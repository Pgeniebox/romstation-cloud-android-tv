package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.Game;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/Database.class */
@JsAccessible
public class Database {
    public EntityManager getEntityManager() {
        return C0081b.m309c();
    }

    public Game findGameByRid(int rid) {
        EntityManager entityManager = C0081b.m309c();
        try {
            return (Game) entityManager.createNamedQuery(Game.f440b, Game.class).setParameter("rid", Integer.valueOf(rid)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public boolean gameFileExists(int gameFileID) {
        EntityManager entityManager = C0081b.m309c();
        try {
            entityManager.createQuery("select file.id from GameFile file where file.rid = :rid", Integer.class).setParameter("rid", Integer.valueOf(gameFileID)).getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } finally {
            entityManager.close();
        }
    }

    public boolean emulatorFileExists(int emulatorFileID) {
        EntityManager entityManager = C0081b.m309c();
        try {
            entityManager.createQuery("select file.id from EmulatorFile file where file.rid = :rid", Integer.class).setParameter("rid", Integer.valueOf(emulatorFileID)).getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } finally {
            entityManager.close();
        }
    }

    public void update(Object entity) throws IllegalArgumentException {
        EntityManager entityManager = C0081b.m309c();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();
    }
}
