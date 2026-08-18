package org.romstation.application;

import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import org.romstation.application.database.entity.I18n;
import org.romstation.application.database.entity.Tag;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.controller.TranslationsPaneController;

/* JADX INFO: renamed from: org.romstation.application.aV */
/* JADX INFO: compiled from: TagEditorDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aV.class */
public class C0049aV extends ApplicationFXMLDialog<Tag> {

    /* JADX INFO: renamed from: a */
    private final Tag f78a;

    @FXML
    private TranslationsPaneController nameController;

    @FXML
    private ResourceBundle resources;

    public C0049aV(Tag tag) {
        this.f78a = tag;
        load(getClass().getResource("/fxml/dialog/editor/tagEditorDialog.fxml"));
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
            I18n i18n = this.f78a.getName() == null ? new I18n() : this.f78a.getName();
            return classType.getDeclaredConstructor(I18n.class).newInstance(i18n);
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Tag resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            if (this.nameController.m1349d().getItems().isEmpty()) {
                this.f78a.setName(null);
            } else {
                this.f78a.setName(this.nameController.m1345a());
                this.f78a.getName().setTranslations(this.nameController.m1349d().getItems());
            }
            return this.f78a;
        }
        return null;
    }
}
