package org.romstation.application;

import com.google.gson.JsonObject;
import org.romstation.application.network.C0217b;

/* JADX INFO: renamed from: org.romstation.application.bC */
/* JADX INFO: compiled from: Player.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bC.class */
public class C0084bC extends C0131bx {

    /* JADX INFO: renamed from: a */
    private final int f166a;

    /* JADX INFO: renamed from: b */
    private final boolean f167b;

    /* JADX INFO: renamed from: c */
    private final String f168c;

    /* JADX INFO: renamed from: d */
    private final String f169d;

    /* JADX INFO: renamed from: e */
    private final String f170e;

    /* JADX INFO: renamed from: f */
    private C0188da f171f;

    /* JADX INFO: renamed from: g */
    private int f172g;

    public C0084bC(JsonObject object) {
        super(object);
        this.f166a = object.get("id").getAsInt();
        this.f167b = object.get("is_host").getAsBoolean();
        this.f168c = object.get("ip_vpn").getAsString();
        if (object.has("controller") && object.getAsJsonObject("controller").get("port").getAsInt() != 0) {
            this.f171f = new C0188da(object.getAsJsonObject("controller").get("id").getAsByte(), object.getAsJsonObject("controller").get("key").getAsByte());
            this.f172g = object.getAsJsonObject("controller").get("port").getAsInt();
        }
        JsonObject countryObject = object.getAsJsonObject("country");
        this.f169d = C0217b.m961b() + countryObject.get("url").getAsString();
        this.f170e = countryObject.get("name").getAsString();
    }

    /* JADX INFO: renamed from: g */
    public int m319g() {
        return this.f166a;
    }

    /* JADX INFO: renamed from: h */
    public boolean m320h() {
        return this.f167b;
    }

    /* JADX INFO: renamed from: i */
    public String m321i() {
        return this.f168c;
    }

    /* JADX INFO: renamed from: j */
    public C0188da m322j() {
        return this.f171f;
    }

    /* JADX INFO: renamed from: a */
    public void m323a(C0188da controllerCredential) {
        this.f171f = controllerCredential;
    }

    /* JADX INFO: renamed from: k */
    public int m324k() {
        return this.f172g;
    }

    /* JADX INFO: renamed from: a */
    public void m325a(int controllerPort) {
        this.f172g = controllerPort;
    }

    /* JADX INFO: renamed from: l */
    public String m326l() {
        return this.f169d;
    }

    /* JADX INFO: renamed from: m */
    public String m327m() {
        return this.f170e;
    }
}
