package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import java.util.Locale;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/I18N.class */
@JsAccessible
public class I18N {
    public String getLocale() {
        return Locale.getDefault().toLanguageTag();
    }

    public String getString(String key) {
        return RomStation.m44d().getString(key);
    }
}
