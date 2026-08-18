package org.romstation.application;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Version;
import org.freedesktop.gstreamer.glib.GLib;

/* JADX INFO: renamed from: org.romstation.application.B */
/* JADX INFO: compiled from: GStreamer.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/B.class */
public class C0001B {

    /* JADX INFO: renamed from: a */
    private static boolean f4a;

    /* JADX INFO: renamed from: a */
    public static boolean m3a() {
        return f4a;
    }

    /* JADX INFO: renamed from: c */
    private static void m4c() {
        if (Platform.isWindows()) {
            Path gstPath = Paths.get("gstreamer", "bin");
            String systemPath = System.getenv("PATH");
            if (systemPath == null || systemPath.trim().isEmpty()) {
                Kernel32.INSTANCE.SetEnvironmentVariable("PATH", gstPath.toString());
                return;
            } else {
                Kernel32.INSTANCE.SetEnvironmentVariable("PATH", gstPath + File.pathSeparator + systemPath);
                return;
            }
        }
        if (Platform.isMac()) {
            Path gstPath2 = Paths.get("../Frameworks/GStreamer.framework/Libraries/", new String[0]);
            String jnaPath = System.getProperty("jna.library.path", "").trim();
            if (jnaPath.isEmpty()) {
                System.setProperty("jna.library.path", gstPath2.toString());
            } else {
                System.setProperty("jna.library.path", jnaPath + File.pathSeparator + gstPath2.toString());
            }
            GLib.setEnv("GST_PLUGIN_PATH_1_0", Paths.get("../Frameworks/GStreamer.framework/Versions/Current/lib/gstreamer-1.0", new String[0]).toString(), true);
            GLib.setEnv("GST_PLUGIN_SCANNER_1_0", Paths.get("../Frameworks/GStreamer.framework/Versions/Current/libexec/gstreamer-1.0/gst-plugin-scanner", new String[0]).toString(), true);
        }
    }

    /* JADX INFO: renamed from: b */
    public static boolean m5b() {
        m4c();
        Gst.init(Version.of(1, 20), "RomStation", new String[0]);
        f4a = true;
        return true;
    }
}
