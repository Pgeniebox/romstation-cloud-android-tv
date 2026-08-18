package org.romstation.application.view.control;

import com.teamdev.jxbrowser.browser.callback.ShowContextMenuCallback;
import com.teamdev.jxbrowser.frame.EditorCommand;
import com.teamdev.jxbrowser.media.MediaType;
import com.teamdev.jxbrowser.ui.Point;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.romstation.application.C0152cR;
import org.romstation.application.RomStation;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/BrowserContextMenu.class */
public class BrowserContextMenu implements ShowContextMenuCallback {

    /* JADX INFO: renamed from: b */
    private final BrowserView f734b;

    /* JADX INFO: renamed from: a */
    private final ContextMenu f733a = new ContextMenu();

    /* JADX INFO: renamed from: c */
    private final ResourceBundle f735c = RomStation.m44d();

    public BrowserContextMenu(BrowserView browserView) {
        this.f734b = browserView;
    }

    /* JADX INFO: renamed from: on, reason: merged with bridge method [inline-methods] */
    public void m1174on(ShowContextMenuCallback.Params params, ShowContextMenuCallback.Action action) {
        Platform.runLater(() -> {
            this.f733a.hide();
            this.f733a.getItems().clear();
            updateContextMenu(params);
            if (!this.f733a.getItems().isEmpty()) {
                Point location = params.location();
                Point2D point2D = this.f734b.localToScreen(location.x(), location.y());
                this.f733a.show(this.f734b, point2D.getX(), point2D.getY());
            }
            action.close();
        });
    }

