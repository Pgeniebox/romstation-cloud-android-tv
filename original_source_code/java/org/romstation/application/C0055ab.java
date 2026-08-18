package org.romstation.application;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;

/* JADX INFO: renamed from: org.romstation.application.ab */
/* JADX INFO: compiled from: Game.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ab.class */
public class C0055ab {

    /* JADX INFO: renamed from: a */
    private int f86a;

    /* JADX INFO: renamed from: b */
    private int f87b;

    /* JADX INFO: renamed from: c */
    private String f88c;

    /* JADX INFO: renamed from: d */
    private String f89d;

    /* JADX INFO: renamed from: e */
    private String f90e;

    /* JADX INFO: renamed from: f */
    private String f91f;

    /* JADX INFO: renamed from: g */
    private int f92g;

    /* JADX INFO: renamed from: h */
    private int f93h;

    /* JADX INFO: renamed from: i */
    private String f94i;

    /* JADX INFO: renamed from: j */
    private String f95j;

    /* JADX INFO: renamed from: k */
    private String f96k;

    /* JADX INFO: renamed from: l */
    private String f97l;

    /* JADX INFO: renamed from: m */
    private String f98m;

    /* JADX INFO: renamed from: n */
    private String f99n;

    /* JADX INFO: renamed from: o */
    private boolean f100o;

    /* JADX INFO: renamed from: p */
    private String f101p;

    /* JADX INFO: renamed from: q */
    private String f102q;

    /* JADX INFO: renamed from: r */
    private long f103r;

    public C0055ab(Path root, ResultSet resultSet) throws SQLException {
        this.f86a = resultSet.getInt("id");
        this.f87b = resultSet.getInt("rs_id");
        this.f88c = resultSet.getString("title");
        this.f89d = resultSet.getString("console");
        this.f90e = resultSet.getString("region");
        this.f91f = resultSet.getString("genre");
        this.f92g = resultSet.getInt("players");
        this.f93h = resultSet.getInt("date");
        this.f94i = resultSet.getString("developer");
        this.f95j = resultSet.getString("publisher");
        this.f96k = resultSet.getString("tags");
        this.f97l = resultSet.getString("url");
        this.f98m = resultSet.getString("filename");
        if (this.f98m != null) {
            this.f98m = this.f98m.replace("${basedir}", root.toString().concat("/"));
        }
        this.f99n = resultSet.getString("screenshot");
        if (this.f99n != null) {
            this.f99n = this.f99n.replace("${basedir}", root.toString().concat("/"));
        }
        this.f100o = resultSet.getBoolean("link");
        this.f101p = resultSet.getString("executable");
        if (this.f98m != null && this.f101p != null) {
            this.f101p = this.f101p.replace("\"", "").replaceAll("(?i)%GamePath%", Matcher.quoteReplacement(this.f98m));
        }
        this.f102q = resultSet.getString("parameters");
        this.f103r = resultSet.getLong("last_use");
    }

    public int getId() {
        return this.f86a;
    }

    public void setId(int id) {
        this.f86a = id;
    }

    public int getRsId() {
        return this.f87b;
    }

    public void setRsId(int rsId) {
        this.f87b = rsId;
    }

    public String getTitle() {
        return this.f88c;
    }

    public void setTitle(String title) {
        this.f88c = title;
    }

    public String getConsole() {
        return this.f89d;
    }

    public void setConsole(String console) {
        this.f89d = console;
    }

    public String getRegion() {
        return this.f90e;
    }

    public void setRegion(String region) {
        this.f90e = region;
    }

    public String getGenre() {
        return this.f91f;
    }

    public void setGenre(String genre) {
        this.f91f = genre;
    }

    public int getPlayers() {
        return this.f92g;
    }

    public void setPlayers(int players) {
        this.f92g = players;
    }

    public int getDate() {
        return this.f93h;
    }

    public void setDate(int date) {
        this.f93h = date;
    }

    public String getDeveloper() {
        return this.f94i;
    }

    public void setDeveloper(String developer) {
        this.f94i = developer;
    }

    public String getPublisher() {
        return this.f95j;
    }

    public void setPublisher(String publisher) {
        this.f95j = publisher;
    }

    public String getTags() {
        return this.f96k;
    }

    public void setTags(String tags) {
        this.f96k = tags;
    }

    public String getUrl() {
        return this.f97l;
    }

    public void setUrl(String url) {
        this.f97l = url;
    }

    public Path getDirectory() {
        Path path = Paths.get(this.f98m, new String[0]);
        return Files.isDirectory(path, new LinkOption[0]) ? path : path.getParent();
    }

    public String getFilename() {
        return this.f98m;
    }

    public void setFilename(String filename) {
        this.f98m = filename;
    }

    public String getScreenshot() {
        return this.f99n;
    }

    public void setScreenshot(String screenshot) {
        this.f99n = screenshot;
    }

    public boolean isLink() {
        return this.f100o;
    }

    public void setLink(boolean link) {
        this.f100o = link;
    }

    public Path getRelativeExecutable() {
        return Paths.get(this.f98m, new String[0]).relativize(Paths.get(this.f101p, new String[0]));
    }

    public String getExecutable() {
        return this.f101p;
    }

    public void setExecutable(String executable) {
        this.f101p = executable;
    }

    public String getParameters() {
        return this.f102q;
    }

    public void setParameters(String parameters) {
        this.f102q = parameters;
    }

    public long getLastUse() {
        return this.f103r;
    }

    public void setLastUse(long lastUse) {
        this.f103r = lastUse;
    }
}
