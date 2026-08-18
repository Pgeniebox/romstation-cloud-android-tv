package org.romstation.application;

import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.InvalidLicenseException;
import com.teamdev.jxbrowser.engine.ProprietaryFeature;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.net.callback.CanSetCookieCallback;
import com.teamdev.jxbrowser.net.callback.VerifyCertificateCallback;
import com.teamdev.jxbrowser.zoom.ZoomLevel;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.logging.Level;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0220e;

/* JADX INFO: renamed from: org.romstation.application.a */
/* JADX INFO: compiled from: BrowserEngine.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/a.class */
public class C0027a {

    /* JADX INFO: renamed from: a */
    private static Engine f48a;

    /* JADX INFO: renamed from: a */
    public static void m90a() {
        try {
            EngineOptions.Builder builderNewBuilder = EngineOptions.newBuilder(RenderingMode.OFF_SCREEN);
            builderNewBuilder.licenseKey("1BNDIEOFAZ0IY7BE4CXDS76GZM53VMLF3065I6EWTV3U7K80T643A8XEXPHPCOQ00XVG6G");
            builderNewBuilder.enableProprietaryFeature(ProprietaryFeature.H_264);
            builderNewBuilder.enableProprietaryFeature(ProprietaryFeature.AAC);
            builderNewBuilder.enableAutoplay();
            builderNewBuilder.userDataDir(Paths.get("browser", new String[0]));
            builderNewBuilder.remoteDebuggingPort(9222);
            f48a = Engine.newInstance(builderNewBuilder.build());
            f48a.network().acceptLanguage(Locale.getDefault().toString());
            f48a.zoomLevels().defaultLevel(ZoomLevel.of(Double.parseDouble(RomStation.m43c().getProperty("browser.defaultZoomLevel"))));
            f48a.cookieStore().cookies().stream().filter(cookie -> {
                return cookie.expirationTime().toMillis() == 0;
            }).forEach(cookie2 -> {
                f48a.cookieStore().delete(cookie2);
            });
            f48a.cookieStore().persist();
            f48a.network().set(CanSetCookieCallback.class, params -> {
                if (params.cookie().name().equals("PHPSESSID") && C0217b.m960a().endsWith(params.cookie().domain())) {
                    C0060ag.m228a().m235b(params.cookie().value());
                }
                return CanSetCookieCallback.Response.can();
            });
            f48a.network().set(VerifyCertificateCallback.class, params2 -> {
                if (System.getProperty("os.name").equals("Windows 7")) {
                    return VerifyCertificateCallback.Response.valid();
                }
                return VerifyCertificateCallback.Response.defaultAction();
            });
            C0220e.m968a(f48a);
        } catch (InvalidLicenseException e) {
            RomStation.m42b().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /* JADX INFO: renamed from: b */
    public static Engine m91b() {
        return f48a;
    }

    /* JADX INFO: renamed from: c */
    public static boolean m92c() {
        return f48a != null;
    }

    /* JADX INFO: renamed from: d */
    public static void m93d() {
        f48a.close();
    }
}
