package org.romstation.application.netplay;

import org.romstation.application.EnumC0121bn;
import org.romstation.application.EnumC0129bv;

/* JADX INFO: renamed from: org.romstation.application.netplay.c */
/* JADX INFO: compiled from: ServerConfig.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/netplay/c.class */
public class C0215c {

    /* JADX INFO: renamed from: a */
    private EnumC0129bv f565a;

    /* JADX INFO: renamed from: b */
    private int f566b;

    /* JADX INFO: renamed from: c */
    private String f567c;

    /* JADX INFO: renamed from: d */
    private String f568d;

    /* JADX INFO: renamed from: e */
    private String f569e;

    /* JADX INFO: renamed from: f */
    private int f570f;

    /* JADX INFO: renamed from: g */
    private int f571g;

    /* JADX INFO: renamed from: h */
    private boolean f572h;

    /* JADX INFO: renamed from: i */
    private boolean f573i;

    /* JADX INFO: renamed from: j */
    private boolean f574j;

    /* JADX INFO: renamed from: k */
    private int f575k;

    /* JADX INFO: renamed from: l */
    private int f576l;

    /* JADX INFO: renamed from: m */
    private int f577m;

    /* JADX INFO: renamed from: n */
    private EnumC0121bn f578n;

    /* JADX INFO: renamed from: o */
    private int f579o;

    public C0215c(EnumC0129bv type, String title, String description, String password, int slots, boolean locked) {
        this.f565a = type;
        this.f567c = title;
        this.f568d = description;
        this.f569e = password;
        this.f570f = slots;
        this.f572h = locked;
    }

    /* JADX INFO: renamed from: a */
    public EnumC0129bv m925a() {
        return this.f565a;
    }

    /* JADX INFO: renamed from: a */
    public void m926a(EnumC0129bv type) {
        this.f565a = type;
    }

    /* JADX INFO: renamed from: b */
    public int m927b() {
        return this.f566b;
    }

    /* JADX INFO: renamed from: a */
    public void m928a(int masterLobbyID) {
        this.f566b = masterLobbyID;
    }

    /* JADX INFO: renamed from: c */
    public String m929c() {
        return this.f567c;
    }

    /* JADX INFO: renamed from: a */
    public void m930a(String title) {
        this.f567c = title;
    }

    /* JADX INFO: renamed from: d */
    public String m931d() {
        return this.f568d;
    }

    /* JADX INFO: renamed from: b */
    public void m932b(String description) {
        this.f568d = description;
    }

    /* JADX INFO: renamed from: e */
    public String m933e() {
        return this.f569e;
    }

    /* JADX INFO: renamed from: c */
    public void m934c(String password) {
        this.f569e = password;
    }

    /* JADX INFO: renamed from: f */
    public int m935f() {
        return this.f570f;
    }

    /* JADX INFO: renamed from: b */
    public void m936b(int slots) {
        this.f570f = slots;
    }

    /* JADX INFO: renamed from: g */
    public int m937g() {
        return this.f571g;
    }

    /* JADX INFO: renamed from: c */
    public void m938c(int gameFileId) {
        this.f571g = gameFileId;
    }

    /* JADX INFO: renamed from: h */
    public boolean m939h() {
        return this.f572h;
    }

    /* JADX INFO: renamed from: a */
    public void m940a(boolean locked) {
        this.f572h = locked;
    }

    /* JADX INFO: renamed from: i */
    public boolean m941i() {
        return this.f573i;
    }

    /* JADX INFO: renamed from: b */
    public void m942b(boolean live) {
        this.f573i = live;
    }

    /* JADX INFO: renamed from: j */
    public boolean m943j() {
        return this.f574j;
    }

    /* JADX INFO: renamed from: c */
    public void m944c(boolean instantiated) {
        this.f574j = instantiated;
    }

    /* JADX INFO: renamed from: k */
    public int m945k() {
        return this.f575k;
    }

    /* JADX INFO: renamed from: d */
    public void m946d(int language) {
        this.f575k = language;
    }

    /* JADX INFO: renamed from: l */
    public int m947l() {
        return this.f576l;
    }

    /* JADX INFO: renamed from: e */
    public void m948e(int region) {
        this.f576l = region;
    }

    /* JADX INFO: renamed from: m */
    public int m949m() {
        return this.f577m;
    }

    /* JADX INFO: renamed from: f */
    public void m950f(int frameRate) {
        this.f577m = frameRate;
    }

    /* JADX INFO: renamed from: n */
    public EnumC0121bn m951n() {
        return this.f578n;
    }

    /* JADX INFO: renamed from: a */
    public void m952a(EnumC0121bn resolution) {
        this.f578n = resolution;
    }

    /* JADX INFO: renamed from: o */
    public int m953o() {
        return this.f579o;
    }

    /* JADX INFO: renamed from: g */
    public void m954g(int bitRate) {
        this.f579o = bitRate;
    }
}
