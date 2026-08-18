package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/Path.class */
@JsAccessible
public class Path {
    public String currentDirectory() {
        return java.lang.System.getProperty("user.dir");
    }

    public String get(String first, String... more) {
        return Paths.get(first, more).toString();
    }

    public String getAbsolutePath(String path) {
        return Paths.get(path, new String[0]).toAbsolutePath().toString();
    }

    public String parent(String path) {
        return Paths.get(path, new String[0]).getParent().toString();
    }

    public String resolve(String root, String other) {
        return Paths.get(root, new String[0]).resolve(other).toString();
    }

    public String relativize(String path, String other) {
        return Paths.get(path, new String[0]).relativize(Paths.get(other, new String[0])).toString();
    }

    public String getFileName(String path) {
        return Paths.get(path, new String[0]).getFileName().toString();
    }

    public boolean exists(String path) {
        return Files.exists(Paths.get(path, new String[0]), new LinkOption[0]);
    }
}
