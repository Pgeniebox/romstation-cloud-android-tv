package org.romstation.application.view.control.cell;

import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.romstation.application.RomStation;
import org.romstation.application.view.controller.ApplicationView;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/ApplicationViewListCell.class */
public class ApplicationViewListCell extends ListCell<ApplicationView> {
    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(ApplicationView item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            return;
        }
        ResourceBundle resources = RomStation.m44d();
        switch (item) {
            case BROWSER:
                setText(resources.getString("application.view.listCell.browser"));
                break;
            case LIBRARY:
                setText(resources.getString("application.view.listCell.library"));
                break;
            case EMULATORS:
                setText(resources.getString("application.view.listCell.emulators"));
                break;
            case DATABASE:
                setText(resources.getString("application.view.listCell.database"));
                break;
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/ApplicationViewListCell$Factory.class */
    public static class Factory implements Callback<ListView<ApplicationView>, ListCell<ApplicationView>> {
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public ListCell<ApplicationView> call(ListView<ApplicationView> param) {
            return new ApplicationViewListCell();
        }
    }
}
