package org.romstation.application.view.controller.library.filters;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javax.persistence.EntityManager;
import org.romstation.application.AbstractC0224p;
import org.romstation.application.C0081b;
import org.romstation.application.C0139cE;
import org.romstation.application.C0166cf;
import org.romstation.application.C0170cj;
import org.romstation.application.C0187d;
import org.romstation.application.C0202e;
import org.romstation.application.C0203f;
import org.romstation.application.C0204g;
import org.romstation.application.C0205h;
import org.romstation.application.C0209k;
import org.romstation.application.C0223o;
import org.romstation.application.C0226r;
import org.romstation.application.C0229t;
import org.romstation.application.C0281x;
import org.romstation.application.EnumC0206i;
import org.romstation.application.EnumC0225q;
import org.romstation.application.EnumC0227s;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Developer;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.Genre;
import org.romstation.application.database.entity.Language;
import org.romstation.application.database.entity.Publisher;
import org.romstation.application.database.entity.Series;
import org.romstation.application.database.entity.System;
import org.romstation.application.database.entity.Tag;
import org.romstation.application.view.control.cell.query.NamedFilterListCell;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/filters/LibraryFiltersController.class */
public class LibraryFiltersController {

    /* JADX INFO: renamed from: a */
    private final C0281x f824a;

    /* JADX INFO: renamed from: b */
    private final NamedFilterListCell.Factory f825b;

    @FXML
    private Accordion accordion;

    @FXML
    private ListView<C0187d> systemsListView;

    @FXML
    private ListView<C0187d> genresListView;

    @FXML
    private ListView<C0187d> languagesListView;

    @FXML
    private ListView<C0187d> developersListView;

    @FXML
    private ListView<C0187d> publishersListView;

    @FXML
    private ListView<C0187d> seriesListView;

    @FXML
    private ListView<C0187d> tagsListView;

    @FXML
    private ListView<C0187d> activeFiltersListView;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private Button deleteFiltersButton;

    public LibraryFiltersController() {
        RomStationController.f786a.register(this);
        this.f824a = new C0281x(null, new C0205h(new C0209k(C0209k.f554a, "title"), EnumC0206i.UPPER), EnumC0225q.MATCH, new C0204g(String.class));
        this.f825b = new NamedFilterListCell.Factory(Game.class);
    }

