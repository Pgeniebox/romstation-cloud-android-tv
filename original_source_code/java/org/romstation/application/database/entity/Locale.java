package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Locale.class */
@Table(name = "LOCALE")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Locale.f492a, query = "select locale from Locale locale"), @NamedQuery(name = Locale.f493b, query = "select locale from Locale locale join locale.name.translations translation where translation.locale.tag = :tag order by translation.string asc")})
public class Locale extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    public static final String f492a = "Locale.findAll";

    /* JADX INFO: renamed from: b */
    public static final String f493b = "Locale.findAllOrdered";

    /* JADX INFO: renamed from: c */
    private String f494c;

    /* JADX INFO: renamed from: d */
    private I18n f495d;

    /* JADX INFO: renamed from: e */
    private Image f496e;

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCALE_GENERATOR")
    @SequenceGenerator(name = "LOCALE_GENERATOR", sequenceName = "LOCALE_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    @Column(length = 5)
    public String getTag() {
        return this.f494c;
    }

    public void setTag(String tag) {
        this.f494c = tag;
    }

    @JoinColumn(name = "NAME_I18N_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public I18n getName() {
        return this.f495d;
    }

    public void setName(I18n name) {
        this.f495d = name;
    }

    @JoinColumn(name = "GRAPHIC_IMAGE_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Image getGraphic() {
        return this.f496e;
    }

    public void setGraphic(Image icon) {
        this.f496e = icon;
    }

    public String toString() {
        return "[" + getId() + "] " + this.f495d;
    }
}
