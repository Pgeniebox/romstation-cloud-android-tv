package org.romstation.application.view.controller.browser;

import com.google.common.eventbus.Subscribe;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import com.teamdev.jxbrowser.browser.callback.OpenPopupCallback;
import com.teamdev.jxbrowser.browser.callback.ShowContextMenuCallback;
import com.teamdev.jxbrowser.browser.callback.StartDownloadCallback;
import com.teamdev.jxbrowser.browser.callback.input.MoveMouseWheelCallback;
import com.teamdev.jxbrowser.browser.callback.input.PressKeyCallback;
import com.teamdev.jxbrowser.browser.callback.input.PressMouseCallback;
import com.teamdev.jxbrowser.browser.event.FaviconChanged;
import com.teamdev.jxbrowser.browser.event.TitleChanged;
import com.teamdev.jxbrowser.dom.PointInspection;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.navigation.event.LoadFinished;
import com.teamdev.jxbrowser.navigation.event.LoadStarted;
import com.teamdev.jxbrowser.navigation.event.NavigationFinished;
import com.teamdev.jxbrowser.ui.Bitmap;
import com.teamdev.jxbrowser.ui.KeyCode;
import com.teamdev.jxbrowser.ui.KeyModifiers;
import com.teamdev.jxbrowser.ui.MouseButton;
import com.teamdev.jxbrowser.ui.Point;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import com.teamdev.jxbrowser.view.javafx.graphics.BitmapImage;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.romstation.application.C0004E;
import org.romstation.application.C0150cP;
import org.romstation.application.C0151cQ;
import org.romstation.application.C0152cR;
import org.romstation.application.C0169ci;
import org.romstation.application.C0170cj;
import org.romstation.application.EnumC0003D;
import org.romstation.application.RomStation;
import org.romstation.application.api.C0070a;
import org.romstation.application.network.C0217b;
import org.romstation.application.view.control.BrowserContextMenu;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/browser/BrowserTabController.class */
public class BrowserTabController extends Tab {

    /* JADX INFO: renamed from: a */
    private final Browser f801a;

    /* JADX INFO: renamed from: b */
    private final BrowserView f802b;

    /* JADX INFO: renamed from: c */
    private final BrowserContextMenu f803c;

    /* JADX INFO: renamed from: d */
    private Timeline f804d;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView progressImageView;

    @FXML
    private ImageView faviconImageView;

    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button reloadButton;

    @FXML
    private TextField urlTextField;

    public BrowserTabController(Browser browser) {
        this(BrowserView.newInstance(browser));
    }

