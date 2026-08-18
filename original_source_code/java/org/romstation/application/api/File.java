package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.Arrays;
import org.romstation.application.p000io.C0207a;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/File.class */
@JsAccessible
public class File {
    public boolean exists(String path) {
        return Files.exists(Paths.get(path, new String[0]), new LinkOption[0]);
    }

    public boolean isDirectory(String path) {
        return Files.isDirectory(Paths.get(path, new String[0]), new LinkOption[0]);
    }

    public boolean isFile(String path) {
        return Files.isRegularFile(Paths.get(path, new String[0]), new LinkOption[0]);
    }

    public String[] list(String path) throws IOException {
        return (String[]) Files.list(Paths.get(path, new String[0])).map((v0) -> {
            return v0.toString();
        }).toArray(x$0 -> {
            return new String[x$0];
        });
    }

    public String[] walk(String path) throws IOException {
        return (String[]) Files.walk(Paths.get(path, new String[0]), new FileVisitOption[0]).map((v0) -> {
            return v0.toString();
        }).toArray(x$0 -> {
            return new String[x$0];
        });
    }

    public void deleteFile(String path) throws IOException {
        Files.delete(Paths.get(path, new String[0]));
    }

    public void deleteDirectory(String path) throws IOException {
        C0207a.m828a(Paths.get(path, new String[0]));
    }

    public String[] read(String path) throws IOException {
        return (String[]) Files.readAllLines(Paths.get(path, new String[0])).toArray(new String[0]);
    }

    public String readString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path, new String[0])));
    }

    public void write(String path, String string) throws IOException {
        Files.write(Paths.get(path, new String[0]), string.getBytes(), new OpenOption[0]);
    }

    public void write(String path, String[] lines) throws IOException {
        Files.write(Paths.get(path, new String[0]), Arrays.asList(lines), new OpenOption[0]);
    }
}
