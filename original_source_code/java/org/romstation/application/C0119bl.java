package org.romstation.application;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.js.JsAccessible;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.romstation.application.netplay.C0214b;
import org.romstation.application.network.C0217b;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bl */
/* JADX INFO: compiled from: NetplayGameChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bl.class */
public class C0119bl extends ApplicationFXMLDialog<Integer> {

    /* JADX INFO: renamed from: a */
    private final EnumC0129bv f288a;

    /* JADX INFO: renamed from: b */
    private final Engine f289b = C0027a.m91b();

    @FXML
    private DialogPane dialogPane;

    public C0119bl(EnumC0129bv type) {
        this.f288a = type;
        load(getClass().getResource("/fxml/dialog/netplay/netplayGameChoiceDialog.fxml"));
        Platform.runLater(() -> {
            getDialogPane().getScene().getWindow().setMaximized(true);
        });
    }

    @FXML
    private void initialize() throws IOException {
        Browser browserNewBrowser = this.f289b.newBrowser();
        FileWriter fileWriter = new FileWriter("C:\\RomStation\\devtools.txt", true);
        fileWriter.write(browserNewBrowser.devTools().remoteDebuggingUrl().orElse("NO_DEVTOOLS_URL").toString());
        fileWriter.write("\n");
        fileWriter.close();
        BrowserView browserViewNewInstance = BrowserView.newInstance(browserNewBrowser);
        browserNewBrowser.set(InjectJsCallback.class, params -> {
            JsObject window = (JsObject) params.frame().executeJavaScript("window");
            if (window != null) {
                window.putProperty("romstation", this);
            }
            return InjectJsCallback.Response.proceed();
        });
        if (this.f288a == EnumC0129bv.CLOUD) {
            browserNewBrowser.navigation().loadUrl(C0217b.m961b() + "/cloud-game-select");
        } else {
            browserNewBrowser.navigation().loadUrl(C0217b.m961b() + "/select-game");
        }
        this.dialogPane.setContent(browserViewNewInstance);
        setOnCloseRequest(dialogEvent -> {
            browserNewBrowser.close();
        });
        setResizable(true);
    }

    @JsAccessible
    public void select(int id) {
        Platform.runLater(() -> {
            setResult(Integer.valueOf(id));
        });
    }

    @JsAccessible
    public void createManualServer() {
        Platform.runLater(this::close);
        Platform.runLater(C0214b::m856e);
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Integer resultConverter(ButtonType buttonType) {
        return null;
    }
}
