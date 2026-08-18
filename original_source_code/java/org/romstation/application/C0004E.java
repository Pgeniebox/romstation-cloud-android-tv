package org.romstation.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

/* JADX INFO: renamed from: org.romstation.application.E */
/* JADX INFO: compiled from: SystemUtil.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/E.class */
public class C0004E {
    /* JADX INFO: renamed from: a */
    public static Path m8a() {
        switch (m10c()) {
            case WINDOWS:
                return Paths.get(System.getenv("LOCALAPPDATA"), new String[0]);
            case MAC_OS:
                return Paths.get(System.getProperty("user.home"), "Library", "Application Support");
            default:
                return null;
        }
    }

    /* JADX INFO: renamed from: b */
    public static Path m9b() {
        Path userDataDirectory = m8a();
        if (userDataDirectory == null) {
            return Paths.get("", new String[0]).toAbsolutePath();
        }
        return userDataDirectory.resolve("RomStation");
    }

    /* JADX INFO: renamed from: c */
    public static EnumC0003D m10c() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("windows")) {
            return EnumC0003D.WINDOWS;
        }
        if (osName.startsWith("mac")) {
            return EnumC0003D.MAC_OS;
        }
        if (osName.startsWith("linux")) {
            return EnumC0003D.LINUX;
        }
        return EnumC0003D.OTHER;
    }

    /* JADX INFO: renamed from: d */
    public static EnumC0002C m11d() {
        if (System.getProperty("os.arch").contains("64")) {
            return EnumC0002C.X64;
        }
        return EnumC0002C.X86;
    }

    /* JADX INFO: renamed from: e */
    public static EnumC0002C m12e() {
        boolean is64bit;
        if (System.getProperty("os.name").contains("Windows")) {
            is64bit = System.getenv("ProgramFiles(x86)") != null;
        } else {
            is64bit = System.getProperty("os.arch").contains("64");
        }
        return is64bit ? EnumC0002C.X64 : EnumC0002C.X86;
    }

    /* JADX INFO: renamed from: f */
    public static String m13f() {
        JsonObject hardwareOverview;
        switch (m10c()) {
            case WINDOWS:
                JsonArray jsonArray = C0008I.m25a("csproduct", "uuid");
                if (jsonArray.size() != 0) {
                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                    if (jsonObject.has("UUID")) {
                        return jsonObject.get("UUID").getAsString();
                    }
                    return null;
                }
                return null;
            case MAC_OS:
                JsonObject hardware = C0005F.m17a("SPHardwareDataType").getAsJsonObject("Hardware");
                if (hardware != null && (hardwareOverview = hardware.getAsJsonObject("Hardware Overview")) != null) {
                    return hardwareOverview.get("Hardware UUID").getAsString();
                }
                return null;
            default:
                return null;
        }
    }

    /* JADX INFO: renamed from: g */
    public static JsonObject m14g() {
        switch (m10c()) {
            case WINDOWS:
                JsonObject json = new JsonObject();
                json.add("os", C0008I.m25a("os", "Caption", "ServicePackMajorVersion", "Version", "osarchitecture", "BuildNumber"));
                json.add("computersystem", C0008I.m25a("computersystem", "Manufacturer", "Model", "NumberofProcessors", "totalphysicalmemory"));
                json.add("cpu", C0008I.m25a("cpu", "Name", "MaxClockSpeed", "NumberOfCores"));
                json.add("gpu", C0008I.m26b("win32_VideoController", "Name", "DriverVersion"));
                return json;
            case MAC_OS:
                return C0005F.m17a("SPHardwareDataType", "SPSoftwareDataType", "SPDisplaysDataType");
            default:
                return new JsonObject();
        }
    }

    /* JADX INFO: renamed from: h */
    public static String m15h() {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            return m16a(networkInterface);
        } catch (SocketException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        } catch (UnknownHostException exception2) {
            RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static String m16a(NetworkInterface networkInterface) {
        if (networkInterface != null) {
            try {
                byte[] hardwareAddress = networkInterface.getHardwareAddress();
                if (hardwareAddress != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append('-');
                        }
                        stringBuilder.append(String.format("%02x", Byte.valueOf(b)).toUpperCase());
                    }
                    return stringBuilder.toString();
                }
                return null;
            } catch (SocketException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                return null;
            }
        }
        return null;
    }
}
