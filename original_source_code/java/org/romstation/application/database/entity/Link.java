package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Link.class */
@Table(name = "LINK")
@Entity
@Access(AccessType.PROPERTY)
public class Link extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private String f489a;

    /* JADX INFO: renamed from: b */
    private String f490b;

    /* JADX INFO: renamed from: c */
    private boolean f491c;

    public Link() {
    }

    public Link(String name, String location) {
        this(name, location, false);
    }

    public Link(String name, String location, Boolean external) {
        this.f489a = name;
        this.f490b = location;
        this.f491c = external.booleanValue();
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LINK_GENERATOR")
    @SequenceGenerator(name = "LINK_GENERATOR", sequenceName = "LINK_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f489a;
    }

    public void setName(String name) {
        this.f489a = name;
    }

    public String getLocation() {
        return this.f490b;
    }

    public void setLocation(String location) {
        this.f490b = location;
    }

    @Column(name = "\"EXTERNAL\"", nullable = false)
    public boolean isExternal() {
        return this.f491c;
    }

    public void setExternal(boolean external) {
        this.f491c = external;
    }
}
