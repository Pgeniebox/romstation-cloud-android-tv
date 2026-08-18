package org.romstation.application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/* JADX INFO: renamed from: org.romstation.application.g */
/* JADX INFO: compiled from: QueryData.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/g.class */
public class C0204g<T> {

    /* JADX INFO: renamed from: a */
    private final ObjectProperty<T> f545a;

    /* JADX INFO: renamed from: b */
    private final Class<T> f546b;

    public C0204g(Class<T> type) {
        this(null, type);
    }

    public C0204g(T value, Class<T> type) {
        this.f545a = new SimpleObjectProperty(value);
        this.f546b = type;
    }

    /* JADX INFO: renamed from: a */
    public ObjectProperty<T> m819a() {
        return this.f545a;
    }

    /* JADX INFO: renamed from: b */
    public T m820b() {
        return (T) this.f545a.get();
    }

    /* JADX INFO: renamed from: a */
    public void m821a(T value) {
        this.f545a.set(value);
    }

    /* JADX INFO: renamed from: c */
    public Class<T> m822c() {
        return this.f546b;
    }
}
