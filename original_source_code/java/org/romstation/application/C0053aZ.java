package org.romstation.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.EntityManager;
import org.romstation.application.database.entity.Developer;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.Publisher;
import org.romstation.application.database.entity.System;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.DeveloperComboBox;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.control.PublisherComboBox;
import org.romstation.application.view.control.SystemComboBox;
import org.romstation.application.view.controller.GameFilesPaneController;
import org.romstation.application.view.controller.GenresPaneController;
import org.romstation.application.view.controller.LanguagesPaneController;
import org.romstation.application.view.controller.LinksPaneController;
import org.romstation.application.view.controller.MetasPaneController;
import org.romstation.application.view.controller.SeriesPaneController;
import org.romstation.application.view.controller.TagsPaneController;
import org.romstation.application.view.controller.TranslationsPaneController;

/* JADX INFO: renamed from: org.romstation.application.aZ */
/* JADX INFO: compiled from: GameEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aZ.class */
public class C0053aZ extends ApplicationFXMLDialog<Game> {

    /* JADX INFO: renamed from: a */
    private final Game f82a;

    /* JADX INFO: renamed from: b */
    private TextFormatter<Integer> f83b;

    /* JADX INFO: renamed from: c */
    private TextFormatter<Integer> f84c;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField titleTextField;

    @FXML
    private SystemComboBox systemComboBox;

    @FXML
    private TextField playersTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private DeveloperComboBox developerComboBox;

    @FXML
    private PublisherComboBox publisherComboBox;

    @FXML
    private PathField graphicPathField;

    @FXML
    private PathField gameDirectoryPathField;

    @FXML
    private GenresPaneController genresController;

    @FXML
    private LanguagesPaneController languagesController;

    @FXML
    private TranslationsPaneController descriptionController;

    @FXML
    private SeriesPaneController seriesController;

    @FXML
    private TagsPaneController tagsController;

    @FXML
    private LinksPaneController linksController;

    @FXML
    private MetasPaneController metasController;

    @FXML
    private GameFilesPaneController gameFilesController;

    @FXML
    private ResourceBundle resources;

