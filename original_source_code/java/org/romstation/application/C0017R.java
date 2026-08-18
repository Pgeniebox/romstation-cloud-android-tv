package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.R */
/* JADX INFO: compiled from: I18N.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/R.class */
public class C0017R {
    public String getLocale() {
        return RomStation.m43c().getProperty("application.locale");
    }

    public String getMessage(String key) {
        return RomStation.m44d().getString(key);
    }
}
