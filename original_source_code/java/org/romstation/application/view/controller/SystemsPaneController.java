package org.romstation.application.view.controller;

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
import org.romstation.application.C0037aJ;
import org.romstation.application.C0048aU;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.System;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/SystemsPaneController.class */
public class SystemsPaneController {

    /* JADX INFO: renamed from: a */
    private final ObservableList<System> f794a = FXCollections.observableList(new LinkedList());

    @FXML
    private MenuItem createMenuItem;

    @FXML
    private MenuItem selectMenuItem;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<System> listView;

    @FXML
    private ResourceBundle resources;

    public SystemsPaneController() {
        EntityManager entityManager = C0081b.m309c();
        this.f794a.setAll(entityManager.createNamedQuery(System.f508b, System.class).getResultList());
        entityManager.close();
    }

    /* JADX INFO: renamed from: a */
    public ListView<System> m1332a() {
        return this.listView;
    }

    /* JADX INFO: renamed from: b */
    private List<System> m1333b() {
        return (List) this.f794a.stream().filter(system -> {
            return !this.listView.getItems().contains(system);
        }).collect(Collectors.toList());
    }

    @FXML
    private void initialize() {
        this.selectMenuItem.disableProperty().bind(Bindings.size(this.listView.getItems()).isEqualTo(Bindings.size(this.f794a)));
        this.removeButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void create(ActionEvent event) {
        C0048aU dialog = new C0048aU(new System());
        Optional<System> optional = dialog.showAndWait();
        optional.ifPresent(system -> {
            this.listView.getItems().add(system);
        });
    }

    @FXML
    private void select(ActionEvent event) {
        C0037aJ dialog = new C0037aJ(m1333b());
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<System>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.listView.getItems().addAll(items);
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        C0048aU dialog = new C0048aU((System) this.listView.getSelectionModel().getSelectedItem());
        Optional<System> optional = dialog.showAndWait();
        optional.ifPresent(system -> {
            this.listView.refresh();
        });
    }

    @FXML
    private void remove(ActionEvent event) {
        this.listView.getItems().removeAll(this.listView.getSelectionModel().getSelectedItems());
    }
}
