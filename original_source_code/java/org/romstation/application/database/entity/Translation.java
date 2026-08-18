package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Translation.class */
@Table(name = "\"TRANSLATION\"", uniqueConstraints = {@UniqueConstraint(columnNames = {"LOCALE_ID", "I18N_ID"})})
@Entity
@Access(AccessType.PROPERTY)
public class Translation extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private Locale f517a;

    /* JADX INFO: renamed from: b */
    private String f518b;

    /* JADX INFO: renamed from: c */
    private I18n f519c;

    public Translation() {
    }

    public Translation(Locale locale) {
        this(locale, null, null);
    }

    public Translation(Locale locale, String string) {
        this(locale, string, null);
    }

    public Translation(Locale locale, String string, I18n i18n) {
        this.f517a = locale;
        this.f518b = string;
        this.f519c = i18n;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSLATION_GENERATOR")
    @SequenceGenerator(name = "TRANSLATION_GENERATOR", sequenceName = "TRANSLATION_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    @OneToOne
    public Locale getLocale() {
        return this.f517a;
    }

    public void setLocale(Locale locale) {
        this.f517a = locale;
    }

    @Column(length = 32672)
    public String getString() {
        return this.f518b;
    }

    public void setString(String string) {
        this.f518b = string;
    }

    @ManyToOne
    public I18n getI18N() {
        return this.f519c;
    }

    public void setI18N(I18n i18N) {
        this.f519c = i18N;
    }
}
