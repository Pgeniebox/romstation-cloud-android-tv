package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.br */
/* JADX INFO: compiled from: SystemRegion.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/br.class */
public enum EnumC0125br {
    AUTO(0),
    NTSC_J(1),
    NTSC_U(2),
    PAL(3);

    private final int value;

    EnumC0125br(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m616a() {
        return this.value;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0125br m617a(int value) {
        switch (value) {
            case 0:
                return AUTO;
            case 1:
                return NTSC_J;
            case 2:
                return NTSC_U;
            case 3:
                return PAL;
            default:
                throw new IllegalArgumentException(String.format("no region found for value %d", Integer.valueOf(value)));
        }
    }
}
