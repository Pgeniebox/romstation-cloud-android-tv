package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Series.class */
@Table(name = "SERIES")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Series.f503a, query = "select count(series) from Series series"), @NamedQuery(name = Series.f504b, query = "select series from Series series order by series.name asc"), @NamedQuery(name = Series.f505c, query = "select series from Series series where series.rid = :rid")})
public class Series extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f503a = "Series.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f504b = "Series.findAll";

    /* JADX INFO: renamed from: c */
    public static final String f505c = "Series.findByRID";

    /* JADX INFO: renamed from: d */
    private String f506d;

    public Series() {
    }

    public Series(int rid, String name) {
        super(Integer.valueOf(rid));
        this.f506d = name;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERIES_GENERATOR")
    @SequenceGenerator(name = "SERIES_GENERATOR", sequenceName = "SERIES_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f506d;
    }

    public void setName(String name) {
        this.f506d = name;
    }
}
