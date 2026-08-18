package org.romstation.application.task;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import javafx.concurrent.Task;

/* JADX INFO: renamed from: org.romstation.application.task.v */
/* JADX INFO: compiled from: HttpDownloadTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/v.class */
public class C0254v extends Task<Void> {

    /* JADX INFO: renamed from: a */
    private static final int f707a = 8096;

    /* JADX INFO: renamed from: b */
    private static final int f708b = 30000;

    /* JADX INFO: renamed from: c */
    private static final int f709c = 30000;

    /* JADX INFO: renamed from: d */
    private final URL f710d;

    /* JADX INFO: renamed from: e */
    private final Path f711e;

    /* JADX INFO: renamed from: f */
    private boolean f712f;

    /* JADX INFO: renamed from: g */
    private boolean f713g;

    /* JADX INFO: renamed from: h */
    private String f714h;

    public C0254v(String source, Path target) throws MalformedURLException {
        this.f710d = new URL(source);
        this.f711e = target;
    }

    public C0254v(URL source, Path target) {
        this.f710d = source;
        this.f711e = target;
    }

    /* JADX INFO: renamed from: a */
    public boolean m1162a() {
        return this.f712f;
    }

    /* JADX INFO: renamed from: a */
    public void m1163a(boolean resume) {
        this.f712f = resume;
    }

    /* JADX INFO: renamed from: b */
    public boolean m1164b() {
        return this.f713g;
    }

    /* JADX INFO: renamed from: b */
    public void m1165b(boolean post) {
        this.f713g = post;
    }

    /* JADX INFO: renamed from: c */
    public String m1166c() {
        return this.f714h;
    }

    /* JADX INFO: renamed from: a */
    public void m1167a(String postData) {
        this.f714h = postData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Bottom block not found for handler: all -> 0x009f */
    /* JADX WARN: Code duplicated, block: B:109:0x025b  */
    /* JADX WARN: Code duplicated, block: B:113:0x01d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:139:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:79:0x01ea A[Catch: Throwable -> 0x0213, all -> 0x021b, IOException -> 0x0241, TryCatch #8 {, blocks: (B:31:0x00d0, B:32:0x00e9, B:33:0x010d, B:35:0x011a, B:41:0x012e, B:44:0x0142, B:43:0x0138, B:55:0x0168, B:56:0x0183, B:58:0x0191, B:59:0x019a, B:64:0x01a5, B:67:0x01b9, B:66:0x01af, B:70:0x01c9, B:76:0x01d6, B:79:0x01ea, B:78:0x01e0, B:81:0x01f1), top: B:128:0x00d0 }] */
    /* JADX WARN: Type inference failed for: r0v78, types: [java.io.BufferedInputStream, java.io.DataOutputStream] */
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Void call() throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 607
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.task.C0254v.call():java.lang.Void");
    }
}
