package org.romstation.application.task;

import com.google.gson.JsonObject;

/* JADX INFO: renamed from: org.romstation.application.task.q */
/* JADX INFO: compiled from: GameFileUploadContext.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/q.class */
public class C0249q {

    /* JADX INFO: renamed from: a */
    private final int f681a;

    /* JADX INFO: renamed from: b */
    private final int f682b;

    /* JADX INFO: renamed from: c */
    private final int f683c;

    /* JADX INFO: renamed from: d */
    private final String f684d;

    /* JADX INFO: renamed from: e */
    private final JsonObject f685e;

    public C0249q(int gameID, int systemID, String checksum, JsonObject form) {
        this(0, gameID, systemID, checksum, form);
    }

    public C0249q(int uploadID, int gameID, int systemID, String checksum, JsonObject form) {
        this.f681a = uploadID;
        this.f682b = gameID;
        this.f683c = systemID;
        this.f684d = checksum;
        this.f685e = form;
    }

    /* JADX INFO: renamed from: a */
    public int m1131a() {
        return this.f681a;
    }

    /* JADX INFO: renamed from: b */
    public int m1132b() {
        return this.f682b;
    }

    /* JADX INFO: renamed from: c */
    public int m1133c() {
        return this.f683c;
    }

    /* JADX INFO: renamed from: d */
    public String m1134d() {
        return this.f684d;
    }

    /* JADX INFO: renamed from: e */
    public JsonObject m1135e() {
        return this.f685e;
    }
}
