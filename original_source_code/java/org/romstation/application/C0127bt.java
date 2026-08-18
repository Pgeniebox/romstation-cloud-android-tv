package org.romstation.application;

import com.google.common.eventbus.EventBus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/* JADX INFO: renamed from: org.romstation.application.bt */
/* JADX INFO: compiled from: BannedMemberContextMenuController.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bt.class */
public class C0127bt {

    /* JADX INFO: renamed from: a */
    private final EventBus f299a;

    /* JADX INFO: renamed from: b */
    private C0131bx f300b;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem unbanMenuItem;

    @FXML
    private MenuItem profileMenuItem;

    public C0127bt(EventBus eventBus) {
        this.f299a = eventBus;
    }

    /* JADX INFO: renamed from: a */
    public ContextMenu m620a() {
        return this.contextMenu;
    }

    @FXML
    private void initialize() {
    }

    /* JADX INFO: renamed from: a */
    public void m621a(C0131bx member) {
        this.f300b = member;
        if (member != null) {
            this.profileMenuItem.setDisable(member.m628a());
        }
    }

    @FXML
    private void unbanPlayer(ActionEvent event) {
        this.f299a.post(new C0147cM(this.f300b));
    }

    @FXML
    private void showMemberProfile(ActionEvent event) {
        this.f299a.post(new C0146cL(this.f300b));
    }
}
