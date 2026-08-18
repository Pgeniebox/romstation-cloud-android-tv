package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.bp */
/* JADX INFO: compiled from: SystemLanguage.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bp.class */
public enum EnumC0123bp {
    JAPANESE(0),
    ENGLISH(1),
    GERMAN(2),
    FRENCH(3),
    SPANISH(4),
    ITALIAN(5),
    DUTCH(6),
    PORTUGUESE(7);

    private final int value;

    EnumC0123bp(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m612a() {
        return this.value;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0123bp m613a(int value) {
        switch (value) {
            case 0:
                return JAPANESE;
            case 1:
                return ENGLISH;
            case 2:
                return GERMAN;
            case 3:
                return FRENCH;
            case 4:
                return SPANISH;
            case 5:
                return ITALIAN;
            case 6:
                return DUTCH;
            case 7:
                return PORTUGUESE;
            default:
                throw new IllegalArgumentException(String.format("no language found for value %d", Integer.valueOf(value)));
        }
    }
}
