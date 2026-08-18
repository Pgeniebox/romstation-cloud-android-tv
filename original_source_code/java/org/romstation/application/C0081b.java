package org.romstation.application;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* JADX INFO: renamed from: org.romstation.application.b */
/* JADX INFO: compiled from: DatabaseManager.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/b.class */
public class C0081b {

    /* JADX INFO: renamed from: a */
    private static final String f158a = "database";

    /* JADX INFO: renamed from: b */
    private static EntityManagerFactory f159b;

    /* JADX INFO: renamed from: a */
    public static boolean m307a() {
        Map<String, String> properties = new HashMap<>();
        if (!Files.isDirectory(Paths.get(f158a, new String[0]), new LinkOption[0])) {
            properties.put("javax.persistence.schema-generation.database.action", "create");
            properties.put("javax.persistence.schema-generation.create-source", "script");
            properties.put("javax.persistence.schema-generation.create-script-source", "META-INF/sql/create.sql");
            properties.put("javax.persistence.sql-load-script-source", "META-INF/sql/import.sql");
        }
        f159b = Persistence.createEntityManagerFactory("romstation", properties);
        return true;
    }

    /* JADX INFO: renamed from: b */
    public static boolean m308b() {
        return f159b != null;
    }

    /* JADX INFO: renamed from: c */
    public static EntityManager m309c() {
        if (f159b != null) {
            return f159b.createEntityManager();
        }
        throw new IllegalStateException("EntityManager not initialized.");
    }

    /* JADX INFO: renamed from: d */
    public static void m310d() {
        f159b.close();
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException exception) {
            if (exception.getSQLState().equals("XJ015")) {
                RomStation.m42b().log(Level.INFO, exception.getMessage(), (Throwable) exception);
            } else {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
        }
    }
}
