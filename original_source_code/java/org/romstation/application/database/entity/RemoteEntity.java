package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/RemoteEntity.class */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class RemoteEntity extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private Integer f501a;

    public RemoteEntity() {
    }

    public RemoteEntity(Integer rid) {
        this.f501a = rid;
    }

    public Integer getRid() {
        return this.f501a;
    }

    public void setRid(Integer rid) {
        this.f501a = rid;
    }
}
