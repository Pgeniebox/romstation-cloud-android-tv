package org.romstation.application;

import com.google.gson.JsonObject;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;

/* JADX INFO: renamed from: org.romstation.application.ag */
/* JADX INFO: compiled from: Software.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ag.class */
public class C0060ag {

    /* JADX INFO: renamed from: a */
    private static C0060ag f127a;

    /* JADX INFO: renamed from: b */
    private int f128b;

    /* JADX INFO: renamed from: c */
    private String f129c;

    /* JADX INFO: renamed from: d */
    private String f130d;

    /* JADX INFO: renamed from: a */
    public static synchronized C0060ag m228a() {
        if (f127a == null) {
            f127a = new C0060ag();
        }
        return f127a;
    }

    /* JADX INFO: renamed from: b */
    public boolean m229b() {
        return this.f128b != 0;
    }

    /* JADX INFO: renamed from: c */
    public int m230c() {
        return this.f128b;
    }

    /* JADX INFO: renamed from: a */
    public void m231a(int id) {
        this.f128b = id;
    }

    /* JADX INFO: renamed from: d */
    public String m232d() {
        if (this.f129c == null) {
            if (C0004E.m10c() == EnumC0003D.WINDOWS) {
                try {
                    String host = C0009J.m28a();
                    InetAddress inetAddress = InetAddress.getByName(host);
                    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
                    this.f129c = C0004E.m16a(networkInterface);
                    if (this.f129c != null && this.f129c.equals("00-00-00-00-00-00")) {
                        this.f129c = null;
                    }
                } catch (SocketException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                } catch (UnknownHostException exception2) {
                    RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                }
            }
            if (this.f129c == null) {
                this.f129c = C0004E.m13f();
            }
        }
        return this.f129c;
    }

    /* JADX INFO: renamed from: a */
    public void m233a(String uid) {
        this.f129c = uid;
    }

    /* JADX INFO: renamed from: e */
    public String m234e() {
        return this.f130d;
    }

    /* JADX INFO: renamed from: b */
    public void m235b(String session) {
        this.f130d = session;
    }

    /* JADX INFO: renamed from: f */
    public String m236f() {
        JsonObject json = new JsonObject();
        json.addProperty("soft_id", Integer.valueOf(m230c()));
        json.addProperty("soft_uid", C0023W.m67a(m232d().getBytes()));
        json.addProperty("soft_raw_uid", m232d());
        json.addProperty("phpsessid", m234e());
        json.addProperty("member_id", Integer.valueOf(C0058ae.m195a().m197c()));
        json.addProperty("member_session", C0058ae.m195a().m217q());
        int softwareID = Integer.parseInt(RomStation.m43c().getProperty("application.id", "0"));
        if (softwareID != 0) {
            json.addProperty("last_soft_id", Integer.valueOf(softwareID));
        }
        return C0023W.m65a(json.toString());
    }
}
