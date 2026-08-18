package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.aj */
/* JADX INFO: compiled from: Progress.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aj.class */
public class C0063aj {

    /* JADX INFO: renamed from: a */
    private final double f134a;

    /* JADX INFO: renamed from: b */
    private double f135b;

    /* JADX INFO: renamed from: c */
    private final double f136c;

    public C0063aj(double totalWork) {
        this(0.0d, totalWork);
    }

    public C0063aj(double workDone, double totalWork) {
        this.f134a = workDone;
        this.f135b = workDone;
        this.f136c = totalWork;
    }

    /* JADX INFO: renamed from: a */
    public void m240a(double value) {
        this.f135b += value;
    }

    /* JADX INFO: renamed from: a */
    public double m241a() {
        return this.f135b;
    }

    /* JADX INFO: renamed from: b */
    public double m242b() {
        return this.f134a;
    }

    /* JADX INFO: renamed from: c */
    public double m243c() {
        return this.f136c;
    }

    /* JADX INFO: renamed from: d */
    public double m244d() {
        return this.f135b - this.f134a;
    }

    /* JADX INFO: renamed from: e */
    public double m245e() {
        return this.f136c - this.f135b;
    }

    /* JADX INFO: renamed from: f */
    public double m246f() {
        return this.f135b / this.f136c;
    }

    /* JADX INFO: renamed from: g */
    public boolean m247g() {
        return this.f135b == this.f136c;
    }
}