    private void updateContextMenu(ShowContextMenuCallback.Params params) {
        params.browser().mainFrame().ifPresent(frame -> {
            if (!params.spellCheckMenu().misspelledWord().isEmpty()) {
                if (!params.spellCheckMenu().dictionarySuggestions().isEmpty()) {
                    params.spellCheckMenu().dictionarySuggestions().forEach(suggestion -> {
                        MenuItem menuItem = new MenuItem(suggestion);
                        menuItem.getStyleClass().addAll(new String[]{"dictionary", "suggestion"});
                        menuItem.setOnAction(actionEvent -> {
                            params.browser().replaceMisspelledWord(suggestion);
                        });
                        this.f733a.getItems().add(menuItem);
                    });
                }
                MenuItem addDictionaryMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.dictionary.add"));
                addDictionaryMenuItem.setOnAction(actionEvent -> {
                    params.browser().engine().spellChecker().customDictionary().add(params.spellCheckMenu().misspelledWord());
                });
                this.f733a.getItems().add(addDictionaryMenuItem);
            }
            if (frame.isCommandEnabled(EditorCommand.Name.INSERT_TEXT)) {
                if (!this.f733a.getItems().isEmpty()) {
                    this.f733a.getItems().add(new SeparatorMenuItem());
                }
                MenuItem undoMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.text.undo"));
                undoMenuItem.setOnAction(actionEvent2 -> {
                    frame.execute(EditorCommand.undo());
                });
                undoMenuItem.setDisable(!frame.isCommandEnabled(EditorCommand.Name.UNDO));
                this.f733a.getItems().addAll(new MenuItem[]{undoMenuItem, new SeparatorMenuItem()});
                MenuItem cutMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.text.cut"));
                cutMenuItem.setOnAction(actionEvent3 -> {
                    frame.execute(EditorCommand.cut());
                });
                cutMenuItem.setDisable(!frame.isCommandEnabled(EditorCommand.Name.CUT));
                this.f733a.getItems().add(cutMenuItem);
                MenuItem copyMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.text.copy"));
                copyMenuItem.setOnAction(actionEvent4 -> {
                    frame.execute(EditorCommand.copy());
                });
                copyMenuItem.setDisable(!frame.isCommandEnabled(EditorCommand.Name.COPY));
                this.f733a.getItems().add(copyMenuItem);
                MenuItem pasteMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.text.paste"));
                pasteMenuItem.setOnAction(actionEvent5 -> {
                    frame.execute(EditorCommand.paste());
                });
                pasteMenuItem.setDisable(!frame.isCommandEnabled(EditorCommand.Name.PASTE));
                this.f733a.getItems().add(pasteMenuItem);
                MenuItem deleteMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.text.delete"));
                deleteMenuItem.setOnAction(actionEvent6 -> {
                    frame.execute(EditorCommand.delete());
                });
                deleteMenuItem.setDisable(!frame.isCommandEnabled(EditorCommand.Name.DELETE));
                this.f733a.getItems().addAll(new MenuItem[]{deleteMenuItem, new SeparatorMenuItem()});
                MenuItem selectAllMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.text.selectAll"));
                selectAllMenuItem.setOnAction(actionEvent7 -> {
                    frame.execute(EditorCommand.selectAll());
                });
                this.f733a.getItems().addAll(new MenuItem[]{selectAllMenuItem});
            } else if (frame.isCommandEnabled(EditorCommand.Name.COPY)) {
                MenuItem copyMenuItem2 = new MenuItem(this.f735c.getString("browser.contextMenu.text.copy"));
                copyMenuItem2.setOnAction(actionEvent8 -> {
                    frame.execute(EditorCommand.copy());
                });
                this.f733a.getItems().add(copyMenuItem2);
                MenuItem selectAllMenuItem2 = new MenuItem(this.f735c.getString("browser.contextMenu.text.selectAll"));
                selectAllMenuItem2.setOnAction(actionEvent9 -> {
                    frame.execute(EditorCommand.selectAll());
                });
                this.f733a.getItems().addAll(new MenuItem[]{selectAllMenuItem2});
            }
            if (!params.linkUrl().isEmpty()) {
                if (!this.f733a.getItems().isEmpty()) {
                    this.f733a.getItems().add(new SeparatorMenuItem());
                }
                MenuItem openNewTabMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.link.openNewTab"));
                openNewTabMenuItem.setOnAction(actionEvent10 -> {
                    RomStationController.f786a.post(new C0152cR(params.linkUrl()));
                });
                this.f733a.getItems().add(openNewTabMenuItem);
                MenuItem openNewWindowMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.link.openNewWindow"));
                openNewWindowMenuItem.setOnAction(actionEvent11 -> {
                    try {
                        Desktop.getDesktop().browse(new URI(params.linkUrl()));
                    } catch (IOException | URISyntaxException exception) {
                        RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                    }
                });
                this.f733a.getItems().add(openNewWindowMenuItem);
                MenuItem copyLocationMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.link.copyLocation"));
                copyLocationMenuItem.setOnAction(actionEvent12 -> {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(params.linkUrl());
                    clipboardContent.putUrl(params.linkUrl());
                    clipboard.setContent(clipboardContent);
                });
                this.f733a.getItems().add(copyLocationMenuItem);
            }
            if (params.mediaType() == MediaType.IMAGE) {
                if (!this.f733a.getItems().isEmpty()) {
                    this.f733a.getItems().add(new SeparatorMenuItem());
                }
                MenuItem showMenuItem = new MenuItem(this.f735c.getString("browser.contextMenu.image.show"));
                showMenuItem.setOnAction(actionEvent13 -> {
                    params.browser().navigation().loadUrl(params.srcUrl());
                });
                this.f733a.getItems().add(showMenuItem);
                MenuItem copyMenuItem3 = new MenuItem(this.f735c.getString("browser.contextMenu.image.copy"));
                copyMenuItem3.setOnAction(actionEvent14 -> {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putImage(new Image(params.srcUrl()));
                    clipboard.setContent(clipboardContent);
                });
                this.f733a.getItems().add(copyMenuItem3);
                MenuItem copyLocationMenuItem2 = new MenuItem(this.f735c.getString("browser.contextMenu.image.copyLocation"));
                copyLocationMenuItem2.setOnAction(actionEvent15 -> {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(params.srcUrl());
                    clipboardContent.putUrl(params.srcUrl());
                    clipboard.setContent(clipboardContent);
                });
                this.f733a.getItems().add(copyLocationMenuItem2);
            }
        });
    }

    public ContextMenu getContextMenu() {
        return this.f733a;
    }

    public BrowserView getBrowserView() {
        return this.f734b;
    }
}
