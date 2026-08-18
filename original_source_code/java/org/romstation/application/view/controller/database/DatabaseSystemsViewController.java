package org.romstation.application.view.controller.database;

import com.google.common.eventbus.Subscribe;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.romstation.application.C0048aU;
import org.romstation.application.C0081b;
import org.romstation.application.C0165ce;
import org.romstation.application.C0169ci;
import org.romstation.application.C0170cj;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.System;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.SearchField;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/database/DatabaseSystemsViewController.class */
public class DatabaseSystemsViewController {

    /* JADX INFO: renamed from: a */
    private Timeline f812a;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private SearchField nameSearchField;

    @FXML
    private Label searchResultLabel;

    @FXML
    private TableView<System> tableView;

    @FXML
    private TableColumn<System, Integer> idTableColumn;

    @FXML
    private TableColumn<System, String> nameTableColumn;

    @FXML
    private TableColumn<System, Image> graphicTableColumn;

    @FXML
    private TableColumn<System, Long> libraryReferencesTableColumn;

    @FXML
    private TableColumn<System, Long> emulatorsReferencesTableColumn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.nameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.f812a.playFromStart();
        });
        this.f812a = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(250.0d), event -> {
            m1453a();
        }, new KeyValue[0])});
        this.libraryReferencesTableColumn.setCellValueFactory(param -> {
            EntityManager entityManager = C0081b.m309c();
            Long values = (Long) entityManager.createQuery("select count(game) from Game game where game.system = :system", Long.class).setParameter("system", param.getValue()).getSingleResult();
            entityManager.close();
            return new ReadOnlyObjectWrapper(values);
        });
        this.emulatorsReferencesTableColumn.setCellValueFactory(param2 -> {
            EntityManager entityManager = C0081b.m309c();
            Long values = (Long) entityManager.createQuery("select count(emulator_profile) from EmulatorProfile emulator_profile where :system member of emulator_profile.systems", Long.class).setParameter("system", param2.getValue()).getSingleResult();
            entityManager.close();
            return new ReadOnlyObjectWrapper(values);
        });
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1450a(C0165ce event) {
        m1453a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1451a(C0170cj event) {
        m1453a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1452a(C0169ci event) {
        m1453a();
    }

    /* JADX INFO: renamed from: a */
    private void m1453a() {
        TypedQuery<System> query;
        EntityManager entityManager = C0081b.m309c();
        if (this.nameSearchField.getText() == null || this.nameSearchField.getText().isEmpty()) {
            query = entityManager.createNamedQuery(System.f508b, System.class);
        } else {
            query = entityManager.createQuery("select system from System system where upper(system.name) like concat('%',:name,'%')", System.class).setParameter("name", this.nameSearchField.getText().toUpperCase());
        }
        this.tableView.getItems().setAll(query.getResultList());
        long max = ((Long) entityManager.createNamedQuery(System.f507a, Long.class).getSingleResult()).longValue();
        entityManager.close();
        this.searchResultLabel.setText(MessageFormat.format(this.resources.getString("database.systems.search.result"), Integer.valueOf(this.tableView.getItems().size()), Long.valueOf(max)));
    }

    @FXML
    private void add() {
        C0048aU dialog = new C0048aU();
        Optional<System> optional = dialog.showAndWait();
        optional.ifPresent(system -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.persist(system);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
            RomStationController.f786a.post(new C0169ci());
        });
    }

    @FXML
    private void delete() {
        ApplicationAlert alert = new ApplicationAlert(this.resources.getString("system.delete.confirmation.alert.header"), this.resources.getString("system.delete.confirmation.alert.content"), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            this.tableView.getSelectionModel().getSelectedItems().forEach(system -> {
                System entity = (System) entityManager.merge(system);
                entityManager.remove(entity);
            });
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
            RomStationController.f786a.post(new C0169ci());
        }
    }

    @FXML
    private void edit() {
        C0048aU dialog = new C0048aU((System) this.tableView.getSelectionModel().getSelectedItem());
        Optional<System> optional = dialog.showAndWait();
        optional.ifPresent(system -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            System entity = (System) entityManager.merge(system);
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
            RomStationController.f786a.post(new C0169ci());
        });
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (!this.tableView.getSelectionModel().isEmpty() && keyEvent.getCode() == KeyCode.DELETE) {
            delete();
        }
    }
}
