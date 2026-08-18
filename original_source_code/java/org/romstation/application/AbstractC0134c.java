package org.romstation.application;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/* JADX INFO: renamed from: org.romstation.application.c */
/* JADX INFO: compiled from: Context.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/c.class */
public abstract class AbstractC0134c {

    /* JADX INFO: renamed from: a */
    protected EntityManager f323a;

    /* JADX INFO: renamed from: b */
    protected CriteriaBuilder f324b;

    /* JADX INFO: renamed from: c */
    protected Root<?> f325c;

    /* JADX INFO: renamed from: d */
    private final Map<String, Path<?>> f326d = new HashMap();

    /* JADX INFO: renamed from: a */
    public EntityManager m639a() {
        return this.f323a;
    }

    /* JADX INFO: renamed from: b */
    public CriteriaBuilder m640b() {
        return this.f324b;
    }

    /* JADX INFO: renamed from: c */
    public Root<?> m641c() {
        return this.f325c;
    }

    /* JADX INFO: renamed from: d */
    public Map<String, Path<?>> m642d() {
        return this.f326d;
    }
}
