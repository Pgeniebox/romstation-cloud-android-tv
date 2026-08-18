package org.romstation.application;

import com.google.common.eventbus.EventBus;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bL */
/* JADX INFO: compiled from: SettingsDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bL.class */
public class C0093bL extends ApplicationFXMLDialog<ButtonType> {

    /* JADX INFO: renamed from: a */
    public static EventBus f230a;

    public C0093bL() {
        f230a = new EventBus(getClass().getName());
        load(getClass().getResource("/fxml/dialog/settings/settings.fxml"));
    }

    @FXML
    private void initialize() {
        setResizable(true);
    }

    /* JADX INFO: renamed from: a */
    private void m448a() {
        ApplicationAlert alert = new ApplicationAlert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(getResources().getString("settings.dialog.header"));
        alert.setContentText(getResources().getString("settings.dialog.alert.save.message"));
        alert.showAndWait();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public ButtonType resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            f230a.post(new C0104bW());
            m448a();
        }
        return buttonType;
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        if (classType == getClass()) {
            return this;
        }
        try {
            return classType.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }
}
