package org.romstation.application.task;

import com.google.gson.JsonObject;

/* JADX INFO: renamed from: org.romstation.application.task.s */
/* JADX INFO: compiled from: GameUploadContext.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/s.class */
public class C0251s {

    /* JADX INFO: renamed from: a */
    private final int f694a;

    /* JADX INFO: renamed from: b */
    private final String f695b;

    /* JADX INFO: renamed from: c */
    private final JsonObject f696c;

    public C0251s(String checksum, JsonObject form) {
        this(0, checksum, form);
    }

    public C0251s(int uploadID, String checksum, JsonObject form) {
        this.f694a = uploadID;
        this.f695b = checksum;
        this.f696c = form;
    }

    /* JADX INFO: renamed from: a */
    public int m1147a() {
        return this.f694a;
    }

    /* JADX INFO: renamed from: b */
    public String m1148b() {
        return this.f695b;
    }

    /* JADX INFO: renamed from: c */
    public JsonObject m1149c() {
        return this.f696c;
    }
}
