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

/* JADX INFO: renamed from: org.romstation.application.bd */
/* JADX INFO: compiled from: GameImporterDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bd.class */
public class C0111bd extends ApplicationFXMLDialog<List<C0055ab>> {

    /* JADX INFO: renamed from: a */
    private final List<C0055ab> f274a;

    /* JADX INFO: renamed from: b */
    private final ObservableList<C0055ab> f275b = FXCollections.observableList(new LinkedList());

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TableView<C0055ab> tableView;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TableColumn<C0055ab, Boolean> checkBoxTableColumn;

    @FXML
    private TableColumn<C0055ab, Integer> idTableColumn;

    @FXML
    private TableColumn<C0055ab, String> titleTableColumn;

    @FXML
    private TableColumn<C0055ab, String> systemTableColumn;

    @FXML
    private Label alertLabel;

    public C0111bd(List<C0055ab> games) {
        this.f274a = games;
        load(getClass().getResource("/fxml/dialog/importer/legacy/gameImporterDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.checkBox.setOnAction(event -> {
            if (this.checkBox.isSelected()) {
                this.f275b.setAll(this.f274a);
            } else {
                this.f275b.clear();
            }
            this.tableView.refresh();
        });
        this.f275b.addListener(c -> {
            this.checkBox.setIndeterminate((this.f275b.isEmpty() || this.f275b.size() == this.f274a.size()) ? false : true);
            this.checkBox.setSelected(this.f275b.size() == this.f274a.size());
        });
        this.checkBoxTableColumn.setCellFactory(param -> {
            return new CheckBoxTableCell(index -> {
                C0055ab game = (C0055ab) this.tableView.getItems().get(index.intValue());
                SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(this.f275b.contains(game));
                simpleBooleanProperty.addListener((observable, oldValue, newValue) -> {
                    if (newValue.booleanValue()) {
                        this.f275b.add(game);
                    } else {
                        this.f275b.remove(game);
                    }
                });
                return simpleBooleanProperty;
            });
        });
        this.alertLabel.setText(String.format(this.alertLabel.getText(), Paths.get(RomStation.m43c().getProperty("path.games"), new String[0]).toAbsolutePath()));
        this.dialogPane.lookupButton(ButtonType.OK).disableProperty().bind(Bindings.size(this.f275b).isEqualTo(0));
        this.tableView.getItems().addAll(this.f274a);
        setResizable(true);
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<C0055ab> resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return this.f275b;
        }
        return null;
    }
}
