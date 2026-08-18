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

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Developer.class */
@Table(name = "DEVELOPER")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Developer.f408a, query = "select count(developer) from Developer developer"), @NamedQuery(name = Developer.f409b, query = "select developer from Developer developer order by developer.name asc"), @NamedQuery(name = Developer.f410c, query = "select developer from Developer developer where developer.rid = :rid")})
public class Developer extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f408a = "Developer.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f409b = "Developer.findAll";

    /* JADX INFO: renamed from: c */
    public static final String f410c = "Developer.findByRID";

    /* JADX INFO: renamed from: d */
    private String f411d;

    public Developer() {
    }

    public Developer(Integer rid, String name) {
        super(rid);
        this.f411d = name;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEVELOPER_GENERATOR")
    @SequenceGenerator(name = "DEVELOPER_GENERATOR", sequenceName = "DEVELOPER_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f411d;
    }

    public void setName(String name) {
        this.f411d = name;
    }

    public String toString() {
        return "[" + getId() + "] " + this.f411d;
    }
}
