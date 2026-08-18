package org.romstation.application;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.SearchField;

/* JADX INFO: renamed from: org.romstation.application.aF */
/* JADX INFO: compiled from: ListViewChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aF.class */
public abstract class AbstractC0033aF<T> extends ApplicationFXMLDialog<List<T>> {

    /* JADX INFO: renamed from: a */
    private final List<T> f63a;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private SearchField searchField;

    @FXML
    private ListView<T> listView;

    /* JADX INFO: renamed from: a */
    abstract List<T> mo118a(String str);

    public AbstractC0033aF(List<T> defaultItems) {
        this.f63a = defaultItems;
        load(getClass().getResource("/fxml/dialog/choice/listViewChoiceDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.listView.getItems().setAll(mo118a(newValue));
        });
        if (this.f63a != null) {
            this.listView.getItems().addAll(this.f63a);
        }
        this.dialogPane.lookupButton(ButtonType.OK).disableProperty().bind(Bindings.isEmpty(this.listView.getSelectionModel().getSelectedItems()));
        setResizable(true);
    }

    /* JADX INFO: renamed from: a */
    public List<T> m122a() {
        return this.f63a;
    }

    /* JADX INFO: renamed from: b */
    public SearchField m123b() {
        return this.searchField;
    }

    /* JADX INFO: renamed from: c */
    public ListView<T> m124c() {
        return this.listView;
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<T> resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return this.listView.getSelectionModel().getSelectedItems();
        }
        return null;
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
            ObservableList<T> selectedItems = this.listView.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
                setResult(selectedItems);
            }
        }
    }
}
