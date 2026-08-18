package org.romstation.application;

import java.time.Instant;

/* JADX INFO: renamed from: org.romstation.application.by */
/* JADX INFO: compiled from: Message.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/by.class */
public abstract class AbstractC0132by {

    /* JADX INFO: renamed from: a */
    private final long f313a;

    /* JADX INFO: renamed from: b */
    private final String f314b;

    public AbstractC0132by(String text) {
        this(Instant.now().getEpochSecond(), text);
    }

    public AbstractC0132by(long timestamp, String text) {
        this.f313a = timestamp;
        this.f314b = text;
    }

    /* JADX INFO: renamed from: a */
    public long m634a() {
        return this.f313a;
    }

    /* JADX INFO: renamed from: b */
    public String m635b() {
        return this.f314b;
    }
}
