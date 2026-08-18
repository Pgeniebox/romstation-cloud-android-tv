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

/* JADX INFO: renamed from: org.romstation.application.bR */
/* JADX INFO: compiled from: AnalogInputSettingsDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bR.class */
public class C0099bR extends ApplicationFXMLDialog<Void> implements Initializable {

    /* JADX INFO: renamed from: a */
    private final AbstractC0271a f246a;

    /* JADX INFO: renamed from: b */
    private final C0197di f247b;

    /* JADX INFO: renamed from: c */
    private AnimationTimer f248c;

    /* JADX INFO: renamed from: d */
    private float f249d;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label nameLabel;

    @FXML
    private ComboBox<String> commandComboBox;

    @FXML
    private Slider sensitivitySlider;

    @FXML
    private Label sensitivityValueLabel;

    @FXML
    private Slider deadZoneSlider;

    @FXML
    private Label deadZoneValueLabel;

    @FXML
    private ProgressBar stateProgressBar;

    @FXML
    private Label stateValueLabel;

    public C0099bR(AbstractC0271a device, C0197di analogInput) {
        this.f246a = device;
        this.f247b = analogInput;
        load(getClass().getResource("/fxml/dialog/virtualcontroller/analogInputSettingsDialog.fxml"));
        setOnCloseRequest(this::m511a);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.nameLabel.setText(this.f247b.m806d());
        if (this.f246a == null) {
            this.commandComboBox.setDisable(true);
        } else {
            this.commandComboBox.getItems().setAll(this.f246a.mo1609b());
        }
        if (this.f247b.m807e() != null) {
            this.commandComboBox.setValue(this.f247b.m807e().toString());
        }
        this.commandComboBox.valueProperty().addListener((observableValue, previous, current) -> {
            if (current == null) {
                this.f247b.m808a((C0159cY) null);
            } else {
                this.f247b.m808a(new C0159cY(current));
            }
        });
        this.sensitivitySlider.valueProperty().addListener((observableValue2, previous2, current2) -> {
            this.f247b.m799a(current2.floatValue());
        });
        this.sensitivityValueLabel.textProperty().bind(this.sensitivitySlider.valueProperty().multiply(100).asString("%.0f%%"));
        this.sensitivitySlider.setValue(this.f247b.m798a());
        this.deadZoneSlider.valueProperty().addListener((observableValue3, previous3, current3) -> {
            this.f247b.m801b(current3.floatValue());
        });
        this.deadZoneValueLabel.textProperty().bind(this.deadZoneSlider.valueProperty().multiply(100).asString("%.0f%%"));
        this.deadZoneSlider.setValue(this.f247b.m800b());
        this.stateValueLabel.textProperty().bind(this.stateProgressBar.progressProperty().multiply(100).asString("%.0f%%"));
        this.f248c = new AnimationTimer() { // from class: org.romstation.application.bR.1
            public void handle(long now) {
                C0099bR.this.m510a(now);
            }
        };
        this.f248c.start();
        if (this.f246a != null && (this.f246a instanceof C0274d)) {
            ((C0274d) this.f246a).m1637a(new C0273c(this.dialogPane));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m510a(long now) {
        if (this.f246a != null && this.f246a.mo1610c()) {
            float value = this.f246a.m1612a(this.f247b);
            if (value != this.f249d) {
                this.stateProgressBar.setProgress(value);
                this.f249d = value;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private void m511a(DialogEvent event) {
        this.f248c.stop();
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
