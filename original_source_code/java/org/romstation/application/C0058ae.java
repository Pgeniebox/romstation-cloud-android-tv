package org.romstation.application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/* JADX INFO: renamed from: org.romstation.application.ae */
/* JADX INFO: compiled from: Account.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ae.class */
public class C0058ae {

    /* JADX INFO: renamed from: a */
    private static C0058ae f116a;

    /* JADX INFO: renamed from: b */
    private final IntegerProperty f117b = new SimpleIntegerProperty();

    /* JADX INFO: renamed from: c */
    private final ReadOnlyBooleanWrapper f118c = new ReadOnlyBooleanWrapper();

    /* JADX INFO: renamed from: d */
    private final StringProperty f119d = new SimpleStringProperty();

    /* JADX INFO: renamed from: e */
    private final IntegerProperty f120e = new SimpleIntegerProperty();

    /* JADX INFO: renamed from: f */
    private final BooleanProperty f121f = new SimpleBooleanProperty();

    /* JADX INFO: renamed from: g */
    private final ObjectProperty<EnumC0059af> f122g = new SimpleObjectProperty();

    /* JADX INFO: renamed from: h */
    private final StringProperty f123h = new SimpleStringProperty();

    /* JADX INFO: renamed from: i */
    private final StringProperty f124i = new SimpleStringProperty();

    /* JADX INFO: renamed from: j */
    private final StringProperty f125j = new SimpleStringProperty();

    /* JADX INFO: renamed from: k */
    private final StringProperty f126k = new SimpleStringProperty();

    private C0058ae() {
        this.f118c.bind(this.f117b.greaterThan(0));
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0058ae m195a() {
        if (f116a == null) {
            f116a = new C0058ae();
        }
        return f116a;
    }

    /* JADX INFO: renamed from: b */
    public IntegerProperty m196b() {
        return this.f117b;
    }

    /* JADX INFO: renamed from: c */
    public int m197c() {
        return this.f117b.get();
    }

    /* JADX INFO: renamed from: a */
    public void m198a(int id) {
        this.f117b.set(id);
    }

    /* JADX INFO: renamed from: d */
    public ReadOnlyBooleanProperty m199d() {
        return this.f118c.getReadOnlyProperty();
    }

    /* JADX INFO: renamed from: e */
    public boolean m200e() {
        return this.f118c.get();
    }

    /* JADX INFO: renamed from: f */
    public StringProperty m201f() {
        return this.f119d;
    }

    /* JADX INFO: renamed from: g */
    public String m202g() {
        return (String) this.f119d.get();
    }

    /* JADX INFO: renamed from: a */
    public void m203a(String name) {
        this.f119d.set(name);
    }

    /* JADX INFO: renamed from: h */
    public IntegerProperty m204h() {
        return this.f120e;
    }

    /* JADX INFO: renamed from: i */
    public int m205i() {
        return this.f120e.get();
    }

    /* JADX INFO: renamed from: b */
    public void m206b(int group) {
        this.f120e.set(group);
    }

    /* JADX INFO: renamed from: j */
    public BooleanProperty m207j() {
        return this.f121f;
    }

    /* JADX INFO: renamed from: k */
    public boolean m208k() {
        return this.f121f.get();
    }

    /* JADX INFO: renamed from: a */
    public void m209a(boolean banned) {
        this.f121f.setValue(Boolean.valueOf(banned));
    }

    /* JADX INFO: renamed from: l */
    public ObjectProperty<EnumC0059af> m210l() {
        return this.f122g;
    }

    /* JADX INFO: renamed from: m */
    public EnumC0059af m211m() {
        return (EnumC0059af) this.f122g.get();
    }

    /* JADX INFO: renamed from: a */
    public void m212a(EnumC0059af membershipStatus) {
        this.f122g.setValue(membershipStatus);
    }

    /* JADX INFO: renamed from: n */
    public StringProperty m213n() {
        return this.f123h;
    }

    /* JADX INFO: renamed from: o */
    public String m214o() {
        return (String) this.f123h.get();
    }

    /* JADX INFO: renamed from: b */
    public void m215b(String photo) {
        this.f123h.set(photo);
    }

    /* JADX INFO: renamed from: p */
    public StringProperty m216p() {
        return this.f124i;
    }

    /* JADX INFO: renamed from: q */
    public String m217q() {
        return (String) this.f124i.get();
    }

    /* JADX INFO: renamed from: c */
    public void m218c(String session) {
        this.f124i.set(session);
    }

    /* JADX INFO: renamed from: r */
    public StringProperty m219r() {
        return this.f125j;
    }

    /* JADX INFO: renamed from: s */
    public String m220s() {
        return (String) this.f125j.get();
    }

    /* JADX INFO: renamed from: d */
    public void m221d(String profile) {
        this.f125j.set(profile);
    }

    /* JADX INFO: renamed from: t */
    public StringProperty m222t() {
        return this.f126k;
    }

    /* JADX INFO: renamed from: u */
    public String m223u() {
        return (String) this.f126k.get();
    }

    /* JADX INFO: renamed from: e */
    public void m224e(String logout) {
        this.f126k.set(logout);
    }

    /* JADX INFO: renamed from: v */
    public void m225v() {
        this.f117b.set(0);
        this.f124i.set((Object) null);
    }
}
