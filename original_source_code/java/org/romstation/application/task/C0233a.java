package org.romstation.application.task;

import java.nio.file.Path;
import java.util.zip.CRC32;

/* JADX INFO: renamed from: org.romstation.application.task.a */
/* JADX INFO: compiled from: CRC32Task.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/a.class */
public class C0233a extends AbstractC0234b {

    /* JADX INFO: renamed from: a */
    private CRC32 f618a;

    public C0233a(Path path) {
        super(path);
    }

    @Override // org.romstation.application.task.AbstractC0234b
    /* JADX INFO: renamed from: a */
    void mo1008a(byte[] bytes, int length) {
        this.f618a.update(bytes, 0, length);
    }

    @Override // org.romstation.application.task.AbstractC0234b
    /* JADX INFO: renamed from: a */
    String mo1009a() {
        return String.format("%08x", Long.valueOf(this.f618a.getValue()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.task.AbstractC0234b
    /* JADX INFO: renamed from: b */
    public String call() throws Exception {
        this.f618a = new CRC32();
        return super.call();
    }
}
