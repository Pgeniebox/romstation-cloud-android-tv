package org.romstation.application;

import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;

/* JADX INFO: renamed from: org.romstation.application.ad */
/* JADX INFO: compiled from: GameScannerTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ad.class */
public class C0057ad extends Task<List<C0055ab>> {

    /* JADX INFO: renamed from: a */
    private final Path f114a;

    /* JADX INFO: renamed from: b */
    private final int[] f115b = {6209, 6210};

    public C0057ad(Path root) {
        this.f114a = root;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<C0055ab> call() throws SQLException {
        EntityManager entityManager = C0081b.m309c();
        List<String> metas = entityManager.createNativeQuery("SELECT \"VALUE\" from GAME_FILE_META WHERE \"KEY\" LIKE 'legacy'").getResultList();
        entityManager.close();
        JsonParser jsonParser = new JsonParser();
        Stream<String> streamFilter = metas.stream().filter((v0) -> {
            return Objects.nonNull(v0);
        });
        jsonParser.getClass();
        List<Integer> values = (List) streamFilter.map(jsonParser::parse).filter((v0) -> {
            return v0.isJsonObject();
        }).map((v0) -> {
            return v0.getAsJsonObject();
        }).filter(jsonObject -> {
            return jsonObject.has("id");
        }).map(jsonObject2 -> {
            return Integer.valueOf(jsonObject2.get("id").getAsInt());
        }).collect(Collectors.toList());
        List<C0055ab> games = new LinkedList<>();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.f114a.resolve("database.sqlite").toString());
        Throwable th = null;
        try {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM games");
                while (resultSet.next()) {
                    C0055ab game = new C0055ab(this.f114a, resultSet);
                    if (game.getFilename() != null && !values.contains(Integer.valueOf(game.getId())) && IntStream.of(this.f115b).noneMatch(i -> {
                        return i == game.getRsId();
                    })) {
                        Path gameFilePath = Paths.get(game.getFilename(), new String[0]);
                        if (!game.isLink() && (!gameFilePath.startsWith(this.f114a) || !Files.exists(gameFilePath, new LinkOption[0]))) {
                            game.setLink(true);
                        }
                        games.add(game);
                    }
                }
                if (connection != null) {
                    if (0 != 0) {
                        try {
                            connection.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        connection.close();
                    }
                }
                return games;
            } catch (Throwable th3) {
                th = th3;
                throw th3;
            }
        } catch (Throwable th4) {
            if (connection != null) {
                if (th != null) {
                    try {
                        connection.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                } else {
                    connection.close();
                }
            }
            throw th4;
        }
    }
}
