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
import org.romstation.application.C0040aM;
import org.romstation.application.C0081b;
import org.romstation.application.C0165ce;
import org.romstation.application.C0170cj;
import org.romstation.application.database.entity.Developer;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.SearchField;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/database/DatabaseDevelopersViewController.class */
public class DatabaseDevelopersViewController {

    /* JADX INFO: renamed from: a */
    private Timeline f807a;

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
    private TableView<Developer> tableView;

    @FXML
    private TableColumn<Developer, Integer> idTableColumn;

    @FXML
    private TableColumn<Developer, String> nameTableColumn;

    @FXML
    private TableColumn<Developer, Long> libraryReferencesTableColumn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.nameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.f807a.playFromStart();
        });
        this.f807a = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(250.0d), event -> {
            m1407a();
        }, new KeyValue[0])});
        this.libraryReferencesTableColumn.setCellValueFactory(param -> {
            EntityManager entityManager = C0081b.m309c();
            Long values = (Long) entityManager.createQuery("select count(game) from Game game where game.developer = :developer", Long.class).setParameter("developer", param.getValue()).getSingleResult();
            entityManager.close();
            return new ReadOnlyObjectWrapper(values);
        });
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1405a(C0165ce event) {
        m1407a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1406a(C0170cj event) {
        m1407a();
    }

    /* JADX INFO: renamed from: a */
    private void m1407a() {
        TypedQuery<Developer> query;
        EntityManager entityManager = C0081b.m309c();
        if (this.nameSearchField.getText() == null || this.nameSearchField.getText().isEmpty()) {
            query = entityManager.createNamedQuery(Developer.f409b, Developer.class);
        } else {
            query = entityManager.createQuery("select developer from Developer developer where upper(developer.name) like concat('%',:name,'%')", Developer.class).setParameter("name", this.nameSearchField.getText().toUpperCase());
        }
        this.tableView.getItems().setAll(query.getResultList());
        long max = ((Long) entityManager.createNamedQuery(Developer.f408a, Long.class).getSingleResult()).longValue();
        entityManager.close();
        this.searchResultLabel.setText(MessageFormat.format(this.resources.getString("database.developers.search.result"), Integer.valueOf(this.tableView.getItems().size()), Long.valueOf(max)));
    }

    @FXML
    private void add() {
        C0040aM dialog = new C0040aM(new Developer());
        Optional<Developer> optional = dialog.showAndWait();
        optional.ifPresent(developer -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.persist(developer);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        });
    }

    @FXML
    private void delete() {
        ApplicationAlert alert = new ApplicationAlert(this.resources.getString("developer.delete.confirmation.alert.header"), this.resources.getString("developer.delete.confirmation.alert.content"), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            this.tableView.getSelectionModel().getSelectedItems().forEach(developer -> {
                Developer entity = (Developer) entityManager.merge(developer);
                entityManager.remove(entity);
            });
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        }
    }

    @FXML
    private void edit() {
        C0040aM dialog = new C0040aM((Developer) this.tableView.getSelectionModel().getSelectedItem());
        Optional<Developer> optional = dialog.showAndWait();
        optional.ifPresent(developer -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            Developer entity = (Developer) entityManager.merge(developer);
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        });
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (!this.tableView.getSelectionModel().isEmpty() && keyEvent.getCode() == KeyCode.DELETE) {
            delete();
        }
    }
}
