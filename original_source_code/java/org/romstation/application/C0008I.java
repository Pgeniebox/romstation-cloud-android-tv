package org.romstation.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

/* JADX INFO: renamed from: org.romstation.application.I */
/* JADX INFO: compiled from: WMIC.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/I.class */
public class C0008I {
    /* JADX INFO: renamed from: a */
    public static JsonArray m25a(String alias, String... properties) {
        ProcessBuilder processBuilder = new ProcessBuilder("wmic", alias, "get", String.join(",", properties), "/format:csv");
        return m27a(processBuilder);
    }

    /* JADX INFO: renamed from: b */
    public static JsonArray m26b(String path, String... properties) {
        ProcessBuilder processBuilder = new ProcessBuilder("wmic", "path", path, "get", String.join(",", properties), "/format:csv");
        return m27a(processBuilder);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:39:0x00e6 A[Catch: IOException | InterruptedException -> 0x00f0, TryCatch #3 {IOException | InterruptedException -> 0x00f0, blocks: (B:3:0x0008, B:4:0x0023, B:5:0x002c, B:7:0x0036, B:9:0x003e, B:11:0x0047, B:12:0x0053, B:14:0x0065, B:15:0x0071, B:17:0x0079, B:18:0x008e, B:20:0x0097, B:24:0x00a5, B:27:0x00b8, B:26:0x00ae, B:30:0x00c7, B:36:0x00d3, B:39:0x00e6, B:38:0x00dc, B:41:0x00ec), top: B:53:0x0008, inners: #0, #1, #4 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: a */
    private static JsonArray m27a(ProcessBuilder processBuilder) {
        JsonArray jsonArray = new JsonArray();
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Throwable th = null;
            try {
                try {
                    int rows = 0;
                    String[] keys = new String[0];
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (!line.isEmpty()) {
                            rows++;
                            if (rows == 1) {
                                keys = line.split(",");
                            } else {
                                String[] values = line.split(",");
                                if (keys.length == values.length) {
                                    JsonObject jsonObject = new JsonObject();
                                    for (int i = 0; i < values.length; i++) {
                                        jsonObject.addProperty(keys[i], values[i]);
                                    }
                                    jsonArray.add(jsonObject);
                                }
                            }
                        }
                        if (reader != null) {
                            if (th != null) {
                                try {
                                    reader.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            } else {
                                reader.close();
                            }
                        }
                        throw th;
                    }
                    process.waitFor();
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
                    return jsonArray;
                } catch (Throwable th4) {
                    if (reader != null) {
                        if (th != null) {
                            reader.close();
                        } else {
                            reader.close();
                        }
                    }
                    throw th4;
                }
            } catch (Throwable th5) {
                th = th5;
                throw th5;
            }
        } catch (IOException | InterruptedException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }
}
