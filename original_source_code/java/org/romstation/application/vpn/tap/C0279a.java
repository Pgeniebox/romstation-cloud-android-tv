package org.romstation.application.vpn.tap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import org.romstation.application.C0004E;
import org.romstation.application.C0008I;
import org.romstation.application.C0010K;
import org.romstation.application.C0011L;
import org.romstation.application.EnumC0002C;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.vpn.tap.a */
/* JADX INFO: compiled from: TAP.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/vpn/tap/a.class */
public class C0279a {
    /* JADX INFO: renamed from: a */
    public static C0011L m1653a() throws TAPDriverInstallException {
        List<C0011L> previousDevices = C0010K.m31a("tap0901");
        String osArch = C0004E.m12e() == EnumC0002C.X64 ? "win64" : "win32";
        String osName = System.getProperty("os.name").equals("Windows 10") ? "win10" : "win7";
        Path directory = Paths.get("openvpn", osArch, osName).toAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder("elevate.exe", "-c", "-w", directory.resolve("install.bat").toString());
        processBuilder.directory(directory.toFile());
        processBuilder.inheritIO();
        try {
            Process process = processBuilder.start();
            process.waitFor();
            List<C0011L> currentDevices = C0010K.m31a("tap0901");
            currentDevices.removeAll(previousDevices);
            if (!currentDevices.isEmpty()) {
                return currentDevices.get(0);
            }
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
        throw new TAPDriverInstallException("TAP driver installation failed.");
    }

    /* JADX INFO: renamed from: a */
    public static String m1654a(String deviceID) throws TAPDriverInstallException {
        JsonArray<JsonElement> jsonArray = C0008I.m25a("nic", "PNPDeviceID", "GUID");
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.get("PNPDeviceID").getAsString().equals(deviceID)) {
                return jsonObject.get("GUID").getAsString();
            }
        }
        throw new TAPDriverInstallException(String.format("PNPDeviceID %s not found.", deviceID));
    }
}
