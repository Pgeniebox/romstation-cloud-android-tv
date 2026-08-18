package org.romstation.application.task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.MessageFormat;
import java.util.Enumeration;
import javafx.concurrent.Task;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.task.B */
/* JADX INFO: compiled from: UnzipTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/B.class */
public class C0231B extends Task<Boolean> {

    /* JADX INFO: renamed from: a */
    private final Path f608a;

    /* JADX INFO: renamed from: b */
    private final Path f609b;

    /* JADX INFO: renamed from: c */
    private static final int f610c = 8096;

    public C0231B(Path source, Path target) {
        this.f608a = source;
        this.f609b = target;
    }

    /* JADX INFO: renamed from: a */
    private long m1005a(ZipFile zipFile) {
        long size = 0;
        Enumeration<ZipArchiveEntry> zipFileEntries = zipFile.getEntries();
        while (zipFileEntries.hasMoreElements()) {
            size += zipFileEntries.nextElement().getSize();
        }
        return size;
    }

    protected void scheduled() {
        updateTitle(RomStation.m44d().getString("unzip.task.title"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v57, types: [java.io.BufferedOutputStream] */
    /* JADX WARN: Type inference failed for: r0v68, types: [int] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r1v26, types: [long] */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Boolean call() throws Exception {
        ZipFile zipFile = new ZipFile(this.f608a.toFile(), "IBM437");
        Throwable th = null;
        try {
            byte[] bArr = new byte[f610c];
            long jM1005a = m1005a(zipFile);
            ?? r13 = 0;
            Enumeration entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) entries.nextElement();
                updateMessage(MessageFormat.format(RomStation.m44d().getString("unzip.task.message"), zipArchiveEntry.getName()));
                File file = new File(this.f609b.toString(), zipArchiveEntry.getName());
                if (zipArchiveEntry.isDirectory() || zipArchiveEntry.getName().endsWith("\\")) {
                    if (!file.isDirectory() && !file.mkdirs()) {
                        throw new IOException("failed to create directory " + file);
                    }
                } else {
                    File parentFile = file.getParentFile();
                    if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                        throw new IOException("failed to create directory " + parentFile);
                    }
                    if (zipArchiveEntry.isUnixSymlink()) {
                        Files.createSymbolicLink(file.toPath(), Paths.get(zipFile.getUnixSymlink(zipArchiveEntry), new String[0]), new FileAttribute[0]);
                    } else {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipArchiveEntry));
                        Throwable th2 = null;
                        try {
                            ?? bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), f610c);
                            Throwable th3 = null;
                            r13 = r13;
                            while (true) {
                                try {
                                    ?? r0 = bufferedInputStream.read(bArr);
                                    if (r0 != -1) {
                                        if (!isCancelled()) {
                                            bufferedOutputStream.write(bArr, 0, r0);
                                            long j = r13 + ((long) r0);
                                            r13 = r0;
                                            updateProgress(j, jM1005a);
                                        } else {
                                            if (bufferedOutputStream != 0) {
                                                if (0 != 0) {
                                                    try {
                                                        bufferedOutputStream.close();
                                                    } catch (Throwable th4) {
                                                        th3.addSuppressed(th4);
                                                    }
                                                } else {
                                                    bufferedOutputStream.close();
                                                }
                                            }
                                            if (bufferedInputStream != null) {
                                                if (0 != 0) {
                                                    try {
                                                        bufferedInputStream.close();
                                                    } catch (Throwable th5) {
                                                        th2.addSuppressed(th5);
                                                    }
                                                } else {
                                                    bufferedInputStream.close();
                                                }
                                            }
                                            if (zipFile != null) {
                                                if (0 != 0) {
                                                    try {
                                                        zipFile.close();
                                                    } catch (Throwable th6) {
                                                        th.addSuppressed(th6);
                                                    }
                                                } else {
                                                    zipFile.close();
                                                }
                                            }
                                            return false;
                                        }
                                    } else {
                                        if (bufferedOutputStream != 0) {
                                            if (0 != 0) {
                                                try {
                                                    bufferedOutputStream.close();
                                                } catch (Throwable th7) {
                                                    th3.addSuppressed(th7);
                                                }
                                            } else {
                                                bufferedOutputStream.close();
                                            }
                                        }
                                        if (bufferedInputStream != null) {
                                            if (0 != 0) {
                                                try {
                                                    bufferedInputStream.close();
                                                } catch (Throwable th8) {
                                                    th2.addSuppressed(th8);
                                                }
                                            } else {
                                                bufferedInputStream.close();
                                            }
                                        }
                                        if (!FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
                                            break;
                                        }
                                        Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));
                                        break;
                                    }
                                } catch (Throwable th9) {
                                    if (bufferedOutputStream != 0) {
                                        if (0 != 0) {
                                            try {
                                                bufferedOutputStream.close();
                                            } catch (Throwable th10) {
                                                th3.addSuppressed(th10);
                                            }
                                        } else {
                                            bufferedOutputStream.close();
                                        }
                                    }
                                    throw th9;
                                }
                            }
                        } catch (Throwable th11) {
                            if (bufferedInputStream != null) {
                                if (0 != 0) {
                                    try {
                                        bufferedInputStream.close();
                                    } catch (Throwable th12) {
                                        th2.addSuppressed(th12);
                                    }
                                } else {
                                    bufferedInputStream.close();
                                }
                            }
                            throw th11;
                        }
                    }
                }
                r13 = r13;
            }
            if (zipFile != null) {
                if (0 != 0) {
                    try {
                        zipFile.close();
                    } catch (Throwable th13) {
                        th.addSuppressed(th13);
                    }
                } else {
                    zipFile.close();
                }
            }
            return true;
        } catch (Throwable th14) {
            if (zipFile != null) {
                if (0 != 0) {
                    try {
                        zipFile.close();
                    } catch (Throwable th15) {
                        th.addSuppressed(th15);
                    }
                } else {
                    zipFile.close();
                }
            }
            throw th14;
        }
    }
}
