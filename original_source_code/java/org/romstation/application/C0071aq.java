package org.romstation.application;

import java.io.File;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.FileChooser;
import javax.persistence.EntityManager;
import org.romstation.application.database.entity.System;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.SystemComboBox;

/* JADX INFO: renamed from: org.romstation.application.aq */
/* JADX INFO: compiled from: GameBulkImportDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aq.class */
public class C0071aq extends ApplicationFXMLDialog<a> {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private SystemComboBox systemComboBox;

    @FXML
    private Button addFileButton;

    @FXML
    private Button removeFileButton;

    @FXML
    private ListView<File> filesListView;

    public C0071aq() {
        load(getClass().getResource("/fxml/dialog/gameBulkImportDialog.fxml"));
    }

    @FXML
    private void initialize() {
        EntityManager entityManager = C0081b.m309c();
        this.systemComboBox.getComboBox().getItems().addAll(entityManager.createNamedQuery(System.f508b, System.class).getResultList());
        this.systemComboBox.getComboBox().getSelectionModel().selectFirst();
        entityManager.close();
        this.filesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.removeFileButton.disableProperty().bind(Bindings.size(this.filesListView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.dialogPane.lookupButton(ButtonType.OK).disableProperty().bind(Bindings.isEmpty(this.filesListView.getItems()));
        setResizable(true);
    }

    @FXML
    private void addFile() {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(getDialogPane().getScene().getWindow());
        if (files != null) {
            this.filesListView.getItems().addAll(files);
        }
    }

    @FXML
    private void removeFile() {
        this.filesListView.getItems().removeAll(this.filesListView.getSelectionModel().getSelectedItems());
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public a resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return new a((System) this.systemComboBox.getComboBox().getValue(), this.filesListView.getItems());
        }
        return null;
    }

    /* JADX INFO: renamed from: org.romstation.application.aq$a */
    /* JADX INFO: compiled from: GameBulkImportDialog.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aq$a.class */
    public static class a {

        /* JADX INFO: renamed from: a */
        private final System f148a;

        /* JADX INFO: renamed from: b */
        private final List<File> f149b;

        a(System system, List<File> files) {
            this.f148a = system;
            this.f149b = files;
        }

        /* JADX INFO: renamed from: a */
        public System m274a() {
            return this.f148a;
        }

        /* JADX INFO: renamed from: b */
        public List<File> m275b() {
            return this.f149b;
        }
    }
}
