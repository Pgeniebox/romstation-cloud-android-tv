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

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Publisher.class */
@Table(name = "PUBLISHER")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Publisher.f497a, query = "select count(publisher) from Publisher publisher"), @NamedQuery(name = Publisher.f498b, query = "select publisher from Publisher publisher order by publisher.name asc"), @NamedQuery(name = Publisher.f499c, query = "select publisher from Publisher publisher where publisher.rid = :rid")})
public class Publisher extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f497a = "Publisher.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f498b = "Publisher.findAll";

    /* JADX INFO: renamed from: c */
    public static final String f499c = "Publisher.findByRID";

    /* JADX INFO: renamed from: d */
    private String f500d;

    public Publisher() {
    }

    public Publisher(Integer rid, String name) {
        super(rid);
        this.f500d = name;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PUBLISHER_GENERATOR")
    @SequenceGenerator(name = "PUBLISHER_GENERATOR", sequenceName = "PUBLISHER_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f500d;
    }

    public void setName(String name) {
        this.f500d = name;
    }

    public String toString() {
        return "[" + getId() + "] " + this.f500d;
    }
}
