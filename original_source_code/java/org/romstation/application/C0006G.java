package org.romstation.application;

import com.google.gson.JsonObject;

/* JADX INFO: renamed from: org.romstation.application.G */
/* JADX INFO: compiled from: SystemProfilerGroup.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/G.class */
public class C0006G {

    /* JADX INFO: renamed from: a */
    private final int f6a;

    /* JADX INFO: renamed from: b */
    private final C0006G f7b;

    /* JADX INFO: renamed from: c */
    private final JsonObject f8c = new JsonObject();

    public C0006G(int level, C0006G parent) {
        this.f6a = level;
        this.f7b = parent;
    }

    /* JADX INFO: renamed from: a */
    public int m18a() {
        return this.f6a;
    }

    /* JADX INFO: renamed from: b */
    public C0006G m19b() {
        return this.f7b;
    }

    /* JADX INFO: renamed from: c */
    public JsonObject m20c() {
        return this.f8c;
    }

    /* JADX INFO: renamed from: a */
    public void m21a(String key, String value) {
        this.f8c.addProperty(key, value);
    }

    /* JADX INFO: renamed from: a */
    public C0006G m22a(String key, C0006G group) {
        this.f8c.add(key, group.f8c);
        return group;
    }
}
