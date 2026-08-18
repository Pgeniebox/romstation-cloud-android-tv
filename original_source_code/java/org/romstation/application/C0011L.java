package org.romstation.application;

import java.io.Serializable;

/* JADX INFO: renamed from: org.romstation.application.L */
/* JADX INFO: compiled from: Device.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/L.class */
public class C0011L implements Serializable {

    /* JADX INFO: renamed from: a */
    private String f10a;

    /* JADX INFO: renamed from: b */
    private String f11b;

    public C0011L(String id, String name) {
        this.f10a = id;
        this.f11b = name;
    }

    /* JADX INFO: renamed from: a */
    public String m34a() {
        return this.f10a;
    }

    /* JADX INFO: renamed from: a */
    public void m35a(String id) {
        this.f10a = id;
    }

    /* JADX INFO: renamed from: b */
    public String m36b() {
        return this.f11b;
    }

    /* JADX INFO: renamed from: b */
    public void m37b(String name) {
        this.f11b = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        C0011L device = (C0011L) o;
        if (this.f10a.equals(device.f10a)) {
            return this.f11b.equals(device.f11b);
        }
        return false;
    }

    public int hashCode() {
        int result = this.f10a.hashCode();
        return (31 * result) + this.f11b.hashCode();
    }
}
