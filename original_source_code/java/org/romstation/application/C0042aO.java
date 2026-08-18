package org.romstation.application;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Image;
import org.romstation.application.database.entity.Language;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;
import org.romstation.application.view.controller.TranslationsPaneController;

/* JADX INFO: renamed from: org.romstation.application.aO */
/* JADX INFO: compiled from: LanguageEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aO.class */
public class C0042aO extends ApplicationFXMLDialog<Language> {

    /* JADX INFO: renamed from: a */
    private final Language f71a;

    @FXML
    private PathField graphicPathField;

    @FXML
    private TranslationsPaneController nameController;

    @FXML
    private ResourceBundle resources;

    public C0042aO(Language language) {
        this.f71a = language;
        load(getClass().getResource("/fxml/dialog/editor/languageEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        if (this.f71a.getGraphic() != null) {
            this.graphicPathField.setPath(this.f71a.getGraphic().getPath());
        }
        setResizable(true);
    }

    @FXML
    private void selectPath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        if (this.graphicPathField.getPath() != null && !this.graphicPathField.getPath().isEmpty()) {
            Path path = Paths.get(this.graphicPathField.getPath(), new String[0]);
            fileChooser.setInitialDirectory(path.getParent().toFile());
            fileChooser.setInitialFileName(path.toString());
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(this.resources.getString("languageEditorDialog.graphic.extensionFilters"), new String[]{"*.bmp", "*.gif", "*.png", "*.jpg"}));
        File path2 = fileChooser.showOpenDialog(getDialogPane().getScene().getWindow());
        if (path2 != null) {
            this.graphicPathField.setPath(path2.toString());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        if (classType == getClass()) {
            return this;
        }
        try {
            I18n i18n = this.f71a.getName() == null ? new I18n() : this.f71a.getName();
            return classType.getDeclaredConstructor(I18n.class).newInstance(i18n);
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Language resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            if (this.graphicPathField.getPath() == null || this.graphicPathField.getPath().isEmpty()) {
                this.f71a.setGraphic(null);
            } else {
                this.f71a.setGraphic(new Image(this.graphicPathField.getPath()));
            }
            if (this.nameController.m1349d().getItems().isEmpty()) {
                this.f71a.setName(null);
            } else {
                this.f71a.setName(this.nameController.m1345a());
                this.f71a.getName().setTranslations(this.nameController.m1349d().getItems());
            }
            return this.f71a;
        }
        return null;
    }
}
