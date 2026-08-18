package org.romstation.application.network;

import com.teamdev.jxbrowser.cookie.Cookie;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.time.Timestamp;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.romstation.application.C0060ag;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.network.e */
/* JADX INFO: compiled from: SyncCookieStore.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/network/e.class */
public class C0220e implements CookieStore {

    /* JADX INFO: renamed from: a */
    private static C0220e f586a;

    /* JADX INFO: renamed from: b */
    private final CookieStore f587b = new CookieManager().getCookieStore();

    /* JADX INFO: renamed from: c */
    private final Engine f588c;

    private C0220e(Engine engine) {
        this.f588c = engine;
    }

    /* JADX INFO: renamed from: a */
    public static void m968a(Engine engine) {
        if (f586a == null) {
            f586a = new C0220e(engine);
            CookieHandler.setDefault(new CookieManager(f586a, CookiePolicy.ACCEPT_ALL));
        }
    }

    /* JADX INFO: renamed from: a */
    public static C0220e m969a() {
        return f586a;
    }

    @Override // java.net.CookieStore
    public void add(URI uri, HttpCookie httpCookie) {
        this.f588c.cookieStore().set(m970a(httpCookie));
        this.f588c.cookieStore().persist();
        if (httpCookie.getName().equals("PHPSESSID") && C0217b.m960a().endsWith(httpCookie.getDomain())) {
            C0060ag.m228a().m235b(httpCookie.getValue());
        }
    }

    /* JADX INFO: renamed from: a */
    private Cookie m970a(HttpCookie httpCookie) {
        Cookie.Builder cookieBuilder = Cookie.newBuilder(httpCookie.getDomain());
        cookieBuilder.name(httpCookie.getName());
        cookieBuilder.value(httpCookie.getValue());
        cookieBuilder.path(httpCookie.getPath());
        cookieBuilder.secure(httpCookie.getSecure());
        cookieBuilder.httpOnly(httpCookie.isHttpOnly());
        if (httpCookie.getMaxAge() >= 0) {
            cookieBuilder.expirationTime(Timestamp.fromSeconds(Instant.now().getEpochSecond() + httpCookie.getMaxAge()));
        }
        return cookieBuilder.build();
    }

    /* JADX INFO: renamed from: a */
    private HttpCookie m971a(Cookie cookie) {
        try {
            HttpCookie httpCookie = new HttpCookie(cookie.name(), cookie.value());
            httpCookie.setDomain(cookie.domain());
            httpCookie.setPath(cookie.path());
            httpCookie.setHttpOnly(cookie.isHttpOnly());
            httpCookie.setSecure(cookie.isSecure());
            httpCookie.setVersion(0);
            if (cookie.expirationTime().toSeconds() >= 0) {
                httpCookie.setMaxAge(cookie.expirationTime().toSeconds() - Instant.now().getEpochSecond());
            }
            return httpCookie;
        } catch (Exception exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    @Override // java.net.CookieStore
    public List<HttpCookie> get(URI uri) {
        return (List) this.f588c.cookieStore().cookies(uri.toString()).stream().map(this::m971a).filter((v0) -> {
            return Objects.nonNull(v0);
        }).collect(Collectors.toList());
    }

    @Override // java.net.CookieStore
    public List<HttpCookie> getCookies() {
        return (List) this.f588c.cookieStore().cookies().stream().map(this::m971a).filter((v0) -> {
            return Objects.nonNull(v0);
        }).collect(Collectors.toList());
    }

    @Override // java.net.CookieStore
    public List<URI> getURIs() {
        return this.f587b.getURIs();
    }

    @Override // java.net.CookieStore
    public boolean remove(URI uri, HttpCookie cookie) {
        return this.f587b.remove(uri, cookie);
    }

    @Override // java.net.CookieStore
    public boolean removeAll() {
        return this.f587b.removeAll();
    }
}
