package org.romstation.application;

import java.text.MessageFormat;
import java.util.logging.Level;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import org.romstation.application.p000io.C0207a;
import org.romstation.application.task.C0250r;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bM */
/* JADX INFO: compiled from: GameFileUploadDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bM.class */
public class C0094bM extends ApplicationFXMLDialog<Void> {

    /* JADX INFO: renamed from: a */
    private final C0250r f231a;

    /* JADX INFO: renamed from: b */
    private final Timeline f232b = new Timeline();

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label percentageLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    @FXML
    private Label speedLabel;

    public C0094bM(C0250r gameFileUploadTask) {
        this.f231a = gameFileUploadTask;
        load(getClass().getResource("/fxml/dialog/upload/gameFileUploadDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.f231a.setOnSucceeded(this::m452a);
        this.f231a.setOnCancelled(this::m453b);
        this.f231a.setOnFailed(this::m454c);
        setOnCloseRequest(this::m455a);
        this.f232b.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5d), this::m450a, new KeyValue[0]));
        this.f232b.setCycleCount(-1);
        this.f232b.play();
        this.nameLabel.setText(this.f231a.m1136a().m1135e().get("name").getAsString());
        this.messageLabel.textProperty().bind(this.f231a.messageProperty());
        this.percentageLabel.textProperty().bind(Bindings.when(this.f231a.progressProperty().isEqualTo(-1)).then("").otherwise(this.f231a.progressProperty().multiply(100).asString("%.0f%%")));
        this.progressBar.progressProperty().bind(this.f231a.progressProperty());
    }

    /* JADX INFO: renamed from: a */
    private void m450a(ActionEvent event) {
        C0064ak timedProgress = this.f231a.m1137b();
        if (timedProgress != null) {
            if (timedProgress.m247g()) {
                this.f232b.stop();
                this.progressLabel.setText((String) null);
                this.speedLabel.setText((String) null);
            } else {
                this.progressLabel.setText(MessageFormat.format(getResources().getString("gameFileUploadDialog.progress.format"), Double.valueOf(timedProgress.m241a() / 1048576.0d), Double.valueOf(timedProgress.m243c() / 1048576.0d), timedProgress.m252l()));
                this.speedLabel.setText(MessageFormat.format(getResources().getString("gameFileUploadDialog.speed.format"), C0207a.m829a((long) timedProgress.m249i(), true)));
            }
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Void resultConverter(ButtonType buttonType) {
        return null;
    }

    /* JADX INFO: renamed from: a */
    private void m452a(WorkerStateEvent event) {
        Alert alert = new ApplicationAlert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(getResources().getString("gameFileUploadDialog.success.header"));
        alert.setContentText(getResources().getString("gameFileUploadDialog.success.content"));
        alert.showAndWait();
        close();
    }

    /* JADX INFO: renamed from: b */
    private void m453b(WorkerStateEvent event) {
        close();
    }

    /* JADX INFO: renamed from: c */
    private void m454c(WorkerStateEvent event) {
        Throwable exception = event.getSource().getException();
        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), exception);
        C0069ap exceptionDialog = new C0069ap(exception);
        exceptionDialog.showAndWait();
        close();
    }

    /* JADX INFO: renamed from: a */
    private void m455a(DialogEvent event) {
        if (this.f231a.isRunning()) {
            this.f231a.cancel(false);
        }
        this.f232b.stop();
    }
}
