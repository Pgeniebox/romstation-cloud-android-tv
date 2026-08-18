package org.romstation.application.view.controller.emulators;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import org.romstation.application.AbstractC0224p;
import org.romstation.application.C0081b;
import org.romstation.application.C0169ci;
import org.romstation.application.C0177cq;
import org.romstation.application.C0187d;
import org.romstation.application.C0202e;
import org.romstation.application.C0203f;
import org.romstation.application.C0204g;
import org.romstation.application.C0205h;
import org.romstation.application.C0208j;
import org.romstation.application.C0209k;
import org.romstation.application.C0226r;
import org.romstation.application.C0229t;
import org.romstation.application.C0281x;
import org.romstation.application.EnumC0206i;
import org.romstation.application.EnumC0225q;
import org.romstation.application.EnumC0227s;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.System;
import org.romstation.application.view.control.cell.query.NamedFilterListCell;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/emulators/EmulatorsFiltersController.class */
public class EmulatorsFiltersController {

    /* JADX INFO: renamed from: a */
    private final C0281x f816a;

    /* JADX INFO: renamed from: b */
    private final NamedFilterListCell.Factory f817b;

    @FXML
    private ListView<C0187d> systemsListView;

    @FXML
    private ListView<C0187d> activeFiltersListView;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private Button deleteFiltersButton;

    public EmulatorsFiltersController() {
        RomStationController.f786a.register(this);
        this.f816a = new C0281x(null, new C0205h(new C0209k(C0209k.f554a, "name"), EnumC0206i.UPPER), EnumC0225q.MATCH, new C0204g(String.class));
        this.f817b = new NamedFilterListCell.Factory(Emulator.class);
    }

    @FXML
    private void initialize() {
        this.systemsListView.setCellFactory(this.f817b);
        this.activeFiltersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.activeFiltersListView.setItems(this.f817b.m1259b());
        this.activeFiltersListView.getItems().addListener(c -> {
            this.systemsListView.refresh();
            m1494a();
        });
        this.resetFiltersButton.disableProperty().bind(Bindings.size(this.activeFiltersListView.getItems()).isEqualTo(0));
        this.deleteFiltersButton.disableProperty().bind(Bindings.size(this.activeFiltersListView.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    /* JADX INFO: renamed from: a */
    private Optional<C0226r> m1493a(List<C0187d> namedFilters) {
        Stream<C0187d> stream = namedFilters.stream();
        ObservableList items = this.activeFiltersListView.getItems();
        items.getClass();
        List<AbstractC0224p> items2 = (List) stream.filter((v1) -> {
            return r1.contains(v1);
        }).map((v0) -> {
            return v0.m758g();
        }).collect(Collectors.toList());
        if (items2.isEmpty()) {
            return Optional.empty();
        }
        C0226r groupFilter = new C0226r(EnumC0227s.OR);
        groupFilter.m984c().setAll(items2);
        return Optional.of(groupFilter);
    }

    /* JADX INFO: renamed from: a */
    public void m1494a() {
        C0226r root = new C0226r(EnumC0227s.AND);
        root.m984c().add(this.f816a);
        m1493a((List<C0187d>) this.systemsListView.getItems()).ifPresent(groupFilter -> {
            root.m984c().add(groupFilter);
        });
        EntityManager entityManager = C0081b.m309c();
        C0202e<Emulator> queryBuilder = new C0202e<>(Emulator.class);
        queryBuilder.m814a(root);
        C0203f c0203f = new C0203f(queryBuilder, entityManager, Emulator.class);
        C0205h queryExpression = new C0205h(new C0209k(C0209k.f554a), EnumC0206i.GET);
        ObservableList<Emulator> emulators = FXCollections.observableList(queryBuilder.m816a(queryExpression, c0203f, true).getResultList());
        entityManager.close();
        RomStationController.f786a.post(new C0177cq(emulators));
    }

    /* JADX INFO: renamed from: a */
    public void m1495a(String name) {
        this.f816a.m845c().m821a(name);
        m1494a();
    }

    @FXML
    public void resetFilters(ActionEvent actionEvent) {
        this.activeFiltersListView.getItems().clear();
    }

    @FXML
    private void deleteFilters(ActionEvent actionEvent) {
        ObservableList<C0187d> selectedItems = this.activeFiltersListView.getSelectionModel().getSelectedItems();
        this.activeFiltersListView.getItems().removeAll(new ArrayList((Collection) selectedItems));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1496a(C0169ci event) {
        this.f817b.m1258a();
        m1497b();
        m1494a();
    }

    /* JADX INFO: renamed from: b */
    public void m1497b() {
        this.systemsListView.getItems().setAll(m1498c());
    }

    /* JADX INFO: renamed from: c */
    private List<C0187d> m1498c() {
        EntityManager entityManager = C0081b.m309c();
        List<System> entities = entityManager.createQuery("select distinct system from Emulator emulator join emulator.files file join file.profiles profile join profile.systems system order by system.name asc", System.class).getResultList();
        entityManager.close();
        return (List) entities.stream().map(system -> {
            C0229t<System> filter = new C0229t<>(null, new C0205h(new C0209k("profile", "systems"), EnumC0206i.GET), EnumC0225q.IS_MEMBER, new C0204g(system, System.class));
            filter.m1664l().add(new C0208j("file", new C0209k(C0209k.f554a, "files"), JoinType.LEFT));
            filter.m1664l().add(new C0208j("profile", new C0209k("file", "profiles"), JoinType.LEFT));
            return new C0187d(System.class + ":" + system.getId(), system.getName(), system.getGraphic(), filter);
        }).collect(Collectors.toList());
    }
}
