package org.romstation.application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/* JADX INFO: renamed from: org.romstation.application.h */
/* JADX INFO: compiled from: QueryExpression.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/h.class */
public class C0205h {

    /* JADX INFO: renamed from: a */
    private final C0209k f547a;

    /* JADX INFO: renamed from: b */
    private final ObjectProperty<EnumC0206i> f548b;

    public C0205h(C0209k queryPath, EnumC0206i queryFunction) {
        this.f547a = queryPath;
        this.f548b = new SimpleObjectProperty(queryFunction);
    }

    /* JADX INFO: renamed from: a */
    public C0209k m823a() {
        return this.f547a;
    }

    /* JADX INFO: renamed from: b */
    public ObjectProperty<EnumC0206i> m824b() {
        return this.f548b;
    }

    /* JADX INFO: renamed from: c */
    public EnumC0206i m825c() {
        return (EnumC0206i) this.f548b.get();
    }

    /* JADX INFO: renamed from: a */
    public void m826a(EnumC0206i value) {
        this.f548b.set(value);
    }

    /* JADX INFO: renamed from: a */
    public Expression m827a(AbstractC0134c context) {
        Path<?> path = context.m642d().get(m823a().toString());
        switch ((EnumC0206i) this.f548b.get()) {
            case GET:
                return path;
            case LENGTH:
                return context.m640b().length(path);
            case SIZE:
                return context.m640b().size(path);
            case UPPER:
                return context.m640b().upper(path);
            case LOWER:
                return context.m640b().lower(path);
            case COUNT:
                return context.m640b().count(path);
            case COUNT_DISTINCT:
                return context.m640b().countDistinct(path);
            default:
                return null;
        }
    }
}
