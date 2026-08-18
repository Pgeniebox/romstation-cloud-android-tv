package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.af */
/* JADX INFO: compiled from: MembershipStatus.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/af.class */
public enum EnumC0059af {
    REGULAR(0),
    PREMIUM(1),
    PLATINUM(2);

    private final int value;

    EnumC0059af(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m226a() {
        return this.value;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0059af m227a(int value) {
        for (EnumC0059af membershipStatus : values()) {
            if (membershipStatus.value == value) {
                return membershipStatus;
            }
        }
        throw new IllegalArgumentException(String.format("no membership status found for value %d", Integer.valueOf(value)));
    }
}
