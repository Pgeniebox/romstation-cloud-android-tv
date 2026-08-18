package org.romstation.application.vpn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.romstation.application.C0004E;
import org.romstation.application.EnumC0002C;
import org.romstation.application.EnumC0003D;
import org.romstation.application.RomStation;

/* JADX INFO: renamed from: org.romstation.application.vpn.a */
/* JADX INFO: compiled from: VpnConnection.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/vpn/a.class */
public class C0275a {

    /* JADX INFO: renamed from: a */
    private static final int f861a = 30000;

    /* JADX INFO: renamed from: b */
    private static final File f862b;

    /* JADX INFO: renamed from: c */
    private final Path f863c;

    /* JADX INFO: renamed from: d */
    private final Path f864d;

    /* JADX INFO: renamed from: e */
    private final String f865e;

    /* JADX INFO: renamed from: f */
    private String f866f;

    /* JADX INFO: renamed from: g */
    private EnumC0278d f867g;

    /* JADX INFO: renamed from: h */
    private InterfaceC0276b f868h;

    /* JADX INFO: renamed from: i */
    private Thread f869i;

    /* JADX INFO: renamed from: j */
    private Process f870j;

    /* JADX INFO: renamed from: k */
    private ServerSocket f871k;

    /* JADX INFO: renamed from: l */
    private Socket f872l;

    static {
        f862b = new File(C0004E.m10c() == EnumC0003D.WINDOWS ? "NUL" : "/dev/null");
    }

    public C0275a(Path cert, Path pass) {
        this(cert, pass, null);
    }

    public C0275a(Path cert, Path pass, String deviceNode) {
        this.f863c = cert;
        this.f864d = pass;
        this.f865e = deviceNode;
    }

    /* JADX INFO: renamed from: a */
    public String m1643a() {
        return this.f866f;
    }

    /* JADX INFO: renamed from: b */
    public EnumC0278d m1644b() {
        return this.f867g;
    }

    /* JADX INFO: renamed from: c */
    public InterfaceC0276b m1645c() {
        return this.f868h;
    }

    /* JADX INFO: renamed from: a */
    public void m1646a(InterfaceC0276b eventHandler) {
        this.f868h = eventHandler;
    }

    /* JADX INFO: renamed from: d */
    public void m1647d() {
        if (this.f869i == null) {
            this.f869i = new Thread(this::m1648g);
            this.f869i.start();
        }
    }

    /* JADX INFO: renamed from: g */
    private void m1648g() {
        try {
            try {
                this.f871k = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"));
                this.f871k.setSoTimeout(f861a);
                String osArch = C0004E.m12e() == EnumC0002C.X64 ? "win64" : "win32";
                String osName = System.getProperty("os.name").equals("Windows 10") ? "win10" : "win7";
                Path directory = Paths.get("openvpn", osArch, osName);
                ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
                processBuilder.command(directory.resolve("openvpn.exe").toString(), "--config", this.f863c.toString(), "--auth-user-pass", this.f864d.toString(), "--management", "127.0.0.1", String.valueOf(this.f871k.getLocalPort()), "--management-client", "--management-hold");
                if (this.f865e != null) {
                    processBuilder.command().addAll(Arrays.asList("--dev-node", this.f865e));
                }
                processBuilder.directory(directory.toFile());
                processBuilder.redirectErrorStream(true);
                processBuilder.redirectInput(f862b);
                processBuilder.redirectOutput(f862b);
                this.f870j = processBuilder.start();
                this.f872l = this.f871k.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.f872l.getInputStream()));
                Throwable th = null;
                try {
                    try {
                        Pattern commandPattern = Pattern.compile("^>(?<cmd>[^:]+)");
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                if (bufferedReader != null) {
                                    if (0 != 0) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                    } else {
                                        bufferedReader.close();
                                    }
                                }
                                if (this.f871k == null || this.f871k.isClosed()) {
                                    return;
                                }
                                try {
                                    this.f871k.close();
                                    return;
                                } catch (IOException exception) {
                                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                                    return;
                                }
                            }
                            Matcher matcher = commandPattern.matcher(line);
                            if (matcher.find()) {
                                switch (matcher.group("cmd")) {
                                    case "HOLD":
                                        m1651a("state on\nhold release\n");
                                        break;
                                    case "STATE":
                                        String[] strings = line.split(",");
                                        switch (strings[1]) {
                                            case "WAIT":
                                                this.f867g = EnumC0278d.WAIT;
                                                break;
                                            case "AUTH":
                                                this.f867g = EnumC0278d.AUTH;
                                                break;
                                            case "GET_CONFIG":
                                                this.f867g = EnumC0278d.GET_CONFIG;
                                                break;
                                            case "ASSIGN_IP":
                                                this.f866f = strings[3];
                                                this.f867g = EnumC0278d.ASSIGN_IP;
                                                break;
                                            case "ADD_ROUTES":
                                                this.f867g = EnumC0278d.ADD_ROUTES;
                                                break;
                                            case "CONNECTED":
                                                this.f867g = EnumC0278d.CONNECTED;
                                                break;
                                            case "RECONNECTING":
                                                this.f867g = EnumC0278d.RECONNECTING;
                                                break;
                                            case "EXITING":
                                                this.f867g = EnumC0278d.EXITING;
                                                break;
                                        }
                                        if (this.f868h != null) {
                                            String description = strings.length >= 3 ? strings[2] : null;
                                            this.f868h.mo390a(this.f867g, description);
                                            break;
                                        } else {
                                            break;
                                        }
                                        break;
                                }
                            }
                        }
                    } catch (Throwable th3) {
                        if (bufferedReader != null) {
                            if (th != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th4) {
                                    th.addSuppressed(th4);
                                }
                            } else {
                                bufferedReader.close();
                            }
                        }
                        throw th3;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    throw th5;
                }
            } catch (IOException exception2) {
                if (this.f868h != null) {
                    this.f868h.mo391a(new VpnException(exception2));
                }
                if (this.f871k == null || this.f871k.isClosed()) {
                    return;
                }
                try {
                    this.f871k.close();
                } catch (IOException exception3) {
                    RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                }
            }
        } catch (Throwable th6) {
            if (this.f871k != null && !this.f871k.isClosed()) {
                try {
                    this.f871k.close();
                } catch (IOException exception4) {
                    RomStation.m42b().log(Level.SEVERE, exception4.getMessage(), (Throwable) exception4);
                }
            }
            throw th6;
        }
    }

    /* JADX INFO: renamed from: e */
    public boolean m1649e() {
        return (this.f871k == null || this.f871k.isClosed()) ? false : true;
    }

    /* JADX INFO: renamed from: f */
    public void m1650f() throws IOException {
        if (this.f872l != null && this.f872l.isConnected()) {
            m1652a(EnumC0277c.SIGTERM);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1651a(String command) throws IOException {
        OutputStream outputStream = this.f872l.getOutputStream();
        outputStream.write(command.getBytes());
        outputStream.flush();
    }

    /* JADX INFO: renamed from: a */
    private void m1652a(EnumC0277c signal) throws IOException {
        switch (signal) {
            case SIGHUP:
                m1651a("signal SIGHUP\n");
                break;
            case SIGUSR1:
                m1651a("signal SIGUSR1\n");
                break;
            case SIGUSR2:
                m1651a("signal SIGUSR2\n");
                break;
            case SIGTERM:
                m1651a("signal SIGTERM\n");
                break;
        }
    }
}
