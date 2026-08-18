package org.romstation.application;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.romstation.application.task.C0231B;
import org.romstation.application.view.control.ApplicationAlert;

/* JADX INFO: renamed from: org.romstation.application.Q */
/* JADX INFO: compiled from: Dialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/Q.class */
public class C0016Q {
    public void alert(String header_text, String content_text) {
        ApplicationAlert alert = new ApplicationAlert(header_text, content_text, Alert.AlertType.NONE);
        alert.showAndWait();
    }

    public void errorAlert(String header_text, String content_text) {
        ApplicationAlert alert = new ApplicationAlert(header_text, content_text, Alert.AlertType.ERROR);
        alert.showAndWait();
    }

    public void informationAlert(String header_text, String content_text) {
        ApplicationAlert alert = new ApplicationAlert(header_text, content_text, Alert.AlertType.INFORMATION);
        alert.showAndWait();
    }

    public void warningAlert(String header_text, String content_text) {
        ApplicationAlert alert = new ApplicationAlert(header_text, content_text, Alert.AlertType.WARNING);
        alert.showAndWait();
    }

    public boolean confirmationAlert(String header_text, String content_text) {
        ApplicationAlert alert = new ApplicationAlert(header_text, content_text, Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public Object comboBoxChoice(String header_text, String content_text, Object value, Object... items) {
        C0079ay<Object> dialog = new C0079ay<>(header_text, content_text, value, items);
        Optional<Object> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public String textInput(String header_text, String default_value) {
        C0078ax dialog = new C0078ax(header_text, default_value);
        return (String) dialog.showAndWait().orElse(null);
    }

    public String passwordInput(String headerText, String text) {
        C0074at dialog = new C0074at(headerText, text);
        return (String) dialog.showAndWait().orElse(null);
    }

    public String textArea(String header_text, String content_text, String value, boolean read_only) {
        C0077aw dialog = new C0077aw(value);
        dialog.m299a().setEditable(!read_only);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public <R> R task(final Function<Task, R> function) {
        Task<R> task = new Task<R>() { // from class: org.romstation.application.Q.1
            protected R call() {
                return (R) function.apply(this);
            }
        };
        Thread thread = new Thread((Runnable) task);
        C0076av c0076av = new C0076av(task);
        thread.start();
        return (R) c0076av.showAndWait().orElse(null);
    }

    @Deprecated
    public boolean unzip(String source, String target) {
        Task c0231b = new C0231B(Paths.get(source, new String[0]), Paths.get(target, new String[0]));
        Thread thread = new Thread((Runnable) c0231b);
        C0076av<Boolean> dialog = new C0076av<>(c0231b, "unzip");
        thread.start();
        Optional<Boolean> result = dialog.showAndWait();
        return result.orElse(false).booleanValue();
    }

    public String fileChooser() {
        throw new UnsupportedOperationException();
    }

    public String[] filesChooser() {
        throw new UnsupportedOperationException();
    }

    public String directoryChooser() {
        throw new UnsupportedOperationException();
    }
}
