package org.romstation.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.EntityManager;
import org.romstation.application.database.entity.Genre;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.Language;
import org.romstation.application.database.entity.Locale;
import org.romstation.application.database.entity.Series;
import org.romstation.application.database.entity.System;
import org.romstation.application.database.entity.Translation;
import org.romstation.application.network.C0217b;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.bQ */
/* JADX INFO: compiled from: GameUploadFormDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bQ.class */
public class C0098bQ extends ApplicationFXMLDialog<JsonObject> {

    /* JADX INFO: renamed from: a */
    private final JsonObject f238a;

    /* JADX INFO: renamed from: b */
    private final Path f239b;

    /* JADX INFO: renamed from: c */
    private Map<String, Locale> f240c;

    /* JADX INFO: renamed from: d */
    private List<System> f241d;

    /* JADX INFO: renamed from: e */
    private List<Language> f242e;

    /* JADX INFO: renamed from: f */
    private List<Genre> f243f;

    /* JADX INFO: renamed from: g */
    private List<Series> f244g;

    /* JADX INFO: renamed from: h */
    private List<Series> f245h;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField titleTextField;

    @FXML
    private ComboBox<System> systemComboBox;

    @FXML
    private TextField playersTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private ComboBox<String> developersComboBox;

    @FXML
    private ComboBox<String> publishersComboBox;

    @FXML
    private PathField imagePathField;

    @FXML
    private TextArea descriptionTextAreaEN;

    @FXML
    private TextArea descriptionTextAreaFR;

    @FXML
    private TextArea tipsTextAreaEN;

    @FXML
    private TextArea tipsTextAreaFR;

    @FXML
    private TextField fileNameTextField;

    @FXML
    private TextField filePathTextField;

    @FXML
    private PathField fileTargetPathField;

    @FXML
    private TextField fileParametersTextField;

    @FXML
    private CheckBox demoCheckBox;

    @FXML
    private CheckBox fangameCheckBox;

    @FXML
    private CheckBox fantradCheckBox;

    @FXML
    private CheckBox hackromCheckBox;

    @FXML
    private CheckBox homebrewCheckBox;

    @FXML
    private ListView<Language> languagesListView;

    @FXML
    private Button removeLanguageButton;

    @FXML
    private ListView<Genre> genresListView;

    @FXML
    private Button removeGenreButton;

    @FXML
    private ListView<Series> seriesListView;

    @FXML
    private Button removeSeriesButton;

