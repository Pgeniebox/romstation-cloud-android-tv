package org.romstation.application.view.controller.browser;

import com.google.common.eventbus.Subscribe;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.romstation.application.C0027a;
import org.romstation.application.C0151cQ;
import org.romstation.application.C0152cR;
import org.romstation.application.C0153cS;
import org.romstation.application.C0154cT;
import org.romstation.application.C0165ce;
import org.romstation.application.C0167cg;
import org.romstation.application.network.C0217b;
import org.romstation.application.view.controller.ApplicationView;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/browser/BrowserController.class */
public class BrowserController {

    /* JADX INFO: renamed from: a */
    private Engine f799a;

    @FXML
    private BorderPane root;

    @FXML
    private TabPane tabPane;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, previousValue, currentValue) -> {
            if (previousValue != null) {
                ((BrowserTabController) previousValue).m1377b().setVisible(false);
            }
            if (currentValue != null) {
                ((BrowserTabController) currentValue).m1377b().setVisible(true);
            }
        });
        this.tabPane.getTabs().addListener(change -> {
            this.tabPane.getTabs().forEach(tab -> {
                tab.setClosable(this.tabPane.getTabs().size() > 1);
            });
        });
    }

    /* JADX INFO: renamed from: org.romstation.application.view.controller.browser.BrowserController$1 */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/browser/BrowserController$1.class */
    static /* synthetic */ class C02641 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f800a = new int[KeyCode.values().length];

        static {
            try {
                f800a[KeyCode.T.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f800a[KeyCode.F4.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f800a[KeyCode.W.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.isShortcutDown()) {
            switch (C02641.f800a[keyEvent.getCode().ordinal()]) {
                case 1:
                    RomStationController.f786a.post(new C0152cR(C0217b.m961b(), true));
                    break;
                case 2:
                case 3:
                    Tab tab = (Tab) this.tabPane.getSelectionModel().getSelectedItem();
                    if (tab != null && tab.isClosable()) {
                        Event.fireEvent(tab, new Event(Tab.TAB_CLOSE_REQUEST_EVENT));
                        this.tabPane.getTabs().remove(tab);
                        break;
                    }
                    break;
            }
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1358a(C0167cg event) {
        boolean isVisible = event.m730a() == ApplicationView.BROWSER;
        this.root.setVisible(isVisible);
        Tab selectedItem = (Tab) this.tabPane.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ((BrowserTabController) selectedItem).m1377b().setVisible(isVisible);
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1359a(C0152cR event) throws IOException {
        Browser browserNewBrowser = this.f799a.newBrowser();
        FileWriter fileWriter = new FileWriter("C:\\RomStation\\devtools.txt", true);
        fileWriter.write(browserNewBrowser.devTools().remoteDebuggingUrl().orElse("NO_DEVTOOLS_URL").toString());
        fileWriter.write("\n");
        fileWriter.close();
        BrowserTabController browserTabController = new BrowserTabController(browserNewBrowser);
        browserNewBrowser.navigation().loadUrl(event.m661a());
        this.tabPane.getTabs().add(browserTabController);
        if (event.m662b()) {
            this.tabPane.getSelectionModel().select(browserTabController);
        } else {
            browserTabController.m1377b().setVisible(false);
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1360a(C0151cQ event) {
        BrowserTabController browserTab = new BrowserTabController(event.m659a());
        this.tabPane.getTabs().add(browserTab);
        if (event.m660b()) {
            this.tabPane.getSelectionModel().select(browserTab);
        } else {
            browserTab.m1377b().setVisible(false);
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1361a(C0165ce event) {
        this.f799a = C0027a.m91b();
        if (this.f799a != null) {
            RomStationController.f786a.post(new C0152cR(C0217b.m961b(), true));
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1362a(C0153cS event) {
        BrowserTabController selectedItem = (BrowserTabController) this.tabPane.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.m1376a().navigation().loadUrl(event.m663a());
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1363a(C0154cT event) {
        BrowserTabController selectedItem = (BrowserTabController) this.tabPane.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.m1376a().navigation().reload();
        }
    }

    /* JADX INFO: renamed from: a */
    public Node m1364a() {
        return this.root;
    }
}
