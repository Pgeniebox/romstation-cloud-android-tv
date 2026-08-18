package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;

/* JADX INFO: renamed from: org.romstation.application.api.a */
/* JADX INFO: compiled from: API.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/a.class */
public class C0070a {

    /* JADX INFO: renamed from: a */
    private static C0070a f147a;
    public final I18N i18n = new I18N();
    public final Library library = new Library();
    public final Database database = new Database();
    public final System system = new System();
    public final Runtime runtime = new Runtime();
    public final Path path = new Path();
    public final File file = new File();
    public final Zip zip = new Zip();
    public final Dialog dialog = new Dialog();
    public final Netplay netplay = new Netplay();

    private C0070a() {
    }

    public static C0070a getInstance() {
        if (f147a == null) {
            f147a = new C0070a();
        }
        return f147a;
    }

    @JsAccessible
    public I18N i18n() {
        return this.i18n;
    }

    @JsAccessible
    public Library library() {
        return this.library;
    }

    @JsAccessible
    public Database database() {
        return this.database;
    }

    @JsAccessible
    public System system() {
        return this.system;
    }

    @JsAccessible
    public Runtime runtime() {
        return this.runtime;
    }

    @JsAccessible
    public Path path() {
        return this.path;
    }

    @JsAccessible
    public File file() {
        return this.file;
    }

    @JsAccessible
    public Zip zip() {
        return this.zip;
    }

    @JsAccessible
    public Dialog dialog() {
        return this.dialog;
    }

    @JsAccessible
    public Netplay netplay() {
        return this.netplay;
    }
}
