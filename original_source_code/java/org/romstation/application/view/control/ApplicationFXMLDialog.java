package org.romstation.application.view.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/ApplicationFXMLDialog.class */
public abstract class ApplicationFXMLDialog<T> extends ApplicationDialog<T> {

    /* JADX INFO: renamed from: a */
    private final FXMLLoader f732a = new FXMLLoader();

    protected abstract Object controllerFactory(Class<?> cls);

    protected abstract T resultConverter(ButtonType buttonType);

    public ApplicationFXMLDialog() {
        this.f732a.setResources(RomStation.m44d());
        this.f732a.setControllerFactory(this::controllerFactory);
        setResultConverter(this::resultConverter);
    }

    protected void load(URL location) {
        try {
            this.f732a.setLocation(location);
            setDialogPane((DialogPane) this.f732a.load());
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    protected ResourceBundle getResources() {
        return this.f732a.getResources();
    }
}
