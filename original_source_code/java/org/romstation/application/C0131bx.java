package org.romstation.application;

import com.google.gson.JsonObject;
import org.romstation.application.network.C0217b;

/* JADX INFO: renamed from: org.romstation.application.bx */
/* JADX INFO: compiled from: Member.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bx.class */
public class C0131bx {

    /* JADX INFO: renamed from: a */
    private final int f308a;

    /* JADX INFO: renamed from: b */
    private final int f309b;

    /* JADX INFO: renamed from: c */
    private final String f310c;

    /* JADX INFO: renamed from: d */
    private final String f311d;

    /* JADX INFO: renamed from: e */
    private final String f312e;

    public C0131bx(int softID, int memberID, String name, String profile, String imageURL) {
        this.f308a = softID;
        this.f309b = memberID;
        this.f310c = name;
        this.f311d = profile;
        this.f312e = imageURL;
    }

    public C0131bx(JsonObject object) {
        this.f308a = object.get("sid").getAsInt();
        this.f309b = object.get("member_id").getAsInt();
        this.f310c = object.get("name").getAsString();
        this.f311d = m628a() ? null : C0217b.m961b() + object.get("profile").getAsString();
        this.f312e = C0217b.m961b() + object.get("avatar").getAsString();
    }

    /* JADX INFO: renamed from: a */
    public boolean m628a() {
        return this.f309b == 0;
    }

    /* JADX INFO: renamed from: b */
    public int m629b() {
        return this.f308a;
    }

    /* JADX INFO: renamed from: c */
    public int m630c() {
        return this.f309b;
    }

    /* JADX INFO: renamed from: d */
    public String m631d() {
        return this.f310c;
    }

    /* JADX INFO: renamed from: e */
    public String m632e() {
        return this.f311d;
    }

    /* JADX INFO: renamed from: f */
    public String m633f() {
        return this.f312e;
    }
}
