package org.romstation.application;

import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bk */
/* JADX INFO: compiled from: FrameRateListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bk.class */
public class C0118bk extends ListCell<Integer> {

    /* JADX INFO: renamed from: a */
    private static final PseudoClass f286a = PseudoClass.getPseudoClass("unavailable");

    /* JADX INFO: renamed from: b */
    private final ResourceBundle f287b = RomStation.m44d();

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Integer frameRate, boolean empty) {
        super.updateItem(frameRate, empty);
        if (empty) {
            setText(null);
        } else {
            setText(String.format(this.f287b.getString("createServerDialog.videoSettings.framerate.format"), frameRate));
            pseudoClassStateChanged(f286a, frameRate.intValue() > 30 && C0058ae.m195a().m211m() != EnumC0059af.PLATINUM);
        }
    }

    /* JADX INFO: renamed from: org.romstation.application.bk$a */
    /* JADX INFO: compiled from: FrameRateListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bk$a.class */
    public static class a implements Callback<ListView<Integer>, ListCell<Integer>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<Integer> call(ListView<Integer> param) {
            return new C0118bk();
        }
    }
}
