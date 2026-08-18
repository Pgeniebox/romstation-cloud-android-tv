package org.romstation.application.p000io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: renamed from: org.romstation.application.io.a */
/* JADX INFO: compiled from: FileUtil.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/io/a.class */
public class C0207a {
    /* JADX INFO: renamed from: a */
    public static void m828a(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() { // from class: org.romstation.application.io.a.1
            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!Files.isWritable(file)) {
                    file.toFile().setWritable(true);
                }
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public static String m829a(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", Double.valueOf(bytes / Math.pow(unit, exp)), pre);
    }

    /* JADX INFO: renamed from: b */
    public static long m830b(Path path) {
        final AtomicLong size = new AtomicLong(0L);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() { // from class: org.romstation.application.io.a.2
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.out.println("skipped: " + file + " (" + exc + ")");
                    return FileVisitResult.CONTINUE;
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    if (exc != null) {
                        System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            return size.get();
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }
    }

    /* JADX INFO: renamed from: a */
    public static String m831a(String filename) {
        return filename.replaceAll("[\\\\/\"*?<>|]", "").replace(":", "-");
    }
}
