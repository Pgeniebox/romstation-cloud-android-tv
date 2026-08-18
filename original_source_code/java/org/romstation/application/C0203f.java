package org.romstation.application;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/* JADX INFO: renamed from: org.romstation.application.f */
/* JADX INFO: compiled from: QueryContext.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/f.class */
public class C0203f<T> extends AbstractC0134c {

    /* JADX INFO: renamed from: e */
    private final C0202e f543e;

    /* JADX INFO: renamed from: d */
    protected CriteriaQuery<T> f544d;

    public C0203f(C0202e<?> queryBuilder, EntityManager entityManager, Class<T> type) {
        this.f323a = entityManager;
        this.f543e = queryBuilder;
        this.f324b = entityManager.getCriteriaBuilder();
        this.f544d = this.f324b.createQuery(type);
        this.f325c = this.f544d.from(queryBuilder.m811a());
        m642d().put(C0209k.f554a, this.f325c);
    }

    /* JADX INFO: renamed from: e */
    public C0202e m817e() {
        return this.f543e;
    }

    /* JADX INFO: renamed from: f */
    public CriteriaQuery<T> m818f() {
        return this.f544d;
    }
}
