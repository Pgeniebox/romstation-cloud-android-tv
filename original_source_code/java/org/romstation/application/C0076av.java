package org.romstation.application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.ServerErrorAlert;

/* JADX INFO: renamed from: org.romstation.application.av */
/* JADX INFO: compiled from: TaskDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/av.class */
public class C0076av<T> extends ApplicationFXMLDialog<T> {

    /* JADX INFO: renamed from: a */
    private final Task<T> f154a;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label progressLabel;

    @FXML
    private ProgressBar progressBar;

    public C0076av(Task<T> task) {
        this(task, null);
    }

    public C0076av(Task<T> task, String styleClass) {
        this.f154a = task;
        load(getClass().getResource("/fxml/dialog/taskDialog.fxml"));
        getDialogPane().getStyleClass().add(styleClass);
    }

    @FXML
    private void initialize() {
        this.f154a.setOnSucceeded(this::m295a);
        this.f154a.setOnFailed(this::m296b);
        this.f154a.titleProperty().addListener((observableValue, oldValue, newValue) -> {
            this.dialogPane.setHeaderText(newValue);
            if (oldValue.isEmpty()) {
                this.dialogPane.getScene().getWindow().sizeToScene();
            }
        });
        this.messageLabel.textProperty().bind(this.f154a.messageProperty());
        this.progressLabel.textProperty().bind(Bindings.when(this.f154a.progressProperty().isEqualTo(-1)).then("").otherwise(this.f154a.progressProperty().multiply(100).asString("%.0f%%")));
        this.progressBar.progressProperty().bind(this.f154a.progressProperty());
        setOnCloseRequest(this::m297a);
    }

    /* JADX INFO: renamed from: a */
    private void m295a(WorkerStateEvent event) {
        close();
    }

    /* JADX INFO: renamed from: b */
    private void m296b(WorkerStateEvent event) {
        Throwable exception = event.getSource().getException();
        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), exception);
        if (exception instanceof ServerResponseException) {
            if (((ServerResponseException) exception).m955a().m965a() == -99) {
                C0075au dialog = new C0075au(getResources().getString("platinumDialog.content.cloudServersNonPlatinumLimitReached"));
                ButtonType buttonType = (ButtonType) dialog.showAndWait().orElse(ButtonType.CANCEL);
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    try {
                        Desktop.getDesktop().browse(new URI(C0217b.m961b() + "/store"));
                    } catch (IOException | URISyntaxException ex) {
                        RomStation.m42b().log(Level.WARNING, ex.getMessage(), (Throwable) ex);
                    }
                }
            } else {
                ServerErrorAlert alert = new ServerErrorAlert((ServerResponseException) exception);
                alert.showAndWait();
            }
        } else {
            C0069ap exceptionDialog = new C0069ap(exception);
            exceptionDialog.showAndWait();
        }
        close();
    }

    /* JADX INFO: renamed from: a */
    private void m297a(DialogEvent event) {
        if (this.f154a.isRunning()) {
            this.f154a.cancel(false);
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected T resultConverter(ButtonType buttonType) {
        return (T) this.f154a.getValue();
    }
}
