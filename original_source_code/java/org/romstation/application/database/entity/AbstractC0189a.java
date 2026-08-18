package org.romstation.application.database.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/* JADX INFO: renamed from: org.romstation.application.database.entity.a */
/* JADX INFO: compiled from: SimpleEntity.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/a.class */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private Integer f520a;

    @Transient
    public Integer getId() {
        return this.f520a;
    }

    public void setId(Integer id) {
        this.f520a = id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractC0189a that = (AbstractC0189a) o;
        return (getId() == null || that.getId() == null) ? super.equals(that) : getId().equals(that.getId());
    }

    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return 0;
    }
}
