package org.romstation.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.virtualcontroller.device.AbstractC0271a;
import org.romstation.application.virtualcontroller.device.C0273c;
import org.romstation.application.virtualcontroller.device.C0274d;

/* JADX INFO: renamed from: org.romstation.application.bT */
/* JADX INFO: compiled from: DigitalInputSettingsDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bT.class */
public class C0101bT extends ApplicationFXMLDialog<Void> implements Initializable {

    /* JADX INFO: renamed from: a */
    private final AbstractC0271a f258a;

    /* JADX INFO: renamed from: b */
    private final C0198dj f259b;

    /* JADX INFO: renamed from: c */
    private AnimationTimer f260c;

    /* JADX INFO: renamed from: d */
    private float f261d;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label nameLabel;

    @FXML
    private ComboBox<String> commandComboBox;

    @FXML
    private Slider thresholdSlider;

    @FXML
    private Label thresholdValueLabel;

    @FXML
    private ProgressBar stateProgressBar;

    @FXML
    private Label stateValueLabel;

    public C0101bT(AbstractC0271a device, C0198dj digitalInput) {
        this.f258a = device;
        this.f259b = digitalInput;
        load(getClass().getResource("/fxml/dialog/virtualcontroller/digitalInputSettingsDialog.fxml"));
        setOnCloseRequest(this::m529a);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.nameLabel.setText(this.f259b.m806d());
        if (this.f258a == null) {
            this.commandComboBox.setDisable(true);
        } else {
            this.commandComboBox.getItems().setAll(this.f258a.mo1609b());
        }
        if (this.f259b.m807e() != null) {
            this.commandComboBox.setValue(this.f259b.m807e().toString());
        }
        this.commandComboBox.valueProperty().addListener((observableValue, previous, current) -> {
            if (current == null) {
                this.f259b.m808a((C0159cY) null);
            } else {
                this.f259b.m808a(new C0159cY(current));
            }
        });
        this.thresholdSlider.valueProperty().addListener((observableValue2, previous2, current2) -> {
            this.f259b.m804a(current2.floatValue());
        });
        this.thresholdValueLabel.textProperty().bind(this.thresholdSlider.valueProperty().multiply(100).asString("%.0f%%"));
        this.thresholdSlider.setValue(this.f259b.m803a());
        this.stateValueLabel.textProperty().bind(this.stateProgressBar.progressProperty().multiply(100).asString("%.0f%%"));
        this.f260c = new AnimationTimer() { // from class: org.romstation.application.bT.1
            public void handle(long now) {
                C0101bT.this.m528a(now);
            }
        };
        this.f260c.start();
        if (this.f258a != null && (this.f258a instanceof C0274d)) {
            ((C0274d) this.f258a).m1637a(new C0273c(this.dialogPane));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m528a(long now) {
        if (this.f258a != null && this.f258a.mo1610c()) {
            float value = this.f258a.m1612a(this.f259b);
            if (value != this.f261d) {
                this.stateProgressBar.setProgress(value);
                this.f261d = value;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private void m529a(DialogEvent event) {
        this.f260c.stop();
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
}
