package org.romstation.application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.p */
/* JADX INFO: compiled from: Filter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/p.class */
public abstract class AbstractC0224p {

    /* JADX INFO: renamed from: a */
    private final BooleanProperty f593a;

    /* JADX INFO: renamed from: a */
    public abstract Predicate mo843a(AbstractC0134c abstractC0134c);

    AbstractC0224p() {
        this(true);
    }

    AbstractC0224p(boolean enable) {
        this.f593a = new SimpleBooleanProperty(enable);
    }

    /* JADX INFO: renamed from: d */
    public BooleanProperty m978d() {
        return this.f593a;
    }

    /* JADX INFO: renamed from: e */
    public boolean m979e() {
        return this.f593a.get();
    }

    /* JADX INFO: renamed from: a */
    public void m980a(boolean enable) {
        this.f593a.set(enable);
    }
}
