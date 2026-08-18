package org.romstation.application;

import com.google.gson.JsonObject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.bm */
/* JADX INFO: compiled from: NetplayGameFileChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bm.class */
public class C0120bm extends ApplicationFXMLDialog<JsonObject> {

    /* JADX INFO: renamed from: a */
    private final JsonObject[] f290a;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TableView<JsonObject> tableView;

    @FXML
    private TableColumn<JsonObject, Integer> idTableColumn;

    @FXML
    private TableColumn<JsonObject, String> nameTableColumn;

    @FXML
    private TableColumn<JsonObject, Long> dateTableColumn;

    @FXML
    private TableColumn<JsonObject, Long> sizeTableColumn;

    public C0120bm(JsonObject[] files) {
        this.f290a = files;
        load(getClass().getResource("/fxml/dialog/netplay/netplayGameFileChoiceDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.tableView.setRowFactory(this::m601a);
        this.idTableColumn.setCellValueFactory(value -> {
            return new SimpleObjectProperty(Integer.valueOf(((JsonObject) value.getValue()).get("file_id").getAsInt()));
        });
        this.nameTableColumn.setCellValueFactory(value2 -> {
            return new SimpleObjectProperty(((JsonObject) value2.getValue()).get("label").getAsString());
        });
        this.dateTableColumn.setCellValueFactory(value3 -> {
            return new SimpleObjectProperty(Long.valueOf(((JsonObject) value3.getValue()).get("added_date").getAsLong()));
        });
        this.sizeTableColumn.setCellValueFactory(value4 -> {
            return new SimpleObjectProperty(Long.valueOf(((JsonObject) value4.getValue()).get("size").getAsLong()));
        });
        this.tableView.getItems().addAll(this.f290a);
        this.dialogPane.lookupButton(ButtonType.OK).disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());
        setResizable(true);
    }

    /* JADX INFO: renamed from: a */
    private TableRow<JsonObject> m601a(TableView<JsonObject> object) {
        TableRow<JsonObject> tableRow = new TableRow<>();
        tableRow.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && !this.tableView.getSelectionModel().isEmpty()) {
                setResult(this.tableView.getSelectionModel().getSelectedItem());
            }
        });
        return tableRow;
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public JsonObject resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return (JsonObject) this.tableView.getSelectionModel().getSelectedItem();
        }
        return null;
    }
}
