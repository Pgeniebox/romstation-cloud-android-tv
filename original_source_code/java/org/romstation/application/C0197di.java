package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.di */
/* JADX INFO: compiled from: AnalogInput.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/di.class */
public class C0197di extends AbstractC0199dk {

    /* JADX INFO: renamed from: a */
    private float f532a;

    /* JADX INFO: renamed from: b */
    private float f533b;

    public C0197di(int id, String name) {
        this(id, name, 1.0f, 0.0f);
    }

    public C0197di(int id, String name, float sensitivity, float deadZone) {
        super(id, name);
        this.f532a = sensitivity;
        this.f533b = deadZone;
    }

    /* JADX INFO: renamed from: a */
    public float m798a() {
        return this.f532a;
    }

    /* JADX INFO: renamed from: a */
    public void m799a(float sensitivity) {
        this.f532a = sensitivity;
    }

    /* JADX INFO: renamed from: b */
    public float m800b() {
        return this.f533b;
    }

    /* JADX INFO: renamed from: b */
    public void m801b(float deadZone) {
        this.f533b = deadZone;
    }

    @Override // org.romstation.application.AbstractC0199dk
    /* JADX INFO: renamed from: a */
    public float mo802a(Float value) {
        Float value2 = Float.valueOf(((value.floatValue() - this.f533b) / (1.0f - this.f533b)) * this.f532a);
        if (value2.floatValue() < 0.0f) {
            return 0.0f;
        }
        if (value2.floatValue() > 1.0f) {
            return 1.0f;
        }
        return value2.floatValue();
    }
}
