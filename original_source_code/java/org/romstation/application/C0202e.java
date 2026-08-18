package org.romstation.application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.e */
/* JADX INFO: compiled from: QueryBuilder.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/e.class */
public class C0202e<T> {

    /* JADX INFO: renamed from: a */
    private final Class<T> f541a;

    /* JADX INFO: renamed from: b */
    private final ObjectProperty<AbstractC0224p> f542b = new SimpleObjectProperty();

    public C0202e(Class<T> type) {
        this.f541a = type;
    }

    /* JADX INFO: renamed from: a */
    public Class<T> m811a() {
        return this.f541a;
    }

    /* JADX INFO: renamed from: b */
    public AbstractC0224p m812b() {
        return (AbstractC0224p) this.f542b.get();
    }

    /* JADX INFO: renamed from: c */
    public ObjectProperty<AbstractC0224p> m813c() {
        return this.f542b;
    }

    /* JADX INFO: renamed from: a */
    public void m814a(AbstractC0224p filter) {
        this.f542b.set(filter);
    }

    /* JADX INFO: renamed from: a */
    public <R> TypedQuery<R> m815a(C0205h expression, C0203f<R> context) {
        return m816a(expression, context, false);
    }

    /* JADX INFO: renamed from: a */
    public <R> TypedQuery<R> m816a(C0205h expression, C0203f<R> context, boolean distinct) {
        Predicate predicate;
        CriteriaQuery<R> criteriaQuery = context.m818f();
        criteriaQuery.select(expression.m827a(context));
        if (m812b() != null && m812b().m979e() && (predicate = m812b().mo843a(context)) != null) {
            criteriaQuery.where(predicate);
        }
        criteriaQuery.distinct(distinct);
        return context.m639a().createQuery(criteriaQuery);
    }
}
