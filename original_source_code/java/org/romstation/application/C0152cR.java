package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.cR */
/* JADX INFO: compiled from: CreateBrowserTabFromURL.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cR.class */
public class C0152cR implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final String f345a;

    /* JADX INFO: renamed from: b */
    private final boolean f346b;

    public C0152cR(String url) {
        this(url, false);
    }

    public C0152cR(String url, boolean selected) {
        this.f345a = url;
        this.f346b = selected;
    }

    /* JADX INFO: renamed from: a */
    public String m661a() {
        return this.f345a;
    }

    /* JADX INFO: renamed from: b */
    public boolean m662b() {
        return this.f346b;
    }
}
