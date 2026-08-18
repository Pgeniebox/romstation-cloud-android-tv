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
import org.romstation.application.network.C0217b;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bj */
/* JADX INFO: compiled from: DedicatedServerChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bj.class */
public class C0117bj extends ApplicationFXMLDialog<Integer> {

    /* JADX INFO: renamed from: a */
    private final Engine f285a = C0027a.m91b();

    @FXML
    private DialogPane dialogPane;

    public C0117bj() {
        load(getClass().getResource("/fxml/dialog/netplay/dedicatedServerChoiceDialog.fxml"));
        Platform.runLater(() -> {
            getDialogPane().getScene().getWindow().setMaximized(true);
        });
    }

    @FXML
    private void initialize() throws IOException {
        Browser browserNewBrowser = this.f285a.newBrowser();
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
        browserNewBrowser.navigation().loadUrl(C0217b.m961b() + "/select-server");
        this.dialogPane.setContent(browserViewNewInstance);
        setOnCloseRequest(dialogEvent -> {
            browserNewBrowser.close();
        });
        setResizable(true);
    }

    @JsAccessible
    public void select(int templateID) {
        Platform.runLater(() -> {
            setResult(Integer.valueOf(templateID));
        });
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
