package org.romstation.application;

import org.romstation.application.database.entity.GameFile;

/* JADX INFO: renamed from: org.romstation.application.cy */
/* JADX INFO: compiled from: LaunchGameFileEvent.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cy.class */
public class C0185cy implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final GameFile f397a;

    /* JADX INFO: renamed from: b */
    private final String[] f398b;

    public C0185cy(GameFile gameFile, String... arguments) {
        this.f397a = gameFile;
        this.f398b = arguments;
    }

    /* JADX INFO: renamed from: a */
    public GameFile m746a() {
        return this.f397a;
    }

    /* JADX INFO: renamed from: b */
    public String[] m747b() {
        return this.f398b;
    }
}
