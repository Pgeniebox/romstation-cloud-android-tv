package org.romstation.application;

import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bf */
/* JADX INFO: compiled from: BitRateListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bf.class */
public class C0113bf extends ListCell<Integer> {

    /* JADX INFO: renamed from: a */
    private static final PseudoClass f276a = PseudoClass.getPseudoClass("unavailable");

    /* JADX INFO: renamed from: b */
    private final ResourceBundle f277b = RomStation.m44d();

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Integer bitRate, boolean empty) {
        super.updateItem(bitRate, empty);
        if (empty) {
            setText(null);
        } else {
            setText(String.format(this.f277b.getString("createServerDialog.videoSettings.bitrate.format"), Integer.valueOf(bitRate.intValue() / 1000)));
            pseudoClassStateChanged(f276a, bitRate.intValue() > 5000 && C0058ae.m195a().m211m() != EnumC0059af.PLATINUM);
        }
    }

    /* JADX INFO: renamed from: org.romstation.application.bf$a */
    /* JADX INFO: compiled from: BitRateListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bf$a.class */
    public static class a implements Callback<ListView<Integer>, ListCell<Integer>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Integer> call(ListView<Integer> param) {
            return new C0113bf();
        }
    }
}
