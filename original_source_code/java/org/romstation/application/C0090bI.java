package org.romstation.application;

import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javax.persistence.EntityManager;
import org.romstation.application.database.entity.Locale;
import org.romstation.application.p000io.C0207a;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.controller.ApplicationView;

/* JADX INFO: renamed from: org.romstation.application.bI */
/* JADX INFO: compiled from: ApplicationSettingsController.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bI.class */
public class C0090bI {

    @FXML
    private ComboBox<Locale> localeComboBox;

    @FXML
    private ComboBox<String> themeComboBox;

    @FXML
    private ComboBox<ApplicationView> startScreenComboBox;

    @FXML
    private CheckBox confirmExitCheckBox;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        C0093bL.f230a.register(this);
        EntityManager entityManager = C0081b.m309c();
        List<Locale> locales = entityManager.createNamedQuery(Locale.f493b, Locale.class).setParameter("tag", RomStation.m43c().getProperty("application.locale")).getResultList();
        entityManager.close();
        this.localeComboBox.getItems().addAll(locales);
        Locale defaultLocale = (Locale) locales.stream().filter(locale -> {
            return locale.getTag().equals(RomStation.m43c().getProperty("application.locale"));
        }).findFirst().orElse(locales.get(0));
        this.localeComboBox.setValue(defaultLocale);
        try {
            List<String> themes = (List) Files.list(Paths.get("themes", new String[0])).filter(x$0 -> {
                return Files.isDirectory(x$0, new LinkOption[0]);
            }).map(path -> {
                return path.getFileName().toString();
            }).collect(Collectors.toList());
            this.themeComboBox.getItems().addAll(themes);
        } catch (IOException exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
        this.themeComboBox.setValue(RomStation.m43c().getProperty("application.theme"));
        this.startScreenComboBox.getItems().addAll(new ApplicationView[]{ApplicationView.BROWSER, ApplicationView.LIBRARY, ApplicationView.EMULATORS, ApplicationView.DATABASE});
        this.startScreenComboBox.setValue(ApplicationView.valueOf(RomStation.m43c().getProperty("application.startScreen")));
        this.confirmExitCheckBox.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("application.confirmExit")));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m439a(C0104bW event) {
        RomStation.m43c().setProperty("application.locale", ((Locale) this.localeComboBox.getValue()).getTag());
        RomStation.m43c().setProperty("application.theme", (String) this.themeComboBox.getValue());
        RomStation.m43c().setProperty("application.startScreen", ((ApplicationView) this.startScreenComboBox.getValue()).name());
        RomStation.m43c().setProperty("application.confirmExit", String.valueOf(this.confirmExitCheckBox.isSelected()));
    }

    @FXML
    private void clearApplicationCache(ActionEvent actionEvent) {
        try {
            C0207a.m828a(Paths.get("cache", new String[0]));
        } catch (IOException exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(this.resources.getString("settings.dialog.header"));
        alert.setContentText(this.resources.getString("settings.dialog.application.cache.clear.alert"));
        alert.showAndWait();
    }
}
