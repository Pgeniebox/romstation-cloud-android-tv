package org.romstation.application.task;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.romstation.application.C0013N;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.GameProfile;

/* JADX INFO: renamed from: org.romstation.application.task.z */
/* JADX INFO: compiled from: SystemLauncherContext.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/task/z.class */
public class C0258z {

    /* JADX INFO: renamed from: a */
    private Pattern f726a;

    /* JADX INFO: renamed from: b */
    private EmulatorProfile f727b;

    /* JADX INFO: renamed from: c */
    private GameProfile f728c;

    /* JADX INFO: renamed from: d */
    private String[] f729d;

    public C0258z(EmulatorProfile emulatorProfile, String... arguments) {
        this(emulatorProfile, null, arguments);
    }

    public C0258z(EmulatorProfile emulatorProfile, GameProfile gameProfile, String... arguments) {
        this.f726a = Pattern.compile("\\$\\{(?<cmd>[\\w.]*)}");
        this.f727b = emulatorProfile;
        this.f728c = gameProfile;
        this.f729d = arguments;
    }

    public EmulatorProfile getEmulatorProfile() {
        return this.f727b;
    }

    public void setEmulatorProfile(EmulatorProfile emulatorProfile) {
        this.f727b = emulatorProfile;
    }

    public GameProfile getGameProfile() {
        return this.f728c;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.f728c = gameProfile;
    }

    public String[] getArguments() {
        return this.f729d;
    }

    public void setArguments(String[] arguments) {
        this.f729d = arguments;
    }

    public String eval(String string) {
        if (string == null) {
            return null;
        }
        Matcher matcher = this.f726a.matcher(string);
        while (matcher.find()) {
            Function<C0258z, String> function = C0013N.m38a().system.getProperty(matcher.group("cmd"));
            String result = function == null ? "" : function.apply(this);
            if (result == null) {
                result = "";
            }
            string = matcher.replaceFirst(Matcher.quoteReplacement(result));
            matcher.reset(string);
        }
        return string;
    }
}
