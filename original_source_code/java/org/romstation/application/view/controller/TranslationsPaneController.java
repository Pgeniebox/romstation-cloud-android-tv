package org.romstation.application.view.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javax.persistence.EntityManager;
import org.romstation.application.C0034aG;
import org.romstation.application.C0077aw;
import org.romstation.application.C0078ax;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Locale;
import org.romstation.application.database.entity.Translation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/TranslationsPaneController.class */
public class TranslationsPaneController {

    /* JADX INFO: renamed from: a */
    private final I18n f796a;

    /* JADX INFO: renamed from: b */
    private final ObservableList<Locale> f797b = FXCollections.observableList(new LinkedList());

    /* JADX INFO: renamed from: c */
    private final BooleanProperty f798c = new SimpleBooleanProperty();

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Translation> tableView;

    @FXML
    private ResourceBundle resources;

    public TranslationsPaneController(I18n i18n) {
        this.f796a = i18n;
        EntityManager entityManager = C0081b.m309c();
        this.f797b.setAll(entityManager.createNamedQuery(Locale.f492a, Locale.class).getResultList());
        this.f797b.sort(Comparator.comparing(genre -> {
            return genre.getName().getDefaultString();
        }));
        entityManager.close();
    }

    /* JADX INFO: renamed from: a */
    public I18n m1345a() {
        return this.f796a;
    }

    /* JADX INFO: renamed from: b */
    public boolean m1346b() {
        return this.f798c.get();
    }

    /* JADX INFO: renamed from: c */
    public BooleanProperty m1347c() {
        return this.f798c;
    }

    /* JADX INFO: renamed from: a */
    public void m1348a(boolean useTextArea) {
        this.f798c.set(useTextArea);
    }

    /* JADX INFO: renamed from: d */
    public TableView<Translation> m1349d() {
        return this.tableView;
    }

    /* JADX INFO: renamed from: e */
    private List<Locale> m1350e() {
        List<Locale> result = new LinkedList<>(this.f797b);
        result.removeAll((Collection) this.tableView.getItems().stream().map((v0) -> {
            return v0.getLocale();
        }).collect(Collectors.toList()));
        return result;
    }

    @FXML
    private void initialize() {
        this.addButton.disableProperty().bind(Bindings.size(this.tableView.getItems()).isEqualTo(Bindings.size(this.f797b)));
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.tableView.getItems().setAll(this.f796a.getTranslations());
    }

    @FXML
    private void add() {
        C0034aG dialog = new C0034aG(m1350e());
        dialog.showAndWait().ifPresent(item -> {
            Optional<String> optional = m1351a((String) null);
            optional.ifPresent(string -> {
                this.tableView.getItems().add(new Translation((Locale) item.get(0), string, this.f796a));
            });
        });
    }

    @FXML
    private void delete() {
        this.tableView.getItems().removeAll(this.tableView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void edit() {
        Translation selectedItem = (Translation) this.tableView.getSelectionModel().getSelectedItem();
        Optional<String> optional = m1351a(selectedItem.getString());
        optional.ifPresent(string -> {
            selectedItem.setString(string);
            this.tableView.refresh();
        });
    }

    /* JADX INFO: renamed from: a */
    private Optional<String> m1351a(String text) {
        if (m1346b()) {
            return m1353c(text);
        }
        return m1352b(text);
    }

    /* JADX INFO: renamed from: b */
    private Optional<String> m1352b(String text) {
        C0078ax dialog = new C0078ax(this.resources.getString("i18n.editor.edit.dialog.header"), text);
        return dialog.showAndWait();
    }

    /* JADX INFO: renamed from: c */
    private Optional<String> m1353c(String text) {
        C0077aw dialog = new C0077aw(this.resources.getString("i18n.editor.edit.dialog.header"), text);
        dialog.setResizable(true);
        return dialog.showAndWait();
    }
}
