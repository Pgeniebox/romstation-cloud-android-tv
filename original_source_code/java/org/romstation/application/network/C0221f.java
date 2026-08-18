package org.romstation.application.network;

import java.net.MalformedURLException;
import java.net.URL;

/* JADX INFO: renamed from: org.romstation.application.network.f */
/* JADX INFO: compiled from: URLBuilder.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/network/f.class */
public class C0221f {

    /* JADX INFO: renamed from: a */
    private final URL f589a;

    /* JADX INFO: renamed from: b */
    private final C0222g f590b;

    public C0221f(String url) throws MalformedURLException {
        this(new URL(url));
    }

    public C0221f(URL url) {
        this.f589a = url;
        this.f590b = new C0222g(url.getQuery());
    }

    /* JADX INFO: renamed from: a */
    public C0222g m972a() {
        return this.f590b;
    }

    /* JADX INFO: renamed from: b */
    public URL m973b() throws MalformedURLException {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f589a.getProtocol()).append("://").append(this.f589a.getHost());
        if (this.f589a.getPort() != -1) {
            sb.append(":").append(this.f589a.getPort());
        }
        sb.append(this.f589a.getPath());
        if (!this.f590b.m975a().isEmpty()) {
            sb.append("?").append(this.f590b);
        }
        if (this.f589a.getRef() != null) {
            sb.append("#").append(this.f589a.getRef());
        }
        return new URL(sb.toString());
    }
}
