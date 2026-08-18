package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.da */
/* JADX INFO: compiled from: Credential.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/da.class */
public class C0188da {

    /* JADX INFO: renamed from: a */
    private final byte f405a;

    /* JADX INFO: renamed from: b */
    private final byte f406b;

    /* JADX INFO: renamed from: c */
    private short f407c;

    public C0188da(byte id, byte key) {
        this.f405a = id;
        this.f406b = key;
    }

    /* JADX INFO: renamed from: a */
    public byte m760a() {
        return this.f405a;
    }

    /* JADX INFO: renamed from: b */
    public byte m761b() {
        return this.f406b;
    }

    /* JADX INFO: renamed from: c */
    public short m762c() {
        return this.f407c;
    }

    /* JADX INFO: renamed from: a */
    public void m763a(short sequence) {
        this.f407c = sequence;
    }

    /* JADX INFO: renamed from: d */
    public short m764d() {
        short s = (short) (this.f407c + 1);
        this.f407c = s;
        return s;
    }
}
