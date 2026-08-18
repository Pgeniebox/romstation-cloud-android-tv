package org.romstation.application;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.aL */
/* JADX INFO: compiled from: ZipEntryChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aL.class */
public class C0039aL extends ApplicationFXMLDialog<Path> {

    /* JADX INFO: renamed from: a */
    private final Path f65a;

    /* JADX INFO: renamed from: b */
    private final HashMap<String, TreeItem<Path>> f66b = new HashMap<>();

    @FXML
    private DialogPane root;

    @FXML
    private Label pathLabel;

    @FXML
    private TreeView<Path> treeView;

    public C0039aL(String headerText, Path path) {
        this.f65a = path;
        load(getClass().getResource("/fxml/dialog/choice/zipEntryChoiceDialog.fxml"));
        setHeaderText(headerText);
    }

    @FXML
    private void initialize() {
        m131a();
        this.treeView.setCellFactory(treeItemValueTreeView -> {
            return new a();
        });
        this.treeView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldItem, newItem) -> {
            if (newItem == null) {
                this.pathLabel.setText((String) null);
            } else {
                this.pathLabel.setText(((Path) newItem.getValue()).toString());
            }
            this.root.lookupButton(ButtonType.OK).setDisable(newItem == null || ((Path) newItem.getValue()).toString().endsWith("/"));
        });
        this.root.lookupButton(ButtonType.OK).setDisable(true);
        setResizable(true);
    }

    /* JADX INFO: renamed from: a */
    private void m131a() {
        try {
            FileSystem zipFileSystem = FileSystems.newFileSystem(this.f65a, (ClassLoader) null);
            Throwable th = null;
            try {
                Files.walk(zipFileSystem.getPath("/", new String[0]), new FileVisitOption[0]).forEach(path -> {
                    TreeItem<Path> treeItem = new TreeItem<>(path);
                    if (path.getParent() == null) {
                        this.treeView.setRoot(treeItem);
                    } else if (path.getParent().toString().equals("/")) {
                        this.f66b.get(path.getParent().toString()).getChildren().add(treeItem);
                    } else {
                        this.f66b.get(path.getParent().toString() + "/").getChildren().add(treeItem);
                    }
                    this.f66b.put(path.toString(), treeItem);
                });
                if (zipFileSystem != null) {
                    if (0 != 0) {
                        try {
                            zipFileSystem.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        zipFileSystem.close();
                    }
                }
            } catch (Throwable th3) {
                if (zipFileSystem != null) {
                    if (0 != 0) {
                        try {
                            zipFileSystem.close();
                        } catch (Throwable th4) {
                            th.addSuppressed(th4);
                        }
                    } else {
                        zipFileSystem.close();
                    }
                }
                throw th3;
            }
        } catch (Exception exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
    }

    @FXML
    private void expand() {
        this.f66b.forEach((key, value) -> {
            value.setExpanded(true);
        });
    }

    @FXML
    private void collapse() {
        this.f66b.forEach((key, value) -> {
            if (value != this.treeView.getRoot()) {
                value.setExpanded(false);
            }
        });
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        TreeItem<Path> selectedItem;
        if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && (selectedItem = (TreeItem) this.treeView.getSelectionModel().getSelectedItem()) != null && !((Path) selectedItem.getValue()).toString().endsWith("/")) {
            setResult(selectedItem.getValue());
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Path resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return (Path) ((TreeItem) this.treeView.getSelectionModel().getSelectedItem()).getValue();
        }
        return null;
    }

    /* JADX INFO: renamed from: org.romstation.application.aL$a */
    /* JADX INFO: compiled from: ZipEntryChoiceDialog.java */
    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aL$a.class */
    private static class a extends TreeCell<Path> {

        /* JADX INFO: renamed from: a */
        private static PseudoClass f67a = PseudoClass.getPseudoClass("directory");

        /* JADX INFO: renamed from: b */
        private static PseudoClass f68b = PseudoClass.getPseudoClass("file");

        private a() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void updateItem(Path item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                return;
            }
            if (item.toString().endsWith("/")) {
                pseudoClassStateChanged(f67a, true);
                pseudoClassStateChanged(f68b, false);
            } else {
                pseudoClassStateChanged(f67a, false);
                pseudoClassStateChanged(f68b, true);
            }
            Path filename = item.getFileName();
            if (filename == null) {
                setText("/");
            } else {
                setText(item.getFileName().toString());
            }
        }
    }
}
