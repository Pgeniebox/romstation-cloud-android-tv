package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.k */
/* JADX INFO: compiled from: QueryPath.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/k.class */
public class C0209k {

    /* JADX INFO: renamed from: a */
    public static final String f554a = "root";

    /* JADX INFO: renamed from: b */
    private final String f555b;

    /* JADX INFO: renamed from: c */
    private final String f556c;

    public C0209k(String parent) {
        this(parent, null);
    }

    public C0209k(String parent, String attribute) {
        this.f555b = parent;
        this.f556c = attribute;
    }

    /* JADX INFO: renamed from: a */
    public String m840a() {
        return this.f555b;
    }

    /* JADX INFO: renamed from: b */
    public String m841b() {
        return this.f556c;
    }

    public String toString() {
        return this.f556c == null ? this.f555b : this.f555b + "/" + this.f556c;
    }
}
