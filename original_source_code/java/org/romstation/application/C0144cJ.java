package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.cJ */
/* JADX INFO: compiled from: SendLobbyCommandEvent.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cJ.class */
public class C0144cJ implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final String f335a;

    /* JADX INFO: renamed from: b */
    private final Object f336b;

    public C0144cJ(String name) {
        this(name, null);
    }

    public C0144cJ(String name, Object value) {
        this.f335a = name;
        this.f336b = value;
    }

    /* JADX INFO: renamed from: a */
    public String m651a() {
        return this.f335a;
    }

    /* JADX INFO: renamed from: b */
    public Object m652b() {
        return this.f336b;
    }
}
