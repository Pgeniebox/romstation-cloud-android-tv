package org.romstation.application;

import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import org.romstation.application.database.entity.Genre;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.controller.TranslationsPaneController;

/* JADX INFO: renamed from: org.romstation.application.aN */
/* JADX INFO: compiled from: GenreEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aN.class */
public class C0041aN extends ApplicationFXMLDialog<Genre> {

    /* JADX INFO: renamed from: a */
    private final Genre f70a;

    @FXML
    private TranslationsPaneController nameController;

    @FXML
    private ResourceBundle resources;

    public C0041aN(Genre genre) {
        this.f70a = genre;
        load(getClass().getResource("/fxml/dialog/editor/genreEditorDialog.fxml"));
    }

    @FXML
    private void initialize() {
        setResizable(true);
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        if (classType == getClass()) {
            return this;
        }
        try {
            I18n i18n = this.f70a.getName() == null ? new I18n() : this.f70a.getName();
            return classType.getDeclaredConstructor(I18n.class).newInstance(i18n);
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Genre resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            if (this.nameController.m1349d().getItems().isEmpty()) {
                this.f70a.setName(null);
            } else {
                this.f70a.setName(this.nameController.m1345a());
                this.f70a.getName().setTranslations(this.nameController.m1349d().getItems());
            }
            return this.f70a;
        }
        return null;
    }
}
