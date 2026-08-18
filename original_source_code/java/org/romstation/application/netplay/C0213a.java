package org.romstation.application.netplay;

import org.romstation.application.vpn.C0275a;

/* JADX INFO: renamed from: org.romstation.application.netplay.a */
/* JADX INFO: compiled from: NetPlayCredential.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/netplay/a.class */
public class C0213a {

    /* JADX INFO: renamed from: a */
    private final int f560a;

    /* JADX INFO: renamed from: b */
    private final C0275a f561b;

    public C0213a(int id) {
        this(id, null);
    }

    public C0213a(int id, C0275a vpnConnection) {
        this.f560a = id;
        this.f561b = vpnConnection;
    }

    /* JADX INFO: renamed from: a */
    public int m847a() {
        return this.f560a;
    }

    /* JADX INFO: renamed from: b */
    public C0275a m848b() {
        return this.f561b;
    }
}
