package org.romstation.application;

import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.elements.AppSink;

/* JADX INFO: renamed from: org.romstation.application.A */
/* JADX INFO: compiled from: FXAppSink.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/A.class */
public class C0000A implements AppSink.NEW_PREROLL, AppSink.NEW_SAMPLE {

    /* JADX INFO: renamed from: a */
    private static final String f0a = "video/x-raw, format=BGRx";

    /* JADX INFO: renamed from: b */
    private final AppSink f1b;

    /* JADX INFO: renamed from: c */
    private final AtomicReference<Sample> f2c;

    /* JADX INFO: renamed from: d */
    private Image f3d;

    public C0000A(String name) {
        this(new AppSink(name));
    }

    public C0000A(AppSink sink) {
        this.f2c = new AtomicReference<>();
        this.f1b = sink;
        sink.set("emit-signals", true);
        sink.connect(this);
        sink.connect(this);
        sink.setCaps(Caps.fromString(f0a));
    }

    /* JADX INFO: renamed from: a */
    public AppSink m0a() {
        return this.f1b;
    }

    /* JADX INFO: renamed from: b */
    public void m1b() throws IllegalStateException {
        if (Platform.isFxApplicationThread()) {
            Sample pendingSample = this.f2c.getAndSet(null);
            if (pendingSample != null) {
                pendingSample.dispose();
            }
            this.f3d = null;
            return;
        }
        throw new IllegalStateException("Not on FX application thread");
    }

    /* JADX INFO: renamed from: c */
    public Image m2c() {
        Sample pendingSample = this.f2c.getAndSet(null);
        if (pendingSample != null) {
            Structure capsStruct = pendingSample.getCaps().getStructure(0);
            int width = capsStruct.getInteger("width");
            int height = capsStruct.getInteger("height");
            if (this.f3d == null) {
                this.f3d = new WritableImage(width, height);
            }
            this.f3d.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), pendingSample.getBuffer().map(false), width * 4);
            pendingSample.getBuffer().unmap();
            pendingSample.dispose();
            return this.f3d;
        }
        return null;
    }

    public FlowReturn newPreroll(AppSink element) {
        Sample previousSample;
        Sample pendingSample = element.pullPreroll();
        if (pendingSample != null && (previousSample = this.f2c.getAndSet(pendingSample)) != null) {
            previousSample.dispose();
        }
        return FlowReturn.OK;
    }

    public FlowReturn newSample(AppSink element) {
        Sample previousSample;
        Sample pendingSample = element.pullSample();
        if (pendingSample != null && (previousSample = this.f2c.getAndSet(pendingSample)) != null) {
            previousSample.dispose();
        }
        return FlowReturn.OK;
    }
}
