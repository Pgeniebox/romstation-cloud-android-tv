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
import org.romstation.application.C0036aI;
import org.romstation.application.C0047aT;
import org.romstation.application.C0081b;
import org.romstation.application.database.entity.Series;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/SeriesPaneController.class */
public class SeriesPaneController {

    /* JADX INFO: renamed from: a */
    private final ObservableList<Series> f793a = FXCollections.observableList(new LinkedList());

    @FXML
    private MenuItem createMenuItem;

    @FXML
    private MenuItem selectMenuItem;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<Series> listView;

    @FXML
    private ResourceBundle resources;

    public SeriesPaneController() {
        EntityManager entityManager = C0081b.m309c();
        this.f793a.setAll(entityManager.createNamedQuery(Series.f504b, Series.class).getResultList());
        entityManager.close();
    }

    /* JADX INFO: renamed from: a */
    public ListView<Series> m1326a() {
        return this.listView;
    }

    /* JADX INFO: renamed from: b */
    private List<Series> m1327b() {
        return (List) this.f793a.stream().filter(language -> {
            return !this.listView.getItems().contains(language);
        }).collect(Collectors.toList());
    }

    @FXML
    private void initialize() {
        this.selectMenuItem.disableProperty().bind(Bindings.size(this.listView.getItems()).isEqualTo(Bindings.size(this.f793a)));
        this.removeButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.editButton.disableProperty().bind(Bindings.size(this.listView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void create(ActionEvent event) {
        C0047aT dialog = new C0047aT(new Series());
        Optional<Series> optional = dialog.showAndWait();
        optional.ifPresent(series -> {
            this.listView.getItems().add(series);
        });
    }

    @FXML
    private void select(ActionEvent event) {
        C0036aI dialog = new C0036aI(m1327b());
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Series>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.listView.getItems().addAll(items);
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        C0047aT dialog = new C0047aT((Series) this.listView.getSelectionModel().getSelectedItem());
        Optional<Series> optional = dialog.showAndWait();
        optional.ifPresent(series -> {
            this.listView.refresh();
        });
    }

    @FXML
    private void remove(ActionEvent event) {
        this.listView.getItems().removeAll(this.listView.getSelectionModel().getSelectedItems());
    }
}
