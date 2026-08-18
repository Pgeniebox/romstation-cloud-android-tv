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
import org.romstation.application.C0030aC;
import org.romstation.application.C0041aN;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.Genre;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/GenresPaneController.class */
public class GenresPaneController {

    /* JADX INFO: renamed from: a */
    private final ObservableList<Genre> f784a = FXCollections.observableList(new LinkedList());

    @FXML
    private MenuItem createMenuItem;

    @FXML
    private MenuItem selectMenuItem;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<Genre> listView;

    @FXML
    private ResourceBundle resources;

    public GenresPaneController() {
        EntityManager entityManager = C0081b.m309c();
        this.f784a.setAll(entityManager.createNamedQuery(Genre.f477b, Genre.class).getResultList());
        this.f784a.sort(Comparator.comparing(genre -> {
            return genre.getName().getDefaultString();
        }));
        entityManager.close();
    }

    /* JADX INFO: renamed from: a */
    public ListView<Genre> m1289a() {
        return this.listView;
    }

    /* JADX INFO: renamed from: b */
    private List<Genre> m1290b() {
        return (List) this.f784a.stream().filter(language -> {
            return !this.listView.getItems().contains(language);
        }).collect(Collectors.toList());
    }

    @FXML
    private void initialize() {
        this.selectMenuItem.disableProperty().bind(Bindings.size(this.listView.getItems()).isEqualTo(Bindings.size(this.f784a)));
        this.removeButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void create(ActionEvent event) {
        C0041aN dialog = new C0041aN(new Genre());
        Optional<Genre> optional = dialog.showAndWait();
        optional.ifPresent(genre -> {
            this.listView.getItems().add(genre);
        });
    }

    @FXML
    private void select(ActionEvent event) {
        C0030aC dialog = new C0030aC(m1290b());
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Genre>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.listView.getItems().addAll(items);
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        C0041aN dialog = new C0041aN((Genre) this.listView.getSelectionModel().getSelectedItem());
        Optional<Genre> optional = dialog.showAndWait();
        optional.ifPresent(genre -> {
            this.listView.refresh();
        });
    }

    @FXML
    private void remove(ActionEvent event) {
        this.listView.getItems().removeAll(this.listView.getSelectionModel().getSelectedItems());
    }
}
