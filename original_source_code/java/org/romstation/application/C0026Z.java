package org.romstation.application;

import java.nio.file.Path;
import java.nio.file.Paths;

/* JADX INFO: renamed from: org.romstation.application.Z */
/* JADX INFO: compiled from: EmulatorProfile.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/Z.class */
public class C0026Z {

    /* JADX INFO: renamed from: a */
    private String f43a;

    /* JADX INFO: renamed from: b */
    private String f44b;

    /* JADX INFO: renamed from: c */
    private String f45c;

    /* JADX INFO: renamed from: d */
    private String f46d;

    /* JADX INFO: renamed from: e */
    private C0024X f47e;

    /* JADX INFO: renamed from: a */
    public String m79a() {
        return this.f43a;
    }

    /* JADX INFO: renamed from: a */
    public void m80a(String name) {
        this.f43a = name;
    }

    /* JADX INFO: renamed from: b */
    public String m81b() {
        return this.f44b;
    }

    /* JADX INFO: renamed from: b */
    public void m82b(String console) {
        this.f44b = console;
    }

    /* JADX INFO: renamed from: c */
    public Path m83c() {
        return this.f47e.getDirectory().relativize(Paths.get(this.f45c, new String[0]));
    }

    /* JADX INFO: renamed from: d */
    public String m84d() {
        return this.f45c;
    }

    /* JADX INFO: renamed from: c */
    public void m85c(String executable) {
        this.f45c = executable;
    }

    /* JADX INFO: renamed from: e */
    public String m86e() {
        return this.f46d;
    }

    /* JADX INFO: renamed from: d */
    public void m87d(String parameters) {
        this.f46d = parameters;
    }

    /* JADX INFO: renamed from: f */
    public C0024X m88f() {
        return this.f47e;
    }

    /* JADX INFO: renamed from: a */
    public void m89a(C0024X emulator) {
        this.f47e = emulator;
    }
}
