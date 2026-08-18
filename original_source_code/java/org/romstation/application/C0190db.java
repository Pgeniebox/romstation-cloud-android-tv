package org.romstation.application;

import java.util.ArrayList;
import org.romstation.application.virtualcontroller.device.AbstractC0271a;

/* JADX INFO: renamed from: org.romstation.application.db */
/* JADX INFO: compiled from: Profile.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/db.class */
public class C0190db {

    /* JADX INFO: renamed from: a */
    private String f521a;

    /* JADX INFO: renamed from: b */
    private AbstractC0271a f522b;

    /* JADX INFO: renamed from: c */
    private final ArrayList<AbstractC0199dk> f523c;

    public C0190db(String name) {
        this(name, new ArrayList());
    }

    public C0190db(String name, ArrayList<AbstractC0199dk> inputs) {
        this.f521a = name;
        this.f523c = inputs;
    }

    /* JADX INFO: renamed from: a */
    public String m765a() {
        return this.f521a;
    }

    /* JADX INFO: renamed from: a */
    public void m766a(String name) {
        this.f521a = name;
    }

    /* JADX INFO: renamed from: b */
    public AbstractC0271a m767b() {
        return this.f522b;
    }

    /* JADX INFO: renamed from: a */
    public void m768a(AbstractC0271a device) {
        this.f522b = device;
    }

    /* JADX INFO: renamed from: c */
    public ArrayList<AbstractC0199dk> m769c() {
        return this.f523c;
    }

    public String toString() {
        return this.f521a;
    }
}
