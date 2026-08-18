package org.romstation.application;

import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bs */
/* JADX INFO: compiled from: SystemRegionListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bs.class */
public class C0126bs extends ListCell<EnumC0125br> {

    /* JADX INFO: renamed from: a */
    private final ResourceBundle f297a = RomStation.m44d();

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(EnumC0125br region, boolean empty) {
        super.updateItem(region, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        switch (region) {
            case AUTO:
                setText(this.f297a.getString("createServerDialog.system.region.auto"));
                setGraphic(null);
                break;
            case NTSC_J:
                setText(this.f297a.getString("createServerDialog.system.region.ntsc-j"));
                setGraphic(new ImageView(new File("images/flags/jp.png").toURI().toString()));
                break;
            case NTSC_U:
                setText(this.f297a.getString("createServerDialog.system.region.ntsc-u"));
                setGraphic(new ImageView(new File("images/flags/us.png").toURI().toString()));
                break;
            case PAL:
                setText(this.f297a.getString("createServerDialog.system.region.pal"));
                setGraphic(new ImageView(new File("images/flags/eu.png").toURI().toString()));
                break;
        }
    }

    /* JADX INFO: renamed from: org.romstation.application.bs$a */
    /* JADX INFO: compiled from: SystemRegionListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bs$a.class */
    public static class a implements Callback<ListView<EnumC0125br>, ListCell<EnumC0125br>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<EnumC0125br> call(ListView<EnumC0125br> param) {
            return new C0126bs();
        }
    }
}
