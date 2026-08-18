package org.romstation.application.task;

import com.google.gson.JsonObject;

/* JADX INFO: renamed from: org.romstation.application.task.o */
/* JADX INFO: compiled from: GameFileDownloadContext.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/o.class */
public class C0247o {

    /* JADX INFO: renamed from: a */
    private JsonObject f666a;

    /* JADX INFO: renamed from: b */
    private JsonObject f667b;

    /* JADX INFO: renamed from: c */
    private JsonObject f668c;

    public C0247o(int gameFileID) {
        throw new UnsupportedOperationException();
    }

    public C0247o(JsonObject jsonObject) {
        throw new UnsupportedOperationException();
    }

    public C0247o(JsonObject game, JsonObject gameFile) {
        this.f667b = game;
        this.f668c = gameFile;
    }

    /* JADX INFO: renamed from: a */
    public JsonObject m1067a() {
        return this.f666a;
    }

    /* JADX INFO: renamed from: a */
    public void m1068a(JsonObject server) {
        this.f666a = server;
    }

    /* JADX INFO: renamed from: b */
    public JsonObject m1069b() {
        return this.f667b;
    }

    /* JADX INFO: renamed from: b */
    public void m1070b(JsonObject game) {
        this.f667b = game;
    }

    /* JADX INFO: renamed from: c */
    public JsonObject m1071c() {
        return this.f668c;
    }

    /* JADX INFO: renamed from: c */
    public void m1072c(JsonObject gameFile) {
        this.f668c = gameFile;
    }
}
