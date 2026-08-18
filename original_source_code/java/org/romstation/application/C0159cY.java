package org.romstation.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.java.games.input.Component;
import org.romstation.application.virtualcontroller.device.C0272b;
import org.romstation.application.virtualcontroller.device.C0274d;
import org.romstation.application.virtualcontroller.device.IdentifierNotFoundException;

/* JADX INFO: renamed from: org.romstation.application.cY */
/* JADX INFO: compiled from: Binding.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cY.class */
public class C0159cY {

    /* JADX INFO: renamed from: a */
    private final ArrayList<String> f375a = new ArrayList<>();

    public C0159cY() {
    }

    public C0159cY(Collection<String> collection) {
        this.f375a.addAll(collection);
    }

    public C0159cY(String... commands) {
        this.f375a.addAll(Arrays.asList(commands));
    }

    /* JADX INFO: renamed from: a */
    public ArrayList<String> m715a() {
        return this.f375a;
    }

    public String toString() {
        return String.join(" + ", this.f375a);
    }

    /* JADX INFO: renamed from: a */
    public static C0159cY m716a(String string) {
        return new C0159cY(string.split(" \\+ "));
    }

    /* JADX INFO: renamed from: a */
    public static C0159cY m717a(KeyCode keyCode) throws IdentifierNotFoundException {
        return new C0159cY(C0274d.m1641a(keyCode));
    }

    /* JADX INFO: renamed from: a */
    public static C0159cY m718a(MouseButton mouseButton) throws IdentifierNotFoundException {
        return new C0159cY(C0274d.m1642a(mouseButton));
    }

    /* JADX INFO: renamed from: a */
    public static C0159cY m719a(Component.Identifier identifier) throws IdentifierNotFoundException {
        return new C0159cY(C0272b.m1623a(identifier));
    }

    /* JADX INFO: renamed from: a */
    public static C0159cY m720a(Component.Identifier identifier, float value) throws IdentifierNotFoundException {
        return new C0159cY(C0272b.m1624a(identifier, value));
    }
}
