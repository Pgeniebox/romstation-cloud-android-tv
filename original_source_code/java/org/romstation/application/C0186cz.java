package org.romstation.application;

import org.romstation.application.database.entity.GameProfile;

/* JADX INFO: renamed from: org.romstation.application.cz */
/* JADX INFO: compiled from: LaunchGameProfileEvent.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cz.class */
public class C0186cz implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final GameProfile f399a;

    /* JADX INFO: renamed from: b */
    private final String[] f400b;

    public C0186cz(GameProfile gameProfile, String... arguments) {
        this.f399a = gameProfile;
        this.f400b = arguments;
    }

    /* JADX INFO: renamed from: a */
    public GameProfile m748a() {
        return this.f399a;
    }

    /* JADX INFO: renamed from: b */
    public String[] m749b() {
        return this.f400b;
    }
}
