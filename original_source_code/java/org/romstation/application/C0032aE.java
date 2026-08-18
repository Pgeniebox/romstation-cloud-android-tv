package org.romstation.application;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.romstation.application.database.entity.Link;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aE */
/* JADX INFO: compiled from: LinkChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aE.class */
public class C0032aE extends ApplicationFXMLDialog<Link> {

    /* JADX INFO: renamed from: a */
    private final List<Link> f62a;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TableView<Link> tableView;

    @FXML
    private TableColumn<Link, Integer> idColumn;

    @FXML
    private TableColumn<Link, String> nameColumn;

    @FXML
    private TableColumn<Link, String> locationColumn;

    public C0032aE(List<Link> links) {
        this.f62a = links;
        setResizable(true);
        load(getClass().getResource("/fxml/dialog/choice/linkChoiceDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.tableView.getItems().addAll(this.f62a);
        this.tableView.getSelectionModel().selectFirst();
        this.dialogPane.lookupButton(ButtonType.OK).disableProperty().bind(Bindings.isEmpty(this.tableView.getSelectionModel().getSelectedItems()));
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Link resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return (Link) this.tableView.getSelectionModel().getSelectedItem();
        }
        return null;
    }
}
