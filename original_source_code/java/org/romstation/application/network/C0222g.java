package org.romstation.application.network;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.network.g */
/* JADX INFO: compiled from: URLQuery.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/network/g.class */
public class C0222g {

    /* JADX INFO: renamed from: a */
    private final Map<String, Object> f591a = new HashMap();

    public C0222g() {
    }

    public C0222g(String string) {
        if (string == null) {
            return;
        }
        for (String param : string.split("&")) {
            String[] pair = param.split("=");
            String key = pair[0];
            try {
                String value = pair.length > 1 ? URLDecoder.decode(pair[1], "UTF-8") : null;
                this.f591a.put(key, value);
            } catch (UnsupportedEncodingException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public C0222g m974a(String key, Object value) {
        this.f591a.put(key, value);
        return this;
    }

    /* JADX INFO: renamed from: a */
    public Map<String, Object> m975a() {
        return this.f591a;
    }

    public String toString() {
        return (String) this.f591a.entrySet().stream().filter(e -> {
            return e.getValue() != null;
        }).map(entry -> {
            String value = null;
            try {
                value = URLEncoder.encode(entry.getValue().toString(), "UTF-8");
            } catch (UnsupportedEncodingException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
            return ((String) entry.getKey()) + "=" + value;
        }).collect(Collectors.joining("&"));
    }
}
