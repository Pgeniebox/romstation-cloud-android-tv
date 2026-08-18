package org.romstation.application;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.virtualcontroller.device.AbstractC0271a;
import org.romstation.application.virtualcontroller.device.C0272b;
import org.romstation.application.virtualcontroller.device.C0273c;
import org.romstation.application.virtualcontroller.device.C0274d;

/* JADX INFO: renamed from: org.romstation.application.bU */
/* JADX INFO: compiled from: VirtualControllerConfigurationDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bU.class */
public class C0102bU extends ApplicationFXMLDialog<C0190db> implements Initializable {

    /* JADX INFO: renamed from: a */
    private final C0160cZ f263a;

    /* JADX INFO: renamed from: b */
    private C0273c f264b;

    /* JADX INFO: renamed from: c */
    private AnimationTimer f265c;

    /* JADX INFO: renamed from: d */
    private final HashMap<AbstractC0199dk, Integer> f266d = new HashMap<>();

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ChoiceBox<AbstractC0271a> devicesChoiceBox;

    @FXML
    private SplitMenuButton refreshDevicesSplitMenuButton;

    @FXML
    private CheckMenuItem showAllDevicesCheckMenuItem;

    @FXML
    private ChoiceBox<C0190db> profilesChoiceBox;

    @FXML
    private MenuItem createProfileMenuItem;

    @FXML
    private MenuItem copyProfileMenuItem;

    @FXML
    private MenuItem renameProfileMenuItem;

    @FXML
    private MenuItem deleteProfileMenuItem;

    @FXML
    private Button configurationWizardButton;

    @FXML
    private Button bindInputButton;

    @FXML
    private Button unbindInputButton;

    @FXML
    private Button configureInputButton;

    @FXML
    private TableView<AbstractC0199dk> inputsTableView;

    @FXML
    private TableColumn<AbstractC0199dk, String> inputNameTableColumn;

    @FXML
    private TableColumn<AbstractC0199dk, C0159cY> inputBindingTableColumn;

    @FXML
    private TableColumn<AbstractC0199dk, Double> inputStateTableColumn;

    @FXML
    private Label controllerNameLabel;

    @FXML
    private StackPane controllerStackPane;

    @FXML
    private ImageView controllerImageView;

