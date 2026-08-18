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
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.EmulatorFile;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aA */
/* JADX INFO: compiled from: EmulatorProfileChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aA.class */
public class C0028aA extends ApplicationFXMLDialog<EmulatorProfile> {

    /* JADX INFO: renamed from: a */
    private final List<EmulatorProfile> f49a;

    /* JADX INFO: renamed from: b */
    private final EmulatorProfile f50b;

    /* JADX INFO: renamed from: c */
    private final HashMap<Emulator, TreeItem<Object>> f51c;

    /* JADX INFO: renamed from: d */
    private final HashMap<EmulatorFile, TreeItem<Object>> f52d;

    @FXML
    private DialogPane root;

    @FXML
    private TreeView<Object> treeView;

    public C0028aA(List<EmulatorProfile> emulatorProfiles) {
        this(emulatorProfiles, null);
    }

    public C0028aA(List<EmulatorProfile> emulatorProfiles, EmulatorProfile selection) {
        this.f51c = new HashMap<>();
        this.f52d = new HashMap<>();
        this.f49a = emulatorProfiles;
        this.f50b = selection;
        setResizable(true);
        load(getClass().getResource("/fxml/dialog/choice/emulatorProfileChoiceDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.root.lookupButton(ButtonType.OK).setDisable(true);
        this.treeView.setCellFactory(param -> {
            return new a();
        });
        this.treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.root.lookupButton(ButtonType.OK).setDisable(newValue == null || !(newValue.getValue() instanceof EmulatorProfile));
        });
        this.treeView.setRoot(new TreeItem());
        this.f49a.forEach(emulatorProfile -> {
            TreeItem<Object> emulatorFileTreeItem = this.f52d.computeIfAbsent(emulatorProfile.getEmulatorFile(), emulatorFile -> {
                TreeItem<Object> emulatorTreeItem = this.f51c.computeIfAbsent(emulatorFile.getEmulator(), emulator -> {
                    TreeItem<Object> treeItem = new TreeItem<>(emulator);
                    treeItem.setExpanded(true);
                    this.treeView.getRoot().getChildren().add(treeItem);
                    return treeItem;
                });
                TreeItem<Object> treeItem = new TreeItem<>(emulatorFile);
                emulatorTreeItem.getChildren().add(treeItem);
                return treeItem;
            });
            TreeItem<Object> treeItem = new TreeItem<>(emulatorProfile);
            emulatorFileTreeItem.getChildren().add(treeItem);
            if (emulatorProfile.equals(this.f50b)) {
                this.treeView.getSelectionModel().select(treeItem);
                this.treeView.scrollTo(this.treeView.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @FXML
    private void expand() {
        this.f51c.forEach((emulator, treeItem) -> {
            treeItem.setExpanded(true);
        });
        this.f52d.forEach((emulator2, treeItem2) -> {
            treeItem2.setExpanded(true);
        });
    }

    @FXML
    private void collapse() {
        this.f51c.forEach((emulator, treeItem) -> {
            treeItem.setExpanded(false);
        });
        this.f52d.forEach((emulator2, treeItem2) -> {
            treeItem2.setExpanded(false);
        });
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        TreeItem<Object> selectedItem;
        if (event.getCode() == KeyCode.ENTER && (selectedItem = (TreeItem) this.treeView.getSelectionModel().getSelectedItem()) != null && (selectedItem.getValue() instanceof EmulatorProfile)) {
            setResult((EmulatorProfile) selectedItem.getValue());
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        TreeItem<Object> selectedItem;
        if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && (selectedItem = (TreeItem) this.treeView.getSelectionModel().getSelectedItem()) != null && (selectedItem.getValue() instanceof EmulatorProfile)) {
            setResult((EmulatorProfile) selectedItem.getValue());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public EmulatorProfile resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return (EmulatorProfile) ((TreeItem) this.treeView.getSelectionModel().getSelectedItem()).getValue();
        }
        return null;
    }

    /* JADX INFO: renamed from: org.romstation.application.aA$a */
    /* JADX INFO: compiled from: EmulatorProfileChoiceDialog.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aA$a.class */
    public static class a extends TreeCell<Object> {

        /* JADX INFO: renamed from: a */
        private static PseudoClass f53a = PseudoClass.getPseudoClass("emulator");

        /* JADX INFO: renamed from: b */
        private static PseudoClass f54b = PseudoClass.getPseudoClass("file");

        /* JADX INFO: renamed from: c */
        private static PseudoClass f55c = PseudoClass.getPseudoClass("profile");

        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                return;
            }
            if (item instanceof Emulator) {
                pseudoClassStateChanged(f53a, true);
                pseudoClassStateChanged(f54b, false);
                pseudoClassStateChanged(f55c, false);
                setText(((Emulator) item).getName());
                return;
            }
            if (item instanceof EmulatorFile) {
                pseudoClassStateChanged(f53a, false);
                pseudoClassStateChanged(f54b, true);
                pseudoClassStateChanged(f55c, false);
                setText(((EmulatorFile) item).getName());
                return;
            }
            if (item instanceof EmulatorProfile) {
                pseudoClassStateChanged(f53a, false);
                pseudoClassStateChanged(f54b, false);
                pseudoClassStateChanged(f55c, true);
                setText(((EmulatorProfile) item).getName());
            }
        }
    }
}
