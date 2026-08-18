package org.romstation.application;

import java.util.List;
import org.romstation.application.database.entity.Emulator;

/* JADX INFO: renamed from: org.romstation.application.cq */
/* JADX INFO: compiled from: EmulatorSearchResultEvent.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cq.class */
public class C0177cq implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final List<Emulator> f388a;

    public C0177cq(List<Emulator> emulators) {
        this.f388a = emulators;
    }

    /* JADX INFO: renamed from: a */
    public List<Emulator> m737a() {
        return this.f388a;
    }
}
