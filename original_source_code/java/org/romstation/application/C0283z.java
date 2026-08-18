package org.romstation.application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

/* JADX INFO: renamed from: org.romstation.application.z */
/* JADX INFO: compiled from: OrderQuery.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/z.class */
public class C0283z {

    /* JADX INFO: renamed from: a */
    private final BooleanProperty f879a = new SimpleBooleanProperty(true);

    /* JADX INFO: renamed from: b */
    private final StringProperty f880b;

    /* JADX INFO: renamed from: c */
    private final ObjectProperty<C0205h> f881c;

    /* JADX INFO: renamed from: d */
    private final ObjectProperty<EnumC0282y> f882d;

    public C0283z(String name, C0205h expression, EnumC0282y operator) {
        this.f880b = new SimpleStringProperty(name);
        this.f881c = new SimpleObjectProperty(expression);
        this.f882d = new SimpleObjectProperty(operator);
    }

    /* JADX INFO: renamed from: a */
    public BooleanProperty m1668a() {
        return this.f879a;
    }

    /* JADX INFO: renamed from: b */
    public boolean m1669b() {
        return this.f879a.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1670a(boolean enable) {
        this.f879a.set(enable);
    }

    /* JADX INFO: renamed from: c */
    public StringProperty m1671c() {
        return this.f880b;
    }

    /* JADX INFO: renamed from: d */
    public String m1672d() {
        return (String) this.f880b.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1673a(String name) {
        this.f880b.set(name);
    }

    /* JADX INFO: renamed from: e */
    public ObjectProperty<C0205h> m1674e() {
        return this.f881c;
    }

    /* JADX INFO: renamed from: f */
    public C0205h m1675f() {
        return (C0205h) this.f881c.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1676a(C0205h expression) {
        this.f881c.set(expression);
    }

    /* JADX INFO: renamed from: g */
    public ObjectProperty<EnumC0282y> m1677g() {
        return this.f882d;
    }

    /* JADX INFO: renamed from: h */
    public EnumC0282y m1678h() {
        return (EnumC0282y) this.f882d.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1679a(EnumC0282y operator) {
        this.f882d.set(operator);
    }

    /* JADX INFO: renamed from: a */
    public Order m1680a(AbstractC0134c context) {
        Expression expression = m1675f().m827a(context);
        switch (m1678h()) {
            case ASC:
                return context.m640b().asc(expression);
            case DESC:
                return context.m640b().desc(expression);
            default:
                return null;
        }
    }
}
