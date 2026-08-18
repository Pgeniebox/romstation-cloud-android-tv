package org.romstation.application;

/* JADX INFO: renamed from: org.romstation.application.H */
/* JADX INFO: compiled from: D3DFeatureLevel.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/H.class */
public enum EnumC0007H {
    D3D_FEATURE_LEVEL_UNKNOWN(0),
    D3D_FEATURE_LEVEL_1_0_CORE(4096),
    D3D_FEATURE_LEVEL_9_1(37120),
    D3D_FEATURE_LEVEL_9_2(37376),
    D3D_FEATURE_LEVEL_9_3(37632),
    D3D_FEATURE_LEVEL_10_0(40960),
    D3D_FEATURE_LEVEL_10_1(41216),
    D3D_FEATURE_LEVEL_11_0(45056),
    D3D_FEATURE_LEVEL_11_1(45312),
    D3D_FEATURE_LEVEL_12_0(49152),
    D3D_FEATURE_LEVEL_12_1(49408),
    D3D_FEATURE_LEVEL_12_2(49664);

    private final int value;

    EnumC0007H(int value) {
        this.value = value;
    }

    /* JADX INFO: renamed from: a */
    public int m23a() {
        return this.value;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0007H m24a(int value) {
        switch (value) {
            case 4096:
                return D3D_FEATURE_LEVEL_1_0_CORE;
            case 37120:
                return D3D_FEATURE_LEVEL_9_1;
            case 37376:
                return D3D_FEATURE_LEVEL_9_2;
            case 37632:
                return D3D_FEATURE_LEVEL_9_3;
            case 40960:
                return D3D_FEATURE_LEVEL_10_0;
            case 41216:
                return D3D_FEATURE_LEVEL_10_1;
            case 45056:
                return D3D_FEATURE_LEVEL_11_0;
            case 45312:
                return D3D_FEATURE_LEVEL_11_1;
            case 49152:
                return D3D_FEATURE_LEVEL_12_0;
            case 49408:
                return D3D_FEATURE_LEVEL_12_1;
            case 49664:
                return D3D_FEATURE_LEVEL_12_2;
            default:
                return D3D_FEATURE_LEVEL_UNKNOWN;
        }
    }
}
