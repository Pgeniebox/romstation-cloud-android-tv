package org.romstation.application;

import java.nio.file.Paths;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.romstation.application.p000io.C0207a;
import org.romstation.application.task.C0252t;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bO */
/* JADX INFO: compiled from: GameUploadDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bO.class */
public class C0096bO extends ApplicationFXMLDialog<Void> {

    /* JADX INFO: renamed from: a */
    private final C0252t f236a;

    /* JADX INFO: renamed from: b */
    private final Timeline f237b = new Timeline();

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ImageView imageView;

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

    public C0096bO(C0252t gameUploadTask) {
        this.f236a = gameUploadTask;
        load(getClass().getResource("/fxml/dialog/upload/gameUploadDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.f236a.setOnSucceeded(this::m464a);
        this.f236a.setOnCancelled(this::m465b);
        this.f236a.setOnFailed(this::m466c);
        setOnCloseRequest(this::m467a);
        this.f237b.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5d), this::m462a, new KeyValue[0]));
        this.f237b.setCycleCount(-1);
        this.f237b.play();
        this.imageView.setImage(new Image(Paths.get(this.f236a.m1150a().m1149c().get("image").getAsString(), new String[0]).toUri().toString()));
        this.nameLabel.setText(this.f236a.m1150a().m1149c().get("title").getAsString());
        this.messageLabel.textProperty().bind(this.f236a.messageProperty());
        this.percentageLabel.textProperty().bind(Bindings.when(this.f236a.progressProperty().isEqualTo(-1)).then("").otherwise(this.f236a.progressProperty().multiply(100).asString("%.0f%%")));
        this.progressBar.progressProperty().bind(this.f236a.progressProperty());
    }

    /* JADX INFO: renamed from: a */
    private void m462a(ActionEvent event) {
        C0064ak timedProgress = this.f236a.m1151b();
        if (timedProgress != null) {
            if (timedProgress.m247g()) {
                this.f237b.stop();
                this.progressLabel.setText((String) null);
                this.speedLabel.setText((String) null);
            } else {
                this.progressLabel.setText(MessageFormat.format(getResources().getString("gameUploadDialog.progress.format"), Double.valueOf(timedProgress.m241a() / 1048576.0d), Double.valueOf(timedProgress.m243c() / 1048576.0d), timedProgress.m252l()));
                this.speedLabel.setText(MessageFormat.format(getResources().getString("gameUploadDialog.speed.format"), C0207a.m829a((long) timedProgress.m249i(), true)));
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
    private void m464a(WorkerStateEvent event) {
        Alert alert = new ApplicationAlert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(getResources().getString("gameUploadDialog.success.header"));
        alert.setContentText(getResources().getString("gameUploadDialog.success.content"));
        alert.showAndWait();
        close();
    }

    /* JADX INFO: renamed from: b */
    private void m465b(WorkerStateEvent event) {
        close();
    }

    /* JADX INFO: renamed from: c */
    private void m466c(WorkerStateEvent event) {
        Throwable exception = event.getSource().getException();
        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), exception);
        C0069ap exceptionDialog = new C0069ap(exception);
        exceptionDialog.showAndWait();
        close();
    }

    /* JADX INFO: renamed from: a */
    private void m467a(DialogEvent event) {
        if (this.f236a.isRunning()) {
            this.f236a.cancel(false);
        }
        this.f237b.stop();
    }
}
