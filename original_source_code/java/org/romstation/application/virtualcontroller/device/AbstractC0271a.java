package org.romstation.application.virtualcontroller.device;

import java.util.Iterator;
import java.util.List;
import org.romstation.application.AbstractC0199dk;

/* JADX INFO: renamed from: org.romstation.application.virtualcontroller.device.a */
/* JADX INFO: compiled from: Device.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/virtualcontroller/device/a.class */
public abstract class AbstractC0271a {
    /* JADX INFO: renamed from: a */
    public abstract String mo1608a();

    /* JADX INFO: renamed from: b */
    public abstract List<String> mo1609b();

    /* JADX INFO: renamed from: c */
    public abstract boolean mo1610c();

    /* JADX INFO: renamed from: d */
    public abstract void mo1611d();

    /* JADX INFO: renamed from: a */
    protected abstract float mo1613a(String str);

    /* JADX INFO: renamed from: a */
    public float m1612a(AbstractC0199dk input) {
        if (input.m807e() != null) {
            Iterator<String> iterator = input.m807e().m715a().iterator();
            while (iterator.hasNext()) {
                float value = mo1613a(iterator.next());
                if (!iterator.hasNext()) {
                    return input.mo802a(Float.valueOf(value));
                }
                if (value < 0.5f) {
                    return 0.0f;
                }
            }
            return 0.0f;
        }
        return 0.0f;
    }

    public String toString() {
        return mo1608a();
    }
}
