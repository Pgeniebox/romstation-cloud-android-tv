package org.romstation.application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.romstation.application.netplay.C0215c;
import org.romstation.application.network.C0217b;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bg */
/* JADX INFO: compiled from: CreateServerDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bg.class */
public class C0114bg extends ApplicationFXMLDialog<C0215c> {

    /* JADX INFO: renamed from: a */
    private final int f278a;

    /* JADX INFO: renamed from: b */
    private final String f279b;

    /* JADX INFO: renamed from: c */
    private final EnumC0129bv f280c;

    /* JADX INFO: renamed from: d */
    private final int f281d;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TitledPane serverTitledPane;

    @FXML
    private GridPane serverGridPane;

    @FXML
    private RowConstraints liveRowConstraints;

    @FXML
    private RowConstraints instantiatedRowConstraints;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Spinner<Integer> slotsSpinner;

    @FXML
    private CheckBox lockCheckBox;

    @FXML
    private CheckBox liveCheckBox;

    @FXML
    private CheckBox instantiatedCheckBox;

    @FXML
    private TitledPane systemTitledPane;

    @FXML
    private ComboBox<EnumC0123bp> languageComboBox;

    @FXML
    private ComboBox<EnumC0125br> regionComboBox;

    @FXML
    private TitledPane videoSettingsTitledPane;

    @FXML
    private Hyperlink platinumHyperLink;

    @FXML
    private ComboBox<Integer> framerateComboBox;

    @FXML
    private ComboBox<EnumC0121bn> resolutionComboBox;

    @FXML
    private ComboBox<Integer> bitrateComboBox;

    @FXML
    private ComboBox<EnumC0115bh> decoderComboBox;

    @FXML
    private Label mandatoryLabel;

    @FXML
    private ResourceBundle resources;

    public C0114bg(int gameFileId, String gameTitle, EnumC0129bv type, int masterLobbyID) {
        this.f278a = gameFileId;
        this.f279b = gameTitle;
        this.f280c = type;
        this.f281d = masterLobbyID;
        load(getClass().getResource("/fxml/dialog/netplay/createServerDialog.fxml"));
    }

