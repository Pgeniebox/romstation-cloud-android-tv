package org.romstation.application.script.api.database.adapter;

import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.AbstractC0189a;

/* JADX INFO: renamed from: org.romstation.application.script.api.database.adapter.a */
/* JADX INFO: compiled from: EntityAdapter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/script/api/database/adapter/a.class */
public abstract class AbstractC0228a<T extends AbstractC0189a> {
    public Consumer<T> on_pre_persist;
    public Consumer<T> on_post_persist;
    public Consumer<T> on_pre_update;
    public Consumer<T> on_post_update;
    public Consumer<T> on_pre_remove;
    public Consumer<T> on_post_remove;

    /* JADX INFO: renamed from: a */
    private final Class<T> f598a;

    public abstract T create();

    public AbstractC0228a(Class<T> type) {
        this.f598a = type;
    }

    public T find(int id) {
        EntityManager entityManager = C0081b.m309c();
        T t = (T) entityManager.find(this.f598a, Integer.valueOf(id));
        entityManager.close();
        return t;
    }

    public Object[] query(String sql, Object... parameters) {
        EntityManager entityManager = C0081b.m309c();
        Query nativeQuery = entityManager.createNativeQuery(sql, this.f598a);
        for (int i = 0; i < parameters.length; i++) {
            nativeQuery.setParameter(i + 1, parameters[i]);
        }
        Object[] entities = nativeQuery.getResultList().toArray();
        entityManager.close();
        return entities;
    }

    public void persist(T entity) {
        EntityManager entityManager = C0081b.m309c();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void merge(T entity) {
        EntityManager entityManager = C0081b.m309c();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remove(T entity) {
        EntityManager entityManager = C0081b.m309c();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(entity));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
