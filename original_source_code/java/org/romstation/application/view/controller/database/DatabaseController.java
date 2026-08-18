package org.romstation.application.view.controller.database;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.romstation.application.C0167cg;
import org.romstation.application.view.controller.ApplicationView;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/database/DatabaseController.class */
public class DatabaseController {

    @FXML
    private BorderPane root;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
    }

    /* JADX INFO: renamed from: a */
    public Node m1403a() {
        return this.root;
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1404a(C0167cg event) {
        this.root.setVisible(event.m730a() == ApplicationView.DATABASE);
    }
}
