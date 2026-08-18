package org.romstation.application.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.romstation.application.C0004E;
import org.romstation.application.C0060ag;
import org.romstation.application.RomStation;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* JADX INFO: renamed from: org.romstation.application.task.h */
/* JADX INFO: compiled from: EmulatorFileDownloadContext.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/h.class */
public class C0240h {

    /* JADX INFO: renamed from: a */
    private JsonObject f627a;

    /* JADX INFO: renamed from: b */
    private JsonArray f628b;

    /* JADX INFO: renamed from: c */
    private Document f629c;

    /* JADX INFO: renamed from: d */
    private Document f630d;

    public C0240h(int emulatorFileID) throws NetworkOfflineException, EmulatorFileDownloadContextException {
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/emulator/get_file.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("arch", Integer.valueOf(C0004E.m11d().m6a())).m974a("efid", Integer.valueOf(emulatorFileID));
            C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
            C0216a request = new C0216a(builder.m973b());
            m1017a(request.m959a(post).m967b());
        } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException e) {
            throw new EmulatorFileDownloadContextException();
        }
    }

    public C0240h(JsonObject jsonObject) throws EmulatorFileDownloadContextException {
        m1017a(jsonObject);
    }

    public C0240h(JsonObject server, JsonArray systems, Document emulator, Document emulatorFile) {
        this.f627a = server;
        this.f628b = systems;
        this.f629c = emulator;
        this.f630d = emulatorFile;
    }

    /* JADX INFO: renamed from: a */
    private void m1017a(JsonObject jsonObject) throws EmulatorFileDownloadContextException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.f627a = jsonObject.getAsJsonObject("download");
            this.f628b = jsonObject.getAsJsonArray("systems");
            this.f629c = m1018a(documentBuilder, jsonObject.get("emulator").getAsString());
            this.f630d = m1018a(documentBuilder, jsonObject.get("file").getAsString());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new EmulatorFileDownloadContextException();
        }
    }

    /* JADX INFO: renamed from: a */
    private Document m1018a(DocumentBuilder documentBuilder, String xmlString) throws ParserConfigurationException, SAXException, IOException {
        StringReader stringReader = new StringReader(xmlString);
        Throwable th = null;
        try {
            try {
                Document document = documentBuilder.parse(new InputSource(stringReader));
                if (stringReader != null) {
                    if (0 != 0) {
                        try {
                            stringReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        stringReader.close();
                    }
                }
                return document;
            } catch (Throwable th3) {
                th = th3;
                throw th3;
            }
        } catch (Throwable th4) {
            if (stringReader != null) {
                if (th != null) {
                    try {
                        stringReader.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                } else {
                    stringReader.close();
                }
            }
            throw th4;
        }
    }

    /* JADX INFO: renamed from: a */
    public JsonObject m1019a() {
        return this.f627a;
    }

    /* JADX INFO: renamed from: b */
    public JsonArray m1020b() {
        return this.f628b;
    }

    /* JADX INFO: renamed from: c */
    public Document m1021c() {
        return this.f629c;
    }

    /* JADX INFO: renamed from: d */
    public Document m1022d() {
        return this.f630d;
    }
}
