package org.romstation.application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/* JADX INFO: renamed from: org.romstation.application.m */
/* JADX INFO: compiled from: DataFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/m.class */
public abstract class AbstractC0211m<T> extends AbstractC0280w {

    /* JADX INFO: renamed from: a */
    private final ObjectProperty<C0204g<T>> f558a;

    public AbstractC0211m(String name, C0205h expression, EnumC0225q operator, C0204g<T> data) {
        super(name, expression, operator);
        this.f558a = new SimpleObjectProperty(data);
    }

    /* JADX INFO: renamed from: b */
    public ObjectProperty<C0204g<T>> m844b() {
        return this.f558a;
    }

    /* JADX INFO: renamed from: c */
    public C0204g<T> m845c() {
        return (C0204g) this.f558a.get();
    }

    /* JADX INFO: renamed from: a */
    public void m846a(C0204g<T> data) {
        this.f558a.set(data);
    }
}
