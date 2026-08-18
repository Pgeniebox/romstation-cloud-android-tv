package org.romstation.application;

import org.romstation.application.database.entity.Game;

/* JADX INFO: renamed from: org.romstation.application.cx */
/* JADX INFO: compiled from: LaunchGameEvent.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cx.class */
public class C0184cx implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final Game f395a;

    /* JADX INFO: renamed from: b */
    private String[] f396b;

    public C0184cx(Game game, String... arguments) {
        this.f395a = game;
        this.f396b = arguments;
    }

    /* JADX INFO: renamed from: a */
    public Game m744a() {
        return this.f395a;
    }

    /* JADX INFO: renamed from: b */
    public String[] m745b() {
        return this.f396b;
    }
}
