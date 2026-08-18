package org.romstation.application;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

/* JADX INFO: renamed from: org.romstation.application.bA */
/* JADX INFO: compiled from: MessageParser.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bA.class */
public class C0082bA {

    /* JADX INFO: renamed from: a */
    private static final Pattern f160a = Pattern.compile("(?<word>([^\\s]+)|\\s)");

    /* JADX INFO: renamed from: b */
    private static final Pattern f161b = Pattern.compile("(?<url>^http(s)?:\\/\\/.+)");

    /* JADX INFO: renamed from: c */
    private final List<Node> f162c = new LinkedList();

    /* JADX INFO: renamed from: d */
    private final StringBuilder f163d = new StringBuilder();

    /* JADX INFO: renamed from: e */
    private final String f164e;

    /* JADX INFO: renamed from: f */
    private final InterfaceC0083bB f165f;

    public C0082bA(String text, InterfaceC0083bB eventHandler) {
        this.f164e = text;
        this.f165f = eventHandler;
    }

    /* JADX INFO: renamed from: a */
    public static List<Node> m311a(String text, InterfaceC0083bB eventHandler) {
        C0082bA messageParser = new C0082bA(text, eventHandler);
        messageParser.m316a();
        return messageParser.f162c;
    }

    /* JADX INFO: renamed from: b */
    private void m312b() {
        if (this.f163d.length() != 0) {
            this.f162c.add(m314b(this.f163d.toString()));
            this.f163d.setLength(0);
        }
    }

    /* JADX INFO: renamed from: a */
    private boolean m313a(String string) {
        return f161b.matcher(string).matches();
    }

    /* JADX INFO: renamed from: b */
    private Text m314b(String string) {
        return new Text(string);
    }

    /* JADX INFO: renamed from: c */
    private Hyperlink m315c(String string) {
        Hyperlink hyperlink = new Hyperlink(string);
        hyperlink.setOnAction(event -> {
            this.f165f.mo318a((Hyperlink) event.getSource());
        });
        return hyperlink;
    }

    /* JADX INFO: renamed from: a */
    public List<Node> m316a() {
        Matcher matcher = f160a.matcher(this.f164e);
        while (matcher.find()) {
            String word = matcher.group("word");
            if (m313a(word)) {
                m312b();
                this.f162c.add(m315c(word));
            } else {
                this.f163d.append(word);
            }
        }
        m312b();
        return this.f162c;
    }
}
