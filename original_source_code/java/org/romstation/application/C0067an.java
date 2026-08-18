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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.network.C0217b;
import org.romstation.application.p000io.C0207a;
import org.romstation.application.task.C0241i;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: renamed from: org.romstation.application.an */
/* JADX INFO: compiled from: EmulatorFileDownloadDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/an.class */
public class C0067an extends ApplicationFXMLDialog<EmulatorFile> {

    /* JADX INFO: renamed from: a */
    private final C0241i f143a;

    /* JADX INFO: renamed from: b */
    private final Timeline f144b = new Timeline();

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

    public C0067an(C0241i emulatorFileDownloadTask) {
        this.f143a = emulatorFileDownloadTask;
        load(getClass().getResource("/fxml/dialog/emulatorFileDownloadDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.f143a.setOnSucceeded(this::m255a);
        this.f143a.setOnFailed(this::m256b);
        setOnCloseRequest(this::m257a);
        this.f144b.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5d), this::m253a, new KeyValue[0]));
        this.f144b.setCycleCount(-1);
        this.f144b.play();
        this.imageView.setImage(new Image(C0217b.m961b() + this.f143a.m1023a().m1021c().getDocumentElement().getAttribute("image"), true));
        this.nameLabel.setText(this.f143a.m1023a().m1022d().getDocumentElement().getAttribute("name"));
        this.messageLabel.textProperty().bind(this.f143a.messageProperty());
        this.percentageLabel.textProperty().bind(Bindings.when(this.f143a.progressProperty().isEqualTo(-1)).then("").otherwise(this.f143a.progressProperty().multiply(100).asString("%.0f%%")));
        this.progressBar.progressProperty().bind(this.f143a.progressProperty());
    }

    /* JADX INFO: renamed from: a */
    private void m253a(ActionEvent event) {
        C0064ak timedProgress = this.f143a.m1024b();
        if (timedProgress != null) {
            if (timedProgress.m247g()) {
                this.f144b.stop();
                this.progressLabel.setText((String) null);
                this.speedLabel.setText((String) null);
            } else {
                this.progressLabel.setText(MessageFormat.format(getResources().getString("emulatorFileDownloadDialog.progress.format"), Double.valueOf(timedProgress.m241a() / 1048576.0d), Double.valueOf(timedProgress.m243c() / 1048576.0d), timedProgress.m252l()));
                this.speedLabel.setText(MessageFormat.format(getResources().getString("emulatorFileDownloadDialog.speed.format"), C0207a.m829a((long) timedProgress.m249i(), true)));
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
    public EmulatorFile resultConverter(ButtonType buttonType) {
        return null;
    }

    /* JADX INFO: renamed from: a */
    private void m255a(WorkerStateEvent event) {
        RomStationController.f786a.post(new C0169ci());
        setResult((EmulatorFile) event.getSource().getValue());
    }

    /* JADX INFO: renamed from: b */
    private void m256b(WorkerStateEvent event) {
        Throwable exception = event.getSource().getException();
        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), exception);
        C0069ap exceptionDialog = new C0069ap(exception);
        exceptionDialog.showAndWait();
        close();
    }

    /* JADX INFO: renamed from: a */
    private void m257a(DialogEvent event) {
        if (this.f143a.isRunning()) {
            this.f143a.cancel(false);
        }
        this.f144b.stop();
    }
}
