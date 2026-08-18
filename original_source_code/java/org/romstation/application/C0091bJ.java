package org.romstation.application;

import com.google.common.eventbus.Subscribe;
import com.teamdev.jxbrowser.engine.Engine;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import org.romstation.application.view.control.ApplicationAlert;

/* JADX INFO: renamed from: org.romstation.application.bJ */
/* JADX INFO: compiled from: BrowserSettingsController.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bJ.class */
public class C0091bJ {

    @FXML
    private ChoiceBox<Double> zoomLevelChoiceBox;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        C0093bL.f230a.register(this);
        this.zoomLevelChoiceBox.setConverter(new StringConverter<Double>() { // from class: org.romstation.application.bJ.1
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public String toString(Double value) {
                return String.format("%.0f%%", Double.valueOf(value.doubleValue() * 100.0d));
            }

            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public Double fromString(String string) {
                return null;
            }
        });
        this.zoomLevelChoiceBox.setValue(Double.valueOf(Double.parseDouble(RomStation.m43c().getProperty("browser.defaultZoomLevel"))));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m443a(C0104bW event) {
        RomStation.m43c().setProperty("browser.defaultZoomLevel", ((Double) this.zoomLevelChoiceBox.getValue()).toString());
    }

    @FXML
    private void clearCookies(ActionEvent actionEvent) {
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(this.resources.getString("settings.dialog.header"));
        alert.setContentText(this.resources.getString("settings.dialog.browser.cookies.clear.alert"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Engine engine = C0027a.m91b();
            engine.cookieStore().deleteAll();
            engine.cookieStore().persist();
        }
    }

    @FXML
    private void clearCache(ActionEvent actionEvent) {
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(this.resources.getString("settings.dialog.header"));
        alert.setContentText(this.resources.getString("settings.dialog.browser.cache.clear.alert"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                CompletableFuture<Void> future = C0027a.m91b().httpCache().clear();
                future.join();
            } catch (CompletionException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            }
        }
    }
}
