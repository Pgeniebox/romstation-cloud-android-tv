package org.romstation.application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.task.C0230A;
import org.romstation.application.task.C0258z;

/* JADX INFO: renamed from: org.romstation.application.U */
/* JADX INFO: compiled from: System.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/U.class */
public class C0021U {

    /* JADX INFO: renamed from: a */
    private final Map<String, Function<C0258z, String>> f27a = new HashMap();
    public Consumer<C0230A> on_init;
    public Consumer<C0230A> on_ready;
    public Consumer<C0230A> on_start;
    public Consumer<C0230A> on_stop;

    public synchronized Function<C0258z, String> getProperty(String property) {
        return this.f27a.get(property);
    }

    public synchronized void setProperty(String property, Function<C0258z, String> function) {
        this.f27a.put(property, function);
    }

    public void launch(EmulatorProfile emulator_profile, GameProfile game_profile, String... args) {
        C0157cW.m681a(new C0258z(emulator_profile, game_profile, args));
    }

    public void launch(C0258z context) {
        C0157cW.m681a(context);
    }
}
