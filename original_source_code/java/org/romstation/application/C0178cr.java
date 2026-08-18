package org.romstation.application;

import java.util.List;
import org.romstation.application.database.entity.Game;

/* JADX INFO: renamed from: org.romstation.application.cr */
/* JADX INFO: compiled from: DeleteGameEvent.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cr.class */
public class C0178cr implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final List<Game> f389a;

    public C0178cr(List<Game> games) {
        this.f389a = games;
    }

    /* JADX INFO: renamed from: a */
    public List<Game> m738a() {
        return this.f389a;
    }
}
