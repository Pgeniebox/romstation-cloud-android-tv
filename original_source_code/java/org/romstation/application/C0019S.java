package org.romstation.application;

import org.romstation.application.netplay.C0214b;

/* JADX INFO: renamed from: org.romstation.application.S */
/* JADX INFO: compiled from: Netplay.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/S.class */
public class C0019S {
    public boolean isConnected() {
        return C0214b.m850b();
    }

    public String getHostIP() {
        if (isConnected()) {
            return C0214b.m852d();
        }
        return null;
    }
}