    public BrowserTabController(BrowserView browserView) throws IOException {
        this.f801a = browserView.getBrowser();
        FileWriter fileWriter = new FileWriter("C:\\RomStation\\devtools.txt", true);
        fileWriter.write(this.f801a.devTools().remoteDebuggingUrl().orElse("NO_DEVTOOLS_URL").toString());
        fileWriter.write("\n");
        fileWriter.close();
        this.f802b = browserView;
        this.f803c = new BrowserContextMenu(browserView);
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/browser/browserTab.fxml"));
        fXMLLoader.setResources(RomStation.m44d());
        fXMLLoader.setRoot(this);
        fXMLLoader.setController(this);
        try {
            fXMLLoader.load();
        } catch (IOException e) {
            RomStation.m42b().log(Level.SEVERE, e.getMessage(), (Throwable) e);
        }
    }

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.f804d = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(125.0d), actionEvent -> {
            if (this.progressImageView.getRotate() > 360.0d) {
                this.progressImageView.setRotate(0.0d);
            }
            this.progressImageView.setRotate(this.progressImageView.getRotate() + 45.0d);
        }, new KeyValue[0])});
        this.f804d.setCycleCount(-1);
        this.f801a.set(ShowContextMenuCallback.class, this.f803c);
        this.f801a.set(PressKeyCallback.class, this::m1373a);
        this.f801a.set(PressMouseCallback.class, this::m1374a);
        this.f801a.set(MoveMouseWheelCallback.class, this::m1375a);
        this.f801a.set(StartDownloadCallback.class, (params, tell) -> {
            tell.cancel();
        });
        this.f801a.set(OpenPopupCallback.class, params2 -> {
            Platform.runLater(() -> {
                RomStationController.f786a.post(new C0151cQ(params2.popupBrowser(), true));
            });
            return OpenPopupCallback.Response.proceed();
        });
        this.f801a.on(TitleChanged.class, event -> {
            String title = event.title();
            Platform.runLater(() -> {
                setText(title);
            });
        });
        this.f801a.navigation().on(LoadStarted.class, event2 -> {
            Platform.runLater(() -> {
                this.faviconImageView.setVisible(false);
                this.f804d.play();
                this.progressImageView.setVisible(true);
                this.reloadButton.setVisible(false);
                this.stopButton.setVisible(true);
            });
        });
        this.f801a.navigation().on(LoadFinished.class, event3 -> {
            RomStationController.f786a.post(new C0150cP(event3.navigation().browser()));
            Platform.runLater(() -> {
                this.progressImageView.setVisible(false);
                this.f804d.stop();
                this.faviconImageView.setVisible(true);
                this.stopButton.setVisible(false);
                this.reloadButton.setVisible(true);
            });
        });
        this.f801a.navigation().on(NavigationFinished.class, event4 -> {
            if (event4.hasCommitted() && event4.isInMainFrame()) {
                boolean canGoBack = event4.frame().browser().navigation().currentEntryIndex() == 1;
                boolean canGoForward = event4.frame().browser().navigation().canGoForward();
                String url = event4.url();
                Platform.runLater(() -> {
                    this.backButton.setDisable(canGoBack);
                    this.forwardButton.setDisable(!canGoForward);
                    this.urlTextField.setText(url);
                });
            }
        });
        this.f801a.on(FaviconChanged.class, event5 -> {
            Bitmap favicon = event5.favicon();
            Platform.runLater(() -> {
                this.faviconImageView.setImage(BitmapImage.toToolkit(favicon));
            });
        });
        this.f801a.set(InjectJsCallback.class, params3 -> {
            JsObject window;
            try {
                URL url = new URL(params3.frame().browser().url());
                if (url.getHost().equals(C0217b.m960a()) && (window = (JsObject) params3.frame().executeJavaScript("window")) != null) {
                    window.putProperty("romstation_api", C0070a.getInstance());
                }
            } catch (MalformedURLException exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
            return InjectJsCallback.Response.proceed();
        });
        this.urlTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.booleanValue()) {
                Platform.runLater(() -> {
                    this.urlTextField.selectAll();
                });
            }
        });
        this.borderPane.setCenter(this.f802b);
    }

    /* JADX INFO: renamed from: c */
    private void m1368c() {
        RomStationController.f786a.unregister(this);
        this.f801a.close();
    }

    @FXML
    private void onCloseRequest() {
        m1368c();
    }

    @FXML
    private void closeTab() {
        m1369a(this);
    }

    /* JADX INFO: renamed from: a */
    private void m1369a(Tab tab) {
        Event.fireEvent(tab, new Event(Tab.TAB_CLOSE_REQUEST_EVENT));
        getTabPane().getTabs().remove(tab);
    }

    @FXML
    private void closeOtherTabs() {
        ((List) getTabPane().getTabs().stream().filter(tab -> {
            return tab != this;
        }).collect(Collectors.toList())).forEach(this::m1369a);
    }

    @FXML
    private void back() {
        if (this.f801a.navigation().canGoBack() && this.f801a.navigation().currentEntryIndex() > 1) {
            this.f801a.navigation().goBack();
        }
    }

    @FXML
    private void forward() {
        if (this.f801a.navigation().canGoForward()) {
            this.f801a.navigation().goForward();
        }
    }

    @FXML
    private void stop() {
        this.f801a.navigation().stop();
    }

    @FXML
    private void reload() {
        this.f801a.navigation().reload();
    }

    @FXML
    private void home() {
        this.f801a.navigation().loadUrl(C0217b.m961b());
    }

    @FXML
    private void openNewTab() {
        RomStationController.f786a.post(new C0152cR(C0217b.m961b(), true));
    }

    @FXML
    private void loadUrl() {
        this.f801a.navigation().loadUrl(this.urlTextField.getText());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1370a(C0170cj event) {
        new Thread(() -> {
            this.f801a.mainFrame().ifPresent(frame -> {
                JsObject window = (JsObject) frame.executeJavaScript("window");
                if (window != null && window.hasProperty("romstation_api") && window.hasProperty("refreshPage")) {
                    window.call("refreshPage", new Object[0]);
                }
            });
        }).start();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1371a(C0169ci event) {
        new Thread(() -> {
            this.f801a.mainFrame().ifPresent(frame -> {
                JsObject window = (JsObject) frame.executeJavaScript("window");
                if (window != null && window.hasProperty("romstation_api") && window.hasProperty("refreshPage")) {
                    window.call("refreshPage", new Object[0]);
                }
            });
        }).start();
    }

    /* JADX INFO: renamed from: a */
    private boolean m1372a(KeyModifiers keyModifiers) {
        if (C0004E.m10c() == EnumC0003D.MAC_OS) {
            return keyModifiers.isMetaDown();
        }
        return keyModifiers.isControlDown();
    }

    /* JADX INFO: renamed from: a */
    private PressKeyCallback.Response m1373a(PressKeyCallback.Params params) {
        switch (C02651.f805a[params.event().keyCode().ordinal()]) {
            case 1:
                this.f801a.navigation().stop();
                break;
            case 2:
                if (m1372a(params.event().keyModifiers())) {
                    if (params.event().keyModifiers().isShiftDown()) {
                        this.f801a.navigation().reloadIgnoringCache();
                    } else {
                        this.f801a.navigation().reload();
                    }
                }
                break;
            case 3:
                if (m1372a(params.event().keyModifiers())) {
                    this.f801a.navigation().reloadIgnoringCache();
                } else {
                    this.f801a.navigation().reload();
                }
                break;
            case 4:
            case 5:
                if (m1372a(params.event().keyModifiers())) {
                    this.f801a.zoom().reset();
                }
                break;
            case 6:
            case 7:
                if (m1372a(params.event().keyModifiers())) {
                    this.f801a.zoom().in();
                }
                break;
            case 8:
            case 9:
                if (m1372a(params.event().keyModifiers())) {
                    this.f801a.zoom().out();
                }
                break;
        }
        return PressKeyCallback.Response.proceed();
    }

    /* JADX INFO: renamed from: org.romstation.application.view.controller.browser.BrowserTabController$1 */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/browser/BrowserTabController$1.class */
    static /* synthetic */ class C02651 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f805a;

        /* JADX INFO: renamed from: b */
        static final /* synthetic */ int[] f806b = new int[MouseButton.values().length];

        static {
            try {
                f806b[MouseButton.PRIMARY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f806b[MouseButton.MIDDLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            f805a = new int[KeyCode.values().length];
            try {
                f805a[KeyCode.KEY_CODE_ESCAPE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f805a[KeyCode.KEY_CODE_R.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f805a[KeyCode.KEY_CODE_F5.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f805a[KeyCode.KEY_CODE_NUMPAD0.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f805a[KeyCode.KEY_CODE_0.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f805a[KeyCode.KEY_CODE_ADD.ordinal()] = 6;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f805a[KeyCode.KEY_CODE_OEM_PLUS.ordinal()] = 7;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f805a[KeyCode.KEY_CODE_SUBTRACT.ordinal()] = 8;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f805a[KeyCode.KEY_CODE_OEM_MINUS.ordinal()] = 9;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private PressMouseCallback.Response m1374a(PressMouseCallback.Params params) {
        switch (C02651.f806b[params.event().button().ordinal()]) {
            case 1:
                Platform.runLater(() -> {
                    this.f803c.getContextMenu().hide();
                });
                if (m1372a(params.event().keyModifiers())) {
                }
            case 2:
                this.f801a.mainFrame().ifPresent(frame -> {
                    PointInspection pointInspection = frame.inspect(Point.of(params.event().location().x(), params.event().location().y()));
                    if (!pointInspection.absoluteLinkUrl().isEmpty()) {
                        Platform.runLater(() -> {
                            RomStationController.f786a.post(new C0152cR(pointInspection.absoluteLinkUrl(), false));
                        });
                    }
                });
                break;
        }
        return PressMouseCallback.Response.proceed();
    }

    /* JADX INFO: renamed from: a */
    private MoveMouseWheelCallback.Response m1375a(MoveMouseWheelCallback.Params params) {
        if (params.event().deltaY() != 0.0f && m1372a(params.event().keyModifiers())) {
            if (params.event().deltaY() > 0.0f) {
                this.f801a.zoom().in();
            } else {
                this.f801a.zoom().out();
            }
        }
        return MoveMouseWheelCallback.Response.proceed();
    }

    /* JADX INFO: renamed from: a */
    public Browser m1376a() {
        return this.f801a;
    }

    /* JADX INFO: renamed from: b */
    public BrowserView m1377b() {
        return this.f802b;
    }
}
