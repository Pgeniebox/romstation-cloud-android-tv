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
import org.romstation.application.C0047aT;
import org.romstation.application.C0081b;
import org.romstation.application.C0165ce;
import org.romstation.application.C0170cj;
import org.romstation.application.database.entity.Series;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.SearchField;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/database/DatabaseSeriesViewController.class */
public class DatabaseSeriesViewController {

    /* JADX INFO: renamed from: a */
    private Timeline f811a;

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
    private TableView<Series> tableView;

    @FXML
    private TableColumn<Series, Integer> idTableColumn;

    @FXML
    private TableColumn<Series, String> nameTableColumn;

    @FXML
    private TableColumn<Series, Long> libraryReferencesTableColumn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.nameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.f811a.playFromStart();
        });
        this.f811a = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(250.0d), event -> {
            m1443a();
        }, new KeyValue[0])});
        this.libraryReferencesTableColumn.setCellValueFactory(param -> {
            EntityManager entityManager = C0081b.m309c();
            Long values = (Long) entityManager.createQuery("select count(game) from Game game where :series member of game.series", Long.class).setParameter("series", param.getValue()).getSingleResult();
            entityManager.close();
            return new ReadOnlyObjectWrapper(values);
        });
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.editButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
        this.deleteButton.disableProperty().bind(Bindings.size(this.tableView.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1441a(C0165ce event) {
        m1443a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1442a(C0170cj event) {
        m1443a();
    }

    /* JADX INFO: renamed from: a */
    private void m1443a() {
        TypedQuery<Series> query;
        EntityManager entityManager = C0081b.m309c();
        if (this.nameSearchField.getText() == null || this.nameSearchField.getText().isEmpty()) {
            query = entityManager.createNamedQuery(Series.f504b, Series.class);
        } else {
            query = entityManager.createQuery("select series from Series series where upper(series.name) like concat('%',:name,'%')", Series.class).setParameter("name", this.nameSearchField.getText().toUpperCase());
        }
        this.tableView.getItems().setAll(query.getResultList());
        long max = ((Long) entityManager.createNamedQuery(Series.f503a, Long.class).getSingleResult()).longValue();
        entityManager.close();
        this.searchResultLabel.setText(MessageFormat.format(this.resources.getString("database.series.search.result"), Integer.valueOf(this.tableView.getItems().size()), Long.valueOf(max)));
    }

    @FXML
    private void add() {
        C0047aT dialog = new C0047aT(new Series());
        Optional<Series> optional = dialog.showAndWait();
        optional.ifPresent(series -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.persist(series);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        });
    }

    @FXML
    private void delete() {
        ApplicationAlert alert = new ApplicationAlert(this.resources.getString("series.delete.confirmation.alert.header"), this.resources.getString("series.delete.confirmation.alert.content"), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            this.tableView.getSelectionModel().getSelectedItems().forEach(series -> {
                Series entity = (Series) entityManager.merge(series);
                entityManager.remove(entity);
            });
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0170cj());
        }
    }

    @FXML
    private void edit() {
        C0047aT dialog = new C0047aT((Series) this.tableView.getSelectionModel().getSelectedItem());
        Optional<Series> optional = dialog.showAndWait();
        optional.ifPresent(series -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            Series entity = (Series) entityManager.merge(series);
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
