package org.romstation.application;

import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/* JADX INFO: renamed from: org.romstation.application.bo */
/* JADX INFO: compiled from: ResolutionListCell.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bo.class */
public class C0122bo extends ListCell<EnumC0121bn> {

    /* JADX INFO: renamed from: a */
    private static final PseudoClass f292a = PseudoClass.getPseudoClass("unavailable");

    /* JADX INFO: renamed from: b */
    private final ResourceBundle f293b = RomStation.m44d();

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(EnumC0121bn resolution, boolean empty) {
        super.updateItem(resolution, empty);
        if (empty) {
            setText(null);
            return;
        }
        switch (resolution) {
            case NATIVE:
                setText(this.f293b.getString("createServerDialog.videoSettings.resolution.native"));
                break;
            case HD:
                setText(this.f293b.getString("createServerDialog.videoSettings.resolution.hd"));
                break;
        }
        pseudoClassStateChanged(f292a, resolution == EnumC0121bn.HD && C0058ae.m195a().m211m() != EnumC0059af.PLATINUM);
    }

    /* JADX INFO: renamed from: org.romstation.application.bo$a */
    /* JADX INFO: compiled from: ResolutionListCell.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bo$a.class */
    public static class a implements Callback<ListView<EnumC0121bn>, ListCell<EnumC0121bn>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<EnumC0121bn> call(ListView<EnumC0121bn> param) {
            return new C0122bo();
        }
    }
}
