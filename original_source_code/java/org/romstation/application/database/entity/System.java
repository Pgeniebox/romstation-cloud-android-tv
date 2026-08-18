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

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/System.class */
@Table(name = "SYSTEM")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = System.f507a, query = "select count(system) from System system"), @NamedQuery(name = System.f508b, query = "select system from System system order by system.name asc"), @NamedQuery(name = System.f509c, query = "select system from System system where system.rid = :rid")})
public class System extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f507a = "System.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f508b = "System.findAll";

    /* JADX INFO: renamed from: c */
    public static final String f509c = "System.findByRID";

    /* JADX INFO: renamed from: d */
    private String f510d;

    /* JADX INFO: renamed from: e */
    private Image f511e;

    public System() {
    }

    public System(Integer rid, String name, Image graphic) {
        setRid(rid);
        this.f510d = name;
        this.f511e = graphic;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYSTEM_GENERATOR")
    @SequenceGenerator(name = "SYSTEM_GENERATOR", sequenceName = "SYSTEM_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f510d;
    }

    public void setName(String name) {
        this.f510d = name;
    }

    @JoinColumn(name = "GRAPHIC_IMAGE_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Image getGraphic() {
        return this.f511e;
    }

    public void setGraphic(Image icon) {
        this.f511e = icon;
    }

    public String toString() {
        return "[" + getId() + "] " + this.f510d;
    }
}
