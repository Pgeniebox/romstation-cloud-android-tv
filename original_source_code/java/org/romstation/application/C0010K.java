package org.romstation.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: org.romstation.application.K */
/* JADX INFO: compiled from: DevCon.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/K.class */
public class C0010K {

    /* JADX INFO: renamed from: a */
    private static final Pattern f9a = Pattern.compile("(?<id>.+):(?<name>.+)");

    /* JADX INFO: renamed from: a */
    private static Path m30a() {
        String osArch = C0004E.m12e() == EnumC0002C.X64 ? "win64" : "win32";
        String osName = System.getProperty("os.name").equals("Windows 10") ? "win10" : "win7";
        return Paths.get("openvpn", osArch, osName);
    }

    /* JADX INFO: renamed from: a */
    public static List<C0011L> m31a(String id) {
        List<C0011L> devices = new LinkedList<>();
        ProcessBuilder processBuilder = new ProcessBuilder(m30a().resolve("tapinstall.exe").toString(), "find", id);
        try {
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Throwable th = null;
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    Matcher matcher = f9a.matcher(line);
                    if (matcher.find()) {
                        devices.add(new C0011L(matcher.group("id").trim(), matcher.group("name").trim()));
                    }
                } catch (Throwable th2) {
                    if (bufferedReader != null) {
                        if (0 != 0) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                        } else {
                            bufferedReader.close();
                        }
                    }
                    throw th2;
                }
            }
            process.waitFor();
            if (bufferedReader != null) {
                if (0 != 0) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th4) {
                        th.addSuppressed(th4);
                    }
                } else {
                    bufferedReader.close();
                }
            }
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
        return devices;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0012M m32a(C0011L device) {
        return m33b("@" + device.m34a());
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:38:0x0116 A[Catch: IOException | InterruptedException -> 0x0121, TryCatch #3 {IOException | InterruptedException -> 0x0121, blocks: (B:3:0x002b, B:4:0x0047, B:6:0x0052, B:7:0x0061, B:8:0x007c, B:11:0x008c, B:15:0x009b, B:16:0x00b4, B:17:0x00bb, B:19:0x00c2, B:23:0x00d1, B:26:0x00e5, B:25:0x00db, B:29:0x00f5, B:35:0x0102, B:38:0x0116, B:37:0x010c, B:40:0x011d), top: B:51:0x002b, inners: #0, #2, #4 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x0102 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: b */
    public static EnumC0012M m33b(String id) {
        EnumC0012M deviceStatus = EnumC0012M.UNKNOWN;
        ProcessBuilder processBuilder = new ProcessBuilder(m30a().resolve("tapinstall.exe").toString(), "status", id);
        try {
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Throwable th = null;
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            switch (line.trim()) {
                                case "Driver is running.":
                                    deviceStatus = EnumC0012M.RUNNING;
                                    break;
                                case "Device is disabled.":
                                    deviceStatus = EnumC0012M.DISABLED;
                                    break;
                            }
                        } else {
                            process.waitFor();
                            if (bufferedReader != null) {
                                if (0 != 0) {
                                    try {
                                        bufferedReader.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                } else {
                                    bufferedReader.close();
                                }
                            }
                            return deviceStatus;
                        }
                    } catch (Throwable th3) {
                        if (bufferedReader != null) {
                            if (th != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th4) {
                                    th.addSuppressed(th4);
                                }
                            } else {
                                bufferedReader.close();
                            }
                        }
                        throw th3;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    throw th5;
                }
                if (bufferedReader != null) {
                    if (th != null) {
                        bufferedReader.close();
                    } else {
                        bufferedReader.close();
                    }
                }
                throw th3;
            }
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }
}
