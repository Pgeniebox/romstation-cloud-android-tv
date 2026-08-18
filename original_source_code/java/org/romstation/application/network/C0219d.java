package org.romstation.application.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.text.MessageFormat;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.network.d */
/* JADX INFO: compiled from: ServerResponse.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/network/d.class */
public class C0219d {

    /* JADX INFO: renamed from: a */
    private final JsonElement f585a;

    public C0219d(String string) throws ServerResponseException, InvalidServerResponseException {
        RomStation.m42b().info("Server Response: " + string);
        if (string == null) {
            throw new InvalidServerResponseException("null value");
        }
        try {
            JsonParser parser = new JsonParser();
            this.f585a = parser.parse(string);
            if (!m966c()) {
                throw new InvalidServerResponseException(MessageFormat.format("invalid response format: {0}", this.f585a));
            }
            if (m965a() <= 0) {
                throw new ServerResponseException(this);
            }
        } catch (JsonSyntaxException ex) {
            throw new InvalidServerResponseException(MessageFormat.format("invalid json syntax: {0}", string), ex);
        }
    }

    /* JADX INFO: renamed from: a */
    public int m965a() {
        return this.f585a.getAsJsonObject().get("error").getAsInt();
    }

    /* JADX INFO: renamed from: c */
    private boolean m966c() {
        return (this.f585a.isJsonNull() || !this.f585a.isJsonObject() || this.f585a.getAsJsonObject().get("error") == null) ? false : true;
    }

    /* JADX INFO: renamed from: b */
    public JsonObject m967b() {
        return this.f585a.getAsJsonObject();
    }

    public String toString() {
        return this.f585a.toString();
    }
}
