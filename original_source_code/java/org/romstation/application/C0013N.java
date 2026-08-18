package org.romstation.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jruby.RubyObject;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.romstation.application.database.entity.Script;

/* JADX INFO: renamed from: org.romstation.application.N */
/* JADX INFO: compiled from: ScriptManager.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/N.class */
public class C0013N {

    /* JADX INFO: renamed from: a */
    private static final C0014O f12a = new C0014O();

    private C0013N() {
    }

    /* JADX INFO: renamed from: a */
    public static C0014O m38a() {
        return f12a;
    }

    /* JADX INFO: renamed from: a */
    public static RubyObject m39a(Script script) throws IOException {
        return m40a(Paths.get(script.getPath(), new String[0]));
    }

    /* JADX INFO: renamed from: a */
    public static RubyObject m40a(Path filename) throws IOException {
        byte[] bytes = Files.readAllBytes(filename);
        ScriptingContainer scriptingContainer = new ScriptingContainer(LocalContextScope.SINGLETON);
        scriptingContainer.setScriptFilename(filename.toString());
        scriptingContainer.put("$romstation", f12a);
        String script = String.format("Class.new{%s}.new", new String(bytes));
        return (RubyObject) scriptingContainer.runScriptlet(script);
    }
}
