package org.romstation.application.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;
import org.romstation.application.C0162cb;
import org.romstation.application.RomStation;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: renamed from: org.romstation.application.network.a */
/* JADX INFO: compiled from: HttpRequest.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/network/a.class */
public class C0216a {

    /* JADX INFO: renamed from: a */
    private final URL f581a;

    /* JADX INFO: renamed from: b */
    private int f582b = 20000;

    public C0216a(URL url) {
        this.f581a = url;
    }

    /* JADX INFO: renamed from: a */
    public int m956a() {
        return this.f582b;
    }

    /* JADX INFO: renamed from: a */
    public void m957a(int timeout) {
        this.f582b = timeout;
    }

    /* JADX INFO: renamed from: b */
    public C0219d m958b() throws ServerResponseException, NetworkOfflineException, InvalidServerResponseException {
        RomStation.m42b().info("HTTP GET: " + this.f581a);
        if (C0217b.m963d() == EnumC0218c.OFFLINE) {
            throw new NetworkOfflineException();
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) this.f581a.openConnection();
            connection.setConnectTimeout(this.f582b);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Throwable th = null;
            try {
                try {
                    String content = (String) bufferedReader.lines().collect(Collectors.joining("\n"));
                    try {
                        C0219d c0219d = new C0219d(content);
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
                        return c0219d;
                    } catch (ServerResponseException exception) {
                        if (exception.m955a().m965a() == -1) {
                            RomStationController.f786a.post(new C0162cb());
                        }
                        throw exception;
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
            throw new InvalidServerResponseException("http request failed", exception2);
        }
    }

    /* JADX INFO: renamed from: a */
    public C0219d m959a(C0222g query) throws ServerResponseException, NetworkOfflineException, InvalidServerResponseException {
        RomStation.m42b().info("HTTP POST: " + this.f581a);
        if (C0217b.m963d() == EnumC0218c.OFFLINE) {
            throw new NetworkOfflineException();
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) this.f581a.openConnection();
            connection.setConnectTimeout(this.f582b);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            Throwable th = null;
            try {
                outputStream.writeBytes(query.toString());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Throwable th2 = null;
                try {
                    String content = (String) bufferedReader.lines().collect(Collectors.joining("\n"));
                    try {
                        C0219d c0219d = new C0219d(content);
                        if (bufferedReader != null) {
                            if (0 != 0) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                }
                            } else {
                                bufferedReader.close();
                            }
                        }
                        if (outputStream != null) {
                            if (0 != 0) {
                                try {
                                    outputStream.close();
                                } catch (Throwable th4) {
                                    th.addSuppressed(th4);
                                }
                            } else {
                                outputStream.close();
                            }
                        }
                        return c0219d;
                    } catch (ServerResponseException exception) {
                        if (exception.m955a().m965a() == -1) {
                            RomStationController.f786a.post(new C0162cb());
                        }
                        throw exception;
                    }
                } catch (Throwable th5) {
                    if (bufferedReader != null) {
                        if (0 != 0) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th6) {
                                th2.addSuppressed(th6);
                            }
                        } else {
                            bufferedReader.close();
                        }
                    }
                    throw th5;
                }
            } catch (Throwable th7) {
                if (outputStream != null) {
                    if (0 != 0) {
                        try {
                            outputStream.close();
                        } catch (Throwable th8) {
                            th.addSuppressed(th8);
                        }
                    } else {
                        outputStream.close();
                    }
                }
                throw th7;
            }
        } catch (IOException exception2) {
            throw new InvalidServerResponseException("http request failed", exception2);
        }
    }
}
