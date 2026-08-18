package org.romstation.application.view.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.romstation.application.C0004E;
import org.romstation.application.C0060ag;
import org.romstation.application.C0065al;
import org.romstation.application.C0066am;
import org.romstation.application.C0069ap;
import org.romstation.application.C0093bL;
import org.romstation.application.C0165ce;
import org.romstation.application.C0166cf;
import org.romstation.application.C0167cg;
import org.romstation.application.C0168ch;
import org.romstation.application.RomStation;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.EnumC0218c;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.task.C0257y;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.controller.browser.BrowserController;
import org.romstation.application.view.controller.database.DatabaseController;
import org.romstation.application.view.controller.emulators.EmulatorsController;
import org.romstation.application.view.controller.library.LibraryController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/RomStationController.class */
public class RomStationController {

    /* JADX INFO: renamed from: a */
    public static final EventBus f786a = new EventBus(RomStationController.class.getName());

    /* JADX INFO: renamed from: b */
    private Stage f787b;

    /* JADX INFO: renamed from: c */
    private Scene f788c;

    /* JADX INFO: renamed from: d */
    private C0257y f789d = new C0257y(C0060ag.m228a());

    @FXML
    private BorderPane root;

    @FXML
    private ToggleGroup viewToggleGroup;

    @FXML
    private ToggleButton browserToggleButton;

    @FXML
    private BrowserController browserController;

    @FXML
    private ToggleButton libraryToggleButton;

    @FXML
    private LibraryController libraryController;

    @FXML
    private ToggleButton emulatorsToggleButton;

    @FXML
    private EmulatorsController emulatorsController;

    @FXML
    private ToggleButton databaseToggleButton;

    @FXML
    private DatabaseController databaseController;

    @FXML
    private HBox offlinePane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        f786a.register(this);
        C0217b.m962c().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case ONLINE:
                    this.offlinePane.setVisible(false);
                    this.offlinePane.setManaged(false);
                    break;
                case OFFLINE:
                    this.offlinePane.setVisible(true);
                    this.offlinePane.setManaged(true);
                    break;
            }
        });
        this.viewToggleGroup.selectedToggleProperty().addListener((observableValue2, previousValue, currentValue) -> {
            if (currentValue != null) {
                if (currentValue == this.browserToggleButton) {
                    f786a.post(new C0167cg(ApplicationView.BROWSER));
                    return;
                }
                if (currentValue == this.libraryToggleButton) {
                    f786a.post(new C0167cg(ApplicationView.LIBRARY));
                    return;
                } else if (currentValue == this.emulatorsToggleButton) {
                    f786a.post(new C0167cg(ApplicationView.EMULATORS));
                    return;
                } else {
                    if (currentValue == this.databaseToggleButton) {
                        f786a.post(new C0167cg(ApplicationView.DATABASE));
                        return;
                    }
                    return;
                }
            }
            this.viewToggleGroup.selectToggle(previousValue);
        });
        m1316a(ApplicationView.valueOf(RomStation.m43c().getProperty("application.startScreen")));
    }

    /* JADX INFO: renamed from: a */
    public void m1315a(Stage stage) {
        this.f787b = stage;
        stage.setTitle(String.format("RomStation %s", RomStation.f16b));
        stage.setWidth(Double.parseDouble(RomStation.m43c().getProperty("window.width")));
        stage.setHeight(Double.parseDouble(RomStation.m43c().getProperty("window.height")));
        stage.setMaximized(Boolean.parseBoolean(RomStation.m43c().getProperty("window.maximized")));
        stage.getIcons().add(new Image(Paths.get("images/icons/romstation.png", new String[0]).toUri().toString()));
        stage.setOnCloseRequest(this::m1317a);
        this.f788c = new Scene(this.root);
        this.f788c.getStylesheets().add(RomStation.m45e());
        stage.setScene(this.f788c);
    }

    @FXML
    private void onSettingsAction(ActionEvent event) {
        C0093bL dialog = new C0093bL();
        dialog.showAndWait();
    }

    @FXML
    private void connect() {
        C0217b.m964a(EnumC0218c.UNDEFINED);
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/soft/start.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("arch", Integer.valueOf(C0004E.m11d().m6a()));
            C0216a request = new C0216a(builder.m973b());
            C0219d response = request.m958b();
            if (response.m967b().get("version").getAsInt() > 229) {
                C0066am dialog = new C0066am();
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
                    switch (C0004E.m10c()) {
                        case WINDOWS:
                            processBuilder.command("Updater.exe", "--url=" + C0217b.m961b() + response.m967b().get("manifest").getAsString(), "--build=229", "--target=" + response.m967b().get("version").getAsInt());
                            break;
                        case MAC_OS:
                            processBuilder.command("open", "Updater.app", "--args", "--url=" + C0217b.m961b() + response.m967b().get("manifest").getAsString(), "--build=229", "--target=" + response.m967b().get("version").getAsInt());
                            break;
                    }
                    try {
                        processBuilder.start();
                        System.exit(1);
                    } catch (IOException exception) {
                        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                        C0069ap exceptionDialog = new C0069ap(exception);
                        exceptionDialog.showAndWait();
                        C0217b.m964a(EnumC0218c.OFFLINE);
                    }
                } else {
                    C0217b.m964a(EnumC0218c.OFFLINE);
                }
            } else {
                this.f789d.m1172c();
                if (this.f789d.m1170a().m229b()) {
                    Thread thread = new Thread((Runnable) this.f789d);
                    thread.start();
                    C0217b.m964a(EnumC0218c.ONLINE);
                } else {
                    C0065al dialog2 = new C0065al();
                    dialog2.showAndWait();
                    C0217b.m964a(EnumC0218c.OFFLINE);
                }
            }
        } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception2) {
            RomStation.m42b().log(Level.SEVERE, exception2.getMessage(), (Throwable) exception2);
            C0065al dialog3 = new C0065al();
            dialog3.showAndWait();
            C0217b.m964a(EnumC0218c.OFFLINE);
        } catch (NetworkOfflineException exception3) {
            RomStation.m42b().log(Level.WARNING, exception3.getMessage(), (Throwable) exception3);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1316a(ApplicationView screen) {
        switch (screen) {
            case BROWSER:
                this.viewToggleGroup.selectToggle(this.browserToggleButton);
                break;
            case LIBRARY:
                this.viewToggleGroup.selectToggle(this.libraryToggleButton);
                break;
            case EMULATORS:
                this.viewToggleGroup.selectToggle(this.emulatorsToggleButton);
                break;
            case DATABASE:
                this.viewToggleGroup.selectToggle(this.databaseToggleButton);
                break;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1317a(WindowEvent event) {
        if (Boolean.parseBoolean(RomStation.m43c().getProperty("application.confirmExit"))) {
            Alert alert = new ApplicationAlert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(this.resources.getString("application.close.alert.header"));
            alert.setContentText(this.resources.getString("application.close.alert.content"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() != ButtonType.OK) {
                event.consume();
            }
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1318a(C0165ce event) {
        connect();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1319a(C0168ch event) {
        m1316a(event.m731a());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1320a(C0166cf event) {
        RomStation.m43c().setProperty("window.width", String.valueOf(this.f787b.getWidth()));
        RomStation.m43c().setProperty("window.height", String.valueOf(this.f787b.getHeight()));
        RomStation.m43c().setProperty("window.maximized", String.valueOf(this.f787b.isMaximized()));
        this.f789d.cancel(false);
    }
}
