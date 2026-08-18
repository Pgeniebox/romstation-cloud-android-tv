package org.romstation.application;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.w */
/* JADX INFO: compiled from: QueryFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/w.class */
public abstract class AbstractC0280w extends AbstractC0224p {

    /* JADX INFO: renamed from: a */
    private final StringProperty f874a;

    /* JADX INFO: renamed from: b */
    private final ObjectProperty<C0205h> f875b;

    /* JADX INFO: renamed from: c */
    private final ObjectProperty<EnumC0225q> f876c;

    /* JADX INFO: renamed from: d */
    private final List<C0208j> f877d;

    /* JADX INFO: renamed from: a */
    public abstract EnumC0225q[] mo842a();

    public AbstractC0280w() {
        this(null, null, null);
    }

    AbstractC0280w(String name, C0205h expression, EnumC0225q operator) {
        this.f874a = new SimpleStringProperty(name);
        this.f875b = new SimpleObjectProperty(expression);
        this.f876c = new SimpleObjectProperty(operator);
        this.f877d = new LinkedList();
    }

    /* JADX INFO: renamed from: f */
    public StringProperty m1655f() {
        return this.f874a;
    }

    /* JADX INFO: renamed from: g */
    public String m1656g() {
        return (String) this.f874a.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1657a(String name) {
        this.f874a.set(name);
    }

    /* JADX INFO: renamed from: h */
    public ObjectProperty<C0205h> m1658h() {
        return this.f875b;
    }

    /* JADX INFO: renamed from: i */
    public C0205h m1659i() {
        return (C0205h) this.f875b.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1660a(C0205h expression) {
        this.f875b.set(expression);
    }

    /* JADX INFO: renamed from: j */
    public ObjectProperty<EnumC0225q> m1661j() {
        return this.f876c;
    }

    /* JADX INFO: renamed from: k */
    public EnumC0225q m1662k() {
        return (EnumC0225q) this.f876c.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1663a(EnumC0225q operator) {
        this.f876c.set(operator);
    }

    /* JADX INFO: renamed from: l */
    public List<C0208j> m1664l() {
        return this.f877d;
    }

    @Override // org.romstation.application.AbstractC0224p
    /* JADX INFO: renamed from: a */
    public Predicate mo843a(AbstractC0134c context) {
        Map<String, Path<?>> paths = context.m642d();
        for (C0208j join : this.f877d) {
            paths.computeIfAbsent(join.m837a(), alias -> {
                C0209k queryPath = join.m838b();
                From<?, ?> parent = (From) paths.get(queryPath.m840a());
                return parent.join(queryPath.m841b(), join.m839c());
            });
        }
        C0209k queryPath = m1659i().m823a();
        paths.computeIfAbsent(queryPath.toString(), path -> {
            Path<?> parent = (Path) paths.get(queryPath.m840a());
            return parent.get(queryPath.m841b());
        });
        return null;
    }

    public String toString() {
        return (String) this.f874a.get();
    }
}
