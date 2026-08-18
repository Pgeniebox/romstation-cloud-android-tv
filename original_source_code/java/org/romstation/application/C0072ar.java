package org.romstation.application;

import com.google.gson.JsonObject;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.logging.Level;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.network.C0217b;
import org.romstation.application.p000io.C0207a;
import org.romstation.application.task.C0248p;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.controller.ApplicationView;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: renamed from: org.romstation.application.ar */
/* JADX INFO: compiled from: GameFileDownloadDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ar.class */
public class C0072ar extends ApplicationFXMLDialog<GameFile> {

    /* JADX INFO: renamed from: a */
    private final C0248p f150a;

    /* JADX INFO: renamed from: b */
    private final Timeline f151b = new Timeline();

    /* JADX INFO: renamed from: c */
    private boolean f152c;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ImageView coverImageView;

    @FXML
    private Label titleLabel;

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

    @FXML
    private Pane queuePane;

    @FXML
    private Label queueMessageLabel;

    @FXML
    private ImageView advertImageView;

    public C0072ar(C0248p gameFileDownloadTask) {
        this.f150a = gameFileDownloadTask;
        load(getClass().getResource("/fxml/dialog/gameFileDownloadDialog.fxml"));
    }

    /* JADX INFO: renamed from: a */
    public boolean m276a() {
        return this.f152c;
    }

    /* JADX INFO: renamed from: a */
    public void m277a(boolean autoClose) {
        this.f152c = autoClose;
    }

    @FXML
    private void initialize() {
        this.f150a.setOnSucceeded(this::m282a);
        this.f150a.setOnFailed(this::m283b);
        setOnCloseRequest(this::m284a);
        this.f151b.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5d), this::m280a, new KeyValue[0]));
        this.f151b.setCycleCount(-1);
        this.f151b.play();
        this.coverImageView.setImage(new Image(C0217b.m961b() + this.f150a.m1073a().m1069b().get("cover").getAsString()));
        this.titleLabel.setText(this.f150a.m1073a().m1071c().get("label").getAsString());
        this.titleLabel.setGraphic(new ImageView(new Image(C0217b.m961b() + this.f150a.m1073a().m1069b().getAsJsonObject("system").get("image").getAsString())));
        this.messageLabel.textProperty().bind(this.f150a.messageProperty());
        this.progressBar.progressProperty().bind(this.f150a.progressProperty());
        this.percentageLabel.textProperty().bind(Bindings.when(this.f150a.progressProperty().isEqualTo(-1)).then("").otherwise(this.f150a.progressProperty().multiply(100).asString("%.1f%%")));
        this.f150a.m1076d().addListener((observableValue, previousValue, currentValue) -> {
            if (Platform.isFxApplicationThread()) {
                m278b(currentValue.booleanValue());
            } else {
                Platform.runLater(() -> {
                    m278b(currentValue.booleanValue());
                });
            }
        });
        this.f150a.m1074b().addListener((observableValue2, previousValue2, currentValue2) -> {
            if (Platform.isFxApplicationThread()) {
                m279a(currentValue2);
            } else {
                Platform.runLater(() -> {
                    m279a(currentValue2);
                });
            }
        });
        if (C0058ae.m195a().m211m() == EnumC0059af.REGULAR) {
            ButtonType platinumButton = new ButtonType(getResources().getString("gameFileDownloadDialog.becomePlatinum"), ButtonBar.ButtonData.OK_DONE);
            this.dialogPane.getButtonTypes().add(platinumButton);
            Button button = this.dialogPane.lookupButton(platinumButton);
            button.addEventFilter(ActionEvent.ACTION, event -> {
                m281b();
                event.consume();
            });
        }
    }

    /* JADX INFO: renamed from: b */
    private void m278b(boolean value) {
        this.queuePane.setVisible(value);
        this.queuePane.setManaged(value);
        getDialogPane().getScene().getWindow().sizeToScene();
    }

    /* JADX INFO: renamed from: a */
    private void m279a(String url) {
        this.advertImageView.setImage(new Image(url));
        getDialogPane().getScene().getWindow().sizeToScene();
    }

    /* JADX INFO: renamed from: a */
    private void m280a(ActionEvent event) {
        C0064ak timedProgress = this.f150a.m1078f();
        if (timedProgress != null) {
            if (timedProgress.m247g()) {
                this.f151b.stop();
                this.progressLabel.setText((String) null);
                this.speedLabel.setText((String) null);
            } else {
                this.progressLabel.setText(MessageFormat.format(getResources().getString("gameFileDownloadDialog.download.progress.format"), Double.valueOf(timedProgress.m241a() / 1048576.0d), Double.valueOf(timedProgress.m243c() / 1048576.0d), timedProgress.m252l()));
                this.speedLabel.setText(MessageFormat.format(getResources().getString("gameFileDownloadDialog.download.speed.format"), C0207a.m829a((long) timedProgress.m249i(), true)));
            }
        }
    }

    @FXML
    public void onCoverClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            JsonObject link = this.f150a.m1073a().m1069b().getAsJsonArray("links").get(0).getAsJsonObject();
            RomStationController.f786a.post(new C0152cR(C0217b.m961b() + link.get("location").getAsString(), true));
            RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
        }
    }

    /* JADX INFO: renamed from: b */
    private void m281b() {
        try {
            Desktop.getDesktop().browse(new URI(C0217b.m961b() + "/store"));
        } catch (IOException | URISyntaxException exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
    }

    @FXML
    private void onAdvertClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            m281b();
        }
    }

    @FXML
    public void cancel() {
        if (this.f150a.isRunning()) {
            this.f150a.cancel(false);
        }
        this.f151b.stop();
    }

    /* JADX INFO: renamed from: a */
    private void m282a(WorkerStateEvent event) {
        getDialogPane().getScene().getWindow().toFront();
        RomStationController.f786a.post(new C0183cw((GameFile) this.f150a.getValue()));
        RomStationController.f786a.post(new C0170cj());
        if (m276a()) {
            setResult(this.f150a.getValue());
        } else {
            getDialogPane().getButtonTypes().setAll(new ButtonType[]{ButtonType.CLOSE, new ButtonType(getResources().getString("gameFileDownloadDialog.launch"), ButtonBar.ButtonData.OK_DONE)});
        }
    }

    /* JADX INFO: renamed from: b */
    private void m283b(WorkerStateEvent event) {
        Throwable exception = event.getSource().getException();
        RomStation.m42b().log(Level.SEVERE, exception.getMessage(), exception);
        getDialogPane().getScene().getWindow().toFront();
        C0069ap exceptionDialog = new C0069ap(exception);
        exceptionDialog.showAndWait();
        close();
    }

    /* JADX INFO: renamed from: a */
    private void m284a(DialogEvent event) {
        cancel();
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public GameFile resultConverter(ButtonType buttonType) {
        if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            RomStationController.f786a.post(new C0185cy((GameFile) this.f150a.getValue(), new String[0]));
        }
        if (this.f150a != null) {
            return (GameFile) this.f150a.getValue();
        }
        return null;
    }
}
