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
import org.romstation.application.C0049aV;
import org.romstation.application.C0081b;
import org.romstation.application.C0165ce;
import org.romstation.application.C0170cj;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Tag;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.SearchField;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/database/DatabaseTagsViewController.class */
public class DatabaseTagsViewController {

    /* JADX INFO: renamed from: a */
    private Timeline f813a;

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
    private TableView<Tag> tableView;

    @FXML
    private TableColumn<Tag, Integer> idTableColumn;

    @FXML
    private TableColumn<Tag, I18n> nameTableColumn;

    @FXML
    private TableColumn<Tag, Long> libraryReferencesTableColumn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.nameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.f813a.playFromStart();
        });
        this.f813a = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(250.0d), event -> {
            m1463a();
        }, new KeyValue[0])});
        this.libraryReferencesTableColumn.setCellValueFactory(param -> {
            EntityManager entityManager = C0081b.m309c();
            Long values = (Long) entityManager.createQuery("select count(game) from Game game where :tag member of game.tags", Long.class).setParameter("tag", param.getValue()).getSingleResult();
            entityManager.close();
            return new ReadOnlyObjectWrapper(values);
        });
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1461a(C0165ce event) {
        m1463a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1462a(C0170cj event) {
        m1463a();
    }

    /* JADX INFO: renamed from: a */
    private void m1463a() {
        TypedQuery<Tag> query;
        EntityManager entityManager = C0081b.m309c();
        if (this.nameSearchField.getText() == null || this.nameSearchField.getText().isEmpty()) {
            query = entityManager.createNamedQuery(Tag.f513b, Tag.class);
        } else {
            query = entityManager.createQuery("select distinct tag from Tag tag join tag.name.translations translation where upper(translation.string) like concat('%',:name,'%')", Tag.class).setParameter("name", this.nameSearchField.getText().toUpperCase());
        }
        this.tableView.getItems().setAll(query.getResultList());
        long max = ((Long) entityManager.createNamedQuery(Tag.f512a, Long.class).getSingleResult()).longValue();
        entityManager.close();
        this.searchResultLabel.setText(MessageFormat.format(this.resources.getString("database.tags.search.result"), Integer.valueOf(this.tableView.getItems().size()), Long.valueOf(max)));
    }

    @FXML
    private void add() {
        C0049aV dialog = new C0049aV(new Tag());
        Optional<Tag> optional = dialog.showAndWait();
        optional.ifPresent(tag -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.persist(tag);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        });
    }

    @FXML
    private void delete() {
        ApplicationAlert alert = new ApplicationAlert(this.resources.getString("tag.delete.confirmation.alert.header"), this.resources.getString("tag.delete.confirmation.alert.content"), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            this.tableView.getSelectionModel().getSelectedItems().forEach(tag -> {
                Tag entity = (Tag) entityManager.merge(tag);
                entityManager.remove(entity);
            });
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        }
    }

    @FXML
    private void edit() {
        C0049aV dialog = new C0049aV((Tag) this.tableView.getSelectionModel().getSelectedItem());
        Optional<Tag> optional = dialog.showAndWait();
        optional.ifPresent(tag -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            Tag entity = (Tag) entityManager.merge(tag);
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
