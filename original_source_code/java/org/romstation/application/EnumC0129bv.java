package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.bv */
/* JADX INFO: compiled from: LobbyType.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bv.class */
public enum EnumC0129bv {
    MANUAL(1),
    AUTOMATIC_VPN(2),
    AUTOMATIC(3),
    DEDICATED(4),
    CLOUD(5);

    private final int value;

    EnumC0129bv(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m626a() {
        return this.value;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0129bv m627a(int value) {
        switch (value) {
            case 1:
                return MANUAL;
            case 2:
                return AUTOMATIC_VPN;
            case 3:
                return AUTOMATIC;
            case 4:
                return DEDICATED;
            case 5:
                return CLOUD;
            default:
                throw new IllegalArgumentException(String.format("invalid value: %d", Integer.valueOf(value)));
        }
    }
}
