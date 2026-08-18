package org.romstation.application;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.time.Instant;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.message.Message;
import org.freedesktop.gstreamer.message.MessageType;
import org.romstation.application.network.C0217b;
import org.romstation.application.virtualcontroller.device.C0273c;
import org.romstation.application.virtualcontroller.device.C0274d;

/* JADX INFO: renamed from: org.romstation.application.cX */
/* JADX INFO: compiled from: CloudPlayer.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cX.class */
public class C0158cX {

    /* JADX INFO: renamed from: a */
    private static final int f351a = 100;

    /* JADX INFO: renamed from: b */
    private static final int f352b = 1;

    /* JADX INFO: renamed from: c */
    private static final int f353c = 10;

    /* JADX INFO: renamed from: d */
    private static final int f354d = 10;

    /* JADX INFO: renamed from: e */
    private static final int f355e = 30;

    /* JADX INFO: renamed from: f */
    private final EventBus f356f;

    /* JADX INFO: renamed from: g */
    private final String f357g;

    /* JADX INFO: renamed from: h */
    private final Image f358h;

    /* JADX INFO: renamed from: i */
    private final JsonObject f359i;

    /* JADX INFO: renamed from: j */
    private Timeline f360j;

    /* JADX INFO: renamed from: k */
    private Timeline f361k;

    /* JADX INFO: renamed from: l */
    private C0160cZ f362l;

    /* JADX INFO: renamed from: m */
    private C0191dc f363m;

    /* JADX INFO: renamed from: n */
    private C0273c f364n;

    /* JADX INFO: renamed from: o */
    private AnimationTimer f365o;

    /* JADX INFO: renamed from: p */
    private Timeline f366p;

    /* JADX INFO: renamed from: q */
    private Pipeline f367q;

    /* JADX INFO: renamed from: r */
    private C0000A f368r;

    /* JADX INFO: renamed from: s */
    private final BooleanProperty f369s = new SimpleBooleanProperty();

    /* JADX INFO: renamed from: t */
    private boolean f370t;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem saveStateMenuItem;

    @FXML
    private MenuItem loadStateMenuItem;

    @FXML
    private MenuItem resetMenuItem;

    @FXML
    private Menu driveMenu;

    @FXML
    private MenuItem openDriveMenuItem;

    @FXML
    private MenuItem changeMediumMenuItem;

    @FXML
    private MenuItem closeDriveMenuItem;

    @FXML
    private MenuItem openDriveToolBarMenuItem;

    @FXML
    private MenuItem changeMediumToolBarMenuItem;

    @FXML
    private MenuItem closeDriveToolBarMenuItem;

    @FXML
    private CheckMenuItem fullscreenMenuItem;

    @FXML
    private CheckMenuItem stretchMenuItem;

    @FXML
    private CheckMenuItem preserveRatioMenuItem;

    @FXML
    private CheckMenuItem alwaysOnTopMenuItem;

    @FXML
    private CheckMenuItem muteMenuItem;

    @FXML
    private CheckMenuItem toolBarMenuItem;

    @FXML
    private CheckMenuItem playerMessagesMenuItem;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button saveStateButton;

    @FXML
    private Button loadStateButton;

    @FXML
    private Button resetButton;

    @FXML
    private MenuButton driveMenuButton;

    @FXML
    private Button controllerButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox osdMessagesVBox;

    @FXML
    private TextField chatTextField;

    @FXML
    private Label loadingLabel;

    @FXML
    private ImageView imageView;

    public C0158cX(EventBus lobbyEventBus, String title, Image graphic, JsonObject json) throws IOException {
        this.f356f = lobbyEventBus;
        this.f357g = title;
        this.f358h = graphic;
        this.f359i = json;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/stage/cloudPlayer.fxml"));
        fxmlLoader.setResources(RomStation.m44d());
        fxmlLoader.setControllerFactory(type -> {
            return this;
        });
        fxmlLoader.load();
    }

