package org.romstation.application;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: renamed from: org.romstation.application.X */
/* JADX INFO: compiled from: Emulator.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/X.class */
public class C0024X {

    /* JADX INFO: renamed from: a */
    private String f31a;

    /* JADX INFO: renamed from: b */
    private Path f32b;

    /* JADX INFO: renamed from: c */
    private List<C0026Z> f33c = new LinkedList();

    public C0024X() {
    }

    public C0024X(String name, Path directory) {
        this.f31a = name;
        this.f32b = directory;
    }

    public String getName() {
        return this.f31a;
    }

    public void setName(String name) {
        this.f31a = name;
    }

    public Path getDirectory() {
        return this.f32b;
    }

    public void setDirectory(Path directory) {
        this.f32b = directory;
    }

    public List<C0026Z> getProfiles() {
        return this.f33c;
    }

    public void setProfiles(List<C0026Z> profiles) {
        this.f33c = profiles;
    }
}