    public C0053aZ(Game game) {
        this.f82a = game;
        load(getClass().getResource("/fxml/dialog/editor/game/gameEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        EntityManager entityManager = C0081b.m309c();
        this.titleTextField.setText(this.f82a.getTitle());
        this.systemComboBox.getComboBox().getItems().addAll(entityManager.createNamedQuery(System.f508b, System.class).getResultList());
        this.systemComboBox.getComboBox().getSelectionModel().select(this.f82a.getSystem());
        this.f83b = new TextFormatter<>(new IntegerStringConverter(), this.f82a.getPlayers());
        this.playersTextField.setTextFormatter(this.f83b);
        this.f84c = new TextFormatter<>(new IntegerStringConverter(), this.f82a.getYear());
        this.yearTextField.setTextFormatter(this.f84c);
        this.developerComboBox.getComboBox().getItems().addAll(entityManager.createNamedQuery(Developer.f409b, Developer.class).getResultList());
        this.developerComboBox.getComboBox().getSelectionModel().select(this.f82a.getDeveloper());
        this.publisherComboBox.getComboBox().getItems().addAll(entityManager.createNamedQuery(Publisher.f498b, Publisher.class).getResultList());
        this.publisherComboBox.getComboBox().getSelectionModel().select(this.f82a.getPublisher());
        if (this.f82a.getGraphic() != null) {
            this.graphicPathField.setPath(this.f82a.getGraphic().getPath());
        }
        this.gameDirectoryPathField.setPath(this.f82a.getDirectory());
        this.gameDirectoryPathField.setDisable(this.f82a.isManaged());
        this.genresController.m1289a().getItems().setAll(this.f82a.getGenres());
        this.languagesController.m1296a().getItems().setAll(this.f82a.getLanguages());
        this.descriptionController.m1348a(true);
        this.seriesController.m1326a().getItems().setAll(this.f82a.getSeries());
        this.tagsController.m1338a().getItems().setAll(this.f82a.getTags());
        this.linksController.m1303a().getItems().setAll(this.f82a.getLinks());
        this.metasController.m1308a().getItems().setAll(this.f82a.getMetas().entrySet());
        entityManager.close();
        setResizable(true);
    }

    @FXML
    private void selectGraphicPath() {
        FileChooser fileChooser = new FileChooser();
        if (this.graphicPathField.getPath() != null) {
            Path defaultPath = Paths.get(this.graphicPathField.getPath(), new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                fileChooser.setInitialDirectory(defaultPath.getParent().toFile());
                fileChooser.setInitialFileName(defaultPath.toString());
            }
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(this.resources.getString("gameEditorDialog.image.extensionFilters"), new String[]{"*.bmp", "*.gif", "*.png", "*.jpg"}));
        File path = fileChooser.showOpenDialog(this.scrollPane.getScene().getWindow());
        if (path != null) {
            this.graphicPathField.setPath(path.getPath());
        }
    }

    @FXML
    private void selectGameDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String path = this.gameDirectoryPathField.getPath() != null ? this.gameDirectoryPathField.getPath() : this.f82a.getDirectory();
        if (path != null) {
            Path defaultPath = Paths.get(path, new String[0]);
            if (Files.exists(defaultPath, new LinkOption[0])) {
                directoryChooser.setInitialDirectory(defaultPath.toFile());
            }
        }
        File directoryPath = directoryChooser.showDialog(this.scrollPane.getScene().getWindow());
        if (directoryPath != null) {
            this.gameDirectoryPathField.setPath(directoryPath.toString());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        if (classType == getClass()) {
            return this;
        }
        try {
            if (classType == GameFilesPaneController.class) {
                return classType.getDeclaredConstructor(Game.class).newInstance(this.f82a);
            }
            if (classType == TranslationsPaneController.class) {
                I18n i18n = this.f82a.getDescription() == null ? new I18n() : this.f82a.getDescription();
                return classType.getDeclaredConstructor(I18n.class).newInstance(i18n);
            }
            return classType.newInstance();
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m155a() {
        this.f82a.setTitle(this.titleTextField.getText());
        this.f82a.setSystem((System) this.systemComboBox.getComboBox().getValue());
        this.f82a.setPlayers((Integer) this.f83b.getValue());
        this.f82a.setYear((Integer) this.f84c.getValue());
        this.f82a.setDeveloper((Developer) this.developerComboBox.getComboBox().getValue());
        this.f82a.setPublisher((Publisher) this.publisherComboBox.getComboBox().getValue());
        this.f82a.setFiles(this.gameFilesController.m1284a().getItems());
        this.f82a.setGenres(this.genresController.m1289a().getItems());
        this.f82a.setLanguages(this.languagesController.m1296a().getItems());
        this.f82a.setLinks(this.linksController.m1303a().getItems());
        this.f82a.setSeries(this.seriesController.m1326a().getItems());
        this.f82a.setTags(this.tagsController.m1338a().getItems());
        if (this.graphicPathField.getPath() == null || this.graphicPathField.getPath().isEmpty()) {
            this.f82a.setGraphic(null);
        } else {
            this.f82a.setGraphic(new Image(this.graphicPathField.getPath()));
        }
        this.f82a.setDirectory(this.gameDirectoryPathField.getPath());
        if (this.descriptionController.m1349d().getItems().isEmpty()) {
            this.f82a.setDescription(null);
        } else {
            this.f82a.setDescription(this.descriptionController.m1345a());
            this.f82a.getDescription().setTranslations(this.descriptionController.m1349d().getItems());
        }
        this.f82a.setMetas(this.metasController.m1309b());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Game resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            m155a();
            return this.f82a;
        }
        return null;
    }
}
