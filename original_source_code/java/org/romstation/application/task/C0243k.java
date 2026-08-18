package org.romstation.application.task;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javafx.concurrent.Task;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

/* JADX INFO: renamed from: org.romstation.application.task.k */
/* JADX INFO: compiled from: FTPUploadTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/k.class */
public class C0243k extends Task<Void> {

    /* JADX INFO: renamed from: a */
    private static final int f650a = 8096;

    /* JADX INFO: renamed from: b */
    private static final int f651b = 30000;

    /* JADX INFO: renamed from: c */
    private static final int f652c = 30000;

    /* JADX INFO: renamed from: d */
    private String f653d;

    /* JADX INFO: renamed from: e */
    private String f654e;

    /* JADX INFO: renamed from: f */
    private String f655f;

    /* JADX INFO: renamed from: g */
    private String f656g;

    /* JADX INFO: renamed from: h */
    private File f657h;

    /* JADX INFO: renamed from: i */
    private boolean f658i;

    public C0243k(String hostname) {
        this(hostname, null, null);
    }

    public C0243k(String hostname, String username, String password) {
        this.f653d = hostname;
        this.f654e = username;
        this.f655f = password;
    }

    /* JADX INFO: renamed from: a */
    public String m1057a() {
        return this.f656g;
    }

    /* JADX INFO: renamed from: a */
    public void m1058a(String source) {
        this.f656g = source;
    }

    /* JADX INFO: renamed from: b */
    public File m1059b() {
        return this.f657h;
    }

    /* JADX INFO: renamed from: a */
    public void m1060a(File inputFile) {
        this.f657h = inputFile;
    }

    /* JADX INFO: renamed from: c */
    public boolean m1061c() {
        return this.f658i;
    }

    /* JADX INFO: renamed from: a */
    public void m1062a(boolean resume) {
        this.f658i = resume;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:37:0x0132  */
    /* JADX WARN: Code duplicated, block: B:42:0x014b A[Catch: Throwable -> 0x0179, all -> 0x0182, TryCatch #7 {, blocks: (B:8:0x006e, B:10:0x0079, B:12:0x008c, B:13:0x0094, B:15:0x00a2, B:16:0x00ac, B:17:0x00bf, B:19:0x00cd, B:21:0x00d4, B:22:0x00dc, B:27:0x0106, B:30:0x011a, B:29:0x0110, B:33:0x012a, B:39:0x0137, B:42:0x014b, B:41:0x0141, B:44:0x0152, B:11:0x0084), top: B:69:0x006e, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x0137 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public Void call() throws Exception {
        FTPFile ftpFile;
        FTPSClient ftpsClient = new FTPSClient();
        ftpsClient.setConnectTimeout(30000);
        ftpsClient.setDataTimeout(30000);
        ftpsClient.connect(this.f653d);
        ftpsClient.login(this.f654e, this.f655f);
        ftpsClient.execPROT("P");
        ftpsClient.setFileType(2);
        ftpsClient.enterLocalPassiveMode();
        long resumeOffset = 0;
        if (this.f658i && (ftpFile = ftpsClient.mlistFile(this.f656g)) != null) {
            resumeOffset = ftpFile.getSize();
            ftpsClient.setRestartOffset(resumeOffset);
        }
        FileInputStream inputStream = new FileInputStream(this.f657h);
        Throwable th = null;
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(this.f658i ? ftpsClient.appendFileStream(this.f656g) : ftpsClient.storeFileStream(this.f656g));
            Throwable th2 = null;
            try {
                try {
                    byte[] bytes = new byte[f650a];
                    if (this.f658i) {
                        inputStream.getChannel().position(resumeOffset);
                    }
                    updateProgress(inputStream.getChannel().position(), this.f657h.length());
                    while (true) {
                        int length = inputStream.read(bytes);
                        if (length == -1) {
                            break;
                        }
                        if (isCancelled()) {
                            ftpsClient.abort();
                            break;
                        }
                        outputStream.write(bytes, 0, length);
                        updateProgress(inputStream.getChannel().position(), this.f657h.length());
                        if (outputStream != null) {
                            if (th2 != null) {
                                try {
                                    outputStream.close();
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                }
                            } else {
                                outputStream.close();
                            }
                        }
                        throw th;
                    }
                    if (outputStream != null) {
                        if (0 != 0) {
                            try {
                                outputStream.close();
                            } catch (Throwable th4) {
                                th2.addSuppressed(th4);
                            }
                        } else {
                            outputStream.close();
                        }
                    }
                    if (inputStream != null) {
                        if (0 != 0) {
                            try {
                                inputStream.close();
                            } catch (Throwable th5) {
                                th.addSuppressed(th5);
                            }
                        } else {
                            inputStream.close();
                        }
                    }
                    ftpsClient.completePendingCommand();
                    ftpsClient.logout();
                    ftpsClient.disconnect();
                    return null;
                } catch (Throwable th6) {
                    if (outputStream != null) {
                        if (th2 != null) {
                            outputStream.close();
                        } else {
                            outputStream.close();
                        }
                    }
                    throw th6;
                }
            } catch (Throwable th7) {
                th2 = th7;
                throw th7;
            }
        } catch (Throwable th8) {
            if (inputStream != null) {
                if (0 != 0) {
                    try {
                        inputStream.close();
                    } catch (Throwable th9) {
                        th.addSuppressed(th9);
                    }
                } else {
                    inputStream.close();
                }
            }
            throw th8;
        }
    }
}
