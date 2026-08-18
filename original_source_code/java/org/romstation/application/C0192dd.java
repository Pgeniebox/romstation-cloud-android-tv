package org.romstation.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.romstation.application.virtualcontroller.device.AbstractC0271a;
import org.romstation.application.virtualcontroller.device.C0272b;

/* JADX INFO: renamed from: org.romstation.application.dd */
/* JADX INFO: compiled from: VirtualControllerManager.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dd.class */
public class C0192dd {
    /* JADX INFO: renamed from: a */
    public static C0160cZ m781a(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(20000);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Throwable th = null;
        try {
            try {
                JsonParser jsonParser = new JsonParser();
                JsonElement json = jsonParser.parse(bufferedReader);
                C0160cZ c0160cZM783a = m783a(json);
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
                return c0160cZM783a;
            } catch (Throwable th3) {
                th = th3;
                throw th3;
            }
        } catch (Throwable th4) {
            if (bufferedReader != null) {
                if (th != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                } else {
                    bufferedReader.close();
                }
            }
            throw th4;
        }
    }

    /* JADX INFO: renamed from: a */
    public static C0160cZ m782a(Path path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path);
        Throwable th = null;
        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement json = jsonParser.parse(reader);
            C0160cZ c0160cZM783a = m783a(json);
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
            return c0160cZM783a;
        } catch (Throwable th3) {
            if (reader != null) {
                if (0 != 0) {
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
    }

    /* JADX INFO: renamed from: a */
    public static C0160cZ m783a(JsonElement json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(C0160cZ.class, new C0193de());
        gsonBuilder.registerTypeAdapter(C0190db.class, new C0196dh());
        gsonBuilder.registerTypeAdapter(AbstractC0271a.class, new C0194df());
        gsonBuilder.registerTypeAdapter(AbstractC0199dk.class, new C0195dg());
        Gson gson = gsonBuilder.create();
        return (C0160cZ) gson.fromJson(json, C0160cZ.class);
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0095 */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m784a(java.nio.file.Path r5, org.romstation.application.C0160cZ r6) throws java.io.IOException {
        /*
            r0 = r5
            r1 = 0
            java.nio.file.OpenOption[] r1 = new java.nio.file.OpenOption[r1]
            java.io.BufferedWriter r0 = java.nio.file.Files.newBufferedWriter(r0, r1)
            r7 = r0
            r0 = 0
            r8 = r0
            com.google.gson.GsonBuilder r0 = new com.google.gson.GsonBuilder     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r1 = r0
            r1.<init>()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r9 = r0
            r0 = r9
            java.lang.Class<org.romstation.application.cZ> r1 = org.romstation.application.C0160cZ.class
            org.romstation.application.de r2 = new org.romstation.application.de     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r3 = r2
            r3.<init>()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            com.google.gson.GsonBuilder r0 = r0.registerTypeAdapter(r1, r2)     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r0 = r9
            java.lang.Class<org.romstation.application.db> r1 = org.romstation.application.C0190db.class
            org.romstation.application.dh r2 = new org.romstation.application.dh     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r3 = r2
            r3.<init>()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            com.google.gson.GsonBuilder r0 = r0.registerTypeAdapter(r1, r2)     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r0 = r9
            java.lang.Class<org.romstation.application.virtualcontroller.device.a> r1 = org.romstation.application.virtualcontroller.device.AbstractC0271a.class
            org.romstation.application.df r2 = new org.romstation.application.df     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r3 = r2
            r3.<init>()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            com.google.gson.GsonBuilder r0 = r0.registerTypeAdapter(r1, r2)     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r0 = r9
            java.lang.Class<org.romstation.application.dk> r1 = org.romstation.application.AbstractC0199dk.class
            org.romstation.application.dg r2 = new org.romstation.application.dg     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r3 = r2
            r3.<init>()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            com.google.gson.GsonBuilder r0 = r0.registerTypeAdapter(r1, r2)     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r0 = r9
            com.google.gson.GsonBuilder r0 = r0.setPrettyPrinting()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r0 = r9
            com.google.gson.GsonBuilder r0 = r0.serializeNulls()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r0 = r9
            com.google.gson.Gson r0 = r0.create()     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r10 = r0
            r0 = r10
            r1 = r6
            java.lang.Class<org.romstation.application.cZ> r2 = org.romstation.application.C0160cZ.class
            r3 = r7
            r0.toJson(r1, r2, r3)     // Catch: java.lang.Throwable -> L8d java.lang.Throwable -> L95
            r0 = r7
            if (r0 == 0) goto Lb8
            r0 = r8
            if (r0 == 0) goto L86
            r0 = r7
            r0.close()     // Catch: java.lang.Throwable -> L7b
            goto Lb8
        L7b:
            r9 = move-exception
            r0 = r8
            r1 = r9
            r0.addSuppressed(r1)
            goto Lb8
        L86:
            r0 = r7
            r0.close()
            goto Lb8
        L8d:
            r9 = move-exception
            r0 = r9
            r8 = r0
            r0 = r9
            throw r0     // Catch: java.lang.Throwable -> L95
        L95:
            r11 = move-exception
            r0 = r7
            if (r0 == 0) goto Lb5
            r0 = r8
            if (r0 == 0) goto Lb1
            r0 = r7
            r0.close()     // Catch: java.lang.Throwable -> La6
            goto Lb5
        La6:
            r12 = move-exception
            r0 = r8
            r1 = r12
            r0.addSuppressed(r1)
            goto Lb5
        Lb1:
            r0 = r7
            r0.close()
        Lb5:
            r0 = r11
            throw r0
        Lb8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.C0192dd.m784a(java.nio.file.Path, org.romstation.application.cZ):void");
    }

    /* JADX INFO: renamed from: a */
    public static C0190db m785a(C0190db profile) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(C0190db.class, new C0196dh());
        gsonBuilder.registerTypeAdapter(AbstractC0271a.class, new C0194df());
        gsonBuilder.registerTypeAdapter(AbstractC0199dk.class, new C0195dg());
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(profile, C0190db.class);
        return (C0190db) gson.fromJson(json, C0190db.class);
    }

    /* JADX INFO: renamed from: a */
    public static void m786a(C0160cZ config) {
        for (C0272b device : C0272b.m1618h()) {
            switch (device.m1616g()) {
                case "2dba755d":
                case "83a0d041":
                    Optional<C0190db> optional = config.m729e().stream().filter(profile -> {
                        return profile.m765a().equals("Xbox Controller");
                    }).findAny();
                    if (optional.isPresent()) {
                        C0190db profile2 = optional.get();
                        profile2.m768a(device);
                        config.m728b(profile2);
                        return;
                    }
                    break;
                case "293ed6da":
                case "04c77bb8":
                    break;
                default:
                    continue;
                    break;
            }
            Optional<C0190db> optional2 = config.m729e().stream().filter(profile3 -> {
                return profile3.m765a().equals("Playstation Controller");
            }).findAny();
            if (optional2.isPresent()) {
                C0190db profile4 = optional2.get();
                profile4.m768a(device);
                config.m728b(profile4);
                return;
            }
        }
        config.m728b(config.m729e().stream().filter(profile5 -> {
            return profile5.m765a().equals("Keyboard");
        }).findAny().orElse(null));
    }
}
