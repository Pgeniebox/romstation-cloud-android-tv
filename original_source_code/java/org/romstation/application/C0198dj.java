package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.dj */
/* JADX INFO: compiled from: DigitalInput.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dj.class */
public class C0198dj extends AbstractC0199dk {

    /* JADX INFO: renamed from: a */
    private float f534a;

    public C0198dj(int id, String name) {
        this(id, name, 0.5f);
    }

    public C0198dj(int id, String name, float threshold) {
        super(id, name);
        this.f534a = threshold;
    }

    /* JADX INFO: renamed from: a */
    public float m803a() {
        return this.f534a;
    }

    /* JADX INFO: renamed from: a */
    public void m804a(float threshold) {
        this.f534a = threshold;
    }

    @Override // org.romstation.application.AbstractC0199dk
    /* JADX INFO: renamed from: a */
    public float mo802a(Float value) {
        return value.floatValue() >= this.f534a ? 1.0f : 0.0f;
    }
}
