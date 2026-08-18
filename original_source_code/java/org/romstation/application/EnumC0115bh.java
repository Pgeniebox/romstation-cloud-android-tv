package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.bh */
/* JADX INFO: compiled from: Decoder.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bh.class */
public enum EnumC0115bh {
    AUTO(0),
    HARDWARE(1),
    SOFTWARE(2);

    private final int value;

    EnumC0115bh(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m585a() {
        return this.value;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0115bh m586a(int value) throws IllegalArgumentException {
        switch (value) {
            case 0:
                return AUTO;
            case 1:
                return HARDWARE;
            case 2:
                return SOFTWARE;
            default:
                throw new IllegalArgumentException(String.format("no decoder found for value %d", Integer.valueOf(value)));
        }
    }
}
