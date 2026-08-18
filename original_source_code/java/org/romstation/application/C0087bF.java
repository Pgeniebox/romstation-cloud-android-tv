package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.bF */
/* JADX INFO: compiled from: PlayerMessage.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bF.class */
public class C0087bF extends AbstractC0132by {

    /* JADX INFO: renamed from: a */
    private final C0084bC f196a;

    public C0087bF(C0084bC player, long timestamp, String text) {
        super(timestamp, text);
        this.f196a = player;
    }

    /* JADX INFO: renamed from: c */
    public C0084bC m336c() {
        return this.f196a;
    }

    public String toString() {
        return String.format("%s: %s", this.f196a.m631d(), m635b());
    }
}
