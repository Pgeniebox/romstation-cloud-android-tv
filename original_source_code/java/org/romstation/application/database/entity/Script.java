package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Script.class */
@Table(name = "SCRIPT")
@Entity
@Access(AccessType.PROPERTY)
@SequenceGenerator(name = "GENERATOR", sequenceName = "SCRIPT_SEQUENCE", allocationSize = 1)
public class Script extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private String f502a;

    public Script() {
    }

    public Script(String path) {
        this.f502a = path;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCRIPT_GENERATOR")
    @SequenceGenerator(name = "SCRIPT_GENERATOR", sequenceName = "SCRIPT_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getPath() {
        return this.f502a;
    }

    public void setPath(String path) {
        this.f502a = path;
    }
}