    /* JADX INFO: renamed from: a */
    public C0188da m684a() {
        if (this.f363m != null) {
            return this.f363m.m772b();
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public void m685a(C0188da credential) {
        if (this.f363m != null) {
            this.f363m.m773a(credential);
        }
    }

    /* JADX INFO: renamed from: b */
    public Stage m686b() {
        return this.stage;
    }

    /* JADX INFO: renamed from: c */
    public void m687c() {
        this.f367q.play();
        this.f369s.set(true);
    }

    /* JADX INFO: renamed from: d */
    public void m688d() {
        this.f367q.stop();
        this.f368r.m1b();
    }

    /* JADX INFO: renamed from: e */
    public void m689e() {
        m688d();
        m687c();
    }

    @FXML
    public void close() {
        this.stage.close();
    }

    @FXML
    private void stageHidden() {
        this.f356f.unregister(this);
        this.f365o.stop();
        this.f366p.stop();
        this.f361k.stop();
        if (this.f363m != null) {
            this.f363m.m780f();
            if (this.f363m.m774c() != null) {
                this.f363m.m774c().mo1611d();
            }
            m695f();
        }
        this.f367q.stop();
        this.f367q.close();
        Properties settings = RomStation.m43c();
        settings.setProperty("cloudPlayer.video.stretch", String.valueOf(this.stretchMenuItem.isSelected()));
        settings.setProperty("cloudPlayer.video.preserveRatio", String.valueOf(this.preserveRatioMenuItem.isSelected()));
        settings.setProperty("cloudPlayer.video.alwaysOnTop", String.valueOf(this.alwaysOnTopMenuItem.isSelected()));
        settings.setProperty("cloudPlayer.audio.mute", String.valueOf(this.muteMenuItem.isSelected()));
        settings.setProperty("cloudPlayer.view.toolbar", String.valueOf(this.toolBarMenuItem.isSelected()));
        settings.setProperty("cloudPlayer.view.playerMessages", String.valueOf(this.playerMessagesMenuItem.isSelected()));
    }

    /* JADX INFO: renamed from: a */
    private void m690a(Bin bin, Bin subBin, Element element) {
        if ("GstRtpJitterBuffer".equals(element.getTypeName())) {
            element.set("post-drop-messages", true);
            element.set("drop-messages-interval", 1000);
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX INFO: renamed from: a */
    private void m691a(Bus bus, Message message) {
        switch (AnonymousClass2.f372a[message.getType().ordinal()]) {
            case f352b /* 1 */:
                if (message.getStructure() != null) {
                    String name = message.getStructure().getName();
                    byte b = -1;
                    switch (name.hashCode()) {
                        case -2006061244:
                            if (name.equals("GstRTSPSrcTimeout")) {
                                b = 0;
                            }
                            break;
                        case -433701981:
                            if (name.equals("drop-msg")) {
                                b = f352b;
                            }
                            break;
                    }
                    switch (b) {
                        case 0:
                            Platform.runLater(this::m689e);
                            break;
                        case f352b /* 1 */:
                            String string = message.getStructure().getString("reason");
                            byte b2 = -1;
                            switch (string.hashCode()) {
                                case -2075995986:
                                    if (string.equals("drop-on-latency")) {
                                        b2 = f352b;
                                    }
                                    break;
                                case -1041062017:
                                    if (string.equals("too-late")) {
                                        b2 = 0;
                                    }
                                    break;
                            }
                            switch (b2) {
                                case 0:
                                    int latePackets = ((Integer) message.getStructure().getValue("num-too-late")).intValue();
                                    RomStation.m42b().log(Level.INFO, String.format("[gstreamer] late packets: %d", Integer.valueOf(latePackets)));
                                    if (this.f370t) {
                                        int latency = ((Integer) message.getSource().get("latency")).intValue();
                                        int newLatency = Math.min(latency + 10, f351a);
                                        if (newLatency != latency) {
                                            message.getSource().set("latency", Integer.valueOf(newLatency));
                                            RomStation.m42b().log(Level.INFO, String.format("[gstreamer] new latency: %d", Integer.valueOf(newLatency)));
                                        }
                                    }
                                    break;
                                case f352b /* 1 */:
                                    int droppedPackets = ((Integer) message.getStructure().getValue("num-drop-on-latency")).intValue();
                                    RomStation.m42b().log(Level.INFO, String.format("[gstreamer] dropped packets: %d", Integer.valueOf(droppedPackets)));
                                    break;
                            }
                            break;
                    }
                }
                break;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m692a(GstObject source, int code, String message) {
        switch (code) {
            case 3:
                Platform.runLater(() -> {
                    this.f366p.play();
                });
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m693a(long now) {
        Image frame = this.f368r.m2c();
        if (frame != null) {
            this.f369s.set(false);
            this.imageView.setImage(frame);
        }
        if (this.f363m != null) {
            try {
                this.f363m.m779e();
            } catch (IOException exception) {
                RomStation.m42b().log(Level.SEVERE, "failed to send inputs to the server", (Throwable) exception);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private C0160cZ m694a(int id) throws IOException {
        try {
            return C0192dd.m782a(Paths.get(String.format("controllers/%d/config.json", Integer.valueOf(id)), new String[0]));
        } catch (NoSuchFileException e) {
            String source = String.format("%s/romstation/resources/cloud/controllers/%d", C0217b.m961b(), Integer.valueOf(this.f359i.getAsJsonObject("controller").get("id").getAsInt()));
            C0160cZ config = C0192dd.m781a(new URL(String.format("%s/config.json", source)));
            Path target = Paths.get(String.format("controllers/%d/controller.png", Integer.valueOf(id)), new String[0]);
            if (!Files.isDirectory(target.getParent(), new LinkOption[0])) {
                Files.createDirectories(target.getParent(), new FileAttribute[0]);
            }
            InputStream inputStream = new URL(String.format("%s/controller.png", source)).openStream();
            Throwable th = null;
            try {
                try {
                    Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
                    config.m724b(String.format("file:%s", target));
                    if (inputStream != null) {
                        if (0 != 0) {
                            try {
                                inputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            inputStream.close();
                        }
                    }
                    return config;
                } catch (Throwable th3) {
                    th = th3;
                    throw th3;
                }
            } catch (Throwable th4) {
                if (inputStream != null) {
                    if (th != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th5) {
                            th.addSuppressed(th5);
                        }
                    } else {
                        inputStream.close();
                    }
                }
                throw th4;
            }
        }
    }

    /* JADX INFO: renamed from: f */
    private void m695f() {
        try {
            Path target = Paths.get(String.format("controllers/%d/config.json", Integer.valueOf(this.f359i.getAsJsonObject("controller").get("id").getAsInt())), new String[0]);
            if (!Files.isDirectory(target.getParent(), new LinkOption[0])) {
                Files.createDirectories(target.getParent(), new FileAttribute[0]);
            }
            C0192dd.m784a(target, this.f362l);
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, "failed to save virtual controller config", (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m696a(C0190db profile) {
        this.f362l.m728b(profile);
        this.f363m.m777a(profile);
        if (this.f363m.m774c() != null && (this.f363m.m774c() instanceof C0274d)) {
            ((C0274d) this.f363m.m774c()).m1637a(this.f364n);
        }
        m699a(String.format(RomStation.m44d().getString("cloudPlayer.message.profileLoaded"), profile.m765a()));
    }

    /* JADX INFO: renamed from: g */
    private void m697g() {
        EnumC0115bh decoder;
        Element audioSink;
        LinkedList<Element> videoElements = new LinkedList<>();
        LinkedList<Element> audioElements = new LinkedList<>();
        Element source = ElementFactory.make("rtspsrc", "source");
        source.set("location", this.f359i.getAsJsonObject("stream").get("uri").getAsString());
        source.set("latency", Integer.valueOf(this.f359i.getAsJsonObject("stream").get("default-latency").getAsInt()));
        source.set("do-retransmission", false);
        source.set("drop-on-latency", Boolean.valueOf(this.f359i.getAsJsonObject("stream").get("drop-on-latency").getAsBoolean()));
        source.connect((element, pad) -> {
            Pad sinkPad;
            Pad sinkPad2;
            if (pad.getName().startsWith("recv_rtp_src")) {
                String string = pad.getCurrentCaps().getStructure(0).getString("media");
                byte b = -1;
                switch (string.hashCode()) {
                    case 93166550:
                        if (string.equals("audio")) {
                            b = f352b;
                        }
                        break;
                    case 112202875:
                        if (string.equals("video")) {
                            b = 0;
                        }
                        break;
                }
                switch (b) {
                    case 0:
                        Element videoDepay = this.f367q.getElementByName("video-depay");
                        if (videoDepay != null && (sinkPad2 = videoDepay.getStaticPad("sink")) != null && !sinkPad2.isLinked()) {
                            pad.link(sinkPad2);
                            break;
                        }
                        break;
                    case f352b /* 1 */:
                        Element audioDepay = this.f367q.getElementByName("audio-depay");
                        if (audioDepay != null && (sinkPad = audioDepay.getStaticPad("sink")) != null && !sinkPad.isLinked()) {
                            pad.link(sinkPad);
                            break;
                        }
                        break;
                }
            }
        });
        videoElements.add(ElementFactory.make("rtph264depay", "video-depay"));
        videoElements.add(ElementFactory.make("h264parse", "video-parse"));
        try {
            decoder = EnumC0115bh.m586a(Integer.parseInt(RomStation.m43c().getProperty("cloudPlayer.decoder")));
        } catch (Exception exception) {
            RomStation.m42b().log(Level.WARNING, "invalid video decoder requested", (Throwable) exception);
            decoder = EnumC0115bh.AUTO;
        }
        switch (AnonymousClass2.f374c[C0004E.m10c().ordinal()]) {
            case f352b /* 1 */:
                if (decoder == EnumC0115bh.AUTO) {
                    decoder = EnumC0115bh.SOFTWARE;
                    try {
                        EnumC0007H d3dFeatureLevel = C0009J.m29b();
                        RomStation.m42b().log(Level.INFO, String.format("%s detected", d3dFeatureLevel));
                        if (d3dFeatureLevel.m23a() >= EnumC0007H.D3D_FEATURE_LEVEL_11_1.m23a()) {
                            RomStation.m42b().log(Level.INFO, "DirectX feature level is compatible with hardware decoder");
                            decoder = EnumC0115bh.HARDWARE;
                        } else {
                            RomStation.m42b().log(Level.WARNING, "DirectX 11.1 feature level is required to use hardware decoder");
                        }
                    } catch (Exception exception2) {
                        RomStation.m42b().log(Level.SEVERE, "failed to get DirectX feature level", (Throwable) exception2);
                    }
                }
                switch (AnonymousClass2.f373b[decoder.ordinal()]) {
                    case f352b /* 1 */:
                        videoElements.add(ElementFactory.make("d3d11h264dec", "video-decoder"));
                        videoElements.add(ElementFactory.make("d3d11colorconvert", "video-convert"));
                        videoElements.add(ElementFactory.make("d3d11download", (String) null));
                        RomStation.m42b().log(Level.INFO, "hardware decoder configured");
                        break;
                    case 2:
                        Element videoDecoder = ElementFactory.make("avdec_h264", "video-decoder");
                        videoDecoder.set("direct-rendering", false);
                        videoDecoder.set("lowres", Integer.valueOf(f352b));
                        videoDecoder.set("max-threads", 0);
                        videoDecoder.set("thread-type", 2);
                        videoElements.add(videoDecoder);
                        videoElements.add(ElementFactory.make("videoconvert", "video-convert"));
                        RomStation.m42b().log(Level.INFO, "software decoder configured");
                        break;
                }
                break;
            case 2:
                switch (AnonymousClass2.f373b[decoder.ordinal()]) {
                    case f352b /* 1 */:
                    case 3:
                        videoElements.add(ElementFactory.make("vtdec", "video-decoder"));
                        videoElements.add(ElementFactory.make("videoconvert", "video-convert"));
                        RomStation.m42b().log(Level.INFO, "automatic decoder configured");
                        break;
                    case 2:
                        Element videoDecoder2 = ElementFactory.make("avdec_h264", "video-decoder");
                        videoDecoder2.set("direct-rendering", false);
                        videoDecoder2.set("lowres", Integer.valueOf(f352b));
                        videoDecoder2.set("max-threads", 0);
                        videoDecoder2.set("thread-type", 2);
                        videoElements.add(videoDecoder2);
                        videoElements.add(ElementFactory.make("videoconvert", "video-convert"));
                        RomStation.m42b().log(Level.INFO, "software decoder configured");
                        break;
                }
                break;
        }
        this.f368r = new C0000A("app-sink");
        this.f368r.m0a().set("sync", false);
        videoElements.add(this.f368r.m0a());
        audioElements.add(ElementFactory.make("rtpmp4adepay", "audio-depay"));
        audioElements.add(ElementFactory.make("aacparse", "audio-parse"));
        audioElements.add(ElementFactory.make("avdec_aac", "audio-decoder"));
        audioElements.add(ElementFactory.make("audioconvert", "audio-convert"));
        audioElements.add(ElementFactory.make("audioresample", "audio-resample"));
        audioElements.add(ElementFactory.make("volume", "volume"));
        switch (AnonymousClass2.f374c[C0004E.m10c().ordinal()]) {
            case f352b /* 1 */:
                try {
                    if (Float.parseFloat(System.getProperty("os.version")) >= 10.0f) {
                        audioSink = ElementFactory.make("wasapi2sink", "audio-sink");
                    } else {
                        audioSink = ElementFactory.make("wasapisink", "audio-sink");
                    }
                } catch (Exception exception3) {
                    RomStation.m42b().log(Level.WARNING, "unable to detect Windows version", (Throwable) exception3);
                    audioSink = ElementFactory.make("wasapisink", "audio-sink");
                }
                audioSink.set("low-latency", true);
                audioElements.add(audioSink);
                break;
            case 2:
                audioElements.add(ElementFactory.make("osxaudiosink", "audio-sink"));
                break;
        }
        this.f367q = new Pipeline("pipeline");
        this.f367q.connect(this::m690a);
        this.f367q.getBus().connect(this::m691a);
        this.f367q.getBus().connect(this::m692a);
        this.f367q.add(source);
        this.f367q.addMany((Element[]) videoElements.toArray(new Element[0]));
        this.f367q.addMany((Element[]) audioElements.toArray(new Element[0]));
        Element.linkMany((Element[]) videoElements.toArray(new Element[0]));
        Element.linkMany((Element[]) audioElements.toArray(new Element[0]));
        this.f370t = this.f359i.getAsJsonObject("stream").get("dynamic-latency").getAsBoolean();
    }

    /* JADX INFO: renamed from: org.romstation.application.cX$2, reason: invalid class name */
    /* JADX INFO: compiled from: CloudPlayer.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cX$2.class */
    static /* synthetic */ class AnonymousClass2 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f372a;

        /* JADX INFO: renamed from: b */
        static final /* synthetic */ int[] f373b;

        /* JADX INFO: renamed from: c */
        static final /* synthetic */ int[] f374c = new int[EnumC0003D.values().length];

        static {
            try {
                f374c[EnumC0003D.WINDOWS.ordinal()] = C0158cX.f352b;
            } catch (NoSuchFieldError e) {
            }
            try {
                f374c[EnumC0003D.MAC_OS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            f373b = new int[EnumC0115bh.values().length];
            try {
                f373b[EnumC0115bh.HARDWARE.ordinal()] = C0158cX.f352b;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f373b[EnumC0115bh.SOFTWARE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f373b[EnumC0115bh.AUTO.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            f372a = new int[MessageType.values().length];
            try {
                f372a[MessageType.ELEMENT.ordinal()] = C0158cX.f352b;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    @FXML
    private void initialize() {
        this.f356f.register(this);
        this.f360j = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(10.0d), actionEvent -> {
            this.osdMessagesVBox.setVisible(false);
        }, new KeyValue[0])});
        this.f361k = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(1.0d), actionEvent2 -> {
            Iterator<Node> iterator = this.osdMessagesVBox.getChildren().iterator();
            while (iterator.hasNext()) {
                long timestamp = ((AbstractC0132by) iterator.next().getUserData()).m634a();
                if (Instant.now().getEpochSecond() - timestamp >= 30) {
                    iterator.remove();
                }
            }
        }, new KeyValue[0])});
        this.f361k.setCycleCount(-1);
        this.f361k.play();
        try {
            this.f362l = m694a(this.f359i.getAsJsonObject("controller").get("id").getAsInt());
            if (this.f362l.m727d() == null) {
                C0192dd.m786a(this.f362l);
            }
            try {
                this.f363m = new C0191dc(new InetSocketAddress(this.f359i.getAsJsonObject("controller").getAsJsonObject("server").get("hostname").getAsString(), this.f359i.getAsJsonObject("controller").getAsJsonObject("server").get("port").getAsInt()));
                this.f364n = new C0273c(this.imageView, this.stackPane);
                if (this.f362l.m727d() != null) {
                    this.f363m.m777a(this.f362l.m727d());
                    if (this.f363m.m774c() != null && (this.f363m.m774c() instanceof C0274d)) {
                        ((C0274d) this.f363m.m774c()).m1637a(this.f364n);
                    }
                }
            } catch (SocketException exception) {
                RomStation.m42b().log(Level.SEVERE, "failed to create virtual controller", (Throwable) exception);
            }
        } catch (IOException exception2) {
            RomStation.m42b().log(Level.SEVERE, "failed to load virtual controller config", (Throwable) exception2);
        }
        m697g();
        this.stretchMenuItem.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("cloudPlayer.video.stretch")));
        stretchMenuAction();
        this.preserveRatioMenuItem.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("cloudPlayer.video.preserveRatio")));
        preserveRatioMenuAction();
        this.alwaysOnTopMenuItem.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("cloudPlayer.video.alwaysOnTop")));
        alwaysOnTopMenuAction();
        this.muteMenuItem.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("cloudPlayer.audio.mute")));
        muteMenuAction();
        this.toolBarMenuItem.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("cloudPlayer.view.toolbar")));
        toolBarMenuAction();
        this.playerMessagesMenuItem.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("cloudPlayer.view.playerMessages")));
        this.f359i.getAsJsonArray("features").forEach(element -> {
            String feature = element.getAsString();
            byte b = -1;
            switch (feature.hashCode()) {
                case -1013255452:
                    if (feature.equals("change_medium")) {
                        b = 6;
                    }
                    break;
                case 95852938:
                    if (feature.equals("drive")) {
                        b = 3;
                    }
                    break;
                case 108404047:
                    if (feature.equals("reset")) {
                        b = 2;
                    }
                    break;
                case 126887928:
                    if (feature.equals("load_state")) {
                        b = f352b;
                    }
                    break;
                case 501776515:
                    if (feature.equals("close_drive")) {
                        b = 5;
                    }
                    break;
                case 677364565:
                    if (feature.equals("open_drive")) {
                        b = 4;
                    }
                    break;
                case 1412634447:
                    if (feature.equals("save_state")) {
                        b = 0;
                    }
                    break;
            }
            switch (b) {
                case 0:
                    this.saveStateMenuItem.setDisable(false);
                    this.saveStateButton.setDisable(false);
                    break;
                case f352b /* 1 */:
                    this.loadStateMenuItem.setDisable(false);
                    this.loadStateButton.setDisable(false);
                    break;
                case 2:
                    this.resetMenuItem.setDisable(false);
                    this.resetButton.setDisable(false);
                    break;
                case 3:
                    this.driveMenu.setDisable(false);
                    this.driveMenuButton.setDisable(false);
                    break;
                case 4:
                    this.openDriveMenuItem.setDisable(false);
                    this.openDriveToolBarMenuItem.setDisable(false);
                    break;
                case 5:
                    this.closeDriveMenuItem.setDisable(false);
                    this.closeDriveToolBarMenuItem.setDisable(false);
                    break;
                case 6:
                    this.changeMediumMenuItem.setDisable(false);
                    this.changeMediumToolBarMenuItem.setDisable(false);
                    break;
            }
        });
        this.f369s.addListener((observableValue, previous, current) -> {
            this.loadingLabel.setVisible(current.booleanValue());
            if (current.booleanValue()) {
                BoxBlur boxBlur = new BoxBlur(5.0d, 5.0d, f352b);
                boxBlur.setInput(new ColorAdjust(0.0d, 0.0d, -0.7d, 0.0d));
                this.imageView.setEffect(boxBlur);
                return;
            }
            this.imageView.setEffect((Effect) null);
        });
        this.f365o = new AnimationTimer() { // from class: org.romstation.application.cX.1
            public void handle(long now) {
                C0158cX.this.m693a(now);
            }
        };
        this.f365o.start();
        this.f366p = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(1.0d), actionEvent3 -> {
            m689e();
        }, new KeyValue[0])});
        this.stage.setTitle(this.f357g);
        this.stage.getIcons().add(this.f358h);
        this.stage.fullScreenProperty().addListener((observableValue2, previous2, fullscreen) -> {
            this.fullscreenMenuItem.setSelected(fullscreen.booleanValue());
            this.menuBar.setVisible(!fullscreen.booleanValue());
            this.menuBar.setManaged(!fullscreen.booleanValue());
            this.toolBar.setVisible(!fullscreen.booleanValue() && this.toolBarMenuItem.isSelected());
            this.toolBar.setManaged(!fullscreen.booleanValue() && this.toolBarMenuItem.isSelected());
            if (fullscreen.booleanValue()) {
                this.scene.setCursor(Cursor.NONE);
            } else {
                this.scene.setCursor(Cursor.DEFAULT);
            }
        });
        this.scene.getStylesheets().add(RomStation.m45e());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m698a(C0143cI event) {
        m700a(event.m650a());
    }

    /* JADX INFO: renamed from: a */
    private void m699a(String message) {
        m700a(new C0089bH(message));
    }

    /* JADX INFO: renamed from: a */
    private void m700a(AbstractC0132by message) {
        if (!this.playerMessagesMenuItem.isSelected() && (message instanceof C0087bF)) {
            return;
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setUserData(message);
        textFlow.setMaxWidth(Double.NEGATIVE_INFINITY);
        textFlow.getStyleClass().add("message");
        if (message instanceof C0089bH) {
            textFlow.getStyleClass().add("system");
        } else if (message instanceof C0087bF) {
            if (((C0087bF) message).m336c().m320h()) {
                textFlow.getStyleClass().add("host");
            }
            if (((C0087bF) message).m336c().m322j() == m684a()) {
                textFlow.getStyleClass().add("self");
            }
        }
        if (message instanceof C0087bF) {
            Label nameLabel = new Label(String.format(RomStation.m44d().getString("cloudPlayer.message.playerNameFormat"), ((C0087bF) message).m336c().m631d()));
            nameLabel.getStyleClass().add("player-name");
            textFlow.getChildren().add(nameLabel);
        }
        Text messageText = new Text(message.m635b());
        messageText.getStyleClass().add("content");
        textFlow.getChildren().add(messageText);
        this.osdMessagesVBox.getChildren().add(textFlow);
        if (this.osdMessagesVBox.getChildren().size() > 10) {
            this.osdMessagesVBox.getChildren().remove(0);
        }
        this.osdMessagesVBox.setVisible(true);
        if (!this.chatTextField.isVisible()) {
            this.f360j.playFromStart();
        }
    }

    @FXML
    private void sendMessage() {
        String text = this.chatTextField.getText();
        if (text != null && !text.isEmpty()) {
            this.f356f.post(new C0145cK(text));
            this.chatTextField.clear();
        }
        this.f360j.playFromStart();
        this.chatTextField.setVisible(false);
    }

    @FXML
    private void screenshotMenuAction() {
        Image image = this.imageView.getImage();
        if (image != null) {
            Thread thread = new Thread(() -> {
                synchronized (this) {
                    try {
                        Path path = Paths.get(RomStation.m43c().getProperty("path.screenshots"), String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS.png", Calendar.getInstance().getTime()));
                        if (Files.notExists(path.getParent(), new LinkOption[0])) {
                            Files.createDirectories(path.getParent(), new FileAttribute[0]);
                        }
                        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, (BufferedImage) null);
                        ImageIO.write(bufferedImage, "png", path.toFile());
                        Platform.runLater(() -> {
                            m699a(String.format(RomStation.m44d().getString("cloudPlayer.message.screenshotSaved"), path.toAbsolutePath()));
                        });
                    } catch (IOException exception) {
                        RomStation.m42b().log(Level.WARNING, "failed to take screenshot", (Throwable) exception);
                    }
                }
            });
            thread.start();
        }
    }

    @FXML
    private void saveStateMenuAction() {
        this.f356f.post(new C0144cJ("send_session_command", "save_state"));
    }

    @FXML
    private void loadStateMenuAction() {
        this.f356f.post(new C0144cJ("send_session_command", "load_state"));
    }

    @FXML
    private void resetMenuAction() {
        this.f356f.post(new C0144cJ("send_session_command", "reset"));
    }

    @FXML
    private void openDriveMenuAction() {
        this.f356f.post(new C0144cJ("send_session_command", "open_drive"));
    }

    @FXML
    private void changeMediumMenuAction() {
        this.f356f.post(new C0144cJ("send_session_command", "change_medium"));
    }

    @FXML
    private void closeDriveMenuAction() {
        this.f356f.post(new C0144cJ("send_session_command", "close_drive"));
    }

    @FXML
    private void configureControllerMenuAction() {
        if (this.f363m != null && this.f362l != null) {
            this.f363m.m771a(true);
            C0102bU dialog = new C0102bU(this.f362l);
            dialog.showAndWait().ifPresent(profile -> {
                this.f363m.m777a(profile);
            });
            this.f363m.m771a(false);
            if (this.f363m.m774c() != null && (this.f363m.m774c() instanceof C0274d)) {
                ((C0274d) this.f363m.m774c()).m1637a(this.f364n);
            }
        }
    }

    @FXML
    private void previousControllerProfileMenuAction() {
        if (this.f363m != null && !this.f362l.m729e().isEmpty()) {
            if (this.f362l.m727d() == null) {
                m696a(this.f362l.m729e().get(0));
            } else if (this.f362l.m729e().size() > f352b) {
                int currentIndex = this.f362l.m729e().indexOf(this.f362l.m727d());
                int lastIndex = this.f362l.m729e().size() - f352b;
                int index = currentIndex == 0 ? lastIndex : currentIndex - f352b;
                m696a(this.f362l.m729e().get(index));
            }
        }
    }

    @FXML
    private void nextControllerProfileMenuAction() {
        if (this.f363m != null && !this.f362l.m729e().isEmpty()) {
            if (this.f362l.m727d() == null) {
                m696a(this.f362l.m729e().get(0));
            } else if (this.f362l.m729e().size() > f352b) {
                int currentIndex = this.f362l.m729e().indexOf(this.f362l.m727d());
                int lastIndex = this.f362l.m729e().size() - f352b;
                int index = currentIndex == lastIndex ? 0 : currentIndex + f352b;
                m696a(this.f362l.m729e().get(index));
            }
        }
    }

    @FXML
    private void fullScreenMenuAction() {
        boolean fullscreen = this.fullscreenMenuItem.isSelected();
        this.stage.setFullScreen(fullscreen);
    }

    @FXML
    private void toggleFullScreen() {
        boolean fullscreen = !this.stage.isFullScreen();
        this.stage.setFullScreen(fullscreen);
    }

    @FXML
    public void stretchMenuAction() {
        if (this.stretchMenuItem.isSelected()) {
            this.imageView.fitWidthProperty().bind(this.stackPane.widthProperty());
            this.imageView.fitHeightProperty().bind(this.stackPane.heightProperty());
            this.stackPane.setMinSize(0.0d, 0.0d);
        } else {
            this.imageView.fitWidthProperty().unbind();
            this.imageView.fitHeightProperty().unbind();
            this.imageView.setFitWidth(0.0d);
            this.imageView.setFitHeight(0.0d);
            this.stackPane.setMinSize(-1.0d, -1.0d);
        }
    }

    @FXML
    private void preserveRatioMenuAction() {
        this.imageView.setPreserveRatio(this.preserveRatioMenuItem.isSelected());
    }

    @FXML
    private void alwaysOnTopMenuAction() {
        this.stage.setAlwaysOnTop(this.alwaysOnTopMenuItem.isSelected());
    }

    @FXML
    private void muteMenuAction() {
        this.f367q.getElementByName("volume").set("mute", Boolean.valueOf(this.muteMenuItem.isSelected()));
    }

    @FXML
    private void toolBarMenuAction() {
        boolean showToolBar = this.toolBarMenuItem.isSelected();
        this.toolBar.setVisible(showToolBar);
        this.toolBar.setManaged(showToolBar);
    }

    @FXML
    private void openChatMenuAction() {
        if (!this.chatTextField.isVisible()) {
            this.f360j.stop();
            this.osdMessagesVBox.setVisible(true);
            this.chatTextField.setVisible(true);
            Platform.runLater(() -> {
                this.chatTextField.requestFocus();
                this.chatTextField.end();
            });
        }
    }

    @FXML
    private void closeChatMenuAction() {
        if (this.chatTextField.isVisible()) {
            this.f360j.playFromStart();
            this.chatTextField.setVisible(false);
        }
    }

    @FXML
    private void playerMessagesMenuAction() {
        boolean visible = this.playerMessagesMenuItem.isSelected();
        for (Node child : this.osdMessagesVBox.getChildren()) {
            if (child.getUserData() instanceof C0087bF) {
                child.setVisible(visible);
                child.setManaged(visible);
            }
        }
    }

    @FXML
    private void helpMenuAction() {
        try {
            Desktop.getDesktop().browse(new URI(C0217b.m961b() + "/documentation/online/multiplayer/cloud-server/"));
        } catch (Exception exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
    }
}
