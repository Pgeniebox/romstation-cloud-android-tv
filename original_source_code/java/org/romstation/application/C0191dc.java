package org.romstation.application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import org.romstation.application.virtualcontroller.device.AbstractC0271a;

/* JADX INFO: renamed from: org.romstation.application.dc */
/* JADX INFO: compiled from: VirtualController.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dc.class */
public class C0191dc {

    /* JADX INFO: renamed from: c */
    private boolean f526c;

    /* JADX INFO: renamed from: d */
    private final DatagramPacket f527d;

    /* JADX INFO: renamed from: f */
    private C0188da f529f;

    /* JADX INFO: renamed from: g */
    private AbstractC0271a f530g;

    /* JADX INFO: renamed from: h */
    private final LinkedList<AbstractC0199dk> f531h = new LinkedList<>();

    /* JADX INFO: renamed from: a */
    private final ByteBuffer f524a = ByteBuffer.allocate(32);

    /* JADX INFO: renamed from: b */
    private final byte[] f525b = new byte[8];

    /* JADX INFO: renamed from: e */
    private final DatagramSocket f528e = new DatagramSocket();

    public C0191dc(InetSocketAddress inetSocketAddress) throws SocketException {
        this.f527d = new DatagramPacket(this.f524a.array(), 0, inetSocketAddress);
    }

    /* JADX INFO: renamed from: a */
    public boolean m770a() {
        return this.f526c;
    }

    /* JADX INFO: renamed from: a */
    public void m771a(boolean disable) {
        this.f526c = disable;
    }

    /* JADX INFO: renamed from: b */
    public C0188da m772b() {
        return this.f529f;
    }

    /* JADX INFO: renamed from: a */
    public void m773a(C0188da credential) {
        this.f529f = credential;
    }

    /* JADX INFO: renamed from: c */
    public AbstractC0271a m774c() {
        return this.f530g;
    }

    /* JADX INFO: renamed from: a */
    public void m775a(AbstractC0271a device) {
        this.f530g = device;
    }

    /* JADX INFO: renamed from: d */
    public LinkedList<AbstractC0199dk> m776d() {
        return this.f531h;
    }

    /* JADX INFO: renamed from: a */
    public void m777a(C0190db profile) {
        this.f530g = profile.m767b();
        this.f531h.clear();
        this.f531h.addAll(profile.m769c());
    }

    /* JADX INFO: renamed from: a */
    private boolean m778a(byte[] buffer) {
        boolean hasData = false;
        if (!this.f526c && this.f530g != null && this.f530g.mo1610c()) {
            for (AbstractC0199dk input : this.f531h) {
                float value = this.f530g.m1612a(input);
                if (value != 0.0f) {
                    hasData = true;
                    switch (input.m805c()) {
                        case 1:
                            buffer[0] = (byte) (buffer[0] | 1);
                            break;
                        case 2:
                            buffer[0] = (byte) (buffer[0] | 2);
                            break;
                        case 3:
                            buffer[0] = (byte) (buffer[0] | 4);
                            break;
                        case 4:
                            buffer[0] = (byte) (buffer[0] | 8);
                            break;
                        case 5:
                            buffer[0] = (byte) (buffer[0] | 16);
                            break;
                        case 6:
                            buffer[0] = (byte) (buffer[0] | 32);
                            break;
                        case 7:
                            buffer[0] = (byte) (buffer[0] | 64);
                            break;
                        case 8:
                            buffer[0] = (byte) (buffer[0] | 128);
                            break;
                        case 9:
                            buffer[1] = (byte) (buffer[1] | 1);
                            break;
                        case 10:
                            buffer[1] = (byte) (buffer[1] | 2);
                            break;
                        case 11:
                            buffer[1] = (byte) (buffer[1] | 4);
                            break;
                        case 12:
                            buffer[1] = (byte) (buffer[1] | 8);
                            break;
                        case 13:
                            buffer[1] = (byte) (buffer[1] | 16);
                            break;
                        case 14:
                            buffer[1] = (byte) (buffer[1] | 32);
                            break;
                        case 15:
                            buffer[1] = (byte) (buffer[1] | 64);
                            break;
                        case 16:
                            buffer[2] = (byte) (value * (-128.0f));
                            break;
                        case 17:
                            buffer[2] = (byte) (value * 127.0f);
                            break;
                        case 18:
                            buffer[3] = (byte) (value * (-128.0f));
                            break;
                        case 19:
                            buffer[3] = (byte) (value * 127.0f);
                            break;
                        case 20:
                            buffer[4] = (byte) (value * 127.0f);
                            break;
                        case 21:
                            buffer[5] = (byte) (value * (-128.0f));
                            break;
                        case 22:
                            buffer[5] = (byte) (value * 127.0f);
                            break;
                        case 23:
                            buffer[6] = (byte) (value * (-128.0f));
                            break;
                        case 24:
                            buffer[6] = (byte) (value * 127.0f);
                            break;
                        case 25:
                            buffer[7] = (byte) (value * 127.0f);
                            break;
                    }
                }
            }
        }
        return hasData;
    }

    /* JADX INFO: renamed from: e */
    public void m779e() throws IOException {
        if (this.f529f != null) {
            this.f524a.rewind();
            this.f524a.put((byte) 1);
            this.f524a.put(this.f529f.m760a());
            this.f524a.put(this.f529f.m761b());
            this.f524a.putShort(this.f529f.m764d());
            if (m778a(this.f525b)) {
                this.f524a.put(this.f525b);
                Arrays.fill(this.f525b, (byte) 0);
            }
            this.f527d.setLength(this.f524a.position());
            this.f528e.send(this.f527d);
        }
    }

    /* JADX INFO: renamed from: f */
    public void m780f() {
        this.f528e.close();
    }
}
