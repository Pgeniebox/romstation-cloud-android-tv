package org.romstation.application;

import java.util.List;
import org.romstation.application.database.entity.Emulator;

/* JADX INFO: renamed from: org.romstation.application.ck */
/* JADX INFO: compiled from: EmulatorDeleteEvent.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ck.class */
public class C0171ck implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final List<Emulator> f383a;

    public C0171ck(List<Emulator> emulators) {
        this.f383a = emulators;
    }

    /* JADX INFO: renamed from: a */
    public List<Emulator> m732a() {
        return this.f383a;
    }
}
