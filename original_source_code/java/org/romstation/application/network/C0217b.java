package org.romstation.application.network;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/* JADX INFO: renamed from: org.romstation.application.network.b */
/* JADX INFO: compiled from: Network.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/network/b.class */
public class C0217b {

    /* JADX INFO: renamed from: a */
    private static String f583a = "www.romstation.fr";

    /* JADX INFO: renamed from: b */
    private static final ObjectProperty<EnumC0218c> f584b = new SimpleObjectProperty(EnumC0218c.UNDEFINED);

    /* JADX INFO: renamed from: a */
    public static String m960a() {
        return f583a;
    }

    /* JADX INFO: renamed from: b */
    public static String m961b() {
        return "https://" + f583a;
    }

    /* JADX INFO: renamed from: c */
    public static ObjectProperty<EnumC0218c> m962c() {
        return f584b;
    }

    /* JADX INFO: renamed from: d */
    public static EnumC0218c m963d() {
        return (EnumC0218c) f584b.get();
    }

    /* JADX INFO: renamed from: a */
    public static void m964a(EnumC0218c value) {
        f584b.set(value);
    }
}
