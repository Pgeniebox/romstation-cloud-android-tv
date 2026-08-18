package org.romstation.application.task;

import java.nio.file.Path;
import java.security.MessageDigest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.task.x */
/* JADX INFO: compiled from: MessageDigestTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/x.class */
public class C0256x extends AbstractC0234b {

    /* JADX INFO: renamed from: a */
    public static String f717a = "MD5";

    /* JADX INFO: renamed from: b */
    public static String f718b = "SHA-1";

    /* JADX INFO: renamed from: c */
    public static String f719c = "SHA-256";

    /* JADX INFO: renamed from: d */
    private MessageDigest f720d;

    /* JADX INFO: renamed from: e */
    private String f721e;

    public C0256x(Path path, String algorithm) {
        super(path);
        this.f721e = algorithm;
    }

    @Override // org.romstation.application.task.AbstractC0234b
    /* JADX INFO: renamed from: a */
    void mo1008a(byte[] bytes, int length) {
        this.f720d.update(bytes, 0, length);
    }

    @Override // org.romstation.application.task.AbstractC0234b
    /* JADX INFO: renamed from: a */
    String mo1009a() {
        return new HexBinaryAdapter().marshal(this.f720d.digest()).toLowerCase();
    }

    @Override // org.romstation.application.task.AbstractC0234b
    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("checksum.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.task.AbstractC0234b
    /* JADX INFO: renamed from: b */
    public String call() throws Exception {
        this.f720d = MessageDigest.getInstance(this.f721e);
        return super.call();
    }
}
