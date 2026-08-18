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
import org.romstation.application.C0038aK;
import org.romstation.application.C0049aV;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.Tag;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/TagsPaneController.class */
public class TagsPaneController {

    /* JADX INFO: renamed from: a */
    private final ObservableList<Tag> f795a = FXCollections.observableList(new LinkedList());

    @FXML
    private MenuItem createMenuItem;

    @FXML
    private MenuItem selectMenuItem;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<Tag> listView;

    @FXML
    private ResourceBundle resources;

    public TagsPaneController() {
        EntityManager entityManager = C0081b.m309c();
        this.f795a.setAll(entityManager.createNamedQuery(Tag.f513b, Tag.class).getResultList());
        this.f795a.sort(Comparator.comparing(genre -> {
            return genre.getName().getDefaultString();
        }));
        entityManager.close();
    }

    /* JADX INFO: renamed from: a */
    public ListView<Tag> m1338a() {
        return this.listView;
    }

    /* JADX INFO: renamed from: b */
    private List<Tag> m1339b() {
        return (List) this.f795a.stream().filter(language -> {
            return !this.listView.getItems().contains(language);
        }).collect(Collectors.toList());
    }

    @FXML
    private void initialize() {
        this.selectMenuItem.disableProperty().bind(Bindings.size(this.listView.getItems()).isEqualTo(Bindings.size(this.f795a)));
        this.removeButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void create(ActionEvent event) {
        C0049aV dialog = new C0049aV(new Tag());
        Optional<Tag> optional = dialog.showAndWait();
        optional.ifPresent(tag -> {
            this.listView.getItems().add(tag);
        });
    }

    @FXML
    private void select(ActionEvent event) {
        C0038aK dialog = new C0038aK(m1339b());
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Tag>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.listView.getItems().addAll(items);
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        C0049aV dialog = new C0049aV((Tag) this.listView.getSelectionModel().getSelectedItem());
        Optional<Tag> optional = dialog.showAndWait();
        optional.ifPresent(tag -> {
            this.listView.refresh();
        });
    }

    @FXML
    private void remove(ActionEvent event) {
        this.listView.getItems().removeAll(this.listView.getSelectionModel().getSelectedItems());
    }
}
