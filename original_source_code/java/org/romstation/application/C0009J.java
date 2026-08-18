package org.romstation.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: org.romstation.application.J */
/* JADX INFO: compiled from: WindowsUtil.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/J.class */
public class C0009J {
    /* JADX WARN: Code duplicated, block: B:26:0x009f  */
    /* JADX WARN: Code duplicated, block: B:31:0x00b8 A[Catch: IOException | InterruptedException -> 0x00c3, TryCatch #3 {IOException | InterruptedException -> 0x00c3, blocks: (B:3:0x001e, B:4:0x003a, B:8:0x0049, B:10:0x0059, B:12:0x0064, B:16:0x0073, B:19:0x0087, B:18:0x007d, B:22:0x0097, B:28:0x00a4, B:31:0x00b8, B:30:0x00ae, B:33:0x00bf), top: B:45:0x001e, inners: #0, #1, #4 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x00a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: a */
    public static String m28a() {
        String host = null;
        Pattern pattern = Pattern.compile("(?<destination>(0\\.?){4})\\s+(?<netmask>(0\\.?){4})\\s+(?<gateway>(\\d+\\.?){4})\\s+(?<interface>(\\d+\\.?){4})\\s+(?<metric>\\d+)");
        ProcessBuilder processBuilder = new ProcessBuilder("route", "print");
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Throwable th = null;
            while (true) {
                try {
                    try {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (host == null) {
                            Matcher matcher = pattern.matcher(line);
                            if (matcher.find()) {
                                host = matcher.group("interface");
                            }
                        }
                    } catch (Throwable th2) {
                        if (reader != null) {
                            if (th != null) {
                                try {
                                    reader.close();
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                }
                            } else {
                                reader.close();
                            }
                        }
                        throw th2;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    throw th4;
                }
                if (reader != null) {
                    if (th != null) {
                        reader.close();
                    } else {
                        reader.close();
                    }
                }
                throw th2;
            }
            process.waitFor();
            if (reader != null) {
                if (0 != 0) {
                    try {
                        reader.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                } else {
                    reader.close();
                }
            }
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
        return host;
    }

    /* JADX INFO: renamed from: b */
    public static EnumC0007H m29b() throws InterruptedException, IOException, RuntimeException {
        ProcessBuilder processBuilder = new ProcessBuilder("REG", "QUERY", "HKLM\\SOFTWARE\\Microsoft\\DirectX", "/v", "MaxFeatureLevel");
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        Throwable th = null;
        try {
            String result = (String) reader.lines().collect(Collectors.joining("\n"));
            if (process.waitFor() == 0) {
                Matcher matcher = Pattern.compile("0x[0-9a-f]+").matcher(result);
                if (matcher.find()) {
                    EnumC0007H enumC0007HM24a = EnumC0007H.m24a(Integer.decode(matcher.group()).intValue());
                    if (reader != null) {
                        if (0 != 0) {
                            try {
                                reader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            reader.close();
                        }
                    }
                    return enumC0007HM24a;
                }
                EnumC0007H enumC0007H = EnumC0007H.D3D_FEATURE_LEVEL_UNKNOWN;
                if (reader != null) {
                    if (0 != 0) {
                        try {
                            reader.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    } else {
                        reader.close();
                    }
                }
                return enumC0007H;
            }
            throw new RuntimeException(String.format("process failed with exit value %d", Integer.valueOf(process.exitValue())));
        } catch (Throwable th4) {
            if (reader != null) {
                if (0 != 0) {
                    try {
                        reader.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                } else {
                    reader.close();
                }
            }
            throw th4;
        }
    }
}
