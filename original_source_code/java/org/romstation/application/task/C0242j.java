package org.romstation.application.task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import javafx.concurrent.Task;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

/* JADX INFO: renamed from: org.romstation.application.task.j */
/* JADX INFO: compiled from: FTPDownloadTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/j.class */
public class C0242j extends Task<Void> {

    /* JADX INFO: renamed from: a */
    private static final int f641a = 8096;

    /* JADX INFO: renamed from: b */
    private static final int f642b = 30000;

    /* JADX INFO: renamed from: c */
    private static final int f643c = 30000;

    /* JADX INFO: renamed from: d */
    private String f644d;

    /* JADX INFO: renamed from: e */
    private String f645e;

    /* JADX INFO: renamed from: f */
    private String f646f;

    /* JADX INFO: renamed from: g */
    private String f647g;

    /* JADX INFO: renamed from: h */
    private File f648h;

    /* JADX INFO: renamed from: i */
    private boolean f649i;

    public C0242j(String hostname) {
        this(hostname, null, null);
    }

    public C0242j(String hostname, String username, String password) {
        this.f644d = hostname;
        this.f645e = username;
        this.f646f = password;
    }

    /* JADX INFO: renamed from: a */
    public String m1050a() {
        return this.f647g;
    }

    /* JADX INFO: renamed from: a */
    public void m1051a(String source) {
        this.f647g = source;
    }

    /* JADX INFO: renamed from: b */
    public File m1052b() {
        return this.f648h;
    }

    /* JADX INFO: renamed from: a */
    public void m1053a(File outputFile) {
        this.f648h = outputFile;
    }

    /* JADX INFO: renamed from: c */
    public boolean m1054c() {
        return this.f649i;
    }

    /* JADX INFO: renamed from: a */
    public void m1055a(boolean resume) {
        this.f649i = resume;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:28:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:33:0x0116 A[Catch: Throwable -> 0x0141, all -> 0x014a, TryCatch #5 {, blocks: (B:6:0x0063, B:7:0x0077, B:8:0x008e, B:10:0x009b, B:12:0x00a2, B:13:0x00aa, B:18:0x00d1, B:21:0x00e5, B:20:0x00db, B:24:0x00f5, B:30:0x0102, B:33:0x0116, B:32:0x010c, B:35:0x011d), top: B:69:0x0063, outer: #6 }] */
    /* JADX WARN: Code duplicated, block: B:60:0x0102 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public Void call() throws Exception {
        FTPSClient ftpClient = new FTPSClient();
        ftpClient.setConnectTimeout(30000);
        ftpClient.setDataTimeout(30000);
        ftpClient.connect(this.f644d);
        ftpClient.login(this.f645e, this.f646f);
        ftpClient.setFileType(2);
        ftpClient.enterLocalPassiveMode();
        if (this.f649i) {
            ftpClient.setRestartOffset(this.f648h.length());
        }
        FTPFile ftpFile = ftpClient.mlistFile(this.f647g);
        BufferedInputStream inputStream = new BufferedInputStream(ftpClient.retrieveFileStream(ftpFile.getName()));
        Throwable th = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(this.f648h, this.f649i);
            Throwable th2 = null;
            try {
                try {
                    byte[] bytes = new byte[f641a];
                    updateProgress(outputStream.getChannel().position(), ftpFile.getSize());
                    while (true) {
                        int length = inputStream.read(bytes);
                        if (length == -1) {
                            break;
                        }
                        if (isCancelled()) {
                            ftpClient.abort();
                            break;
                        }
                        outputStream.write(bytes, 0, length);
                        updateProgress(outputStream.getChannel().position(), ftpFile.getSize());
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
                    ftpClient.completePendingCommand();
                    ftpClient.logout();
                    ftpClient.disconnect();
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
