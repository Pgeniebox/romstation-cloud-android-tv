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

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Tag.class */
@Table(name = "TAG")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Tag.f512a, query = "select count(tag) from Tag tag"), @NamedQuery(name = Tag.f513b, query = "select tag from Tag tag"), @NamedQuery(name = Tag.f514c, query = "select tag from Tag tag join tag.name.translations translation where translation.locale.tag = :tag order by translation.string asc")})
public class Tag extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f512a = "Tag.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f513b = "Tag.findAll";

    /* JADX INFO: renamed from: c */
    public static final String f514c = "Tag.findAllOrdered";

    /* JADX INFO: renamed from: d */
    private I18n f515d;

    /* JADX INFO: renamed from: e */
    private Image f516e;

    public Tag() {
    }

    public Tag(I18n name) {
        this.f515d = name;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_GENERATOR")
    @SequenceGenerator(name = "TAG_GENERATOR", sequenceName = "TAG_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    @JoinColumn(name = "NAME_I18N_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public I18n getName() {
        return this.f515d;
    }

    public void setName(I18n name) {
        this.f515d = name;
    }

    @JoinColumn(name = "GRAPHIC_IMAGE_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Image getGraphic() {
        return this.f516e;
    }

    public void setGraphic(Image icon) {
        this.f516e = icon;
    }

    public String toString() {
        return "[" + getId() + "] " + this.f515d;
    }
}
