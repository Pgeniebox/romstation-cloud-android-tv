package org.romstation.application;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: renamed from: org.romstation.application.ah */
/* JADX INFO: compiled from: Cache.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ah.class */
public class C0061ah<K, V> extends LinkedHashMap<K, V> {

    /* JADX INFO: renamed from: a */
    private final int f131a;

    public C0061ah(int maxSize) {
        this.f131a = maxSize;
    }

    /* JADX INFO: renamed from: a */
    public int m237a() {
        return this.f131a;
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.f131a;
    }
}
