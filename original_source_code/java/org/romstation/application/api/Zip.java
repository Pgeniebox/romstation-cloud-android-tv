package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipFile;
import javafx.concurrent.Task;
import org.romstation.application.C0076av;
import org.romstation.application.task.C0231B;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/Zip.class */
@JsAccessible
public class Zip {
    public boolean unzipDialog(String source, String target) {
        Task c0231b = new C0231B(Paths.get(source, new String[0]), Paths.get(target, new String[0]));
        Thread thread = new Thread((Runnable) c0231b);
        C0076av<Boolean> dialog = new C0076av<>(c0231b, "unzip");
        thread.start();
        Optional<Boolean> result = dialog.showAndWait();
        return result.orElse(false).booleanValue();
    }

    public boolean unzip(String source, String target) throws ExecutionException, InterruptedException {
        C0231B task = new C0231B(Paths.get(source, new String[0]), Paths.get(target, new String[0]));
        task.run();
        return ((Boolean) task.get()).booleanValue();
    }

    /* JADX WARN: Unreachable blocks removed: 14, instructions: 22 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:16:0x0037
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1478)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public boolean isZip(java.lang.String r6) {
        /*
            r5 = this;
            java.util.zip.ZipFile r0 = new java.util.zip.ZipFile     // Catch: java.io.IOException -> L5a
            r1 = r0
            r2 = r6
            r1.<init>(r2)     // Catch: java.io.IOException -> L5a
            r7 = r0
            r0 = 0
            r8 = r0
            r0 = 1
            r9 = r0
            r0 = r7
            if (r0 == 0) goto L2c
            r0 = r8
            if (r0 == 0) goto L28
            r0 = r7
            r0.close()     // Catch: java.lang.Throwable -> L1d java.io.IOException -> L5a
            goto L2c
        L1d:
            r10 = move-exception
            r0 = r8
            r1 = r10
            r0.addSuppressed(r1)     // Catch: java.io.IOException -> L5a
            goto L2c
        L28:
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L5a
        L2c:
            r0 = r9
            return r0
        L2f:
            r9 = move-exception
            r0 = r9
            r8 = r0
            r0 = r9
            throw r0     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L5a
        L37:
            r11 = move-exception
            r0 = r7
            if (r0 == 0) goto L57
            r0 = r8
            if (r0 == 0) goto L53
            r0 = r7
            r0.close()     // Catch: java.lang.Throwable -> L48 java.io.IOException -> L5a
            goto L57
        L48:
            r12 = move-exception
            r0 = r8
            r1 = r12
            r0.addSuppressed(r1)     // Catch: java.io.IOException -> L5a
            goto L57
        L53:
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L5a
        L57:
            r0 = r11
            throw r0     // Catch: java.io.IOException -> L5a
        L5a:
            r7 = move-exception
            java.util.logging.Logger r0 = org.romstation.application.RomStation.m42b()
            java.util.logging.Level r1 = java.util.logging.Level.WARNING
            r2 = r7
            java.lang.String r2 = r2.getMessage()
            r3 = r7
            r0.log(r1, r2, r3)
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.api.Zip.isZip(java.lang.String):boolean");
    }

    public String[] list(String source) throws IOException {
        ZipFile zip = new ZipFile(source);
        Throwable th = null;
        try {
            String[] strArr = (String[]) zip.stream().map((v0) -> {
                return v0.getName();
            }).toArray(x$0 -> {
                return new String[x$0];
            });
            if (zip != null) {
                if (0 != 0) {
                    try {
                        zip.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    zip.close();
                }
            }
            return strArr;
        } catch (Throwable th3) {
            if (zip != null) {
                if (0 != 0) {
                    try {
                        zip.close();
                    } catch (Throwable th4) {
                        th.addSuppressed(th4);
                    }
                } else {
                    zip.close();
                }
            }
            throw th3;
        }
    }
}
