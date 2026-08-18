package org.romstation.application;

import javax.persistence.criteria.JoinType;

/* JADX INFO: renamed from: org.romstation.application.j */
/* JADX INFO: compiled from: QueryJoin.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/j.class */
public class C0208j {

    /* JADX INFO: renamed from: a */
    private final String f551a;

    /* JADX INFO: renamed from: b */
    private final C0209k f552b;

    /* JADX INFO: renamed from: c */
    private final JoinType f553c;

    public C0208j(String alias, C0209k queryPath) {
        this(alias, queryPath, JoinType.INNER);
    }

    public C0208j(String alias, C0209k queryPath, JoinType joinType) {
        this.f551a = alias;
        this.f552b = queryPath;
        this.f553c = joinType;
    }

    /* JADX INFO: renamed from: a */
    public String m837a() {
        return this.f551a;
    }

    /* JADX INFO: renamed from: b */
    public C0209k m838b() {
        return this.f552b;
    }

    /* JADX INFO: renamed from: c */
    public JoinType m839c() {
        return this.f553c;
    }
}
