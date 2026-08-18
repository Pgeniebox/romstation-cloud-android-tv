package org.romstation.application;

import com.teamdev.jxbrowser.browser.Browser;

/* JADX INFO: renamed from: org.romstation.application.cQ */
/* JADX INFO: compiled from: CreateBrowserTabFromBrowser.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cQ.class */
public class C0151cQ implements InterfaceC0103bV {

    /* JADX INFO: renamed from: a */
    private final Browser f343a;

    /* JADX INFO: renamed from: b */
    private final boolean f344b;

    public C0151cQ(Browser browser) {
        this(browser, false);
    }

    public C0151cQ(Browser browser, boolean selected) {
        this.f343a = browser;
        this.f344b = selected;
    }

    /* JADX INFO: renamed from: a */
    public Browser m659a() {
        return this.f343a;
    }

    /* JADX INFO: renamed from: b */
    public boolean m660b() {
        return this.f344b;
    }
}
