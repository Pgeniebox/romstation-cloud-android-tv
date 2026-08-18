package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.ak */
/* JADX INFO: compiled from: TimedProgress.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ak.class */
public class C0064ak extends C0063aj {

    /* JADX INFO: renamed from: a */
    private final long f137a;

    /* JADX INFO: renamed from: b */
    private double f138b;

    /* JADX INFO: renamed from: c */
    private long f139c;

    /* JADX INFO: renamed from: d */
    private double f140d;

    /* JADX INFO: renamed from: e */
    private double f141e;

    /* JADX INFO: renamed from: f */
    private long f142f;

    public C0064ak(double totalWork) {
        this(0.0d, totalWork);
    }

    public C0064ak(double workDone, double totalWork) {
        super(workDone, totalWork);
        long timestamp = System.currentTimeMillis();
        this.f137a = timestamp;
        this.f138b = workDone;
        this.f139c = timestamp;
    }

    /* JADX INFO: renamed from: h */
    public void m248h() {
        long timestamp = System.currentTimeMillis();
        this.f140d = (m241a() - this.f138b) / ((timestamp - this.f139c) / 1000.0d);
        this.f141e = m244d() / ((timestamp - this.f137a) / 1000.0d);
        this.f142f = (long) (m245e() / this.f140d);
        this.f138b = m241a();
        this.f139c = timestamp;
    }

    /* JADX INFO: renamed from: i */
    public double m249i() {
        return this.f140d;
    }

    /* JADX INFO: renamed from: j */
    public double m250j() {
        return this.f141e;
    }

    /* JADX INFO: renamed from: k */
    public long m251k() {
        return this.f142f;
    }

    /* JADX INFO: renamed from: l */
    public String m252l() {
        long hours = this.f142f / 3600;
        long minutes = (this.f142f / 60) % 60;
        long seconds = this.f142f % 60;
        if (this.f142f >= 3600) {
            return String.format("%1$02dh:%2$02dm:%3$02ds", Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds));
        }
        if (this.f142f >= 60) {
            return String.format("%1$02dm:%2$02ds", Long.valueOf(minutes), Long.valueOf(seconds));
        }
        return String.format("%1$02ds", Long.valueOf(seconds));
    }
}
