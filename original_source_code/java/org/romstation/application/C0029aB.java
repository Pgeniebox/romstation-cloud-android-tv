package org.romstation.application;

import java.util.HashMap;
import java.util.List;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.romstation.application.database.entity.Game;
import org.romstation.application.database.entity.GameFile;
import org.romstation.application.database.entity.GameProfile;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aB */
/* JADX INFO: compiled from: GameProfileChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aB.class */
public class C0029aB extends ApplicationFXMLDialog<GameProfile> {

    /* JADX INFO: renamed from: a */
    private final List<GameProfile> f56a;

    /* JADX INFO: renamed from: b */
    private final HashMap<Game, TreeItem<Object>> f57b = new HashMap<>();

    /* JADX INFO: renamed from: c */
    private final HashMap<GameFile, TreeItem<Object>> f58c = new HashMap<>();

    @FXML
    private DialogPane root;

    @FXML
    private TreeView<Object> treeView;

    public C0029aB(List<GameProfile> gameProfiles) {
        this.f56a = gameProfiles;
        setResizable(true);
        load(getClass().getResource("/fxml/dialog/choice/gameProfileChoiceDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.root.lookupButton(ButtonType.OK).setDisable(true);
        this.treeView.setCellFactory(param -> {
            return new a();
        });
        this.treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.root.lookupButton(ButtonType.OK).setDisable(newValue == null || !(newValue.getValue() instanceof GameProfile));
        });
        this.treeView.setRoot(new TreeItem());
        this.f56a.forEach(gameProfile -> {
            TreeItem<Object> gameFileTreeItem = this.f58c.computeIfAbsent(gameProfile.getGameFile(), gameFile -> {
                TreeItem<Object> gameTreeItem = this.f57b.computeIfAbsent(gameFile.getGame(), game -> {
                    TreeItem<Object> treeItem = new TreeItem<>(game);
                    treeItem.setExpanded(true);
                    this.treeView.getRoot().getChildren().add(treeItem);
                    return treeItem;
                });
                TreeItem<Object> treeItem = new TreeItem<>(gameFile);
                gameTreeItem.getChildren().add(treeItem);
                return treeItem;
            });
            TreeItem<Object> treeItem = new TreeItem<>(gameProfile);
            gameFileTreeItem.getChildren().add(treeItem);
        });
    }

    @FXML
    private void expand() {
        this.f57b.forEach((emulator, treeItem) -> {
            treeItem.setExpanded(true);
        });
        this.f58c.forEach((emulator2, treeItem2) -> {
            treeItem2.setExpanded(true);
        });
    }

    @FXML
    private void collapse() {
        this.f57b.forEach((emulator, treeItem) -> {
            treeItem.setExpanded(false);
        });
        this.f58c.forEach((emulator2, treeItem2) -> {
            treeItem2.setExpanded(false);
        });
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        TreeItem<Object> selectedItem;
        if (event.getCode() == KeyCode.ENTER && (selectedItem = (TreeItem) this.treeView.getSelectionModel().getSelectedItem()) != null && (selectedItem.getValue() instanceof GameProfile)) {
            setResult((GameProfile) selectedItem.getValue());
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        TreeItem<Object> selectedItem;
        if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && (selectedItem = (TreeItem) this.treeView.getSelectionModel().getSelectedItem()) != null && (selectedItem.getValue() instanceof GameProfile)) {
            setResult((GameProfile) selectedItem.getValue());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public GameProfile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return (GameProfile) ((TreeItem) this.treeView.getSelectionModel().getSelectedItem()).getValue();
        }
        return null;
    }

    /* JADX INFO: renamed from: org.romstation.application.aB$a */
    /* JADX INFO: compiled from: GameProfileChoiceDialog.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aB$a.class */
    public static class a extends TreeCell<Object> {

        /* JADX INFO: renamed from: a */
        private static PseudoClass f59a = PseudoClass.getPseudoClass("game");

        /* JADX INFO: renamed from: b */
        private static PseudoClass f60b = PseudoClass.getPseudoClass("file");

        /* JADX INFO: renamed from: c */
        private static PseudoClass f61c = PseudoClass.getPseudoClass("profile");

        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                return;
            }
            if (item instanceof Game) {
                pseudoClassStateChanged(f59a, true);
                pseudoClassStateChanged(f60b, false);
                pseudoClassStateChanged(f61c, false);
                setText(((Game) item).getTitle());
                return;
            }
            if (item instanceof GameFile) {
                pseudoClassStateChanged(f59a, false);
                pseudoClassStateChanged(f60b, true);
                pseudoClassStateChanged(f61c, false);
                setText(((GameFile) item).getName());
                return;
            }
            if (item instanceof GameProfile) {
                pseudoClassStateChanged(f59a, false);
                pseudoClassStateChanged(f60b, false);
                pseudoClassStateChanged(f61c, true);
                setText(((GameProfile) item).getName());
            }
        }
    }
}
