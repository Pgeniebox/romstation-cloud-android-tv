package org.romstation.application.view.controller;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javax.persistence.EntityManager;
import org.romstation.application.C0031aD;
import org.romstation.application.C0042aO;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.Language;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/LanguagesPaneController.class */
public class LanguagesPaneController {

    /* JADX INFO: renamed from: a */
    private final ObservableList<Language> f785a = FXCollections.observableList(new LinkedList());

    @FXML
    private MenuItem createMenuItem;

    @FXML
    private MenuItem selectMenuItem;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<Language> listView;

    @FXML
    private ResourceBundle resources;

    public LanguagesPaneController() {
        EntityManager entityManager = C0081b.m309c();
        this.f785a.setAll(entityManager.createNamedQuery(Language.f485b, Language.class).getResultList());
        this.f785a.sort(Comparator.comparing(language -> {
            return language.getName().getDefaultString();
        }));
        entityManager.close();
    }

    /* JADX INFO: renamed from: a */
    public ListView<Language> m1296a() {
        return this.listView;
    }

    /* JADX INFO: renamed from: b */
    private List<Language> m1297b() {
        return (List) this.f785a.stream().filter(language -> {
            return !this.listView.getItems().contains(language);
        }).collect(Collectors.toList());
    }

    @FXML
    private void initialize() {
        this.selectMenuItem.disableProperty().bind(Bindings.size(this.listView.getItems()).isEqualTo(Bindings.size(this.f785a)));
        this.removeButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void create(ActionEvent event) {
        C0042aO dialog = new C0042aO(new Language());
        Optional<Language> optional = dialog.showAndWait();
        optional.ifPresent(language -> {
            this.listView.getItems().add(language);
        });
    }

    @FXML
    private void select(ActionEvent event) {
        C0031aD dialog = new C0031aD(m1297b());
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Language>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.listView.getItems().addAll(items);
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        C0042aO dialog = new C0042aO((Language) this.listView.getSelectionModel().getSelectedItem());
        Optional<Language> optional = dialog.showAndWait();
        optional.ifPresent(language -> {
            this.listView.refresh();
        });
    }

    @FXML
    private void remove(ActionEvent event) {
        this.listView.getItems().removeAll(this.listView.getSelectionModel().getSelectedItems());
    }
}
