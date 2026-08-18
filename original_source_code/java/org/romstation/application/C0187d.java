package org.romstation.application;

import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.romstation.application.database.entity.Image;

/* JADX INFO: renamed from: org.romstation.application.d */
/* JADX INFO: compiled from: NamedFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/d.class */
public class C0187d {

    /* JADX INFO: renamed from: a */
    private final String f401a;

    /* JADX INFO: renamed from: b */
    private final StringProperty f402b;

    /* JADX INFO: renamed from: c */
    private final ObjectProperty<Image> f403c;

    /* JADX INFO: renamed from: d */
    private final ObjectProperty<AbstractC0224p> f404d;

    public C0187d(String id, String name, AbstractC0224p filter) {
        this(id, name, null, filter);
    }

    public C0187d(String id, String name, Image graphic, AbstractC0224p filter) {
        this.f401a = id;
        this.f402b = new SimpleStringProperty(name);
        this.f403c = new SimpleObjectProperty(graphic);
        this.f404d = new SimpleObjectProperty(filter);
    }

    /* JADX INFO: renamed from: a */
    public String m750a() {
        return this.f401a;
    }

    /* JADX INFO: renamed from: b */
    public StringProperty m751b() {
        return this.f402b;
    }

    /* JADX INFO: renamed from: c */
    public String m752c() {
        return (String) this.f402b.get();
    }

    /* JADX INFO: renamed from: a */
    public void m753a(String name) {
        this.f402b.set(name);
    }

    /* JADX INFO: renamed from: d */
    public ObjectProperty<Image> m754d() {
        return this.f403c;
    }

    /* JADX INFO: renamed from: e */
    public Image m755e() {
        return (Image) this.f403c.get();
    }

    /* JADX INFO: renamed from: a */
    public void m756a(Image graphic) {
        this.f403c.set(graphic);
    }

    /* JADX INFO: renamed from: f */
    public ObjectProperty<AbstractC0224p> m757f() {
        return this.f404d;
    }

    /* JADX INFO: renamed from: g */
    public AbstractC0224p m758g() {
        return (AbstractC0224p) this.f404d.get();
    }

    /* JADX INFO: renamed from: a */
    public void m759a(AbstractC0224p filter) {
        this.f404d.set(filter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        C0187d that = (C0187d) other;
        return this.f401a.equals(that.f401a);
    }

    public int hashCode() {
        return Objects.hash(this.f401a);
    }
}
