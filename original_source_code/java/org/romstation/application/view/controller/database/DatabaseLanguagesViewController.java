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
import org.romstation.application.C0042aO;
import org.romstation.application.C0081b;
import org.romstation.application.C0165ce;
import org.romstation.application.C0170cj;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Language;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.SearchField;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/database/DatabaseLanguagesViewController.class */
public class DatabaseLanguagesViewController {

    /* JADX INFO: renamed from: a */
    private Timeline f809a;

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
    private TableView<Language> tableView;

    @FXML
    private TableColumn<Language, Integer> idTableColumn;

    @FXML
    private TableColumn<Language, I18n> nameTableColumn;

    @FXML
    private TableColumn<Language, Long> libraryReferencesTableColumn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.nameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.f809a.playFromStart();
        });
        this.f809a = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(250.0d), event -> {
            m1425a();
        }, new KeyValue[0])});
        this.libraryReferencesTableColumn.setCellValueFactory(param -> {
            EntityManager entityManager = C0081b.m309c();
            Long values = (Long) entityManager.createQuery("select count(game) from Game game where :language member of game.languages", Long.class).setParameter("language", param.getValue()).getSingleResult();
            entityManager.close();
            return new ReadOnlyObjectWrapper(values);
        });
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1423a(C0165ce event) {
        m1425a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1424a(C0170cj event) {
        m1425a();
    }

    /* JADX INFO: renamed from: a */
    private void m1425a() {
        TypedQuery<Language> query;
        EntityManager entityManager = C0081b.m309c();
        if (this.nameSearchField.getText() == null || this.nameSearchField.getText().isEmpty()) {
            query = entityManager.createNamedQuery(Language.f485b, Language.class);
        } else {
            query = entityManager.createQuery("select distinct language from Language language join language.name.translations translation where upper(translation.string) like concat('%',:name,'%')", Language.class).setParameter("name", this.nameSearchField.getText().toUpperCase());
        }
        this.tableView.getItems().setAll(query.getResultList());
        long max = ((Long) entityManager.createNamedQuery(Language.f484a, Long.class).getSingleResult()).longValue();
        entityManager.close();
        this.searchResultLabel.setText(MessageFormat.format(this.resources.getString("database.languages.search.result"), Integer.valueOf(this.tableView.getItems().size()), Long.valueOf(max)));
    }

    @FXML
    private void add() {
        C0042aO dialog = new C0042aO(new Language());
        Optional<Language> optional = dialog.showAndWait();
        optional.ifPresent(language -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.persist(language);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        });
    }

    @FXML
    private void delete() {
        ApplicationAlert alert = new ApplicationAlert(this.resources.getString("language.delete.confirmation.alert.header"), this.resources.getString("language.delete.confirmation.alert.content"), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            this.tableView.getSelectionModel().getSelectedItems().forEach(language -> {
                Language entity = (Language) entityManager.merge(language);
                entityManager.remove(entity);
            });
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        }
    }

    @FXML
    private void edit() {
        C0042aO dialog = new C0042aO((Language) this.tableView.getSelectionModel().getSelectedItem());
        Optional<Language> optional = dialog.showAndWait();
        optional.ifPresent(language -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            Language entity = (Language) entityManager.merge(language);
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