    public C0098bQ(JsonObject uploadJsonObject, Path archive) {
        this.f238a = uploadJsonObject;
        this.f239b = archive;
        load(getClass().getResource("/fxml/dialog/upload/gameUploadFormDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.systemComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            boolean isTargetRequired = m478b();
            this.fileTargetPathField.setDisable(!isTargetRequired);
            this.fileParametersTextField.setDisable(!isTargetRequired);
        });
        EntityManager entityManager = C0081b.m309c();
        this.f240c = m471a(entityManager);
        this.f241d = m472a(this.f238a.getAsJsonObject("form").getAsJsonArray("systems"));
        this.f242e = m473a(this.f238a.getAsJsonObject("form").getAsJsonArray("languages"), this.f240c);
        this.f243f = m474b(this.f238a.getAsJsonObject("form").getAsJsonArray("genres"), this.f240c);
        this.f244g = m475b(entityManager);
        this.f245h = m476b(this.f238a.getAsJsonObject("form").getAsJsonArray("series"));
        this.systemComboBox.getItems().setAll(this.f241d);
        this.systemComboBox.getSelectionModel().selectFirst();
        this.playersTextField.setTextFormatter(new TextFormatter(new IntegerStringConverter()));
        this.yearTextField.setTextFormatter(new TextFormatter(new IntegerStringConverter()));
        List<String> developers = entityManager.createQuery("SELECT developer.name FROM Developer developer ORDER BY developer.name ASC", String.class).getResultList();
        this.developersComboBox.getItems().setAll(developers);
        List<String> publishers = entityManager.createQuery("SELECT publisher.name FROM Publisher publisher ORDER BY publisher.name ASC", String.class).getResultList();
        this.publishersComboBox.getItems().setAll(publishers);
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(this.imagePathField.pathProperty());
        Tooltip.install(this.imagePathField.getTextField(), tooltip);
        this.imagePathField.getTextField().setEditable(false);
        this.filePathTextField.setText(this.f239b.toString());
        this.fileTargetPathField.getTextField().setEditable(false);
        this.removeLanguageButton.disableProperty().bind(Bindings.size(this.languagesListView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.languagesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.removeGenreButton.disableProperty().bind(Bindings.size(this.genresListView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.genresListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.removeSeriesButton.disableProperty().bind(Bindings.size(this.seriesListView.getSelectionModel().getSelectedItems()).isEqualTo(0));
        this.seriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        entityManager.close();
        if (this.f238a.get("resume") != null) {
            m470a(this.f238a.getAsJsonObject("resume"));
            this.systemComboBox.setDisable(true);
        }
        this.fileNameTextField.textProperty().bind(this.titleTextField.textProperty());
        Button nextButton = this.dialogPane.lookupButton(ButtonType.OK);
        nextButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!m477a()) {
                event.consume();
            }
        });
        setResizable(true);
    }

    /* JADX INFO: renamed from: a */
    private void m470a(JsonObject resumeObject) {
        this.titleTextField.setText(resumeObject.get("title").getAsString());
        this.f241d.stream().filter(system -> {
            return system.getRid().equals(Integer.valueOf(resumeObject.get("console_id").getAsInt()));
        }).findAny().ifPresent(system2 -> {
            this.systemComboBox.getSelectionModel().select(system2);
        });
        this.playersTextField.setText(resumeObject.get("players").getAsString());
        this.yearTextField.setText(resumeObject.get("release_date").getAsString());
        this.developersComboBox.setValue(resumeObject.get("developer").getAsString());
        this.publishersComboBox.setValue(resumeObject.get("publisher").isJsonNull() ? null : resumeObject.get("publisher").getAsString());
        this.descriptionTextAreaEN.setText(resumeObject.getAsJsonObject("description").get("en").getAsString());
        this.descriptionTextAreaFR.setText(resumeObject.getAsJsonObject("description").get("fr").getAsString());
        this.tipsTextAreaEN.setText(resumeObject.getAsJsonObject("tips").get("en").getAsString());
        this.tipsTextAreaFR.setText(resumeObject.getAsJsonObject("tips").get("fr").getAsString());
        this.fileNameTextField.setText(resumeObject.getAsJsonObject("file").get("name").getAsString());
        this.fileTargetPathField.setPath(resumeObject.getAsJsonObject("file").get("target").getAsString());
        this.fileParametersTextField.setText(resumeObject.getAsJsonObject("file").get("parameters").getAsString());
        this.demoCheckBox.setSelected(resumeObject.get("demo").getAsBoolean());
        this.fangameCheckBox.setSelected(resumeObject.get("fangame").getAsBoolean());
        this.fantradCheckBox.setSelected(resumeObject.get("fantrad").getAsBoolean());
        this.hackromCheckBox.setSelected(resumeObject.get("hack").getAsBoolean());
        this.homebrewCheckBox.setSelected(resumeObject.get("homebrew").getAsBoolean());
        resumeObject.getAsJsonArray("languages").forEach(element -> {
            int id = element.getAsJsonObject().get("id").getAsInt();
            this.f242e.stream().filter(language -> {
                return language.getRid().equals(Integer.valueOf(id));
            }).findAny().ifPresent(language2 -> {
                this.languagesListView.getItems().add(language2);
            });
        });
        resumeObject.getAsJsonArray("genres").forEach(element2 -> {
            int id = element2.getAsJsonObject().get("id").getAsInt();
            this.f243f.stream().filter(genre -> {
                return genre.getRid().equals(Integer.valueOf(id));
            }).findAny().ifPresent(genre2 -> {
                this.genresListView.getItems().add(genre2);
            });
        });
        resumeObject.getAsJsonArray("series").forEach(element3 -> {
            int id = element3.getAsJsonObject().get("id").getAsInt();
            this.f245h.stream().filter(series -> {
                return series.getRid().equals(Integer.valueOf(id));
            }).findAny().ifPresent(series2 -> {
                this.seriesListView.getItems().add(series2);
            });
        });
    }

    /* JADX INFO: renamed from: a */
    private Map<String, Locale> m471a(EntityManager entityManager) {
        return (Map) entityManager.createNamedQuery(Locale.f492a, Locale.class).getResultList().stream().collect(Collectors.toMap((v0) -> {
            return v0.getTag();
        }, Function.identity()));
    }

    /* JADX INFO: renamed from: a */
    private List<System> m472a(JsonArray array) {
        List<System> items = new LinkedList<>();
        array.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            System system = new System(Integer.valueOf(jsonObject.get("id").getAsInt()), jsonObject.get("name").getAsString(), new Image(C0217b.m961b() + jsonObject.get("image").getAsString(), true));
            items.add(system);
        });
        return items;
    }

    /* JADX INFO: renamed from: a */
    private List<Language> m473a(JsonArray array, Map<String, Locale> locales) {
        List<Language> items = new LinkedList<>();
        array.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            I18n name = new I18n();
            name.getTranslations().add(new Translation((Locale) locales.get("fr"), jsonObject.get("name_fr").getAsString(), name));
            name.getTranslations().add(new Translation((Locale) locales.get("en"), jsonObject.get("name_en").getAsString(), name));
            Image image = new Image(C0217b.m961b() + jsonObject.get("image").getAsString(), true);
            Language language = new Language(Integer.valueOf(jsonObject.get("id").getAsInt()), name, image);
            items.add(language);
        });
        return items;
    }

    /* JADX INFO: renamed from: b */
    private List<Genre> m474b(JsonArray array, Map<String, Locale> locales) {
        List<Genre> items = new LinkedList<>();
        array.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            I18n name = new I18n();
            name.getTranslations().add(new Translation((Locale) locales.get("fr"), jsonObject.get("name_fr").getAsString(), name));
            name.getTranslations().add(new Translation((Locale) locales.get("en"), jsonObject.get("name_en").getAsString(), name));
            Genre genre = new Genre(Integer.valueOf(jsonObject.get("id").getAsInt()), name);
            items.add(genre);
        });
        return items;
    }

    /* JADX INFO: renamed from: b */
    private List<Series> m475b(EntityManager entityManager) {
        return entityManager.createNamedQuery(Series.f504b, Series.class).getResultList();
    }

    /* JADX INFO: renamed from: b */
    private List<Series> m476b(JsonArray array) {
        List<Series> items = new LinkedList<>();
        array.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Series series = new Series(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString());
            items.add(series);
        });
        return items;
    }

    /* JADX INFO: renamed from: a */
    private boolean m477a() {
        if (this.titleTextField.getText().trim().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.title"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.playersTextField.getText().trim().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.players"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.yearTextField.getText().trim().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.year"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.developersComboBox.getValue() == null || ((String) this.developersComboBox.getValue()).trim().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.developer"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.imagePathField.getPath() == null) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.coverImage"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.descriptionTextAreaEN.getText().trim().isEmpty() && this.descriptionTextAreaFR.getText().trim().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.description"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.fileNameTextField.getText().trim().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.file.name"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (m478b() && this.fileTargetPathField.getPath() == null) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.file.target"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.languagesListView.getItems().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.languages"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        if (this.genresListView.getItems().isEmpty()) {
            new ApplicationAlert(getResources().getString("gameUploadFormDialog.validationError.header"), getResources().getString("gameUploadFormDialog.validationError.genres"), Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        return true;
    }

    /* JADX INFO: renamed from: b */
    private boolean m478b() {
        int systemID = ((System) this.systemComboBox.getValue()).getRid().intValue();
        return systemID == 20 || systemID == 26 || systemID == 35 || systemID == 36;
    }

    @FXML
    private void selectImagePath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(getResources().getString("gameUploadFormDialog.coverImage.extensionFilter.description"), new String[]{"*.jpg", "*.png"}));
        if (this.imagePathField.getPath() != null) {
            Path path = Paths.get(this.imagePathField.getPath(), new String[0]);
            if (Files.exists(path, new LinkOption[0])) {
                fileChooser.setInitialDirectory(path.getParent().toFile());
                fileChooser.setInitialFileName(path.toString());
            }
        }
        File file = fileChooser.showOpenDialog(getDialogPane().getScene().getWindow());
        if (file != null) {
            if (m479a(file)) {
                this.imagePathField.setPath(file.toString());
            } else {
                new ApplicationAlert(getResources().getString("gameUploadFormDialog.coverImage.error.formatInvalid.header"), getResources().getString("gameUploadFormDialog.coverImage.error.formatInvalid.content"), Alert.AlertType.ERROR).showAndWait();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private boolean m479a(File file) {
        String contentType = URLConnection.guessContentTypeFromName(file.getName());
        if (contentType == null) {
            return false;
        }
        if (contentType.equals("image/jpeg") || contentType.equals("image/png")) {
            javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
            return !image.isError();
        }
        return false;
    }

    @FXML
    private void selectFileTarget(ActionEvent actionEvent) {
        C0039aL zipEntryChoiceDialog = new C0039aL(getResources().getString("gameUploadFormDialog.file.target.chooser.header"), Paths.get(this.filePathTextField.getText(), new String[0]));
        zipEntryChoiceDialog.showAndWait().ifPresent(path -> {
            this.fileTargetPathField.setPath(path.toString());
        });
    }

    @FXML
    private void addLanguage() {
        List<Language> collection = (List) this.f242e.stream().filter(language -> {
            return !this.languagesListView.getItems().contains(language);
        }).collect(Collectors.toList());
        C0031aD dialog = new C0031aD(collection);
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Language>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.languagesListView.getItems().addAll(items);
        });
    }

    @FXML
    private void removeLanguage() {
        this.languagesListView.getItems().removeAll(this.languagesListView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void addGenre() {
        List<Genre> collection = (List) this.f243f.stream().filter(language -> {
            return !this.genresListView.getItems().contains(language);
        }).collect(Collectors.toList());
        C0030aC dialog = new C0030aC(collection);
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Genre>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.genresListView.getItems().addAll(items);
        });
    }

    @FXML
    private void removeGenre() {
        this.genresListView.getItems().removeAll(this.genresListView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void createLocalSeries() {
        C0047aT dialog = new C0047aT(new Series());
        Optional<Series> optional = dialog.showAndWait();
        ObservableList items = this.seriesListView.getItems();
        items.getClass();
        optional.ifPresent((v1) -> {
            r1.add(v1);
        });
    }

    @FXML
    private void addLocalSeries() {
        List<Series> collection = (List) this.f244g.stream().filter(series -> {
            return !this.seriesListView.getItems().contains(series);
        }).collect(Collectors.toList());
        C0036aI dialog = new C0036aI(collection);
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Series>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.seriesListView.getItems().addAll(items);
        });
    }

    @FXML
    private void addRemoteSeries() {
        List<Series> collection = (List) this.f245h.stream().filter(language -> {
            return !this.seriesListView.getItems().contains(language);
        }).collect(Collectors.toList());
        C0036aI dialog = new C0036aI(collection);
        dialog.m124c().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Optional<List<Series>> optional = dialog.showAndWait();
        optional.ifPresent(items -> {
            this.seriesListView.getItems().addAll(items);
        });
    }

    @FXML
    private void removeSeries() {
        this.seriesListView.getItems().removeAll(this.seriesListView.getSelectionModel().getSelectedItems());
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public JsonObject resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            JsonObject object = new JsonObject();
            object.addProperty("title", this.titleTextField.getText());
            object.addProperty("system", ((System) this.systemComboBox.getValue()).getRid());
            object.addProperty("players", Integer.valueOf(Integer.parseInt(this.playersTextField.getText())));
            object.addProperty("year", Integer.valueOf(Integer.parseInt(this.yearTextField.getText())));
            object.addProperty("developer", (String) this.developersComboBox.getValue());
            object.addProperty("publisher", (String) this.publishersComboBox.getValue());
            object.addProperty("image", this.imagePathField.getPath());
            JsonObject descriptionObject = new JsonObject();
            descriptionObject.addProperty("en", this.descriptionTextAreaEN.getText());
            descriptionObject.addProperty("fr", this.descriptionTextAreaFR.getText());
            object.add("description", descriptionObject);
            JsonObject tipsObject = new JsonObject();
            tipsObject.addProperty("en", this.tipsTextAreaEN.getText());
            tipsObject.addProperty("fr", this.tipsTextAreaFR.getText());
            object.add("tips", tipsObject);
            JsonObject fileObject = new JsonObject();
            fileObject.addProperty("name", this.fileNameTextField.getText());
            fileObject.addProperty("path", this.filePathTextField.getText());
            fileObject.addProperty("target", this.fileTargetPathField.getPath());
            fileObject.addProperty("parameters", this.fileParametersTextField.getText());
            object.add("file", fileObject);
            JsonObject typeObject = new JsonObject();
            typeObject.addProperty("demo", Boolean.valueOf(this.demoCheckBox.isSelected()));
            typeObject.addProperty("fangame", Boolean.valueOf(this.fangameCheckBox.isSelected()));
            typeObject.addProperty("fantrad", Boolean.valueOf(this.fantradCheckBox.isSelected()));
            typeObject.addProperty("hackgame", Boolean.valueOf(this.hackromCheckBox.isSelected()));
            typeObject.addProperty("homebrew", Boolean.valueOf(this.homebrewCheckBox.isSelected()));
            object.add("type", typeObject);
            JsonArray languagesArray = new JsonArray();
            this.languagesListView.getItems().forEach(language -> {
                languagesArray.add(language.getRid());
            });
            object.add("languages", languagesArray);
            JsonArray genresArray = new JsonArray();
            this.genresListView.getItems().forEach(genre -> {
                genresArray.add(genre.getRid());
            });
            object.add("genres", genresArray);
            JsonArray seriesArray = new JsonArray();
            this.seriesListView.getItems().forEach(series -> {
                JsonObject seriesObject = new JsonObject();
                seriesObject.addProperty("id", series.getRid());
                seriesObject.addProperty("name", series.getName());
                seriesArray.add(seriesObject);
            });
            object.add("series", seriesArray);
            return object;
        }
        return null;
    }
}
