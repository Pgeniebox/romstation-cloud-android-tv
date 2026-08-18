package org.romstation.application;

import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bq */
/* JADX INFO: compiled from: SystemLanguageListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bq.class */
public class C0124bq extends ListCell<EnumC0123bp> {

    /* JADX INFO: renamed from: a */
    private final ResourceBundle f295a = RomStation.m44d();

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(EnumC0123bp language, boolean empty) {
        super.updateItem(language, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        switch (language) {
            case JAPANESE:
                setText(this.f295a.getString("createServerDialog.system.language.jp"));
                setGraphic(new ImageView(new File("images/flags/jp.png").toURI().toString()));
                break;
            case ENGLISH:
                setText(this.f295a.getString("createServerDialog.system.language.en"));
                setGraphic(new ImageView(new File("images/flags/gb.png").toURI().toString()));
                break;
            case GERMAN:
                setText(this.f295a.getString("createServerDialog.system.language.de"));
                setGraphic(new ImageView(new File("images/flags/de.png").toURI().toString()));
                break;
            case FRENCH:
                setText(this.f295a.getString("createServerDialog.system.language.fr"));
                setGraphic(new ImageView(new File("images/flags/fr.png").toURI().toString()));
                break;
            case SPANISH:
                setText(this.f295a.getString("createServerDialog.system.language.es"));
                setGraphic(new ImageView(new File("images/flags/es.png").toURI().toString()));
                break;
            case ITALIAN:
                setText(this.f295a.getString("createServerDialog.system.language.it"));
                setGraphic(new ImageView(new File("images/flags/it.png").toURI().toString()));
                break;
            case DUTCH:
                setText(this.f295a.getString("createServerDialog.system.language.nl"));
                setGraphic(new ImageView(new File("images/flags/nl.png").toURI().toString()));
                break;
            case PORTUGUESE:
                setText(this.f295a.getString("createServerDialog.system.language.pt"));
                setGraphic(new ImageView(new File("images/flags/pt.png").toURI().toString()));
                break;
        }
    }

    /* JADX INFO: renamed from: org.romstation.application.bq$a */
    /* JADX INFO: compiled from: SystemLanguageListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bq$a.class */
    public static class a implements Callback<ListView<EnumC0123bp>, ListCell<EnumC0123bp>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<EnumC0123bp> call(ListView<EnumC0123bp> param) {
            return new C0124bq();
        }
    }
}
