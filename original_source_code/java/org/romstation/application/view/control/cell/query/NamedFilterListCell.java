package org.romstation.application.view.control.cell.query;

import java.util.HashMap;
import java.util.LinkedList;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import org.romstation.application.C0081b;
import org.romstation.application.C0187d;
import org.romstation.application.C0202e;
import org.romstation.application.C0203f;
import org.romstation.application.C0205h;
import org.romstation.application.C0209k;
import org.romstation.application.EnumC0206i;
import org.romstation.application.database.entity.AbstractC0189a;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/query/NamedFilterListCell.class */
public class NamedFilterListCell extends ListCell<C0187d> {

    /* JADX INFO: renamed from: a */
    private final Class<? extends AbstractC0189a> f765a;

    /* JADX INFO: renamed from: b */
    private final HashMap<C0187d, Long> f766b;

    /* JADX INFO: renamed from: c */
    private final ObservableList<C0187d> f767c;

    /* JADX INFO: renamed from: d */
    private final ChangeListener<Boolean> f768d;

    /* JADX INFO: renamed from: e */
    private final HBox f769e;

    /* JADX INFO: renamed from: f */
    private final CheckBox f770f;

    /* JADX INFO: renamed from: g */
    private final Label f771g;

    public NamedFilterListCell(Class<? extends AbstractC0189a> queryClass, HashMap<C0187d, Long> queryCountCache, ObservableList<C0187d> activeFilters) {
        this.f765a = queryClass;
        this.f766b = queryCountCache;
        this.f767c = activeFilters;
        getStyleClass().add("query");
        this.f770f = new CheckBox();
        this.f768d = (observableValue, previous, selected) -> {
            if (selected.booleanValue()) {
                activeFilters.add(getItem());
            } else {
                activeFilters.remove(getItem());
            }
        };
        this.f771g = new Label();
        this.f771g.getStyleClass().add("rows");
        this.f769e = new HBox(new Node[]{this.f770f, this.f771g});
        this.f769e.getStyleClass().add("container");
        HBox.setHgrow(this.f770f, Priority.ALWAYS);
    }

    /* JADX INFO: renamed from: a */
    private long m1254a() {
        return this.f766b.computeIfAbsent((C0187d) getItem(), namedFilter -> {
            C0202e<? extends AbstractC0189a> queryBuilder = new C0202e<>(this.f765a);
            queryBuilder.m814a(namedFilter.m758g());
            EntityManager entityManager = C0081b.m309c();
            C0203f c0203f = new C0203f(queryBuilder, entityManager, Long.class);
            C0205h queryExpression = new C0205h(new C0209k(C0209k.f554a), EnumC0206i.COUNT_DISTINCT);
            long count = ((Long) queryBuilder.m815a(queryExpression, c0203f).getSingleResult()).longValue();
            entityManager.close();
            return Long.valueOf(count);
        }).longValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(C0187d namedFilter, boolean empty) {
        super.updateItem(namedFilter, empty);
        if (empty) {
            setGraphic(null);
            return;
        }
        this.f770f.selectedProperty().removeListener(this.f768d);
        this.f770f.setSelected(this.f767c.contains(namedFilter));
        this.f770f.selectedProperty().addListener(this.f768d);
        if (namedFilter.m755e() == null) {
            this.f770f.setGraphic((Node) null);
        } else {
            this.f770f.setGraphic(new ImageView(namedFilter.m755e().getURI().toString()));
        }
        this.f770f.setText(namedFilter.m752c());
        this.f771g.setText(String.valueOf(m1254a()));
        setGraphic(this.f769e);
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/query/NamedFilterListCell$Factory.class */
    public static class Factory implements Callback<ListView<C0187d>, ListCell<C0187d>> {

        /* JADX INFO: renamed from: a */
        private final Class<? extends AbstractC0189a> f772a;

        /* JADX INFO: renamed from: b */
        private final HashMap<C0187d, Long> f773b = new HashMap<>();

        /* JADX INFO: renamed from: c */
        private final ObservableList<C0187d> f774c = FXCollections.observableList(new LinkedList());

        public Factory(Class<? extends AbstractC0189a> queryClass) {
            this.f772a = queryClass;
        }

        /* JADX INFO: renamed from: a */
        public void m1258a() {
            this.f773b.clear();
        }

        /* JADX INFO: renamed from: b */
        public ObservableList<C0187d> m1259b() {
            return this.f774c;
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<C0187d> call(ListView<C0187d> param) {
            return new NamedFilterListCell(this.f772a, this.f773b, this.f774c);
        }
    }
}
