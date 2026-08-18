package org.romstation.application.task;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.text.MessageFormat;
import javafx.concurrent.Task;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.task.b */
/* JADX INFO: compiled from: ChecksumTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/b.class */
public abstract class AbstractC0234b extends Task<String> {

    /* JADX INFO: renamed from: a */
    private Path f619a;

    /* JADX INFO: renamed from: a */
    abstract void mo1008a(byte[] bArr, int i);

    /* JADX INFO: renamed from: a */
    abstract String mo1009a();

    public AbstractC0234b(Path path) {
        this.f619a = path;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("checksum.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:33:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:38:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:50:0x00be A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // 
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public String call() throws Exception {
        byte[] bytes = new byte[1024];
        FileInputStream stream = new FileInputStream(this.f619a.toFile());
        Throwable th = null;
        try {
            try {
                updateMessage(MessageFormat.format(RomStation.m44d().getString("checksum.task.message"), this.f619a.getFileName()));
                while (true) {
                    int length = stream.read(bytes);
                    if (length != -1) {
                        if (!isCancelled()) {
                            mo1008a(bytes, length);
                            updateProgress(stream.getChannel().position(), stream.getChannel().size());
                        } else {
                            if (stream != null) {
                                if (0 != 0) {
                                    try {
                                        stream.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                } else {
                                    stream.close();
                                }
                            }
                            return null;
                        }
                    } else {
                        if (stream != null) {
                            if (0 != 0) {
                                try {
                                    stream.close();
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                }
                            } else {
                                stream.close();
                            }
                        }
                        return mo1009a();
                    }
                    if (stream != null) {
                        if (th != null) {
                            try {
                                stream.close();
                            } catch (Throwable th4) {
                                th.addSuppressed(th4);
                            }
                        } else {
                            stream.close();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                throw th5;
            }
        } catch (Throwable th6) {
            if (stream != null) {
                if (th != null) {
                    stream.close();
                } else {
                    stream.close();
                }
            }
            throw th6;
        }
    }
}
