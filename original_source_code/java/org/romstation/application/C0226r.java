package org.romstation.application;

import java.util.LinkedList;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.r */
/* JADX INFO: compiled from: GroupFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/r.class */
public class C0226r extends AbstractC0224p {

    /* JADX INFO: renamed from: a */
    private final ObjectProperty<EnumC0227s> f594a;

    /* JADX INFO: renamed from: b */
    private final ObservableList<AbstractC0224p> f595b = FXCollections.observableList(new LinkedList());

    public C0226r(EnumC0227s operator) {
        this.f594a = new SimpleObjectProperty(operator);
    }

    /* JADX INFO: renamed from: a */
    public ObjectProperty<EnumC0227s> m981a() {
        return this.f594a;
    }

    /* JADX INFO: renamed from: b */
    public EnumC0227s m982b() {
        return (EnumC0227s) this.f594a.get();
    }

    /* JADX INFO: renamed from: a */
    public void m983a(EnumC0227s operator) {
        this.f594a.set(operator);
    }

    /* JADX INFO: renamed from: c */
    public ObservableList<AbstractC0224p> m984c() {
        return this.f595b;
    }

    @Override // org.romstation.application.AbstractC0224p
    /* JADX INFO: renamed from: a */
    public Predicate mo843a(AbstractC0134c context) {
        Predicate[] predicates = (Predicate[]) this.f595b.stream().filter((v0) -> {
            return v0.m979e();
        }).map(filter -> {
            return filter.mo843a(context);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).toArray(x$0 -> {
            return new Predicate[x$0];
        });
        if (predicates.length != 0) {
            switch ((EnumC0227s) this.f594a.get()) {
                case AND:
                    return context.m640b().and(predicates);
                case OR:
                    return context.m640b().or(predicates);
                default:
                    return null;
            }
        }
        return null;
    }

    public String toString() {
        return ((EnumC0227s) this.f594a.get()).toString();
    }
}
