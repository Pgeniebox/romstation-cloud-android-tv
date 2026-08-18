package org.romstation.application.database.entity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.logging.Level;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Image.class */
@Table(name = "IMAGE")
@Entity
@Access(AccessType.PROPERTY)
public class Image extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private String f482a;

    /* JADX INFO: renamed from: b */
    private boolean f483b;

    public Image() {
    }

    public Image(Path path) {
        this(path.toString());
    }

    public Image(String path) {
        this(path, false);
    }

    public Image(String path, boolean url) {
        this.f482a = path;
        this.f483b = url;
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_GENERATOR")
    @SequenceGenerator(name = "IMAGE_GENERATOR", sequenceName = "IMAGE_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getPath() {
        return this.f482a;
    }

    public void setPath(String path) {
        this.f482a = path;
    }

    @Transient
    public URI getURI() {
        try {
            return this.f483b ? new URI(this.f482a) : new File(this.f482a).toURI();
        } catch (URISyntaxException exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    @Transient
    public boolean isUrl() {
        return this.f483b;
    }

    @Transient
    public void setUrl(boolean url) {
        this.f483b = url;
    }
}
