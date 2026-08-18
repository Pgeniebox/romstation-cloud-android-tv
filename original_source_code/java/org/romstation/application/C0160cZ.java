package org.romstation.application;

import java.util.LinkedList;
import java.util.List;

/* JADX INFO: renamed from: org.romstation.application.cZ */
/* JADX INFO: compiled from: Config.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cZ.class */
public class C0160cZ {

    /* JADX INFO: renamed from: a */
    private String f376a;

    /* JADX INFO: renamed from: b */
    private String f377b;

    /* JADX INFO: renamed from: c */
    private C0190db f378c;

    /* JADX INFO: renamed from: d */
    private C0190db f379d;

    /* JADX INFO: renamed from: e */
    private final List<C0190db> f380e;

    public C0160cZ(String name) {
        this(name, null);
    }

    public C0160cZ(String name, String image) {
        this.f380e = new LinkedList();
        this.f376a = name;
        this.f377b = image;
    }

    /* JADX INFO: renamed from: a */
    public String m721a() {
        return this.f376a;
    }

    /* JADX INFO: renamed from: a */
    public void m722a(String name) {
        this.f376a = name;
    }

    /* JADX INFO: renamed from: b */
    public String m723b() {
        return this.f377b;
    }

    /* JADX INFO: renamed from: b */
    public void m724b(String image) {
        this.f377b = image;
    }

    /* JADX INFO: renamed from: c */
    public C0190db m725c() {
        return this.f378c;
    }

    /* JADX INFO: renamed from: a */
    public void m726a(C0190db template) {
        this.f378c = template;
    }

    /* JADX INFO: renamed from: d */
    public C0190db m727d() {
        return this.f379d;
    }

    /* JADX INFO: renamed from: b */
    public void m728b(C0190db currentProfile) {
        this.f379d = currentProfile;
    }

    /* JADX INFO: renamed from: e */
    public List<C0190db> m729e() {
        return this.f380e;
    }
}
