package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.D */
/* JADX INFO: compiled from: OS.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/D.class */
public enum EnumC0003D {
    OTHER(0),
    WINDOWS(1),
    MAC_OS(2),
    LINUX(3);

    private final int value;

    EnumC0003D(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m7a() {
        return this.value;
    }
}