    @FXML
    private void initialize() {
        this.systemsListView.setCellFactory(this.f825b);
        this.genresListView.setCellFactory(this.f825b);
        this.languagesListView.setCellFactory(this.f825b);
        this.developersListView.setCellFactory(this.f825b);
        this.publishersListView.setCellFactory(this.f825b);
        this.seriesListView.setCellFactory(this.f825b);
        this.tagsListView.setCellFactory(this.f825b);
        this.activeFiltersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.activeFiltersListView.setItems(this.f825b.m1259b());
        this.activeFiltersListView.getItems().addListener(c -> {
            if (this.accordion.getExpandedPane() != null) {
                this.accordion.getExpandedPane().getContent().refresh();
            }
            m1547a();
        });
        this.resetFiltersButton.disableProperty().bind(Bindings.size(this.activeFiltersListView.getItems()).isEqualTo(0));
        this.deleteFiltersButton.disableProperty().bind(Bindings.size(this.activeFiltersListView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.accordion.getPanes().stream().filter(pane -> {
            try {
                return pane.getUserData() == LibraryFilterPane.valueOf(RomStation.m43c().getProperty("library.filters.expandedPane"));
            } catch (IllegalArgumentException e) {
                return false;
            }
        }).findFirst().ifPresent(pane2 -> {
            this.accordion.setExpandedPane(pane2);
        });
    }

    /* JADX INFO: renamed from: a */
    private Optional<C0226r> m1546a(List<C0187d> namedFilters) {
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
    public void m1547a() {
        C0226r root = new C0226r(EnumC0227s.AND);
        root.m984c().add(this.f824a);
        m1546a((List<C0187d>) this.systemsListView.getItems()).ifPresent(groupFilter -> {
            root.m984c().add(groupFilter);
        });
        m1546a((List<C0187d>) this.genresListView.getItems()).ifPresent(groupFilter2 -> {
            root.m984c().add(groupFilter2);
        });
        m1546a((List<C0187d>) this.languagesListView.getItems()).ifPresent(groupFilter3 -> {
            root.m984c().add(groupFilter3);
        });
        m1546a((List<C0187d>) this.developersListView.getItems()).ifPresent(groupFilter4 -> {
            root.m984c().add(groupFilter4);
        });
        m1546a((List<C0187d>) this.publishersListView.getItems()).ifPresent(groupFilter5 -> {
            root.m984c().add(groupFilter5);
        });
        m1546a((List<C0187d>) this.seriesListView.getItems()).ifPresent(groupFilter6 -> {
            root.m984c().add(groupFilter6);
        });
        m1546a((List<C0187d>) this.tagsListView.getItems()).ifPresent(groupFilter7 -> {
            root.m984c().add(groupFilter7);
        });
        EntityManager entityManager = C0081b.m309c();
        C0202e<Game> queryBuilder = new C0202e<>(Game.class);
        queryBuilder.m814a(root);
        C0203f c0203f = new C0203f(queryBuilder, entityManager, Game.class);
        C0205h queryExpression = new C0205h(new C0209k(C0209k.f554a), EnumC0206i.GET);
        ObservableList<Game> games = FXCollections.observableList(queryBuilder.m816a(queryExpression, c0203f, true).getResultList());
        entityManager.close();
        RomStationController.f786a.post(new C0139cE(games));
    }

    /* JADX INFO: renamed from: a */
    public void m1548a(String title) {
        this.f824a.m845c().m821a(title);
        m1547a();
    }

    @FXML
    private void resetFilters(ActionEvent actionEvent) {
        this.activeFiltersListView.getItems().clear();
    }

    @FXML
    private void deleteFilters(ActionEvent actionEvent) {
        ObservableList<C0187d> selectedItems = this.activeFiltersListView.getSelectionModel().getSelectedItems();
        this.activeFiltersListView.getItems().removeAll(new ArrayList((Collection) selectedItems));
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1549a(C0170cj event) {
        this.f825b.m1258a();
        m1551b();
        m1547a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1550a(C0166cf event) {
        if (this.accordion.getExpandedPane() != null) {
            RomStation.m43c().setProperty("library.filters.expandedPane", ((LibraryFilterPane) this.accordion.getExpandedPane().getUserData()).name());
        } else {
            RomStation.m43c().remove("library.filters.expandedPane");
        }
    }

    /* JADX INFO: renamed from: b */
    public void m1551b() {
        this.systemsListView.getItems().setAll(m1552c());
        this.genresListView.getItems().setAll(m1553d());
        this.languagesListView.getItems().setAll(m1554e());
        this.developersListView.getItems().setAll(m1555f());
        this.publishersListView.getItems().setAll(m1556g());
        this.seriesListView.getItems().setAll(m1557h());
        this.tagsListView.getItems().setAll(m1558i());
    }

    /* JADX INFO: renamed from: c */
    private List<C0187d> m1552c() {
        EntityManager entityManager = C0081b.m309c();
        List<System> entities = entityManager.createQuery("select distinct game.system from Game game order by upper(game.system.name) asc", System.class).getResultList();
        entityManager.close();
        return (List) entities.stream().map(system -> {
            C0223o<System> filter = new C0223o<>(null, new C0205h(new C0209k(C0209k.f554a, "system"), EnumC0206i.GET), EnumC0225q.EQUAL, new C0204g(system, System.class));
            return new C0187d(System.class + ":" + system.getId(), system.getName(), system.getGraphic(), filter);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: d */
    private List<C0187d> m1553d() {
        EntityManager entityManager = C0081b.m309c();
        List<Genre> entities = (List) entityManager.createQuery("select distinct genre from Game game join game.genres genre join genre.name.translations translation where translation.locale.tag = :tag", Genre.class).setParameter("tag", Locale.getDefault().toLanguageTag()).getResultStream().sorted((o1, o2) -> {
            return o1.getName().getDefaultString().compareToIgnoreCase(o2.getName().getDefaultString());
        }).collect(Collectors.toList());
        entityManager.close();
        return (List) entities.stream().map(genre -> {
            C0229t<Genre> filter = new C0229t<>(genre.getName().getDefaultString(), new C0205h(new C0209k(C0209k.f554a, "genres"), EnumC0206i.GET), EnumC0225q.IS_MEMBER, new C0204g(genre, Genre.class));
            return new C0187d(Genre.class + ":" + genre.getId(), genre.getName().getDefaultString(), genre.getGraphic(), filter);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: e */
    private List<C0187d> m1554e() {
        EntityManager entityManager = C0081b.m309c();
        List<Language> entities = (List) entityManager.createQuery("select distinct language from Game game join game.languages language join language.name.translations translation where translation.locale.tag = :tag", Language.class).setParameter("tag", Locale.getDefault().toLanguageTag()).getResultStream().sorted((o1, o2) -> {
            return o1.getName().getDefaultString().compareToIgnoreCase(o2.getName().getDefaultString());
        }).collect(Collectors.toList());
        entityManager.close();
        return (List) entities.stream().map(language -> {
            C0229t<Language> filter = new C0229t<>(language.getName().getDefaultString(), new C0205h(new C0209k(C0209k.f554a, "languages"), EnumC0206i.GET), EnumC0225q.IS_MEMBER, new C0204g(language, Language.class));
            return new C0187d(Language.class + ":" + language.getId(), language.getName().getDefaultString(), language.getGraphic(), filter);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: f */
    private List<C0187d> m1555f() {
        EntityManager entityManager = C0081b.m309c();
        List<Developer> entities = entityManager.createQuery("select distinct game.developer from Game game order by upper(game.developer.name) asc", Developer.class).getResultList();
        entityManager.close();
        return (List) entities.stream().map(developer -> {
            C0223o<Developer> filter = new C0223o<>(developer.getName(), new C0205h(new C0209k(C0209k.f554a, "developer"), EnumC0206i.GET), EnumC0225q.EQUAL, new C0204g(developer, Developer.class));
            return new C0187d(Developer.class + ":" + developer.getId(), developer.getName(), filter);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: g */
    private List<C0187d> m1556g() {
        EntityManager entityManager = C0081b.m309c();
        List<Publisher> entities = entityManager.createQuery("select distinct game.publisher from Game game order by upper(game.publisher.name) asc", Publisher.class).getResultList();
        entityManager.close();
        return (List) entities.stream().map(publisher -> {
            C0223o<Publisher> filter = new C0223o<>(publisher.getName(), new C0205h(new C0209k(C0209k.f554a, "publisher"), EnumC0206i.GET), EnumC0225q.EQUAL, new C0204g(publisher, Publisher.class));
            return new C0187d(Publisher.class + ":" + publisher.getId(), publisher.getName(), filter);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: h */
    private List<C0187d> m1557h() {
        EntityManager entityManager = C0081b.m309c();
        List<Series> entities = entityManager.createQuery("select distinct series from Game game join game.series series order by upper(series.name) asc", Series.class).getResultList();
        entityManager.close();
        return (List) entities.stream().map(series -> {
            C0229t<Series> filter = new C0229t<>(series.getName(), new C0205h(new C0209k(C0209k.f554a, "series"), EnumC0206i.GET), EnumC0225q.IS_MEMBER, new C0204g(series, Series.class));
            return new C0187d(Series.class + ":" + series.getId(), series.getName(), filter);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: i */
    private List<C0187d> m1558i() {
        EntityManager entityManager = C0081b.m309c();
        List<Tag> entities = (List) entityManager.createQuery("select distinct tag from Game game join game.tags tag join tag.name.translations translation where translation.locale.tag = :tag", Tag.class).setParameter("tag", Locale.getDefault().toLanguageTag()).getResultStream().sorted((o1, o2) -> {
            return o1.getName().getDefaultString().compareToIgnoreCase(o2.getName().getDefaultString());
        }).collect(Collectors.toList());
        entityManager.close();
        return (List) entities.stream().map(tag -> {
            C0229t<Tag> filter = new C0229t<>(tag.getName().getDefaultString(), new C0205h(new C0209k(C0209k.f554a, "tags"), EnumC0206i.GET), EnumC0225q.IS_MEMBER, new C0204g(tag, Tag.class));
            return new C0187d(Tag.class + ":" + tag.getId(), tag.getName().getDefaultString(), tag.getGraphic(), filter);
        }).collect(Collectors.toList());
    }
}
