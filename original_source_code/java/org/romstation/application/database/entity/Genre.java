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

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Genre.class */
@Table(name = "GENRE")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Genre.f476a, query = "select count(genre) from Genre genre"), @NamedQuery(name = Genre.f477b, query = "select genre from Genre genre"), @NamedQuery(name = Genre.f478c, query = "select genre from Genre genre join genre.name.translations translation where translation.locale.tag = :tag order by translation.string asc")})
public class Genre extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f476a = "Genre.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f477b = "Genre.findAll";

    /* JADX INFO: renamed from: c */
    public static final String f478c = "Genre.findAllOrdered";

    /* JADX INFO: renamed from: d */
    private I18n f479d;

    /* JADX INFO: renamed from: e */
    private Image f480e;

    public Genre() {
    }

    public Genre(Integer rid, I18n name) {
        super(rid);
        this.f479d = name;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENRE_GENERATOR")
    @SequenceGenerator(name = "GENRE_GENERATOR", sequenceName = "GENRE_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    @JoinColumn(name = "NAME_I18N_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public I18n getName() {
        return this.f479d;
    }

    public void setName(I18n name) {
        this.f479d = name;
    }

    @JoinColumn(name = "GRAPHIC_IMAGE_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Image getGraphic() {
        return this.f480e;
    }

    public void setGraphic(Image icon) {
        this.f480e = icon;
    }

    public String toString() {
        return "[" + getId() + "] " + this.f479d;
    }
}
