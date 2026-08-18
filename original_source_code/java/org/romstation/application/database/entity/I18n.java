package org.romstation.application.database.entity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/I18n.class */
@Table(name = "I18N")
@Entity
@Access(AccessType.PROPERTY)
public class I18n extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private List<Translation> f481a;

    public I18n() {
        this.f481a = new LinkedList();
    }

    public I18n(Translation... translations) {
        this((List<Translation>) Arrays.asList(translations));
    }

    public I18n(List<Translation> translations) {
        this.f481a = new LinkedList();
        this.f481a.addAll(translations);
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "I18N_GENERATOR")
    @SequenceGenerator(name = "I18N_GENERATOR", sequenceName = "I18N_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    @JoinColumn(name = "I18N_ID")
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Translation> getTranslations() {
        return this.f481a;
    }

    public void setTranslations(List<Translation> translations) {
        this.f481a = translations;
    }

    @Transient
    public String getDefaultString() {
        return (String) this.f481a.stream().filter(e -> {
            return e.getLocale().getTag().equals(java.util.Locale.getDefault().toLanguageTag());
        }).findFirst().map((v0) -> {
            return v0.getString();
        }).orElse("");
    }

    public String toString() {
        return "[" + getId() + "] " + getDefaultString();
    }
}