    @FXML
    private void initialize() {
        if (this.f281d == 0) {
            Button okButton = this.dialogPane.lookupButton(ButtonType.OK);
            okButton.disableProperty().bind(this.titleTextField.textProperty().isEmpty());
        } else {
            this.serverTitledPane.setVisible(false);
            this.serverTitledPane.setManaged(false);
            this.mandatoryLabel.setVisible(false);
            this.mandatoryLabel.setManaged(false);
        }
        this.titleTextField.setText(this.f279b);
        this.slotsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 32, Integer.parseInt(RomStation.m43c().getProperty("serverSetup.slots"))));
        this.slotsSpinner.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.booleanValue()) {
                this.slotsSpinner.increment(0);
            }
        });
        this.lockCheckBox.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("serverSetup.lock")));
        if (this.f280c == EnumC0129bv.CLOUD) {
            this.liveCheckBox.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("serverSetup.live")));
            this.instantiatedCheckBox.setSelected(Boolean.parseBoolean(RomStation.m43c().getProperty("serverSetup.instantiated")));
            this.languageComboBox.getItems().setAll(EnumC0123bp.values());
            try {
                this.languageComboBox.setValue(EnumC0123bp.m613a(Integer.parseInt(RomStation.m43c().getProperty("serverSetup.language"))));
            } catch (IllegalArgumentException exception) {
                RomStation.m42b().log(Level.WARNING, "invalid system language requested", (Throwable) exception);
                this.languageComboBox.setValue(EnumC0123bp.ENGLISH);
            }
            this.languageComboBox.setButtonCell(new C0124bq());
            this.languageComboBox.setCellFactory(new C0124bq.a());
            this.regionComboBox.getItems().setAll(EnumC0125br.values());
            try {
                this.regionComboBox.setValue(EnumC0125br.m617a(Integer.parseInt(RomStation.m43c().getProperty("serverSetup.region"))));
            } catch (IllegalArgumentException exception2) {
                RomStation.m42b().log(Level.WARNING, "invalid system region requested", (Throwable) exception2);
                this.regionComboBox.setValue(EnumC0125br.AUTO);
            }
            this.regionComboBox.setButtonCell(new C0126bs());
            this.regionComboBox.setCellFactory(new C0126bs.a());
            this.platinumHyperLink.translateXProperty().bind(Bindings.createDoubleBinding(() -> {
                return Double.valueOf(((this.videoSettingsTitledPane.getWidth() - this.platinumHyperLink.getLayoutX()) - this.platinumHyperLink.getWidth()) - 5.0d);
            }, new Observable[]{this.videoSettingsTitledPane.widthProperty()}));
            this.framerateComboBox.setButtonCell(new C0118bk());
            this.framerateComboBox.setCellFactory(new C0118bk.a());
            this.framerateComboBox.getItems().setAll(new Integer[]{30, 60});
            if (C0058ae.m195a().m211m() == EnumC0059af.PLATINUM) {
                try {
                    this.framerateComboBox.setValue(Integer.valueOf(RomStation.m43c().getProperty("serverSetup.framerate")));
                } catch (NumberFormatException exception3) {
                    RomStation.m42b().log(Level.WARNING, "invalid video framerate requested", (Throwable) exception3);
                    this.framerateComboBox.setValue(30);
                }
            } else {
                this.framerateComboBox.getSelectionModel().selectFirst();
            }
            this.framerateComboBox.valueProperty().addListener((observableValue2, oldValue2, newValue2) -> {
                if (newValue2.intValue() > 30 && C0058ae.m195a().m211m() != EnumC0059af.PLATINUM) {
                    m575b();
                    Platform.runLater(() -> {
                        this.framerateComboBox.setValue(oldValue2);
                    });
                } else {
                    m574a();
                }
            });
            this.resolutionComboBox.setButtonCell(new C0122bo());
            this.resolutionComboBox.setCellFactory(new C0122bo.a());
            this.resolutionComboBox.getItems().setAll(EnumC0121bn.values());
            if (C0058ae.m195a().m211m() == EnumC0059af.PLATINUM) {
                try {
                    this.resolutionComboBox.setValue(EnumC0121bn.m609a(Integer.parseInt(RomStation.m43c().getProperty("serverSetup.resolution"))));
                } catch (IllegalArgumentException exception4) {
                    RomStation.m42b().log(Level.WARNING, "invalid video resolution requested", (Throwable) exception4);
                    this.resolutionComboBox.setValue(EnumC0121bn.NATIVE);
                }
            } else {
                this.resolutionComboBox.getSelectionModel().selectFirst();
            }
            this.resolutionComboBox.valueProperty().addListener((observableValue3, oldValue3, newValue3) -> {
                if (newValue3 == EnumC0121bn.HD && C0058ae.m195a().m211m() != EnumC0059af.PLATINUM) {
                    m575b();
                    Platform.runLater(() -> {
                        this.resolutionComboBox.setValue(oldValue3);
                    });
                } else {
                    m574a();
                }
            });
            this.bitrateComboBox.setButtonCell(new C0113bf());
            this.bitrateComboBox.setCellFactory(new C0113bf.a());
            for (int i = 2000; i < 14000; i += 1000) {
                this.bitrateComboBox.getItems().add(Integer.valueOf(i));
            }
            try {
                this.bitrateComboBox.setValue(Integer.valueOf(RomStation.m43c().getProperty("serverSetup.bitrate")));
            } catch (NumberFormatException exception5) {
                RomStation.m42b().log(Level.WARNING, "invalid video bitrate requested", (Throwable) exception5);
                this.bitrateComboBox.setValue(5000);
            }
            if (C0058ae.m195a().m211m() != EnumC0059af.PLATINUM && ((Integer) this.bitrateComboBox.getValue()).intValue() > 5000) {
                this.bitrateComboBox.setValue(5000);
            }
            this.bitrateComboBox.valueProperty().addListener((observableValue4, oldValue4, newValue4) -> {
                if (newValue4.intValue() > 5000 && C0058ae.m195a().m211m() != EnumC0059af.PLATINUM) {
                    m575b();
                    Platform.runLater(() -> {
                        this.bitrateComboBox.setValue(oldValue4);
                    });
                }
            });
            this.decoderComboBox.setButtonCell(new C0116bi());
            this.decoderComboBox.setCellFactory(new C0116bi.a());
            this.decoderComboBox.getItems().addAll(EnumC0115bh.values());
            try {
                this.decoderComboBox.setValue(EnumC0115bh.m586a(Integer.parseInt(RomStation.m43c().getProperty("cloudPlayer.decoder"))));
                return;
            } catch (Exception exception6) {
                RomStation.m42b().log(Level.WARNING, "invalid video decoder requested", (Throwable) exception6);
                this.decoderComboBox.setValue(EnumC0115bh.AUTO);
                return;
            }
        }
        this.liveCheckBox.setVisible(false);
        this.liveCheckBox.setManaged(false);
        this.instantiatedCheckBox.setVisible(false);
        this.instantiatedCheckBox.setManaged(false);
        this.systemTitledPane.setVisible(false);
        this.systemTitledPane.setManaged(false);
        this.videoSettingsTitledPane.setVisible(false);
        this.videoSettingsTitledPane.setManaged(false);
        this.serverGridPane.getRowConstraints().removeAll(new RowConstraints[]{this.liveRowConstraints, this.instantiatedRowConstraints});
    }

    /* JADX INFO: renamed from: a */
    private void m574a() {
        switch (((Integer) this.framerateComboBox.getValue()).intValue()) {
            case 30:
                switch ((EnumC0121bn) this.resolutionComboBox.getValue()) {
                    case NATIVE:
                        this.bitrateComboBox.setValue(5000);
                        break;
                    case HD:
                        this.bitrateComboBox.setValue(7000);
                        break;
                }
                break;
            case 60:
                switch ((EnumC0121bn) this.resolutionComboBox.getValue()) {
                    case NATIVE:
                        this.bitrateComboBox.setValue(7000);
                        break;
                    case HD:
                        this.bitrateComboBox.setValue(10000);
                        break;
                }
                break;
        }
    }

    /* JADX INFO: renamed from: b */
    private void m575b() {
        C0075au dialog = new C0075au(this.resources.getString("platinumDialog.content.platinumOption"));
        ButtonType buttonType = (ButtonType) dialog.showAndWait().orElse(ButtonType.CANCEL);
        if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            showStore();
        }
    }

    @FXML
    private void showStore() {
        try {
            Desktop.getDesktop().browse(new URI(C0217b.m961b() + "/store"));
        } catch (IOException | URISyntaxException exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0215c resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            RomStation.m43c().setProperty("serverSetup.slots", ((Integer) this.slotsSpinner.getValue()).toString());
            RomStation.m43c().setProperty("serverSetup.lock", String.valueOf(this.lockCheckBox.isSelected()));
            if (this.f280c == EnumC0129bv.CLOUD) {
                RomStation.m43c().setProperty("serverSetup.live", String.valueOf(this.liveCheckBox.isSelected()));
                RomStation.m43c().setProperty("serverSetup.instantiated", String.valueOf(this.instantiatedCheckBox.isSelected()));
                RomStation.m43c().setProperty("serverSetup.language", String.valueOf(((EnumC0123bp) this.languageComboBox.getValue()).m612a()));
                RomStation.m43c().setProperty("serverSetup.region", String.valueOf(((EnumC0125br) this.regionComboBox.getValue()).m616a()));
                RomStation.m43c().setProperty("serverSetup.framerate", String.valueOf(this.framerateComboBox.getValue()));
                RomStation.m43c().setProperty("serverSetup.resolution", String.valueOf(((EnumC0121bn) this.resolutionComboBox.getValue()).m608a()));
                RomStation.m43c().setProperty("serverSetup.bitrate", String.valueOf(this.bitrateComboBox.getValue()));
                RomStation.m43c().setProperty("cloudPlayer.decoder", String.valueOf(((EnumC0115bh) this.decoderComboBox.getValue()).m585a()));
            }
            C0215c config = new C0215c(this.f280c, this.titleTextField.getText(), this.descriptionTextField.getText(), this.passwordTextField.getText(), ((Integer) this.slotsSpinner.getValue()).intValue(), this.lockCheckBox.isSelected());
            config.m938c(this.f278a);
            if (this.f280c == EnumC0129bv.CLOUD) {
                config.m928a(this.f281d);
                config.m942b(this.liveCheckBox.isSelected());
                config.m944c(this.instantiatedCheckBox.isSelected());
                config.m946d(((EnumC0123bp) this.languageComboBox.getValue()).m612a());
                config.m948e(((EnumC0125br) this.regionComboBox.getValue()).m616a());
                config.m950f(((Integer) this.framerateComboBox.getValue()).intValue());
                config.m952a((EnumC0121bn) this.resolutionComboBox.getValue());
                config.m954g(((Integer) this.bitrateComboBox.getValue()).intValue());
            }
            return config;
        }
        return null;
    }
}
