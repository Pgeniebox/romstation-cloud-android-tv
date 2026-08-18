package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.dk */
/* JADX INFO: compiled from: Input.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dk.class */
public abstract class AbstractC0199dk {

    /* JADX INFO: renamed from: a */
    private final int f535a;

    /* JADX INFO: renamed from: b */
    private final String f536b;

    /* JADX INFO: renamed from: c */
    private C0159cY f537c;

    /* JADX INFO: renamed from: a */
    public abstract float mo802a(Float f);

    public AbstractC0199dk(int id, String name) {
        this.f535a = id;
        this.f536b = name;
    }

    /* JADX INFO: renamed from: c */
    public int m805c() {
        return this.f535a;
    }

    /* JADX INFO: renamed from: d */
    public String m806d() {
        return this.f536b;
    }

    /* JADX INFO: renamed from: e */
    public C0159cY m807e() {
        return this.f537c;
    }

    /* JADX INFO: renamed from: a */
    public void m808a(C0159cY binding) {
        this.f537c = binding;
    }

    public String toString() {
        return m806d();
    }
}