    public C0102bU(C0160cZ config) {
        this.f263a = config;
        load(getClass().getResource("/fxml/dialog/virtualcontroller/virtualControllerConfigurationDialog.fxml"));
        setOnCloseRequest(this::m535a);
        setResizable(true);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.devicesChoiceBox.valueProperty().addListener((observableValue, previous, current) -> {
            if (!this.profilesChoiceBox.getSelectionModel().isEmpty()) {
                ((C0190db) this.profilesChoiceBox.getValue()).m768a(current);
            }
        });
        this.devicesChoiceBox.disableProperty().bind(this.profilesChoiceBox.valueProperty().isNull());
        this.refreshDevicesSplitMenuButton.disableProperty().bind(this.profilesChoiceBox.valueProperty().isNull());
        this.profilesChoiceBox.getItems().setAll(this.f263a.m729e());
        this.profilesChoiceBox.valueProperty().addListener((observableValue2, previous2, current2) -> {
            if (current2 != null) {
                this.devicesChoiceBox.getSelectionModel().select(current2.m767b());
                this.inputsTableView.getItems().setAll(current2.m769c());
            } else {
                this.devicesChoiceBox.getSelectionModel().clearSelection();
                this.inputsTableView.getItems().clear();
            }
        });
        this.profilesChoiceBox.getSelectionModel().select(this.f263a.m727d());
        this.copyProfileMenuItem.disableProperty().bind(this.profilesChoiceBox.valueProperty().isNull());
        this.renameProfileMenuItem.disableProperty().bind(this.profilesChoiceBox.valueProperty().isNull());
        this.deleteProfileMenuItem.disableProperty().bind(this.profilesChoiceBox.valueProperty().isNull());
        this.configurationWizardButton.disableProperty().bind(this.profilesChoiceBox.valueProperty().isNull());
        this.bindInputButton.disableProperty().bind(Bindings.size(this.inputsTableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.unbindInputButton.disableProperty().bind(this.inputsTableView.getSelectionModel().selectedItemProperty().isNull());
        this.configureInputButton.disableProperty().bind(Bindings.size(this.inputsTableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.inputsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.inputsTableView.setRowFactory(tableView -> {
            TableRow<AbstractC0199dk> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    m536a((AbstractC0271a) this.devicesChoiceBox.getValue(), (AbstractC0199dk) row.getItem());
                }
            });
            return row;
        });
        this.inputNameTableColumn.setCellValueFactory(cell -> {
            return new SimpleObjectProperty(((AbstractC0199dk) cell.getValue()).m806d());
        });
        this.inputBindingTableColumn.setCellValueFactory(cell2 -> {
            return new SimpleObjectProperty(((AbstractC0199dk) cell2.getValue()).m807e());
        });
        this.inputStateTableColumn.setCellValueFactory(cell3 -> {
            if (this.devicesChoiceBox.getSelectionModel().isEmpty()) {
                return new SimpleObjectProperty(Double.valueOf(0.0d));
            }
            return new SimpleObjectProperty(Double.valueOf(((AbstractC0271a) this.devicesChoiceBox.getValue()).m1612a((AbstractC0199dk) cell3.getValue())));
        });
        this.controllerNameLabel.setText(this.f263a.m721a());
        if (this.f263a.m723b() != null) {
            this.controllerImageView.setImage(new Image(this.f263a.m723b()));
            this.controllerImageView.fitWidthProperty().bind(this.controllerStackPane.widthProperty());
            this.controllerImageView.fitHeightProperty().bind(this.controllerStackPane.heightProperty());
        }
        this.f265c = new AnimationTimer() { // from class: org.romstation.application.bU.1
            public void handle(long now) {
                C0102bU.this.m534a(now);
            }
        };
        this.f265c.start();
        this.f264b = new C0273c(this.dialogPane);
        m537a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m534a(long now) {
        AbstractC0271a device;
        boolean refresh = false;
        if (getDialogPane().getScene().getWindow().isFocused() && (device = (AbstractC0271a) this.devicesChoiceBox.getValue()) != null && device.mo1610c()) {
            for (AbstractC0199dk input : this.inputsTableView.getItems()) {
                int value = (int) (device.m1612a(input) * 100.0f);
                Integer cache = this.f266d.put(input, Integer.valueOf(value));
                if (cache == null) {
                    refresh = true;
                } else if (value != cache.intValue()) {
                    refresh = true;
                }
            }
            if (refresh) {
                this.inputsTableView.refresh();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private void m535a(DialogEvent event) {
        this.f265c.stop();
    }

    /* JADX INFO: renamed from: a */
    private void m536a(AbstractC0271a device, AbstractC0199dk input) {
        C0100bS dialog = new C0100bS(device, input);
        dialog.showAndWait().ifPresent(binding -> {
            input.m808a(binding);
            this.inputsTableView.refresh();
        });
    }

    @FXML
    private void refreshDevices(ActionEvent event) {
        m537a();
    }

    /* JADX INFO: renamed from: a */
    private void m537a() {
        AbstractC0271a selectedItem = (AbstractC0271a) this.devicesChoiceBox.getSelectionModel().getSelectedItem();
        this.devicesChoiceBox.getItems().clear();
        C0274d keyboardMouseDevice = C0274d.m1638f();
        keyboardMouseDevice.m1637a(this.f264b);
        this.devicesChoiceBox.getItems().add(keyboardMouseDevice);
        if (this.showAllDevicesCheckMenuItem.isSelected()) {
            this.devicesChoiceBox.getItems().addAll(C0272b.m1619i());
        } else {
            this.devicesChoiceBox.getItems().addAll(C0272b.m1618h());
        }
        this.devicesChoiceBox.getSelectionModel().select(selectedItem);
    }

    @FXML
    private void createProfile(ActionEvent event) {
        C0078ax textInputDialog = new C0078ax(getResources().getString("virtualControllerConfigurationDialog.profile.name.dialog.header"));
        textInputDialog.showAndWait().ifPresent(name -> {
            try {
                C0190db profile = C0192dd.m785a(this.f263a.m725c());
                profile.m766a(name);
                this.profilesChoiceBox.getItems().add(profile);
                this.profilesChoiceBox.getSelectionModel().select(profile);
            } catch (Exception exception) {
                RomStation.m42b().log(Level.SEVERE, "failed to create controller profile", (Throwable) exception);
            }
        });
    }

    @FXML
    private void copyProfile(ActionEvent event) {
        C0078ax textInputDialog = new C0078ax(getResources().getString("virtualControllerConfigurationDialog.profile.name.dialog.header"), ((C0190db) this.profilesChoiceBox.getValue()).m765a());
        textInputDialog.showAndWait().ifPresent(name -> {
            try {
                C0190db profile = C0192dd.m785a((C0190db) this.profilesChoiceBox.getValue());
                profile.m766a(name);
                this.profilesChoiceBox.getItems().add(profile);
                this.profilesChoiceBox.getSelectionModel().select(profile);
            } catch (Exception exception) {
                RomStation.m42b().log(Level.SEVERE, "failed to copy controller profile", (Throwable) exception);
            }
        });
    }

    @FXML
    private void renameProfile(ActionEvent event) {
        C0190db profile = (C0190db) this.profilesChoiceBox.getValue();
        int index = this.profilesChoiceBox.getSelectionModel().getSelectedIndex();
        C0078ax textInputDialog = new C0078ax(getResources().getString("virtualControllerConfigurationDialog.profile.name.dialog.header"), profile.m765a());
        textInputDialog.showAndWait().ifPresent(name -> {
            profile.m766a(name);
            this.profilesChoiceBox.getItems().remove(index);
            this.profilesChoiceBox.getItems().add(index, profile);
            this.profilesChoiceBox.getSelectionModel().select(index);
        });
    }

    @FXML
    private void deleteProfile(ActionEvent event) {
        C0190db selectedItem = (C0190db) this.profilesChoiceBox.getSelectionModel().getSelectedItem();
        ApplicationAlert alert = new ApplicationAlert(getResources().getString("virtualControllerConfigurationDialog.profile.delete.alert.header"), String.format(getResources().getString("virtualControllerConfigurationDialog.profile.delete.alert.content"), selectedItem), Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                this.profilesChoiceBox.getItems().remove(selectedItem);
                if (this.profilesChoiceBox.getItems().isEmpty()) {
                    try {
                        C0190db profile = C0192dd.m785a(this.f263a.m725c());
                        this.profilesChoiceBox.getItems().add(profile);
                        this.profilesChoiceBox.getSelectionModel().select(profile);
                        return;
                    } catch (Exception exception) {
                        RomStation.m42b().log(Level.SEVERE, "failed to create controller profile", (Throwable) exception);
                        return;
                    }
                }
                this.profilesChoiceBox.getSelectionModel().selectNext();
            }
        });
    }

    @FXML
    private void configurationWizard(ActionEvent event) {
        AbstractC0271a device = (AbstractC0271a) this.devicesChoiceBox.getValue();
        if (device != null) {
            int row = 0;
            for (AbstractC0199dk input : this.inputsTableView.getItems()) {
                this.inputsTableView.getSelectionModel().clearAndSelect(row);
                C0100bS dialog = new C0100bS(device, input);
                Optional<C0159cY> optional = dialog.showAndWait();
                if (optional.isPresent()) {
                    input.m808a(optional.get());
                    this.inputsTableView.refresh();
                    row++;
                } else {
                    return;
                }
            }
        }
    }

    @FXML
    private void bindInput(ActionEvent event) {
        m536a((AbstractC0271a) this.devicesChoiceBox.getValue(), (AbstractC0199dk) this.inputsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void configureInput(ActionEvent event) {
        AbstractC0199dk input = (AbstractC0199dk) this.inputsTableView.getSelectionModel().getSelectedItem();
        if (input instanceof C0198dj) {
            C0101bT dialog = new C0101bT((AbstractC0271a) this.devicesChoiceBox.getValue(), (C0198dj) input);
            dialog.showAndWait();
        } else if (input instanceof C0197di) {
            C0099bR dialog2 = new C0099bR((AbstractC0271a) this.devicesChoiceBox.getValue(), (C0197di) input);
            dialog2.showAndWait();
        }
        if (this.devicesChoiceBox.getValue() != null && (this.devicesChoiceBox.getValue() instanceof C0274d)) {
            ((C0274d) this.devicesChoiceBox.getValue()).m1637a(this.f264b);
        }
        this.inputsTableView.refresh();
    }

    @FXML
    private void clearInputBinding(ActionEvent event) {
        for (AbstractC0199dk selectedItem : this.inputsTableView.getSelectionModel().getSelectedItems()) {
            selectedItem.m808a((C0159cY) null);
        }
        this.inputsTableView.refresh();
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if (!this.inputsTableView.getSelectionModel().isEmpty() && event.getCode() == KeyCode.DELETE) {
            for (AbstractC0199dk selectedItem : this.inputsTableView.getSelectionModel().getSelectedItems()) {
                selectedItem.m808a((C0159cY) null);
            }
            this.inputsTableView.refresh();
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        if (!this.inputsTableView.getSelectionModel().isEmpty() && event.getCode() == KeyCode.ENTER) {
            m536a((AbstractC0271a) this.devicesChoiceBox.getValue(), (AbstractC0199dk) this.inputsTableView.getSelectionModel().getSelectedItem());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0190db resultConverter(ButtonType buttonType) {
        this.f263a.m728b((C0190db) this.profilesChoiceBox.getValue());
        this.f263a.m729e().clear();
        this.f263a.m729e().addAll(this.profilesChoiceBox.getItems());
        return (C0190db) this.profilesChoiceBox.getValue();
    }
}
