package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javafx.application.Platform;
import org.romstation.application.C0004E;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/Runtime.class */
@JsAccessible
public class Runtime {
    public static final List<Runnable> onInit = new LinkedList();
    public static final List<Runnable> onStart = new LinkedList();
    public static final List<Runnable> onStop = new LinkedList();

    public void exit() {
        Platform.exit();
    }

    public Properties getSettings() {
        return RomStation.m43c();
    }

    public int build() {
        return RomStation.f15a;
    }

    public String version() {
        return RomStation.f16b;
    }

    public int getOS() {
        return C0004E.m10c().m7a();
    }

    public int getArch() {
        return C0004E.m11d().m6a();
    }

    public void onInit(Runnable callback) {
        onInit.add(callback);
    }

    public void onStart(Runnable callback) {
        onStart.add(callback);
    }

    public void onStop(Runnable callback) {
        onStop.add(callback);
    }
}
