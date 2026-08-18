package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.bn */
/* JADX INFO: compiled from: Resolution.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bn.class */
public enum EnumC0121bn {
    NATIVE(0),
    HD(1);

    private final int value;

    EnumC0121bn(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m608a() {
        return this.value;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0121bn m609a(int value) {
        switch (value) {
            case 0:
                return NATIVE;
            case 1:
                return HD;
            default:
                throw new IllegalArgumentException(String.format("invalid resolution for value %d", Integer.valueOf(value)));
        }
    }
}
