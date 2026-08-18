package org.romstation.application;

import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bi */
/* JADX INFO: compiled from: DecoderListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bi.class */
public class C0116bi extends ListCell<EnumC0115bh> {

    /* JADX INFO: renamed from: a */
    private final ResourceBundle f283a = RomStation.m44d();

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(EnumC0115bh decoder, boolean empty) {
        super.updateItem(decoder, empty);
        if (empty) {
            setText(null);
            return;
        }
        switch (decoder) {
            case AUTO:
                setText(this.f283a.getString("createServerDialog.videoSettings.decoder.auto"));
                break;
            case HARDWARE:
                setText(this.f283a.getString("createServerDialog.videoSettings.decoder.hardware"));
                break;
            case SOFTWARE:
                setText(this.f283a.getString("createServerDialog.videoSettings.decoder.software"));
                break;
        }
    }

    /* JADX INFO: renamed from: org.romstation.application.bi$a */
    /* JADX INFO: compiled from: DecoderListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bi$a.class */
    public static class a implements Callback<ListView<EnumC0115bh>, ListCell<EnumC0115bh>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<EnumC0115bh> call(ListView<EnumC0115bh> param) {
            return new C0116bi();
        }
    }
}
