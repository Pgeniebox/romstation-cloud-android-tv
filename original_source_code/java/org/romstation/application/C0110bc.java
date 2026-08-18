package org.romstation.application;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bc */
/* JADX INFO: compiled from: EmulatorImporterDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bc.class */
public class C0110bc extends ApplicationFXMLDialog<List<C0024X>> {

    /* JADX INFO: renamed from: a */
    private final List<C0024X> f272a;

    /* JADX INFO: renamed from: b */
    private final ObservableList<C0024X> f273b = FXCollections.observableList(new LinkedList());

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TableView<C0024X> tableView;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TableColumn<C0024X, Boolean> checkBoxTableColumn;

    @FXML
    private TableColumn<C0024X, String> nameTableColumn;

    @FXML
    private Label alertLabel;

    public C0110bc(List<C0024X> emulators) {
        this.f272a = emulators;
        load(getClass().getResource("/fxml/dialog/importer/legacy/emulatorImporterDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.checkBox.setOnAction(event -> {
            if (this.checkBox.isSelected()) {
                this.f273b.setAll(this.f272a);
            } else {
                this.f273b.clear();
            }
            this.tableView.refresh();
        });
        this.f273b.addListener(c -> {
            this.checkBox.setIndeterminate((this.f273b.isEmpty() || this.f273b.size() == this.f272a.size()) ? false : true);
            this.checkBox.setSelected(this.f273b.size() == this.f272a.size());
        });
        this.checkBoxTableColumn.setCellFactory(param -> {
            return new CheckBoxTableCell(index -> {
                C0024X emulator = (C0024X) this.tableView.getItems().get(index.intValue());
                SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(this.f273b.contains(emulator));
                simpleBooleanProperty.addListener((observable, oldValue, newValue) -> {
                    if (newValue.booleanValue()) {
                        this.f273b.add(emulator);
                    } else {
                        this.f273b.remove(emulator);
                    }
                });
                return simpleBooleanProperty;
            });
        });
        this.alertLabel.setText(String.format(this.alertLabel.getText(), Paths.get(RomStation.m43c().getProperty("path.emulators"), new String[0]).toAbsolutePath()));
        this.dialogPane.lookupButton(ButtonType.OK).disableProperty().bind(Bindings.size(this.f273b).isEqualTo(0));
        this.tableView.getItems().addAll(this.f272a);
        setResizable(true);
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<C0024X> resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return this.f273b;
        }
        return null;
    }
}
