package org.romstation.application;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;

/* JADX INFO: renamed from: org.romstation.application.F */
/* JADX INFO: compiled from: SystemProfiler.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/F.class */
public class C0005F {
    /* JADX WARN: Code duplicated, block: B:35:0x012f  */
    /* JADX WARN: Code duplicated, block: B:40:0x0148 A[Catch: IOException | InterruptedException -> 0x0153, TryCatch #3 {IOException | InterruptedException -> 0x0153, blocks: (B:3:0x002b, B:4:0x0049, B:6:0x0054, B:8:0x005c, B:10:0x0079, B:12:0x0080, B:15:0x0091, B:16:0x009d, B:18:0x00bb, B:19:0x00d4, B:21:0x00f3, B:25:0x0103, B:28:0x0117, B:27:0x010d, B:31:0x0127, B:37:0x0134, B:40:0x0148, B:39:0x013e, B:42:0x014f), top: B:54:0x002b, inners: #0, #1, #4 }] */
    /* JADX WARN: Code duplicated, block: B:50:0x0134 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: a */
    public static JsonObject m17a(String... dataTypes) {
        C0006G root = new C0006G(0, null);
        C0006G node = root;
        ProcessBuilder builder = new ProcessBuilder("system_profiler");
        builder.command().addAll(Arrays.asList(dataTypes));
        try {
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Throwable th = null;
            while (true) {
                try {
                    try {
                        String line = reader.readLine();
                        if (line != null) {
                            if (!line.isEmpty()) {
                                int level = line.length() - line.replace("  ", "").length();
                                if (level <= node.m18a()) {
                                    while (node.m19b() != null) {
                                        node = node.m19b();
                                        if (node.m18a() < level) {
                                        }
                                    }
                                }
                                String[] strings = line.split(":");
                                switch (strings.length) {
                                    case 0:
                                        break;
                                    case 1:
                                        node = node.m22a(strings[0].trim(), new C0006G(level, node));
                                        break;
                                    default:
                                        node.m21a(strings[0].trim(), line.substring(strings[0].length() + 1).trim());
                                        break;
                                }
                            }
                        } else {
                            process.waitFor();
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
                            return root.m20c();
                        }
                    } catch (Throwable th3) {
                        if (reader != null) {
                            if (th != null) {
                                try {
                                    reader.close();
                                } catch (Throwable th4) {
                                    th.addSuppressed(th4);
                                }
                            } else {
                                reader.close();
                            }
                        }
                        throw th3;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    throw th5;
                }
                if (reader != null) {
                    if (th != null) {
                        reader.close();
                    } else {
                        reader.close();
                    }
                }
                throw th3;
            }
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }
}
