package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Language.class */
@Table(name = "LANGUAGE")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Language.f484a, query = "select count(language) from Language language"), @NamedQuery(name = Language.f485b, query = "select language from Language language"), @NamedQuery(name = Language.f486c, query = "select language from Language language join language.name.translations translation where translation.locale.tag = :tag order by translation.string asc")})
public class Language extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f484a = "Language.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f485b = "Language.findAll";

    /* JADX INFO: renamed from: c */
    public static final String f486c = "Language.findAllOrdered";

    /* JADX INFO: renamed from: d */
    private I18n f487d;

    /* JADX INFO: renamed from: e */
    private Image f488e;

    public Language() {
    }

    public Language(Integer rid, I18n name) {
        this(rid, name, null);
    }

    public Language(Integer rid, I18n name, Image graphic) {
        super(rid);
        this.f487d = name;
        this.f488e = graphic;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LANGUAGE_GENERATOR")
    @SequenceGenerator(name = "LANGUAGE_GENERATOR", sequenceName = "LANGUAGE_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    @JoinColumn(name = "NAME_I18N_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public I18n getName() {
        return this.f487d;
    }

    public void setName(I18n name) {
        this.f487d = name;
    }

    @JoinColumn(name = "GRAPHIC_IMAGE_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Image getGraphic() {
        return this.f488e;
    }

    public void setGraphic(Image icon) {
        this.f488e = icon;
    }

    public String toString() {
        return "[" + getId() + "] " + this.f487d;
    }
}
