package org.romstation.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.FileChooser;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.view.control.PathField;

/* JADX INFO: renamed from: org.romstation.application.bP */
/* JADX INFO: compiled from: GameUploadFileChooserDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bP.class */
public class C0097bP extends ApplicationFXMLDialog<Path> {

    @FXML
    private DialogPane root;

    @FXML
    private PathField pathField;

    public C0097bP() {
        load(getClass().getResource("/fxml/dialog/upload/gameUploadFileChooserDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.pathField.getTextField().setEditable(false);
        Button okButton = this.root.lookupButton(ButtonType.OK);
        okButton.disableProperty().bind(this.pathField.pathProperty().isNull());
    }

    @FXML
    private void selectPath(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(getResources().getString("gameUploadFileChooserDialog.extension.description"), new String[]{"*.zip"}));
        if (this.pathField.getPath() != null) {
            Path path = Paths.get(this.pathField.getPath(), new String[0]);
            if (Files.exists(path, new LinkOption[0])) {
                fileChooser.setInitialDirectory(path.getParent().toFile());
                fileChooser.setInitialFileName(path.toString());
            }
        }
        File file = fileChooser.showOpenDialog(getDialogPane().getScene().getWindow());
        if (file != null) {
            if (m468a(file)) {
                this.pathField.setPath(file.toString());
            } else {
                new ApplicationAlert(getResources().getString("gameUploadFileChooserDialog.error.header"), getResources().getString("gameUploadFileChooserDialog.error.contentType"), Alert.AlertType.ERROR).showAndWait();
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 14, instructions: 22 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:16:0x0037
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1478)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX INFO: renamed from: a */
    private boolean m468a(java.io.File r5) {
        /*
            r4 = this;
            java.util.zip.ZipFile r0 = new java.util.zip.ZipFile     // Catch: java.io.IOException -> L5a
            r1 = r0
            r2 = r5
            r1.<init>(r2)     // Catch: java.io.IOException -> L5a
            r6 = r0
            r0 = 0
            r7 = r0
            r0 = 1
            r8 = r0
            r0 = r6
            if (r0 == 0) goto L2c
            r0 = r7
            if (r0 == 0) goto L28
            r0 = r6
            r0.close()     // Catch: java.lang.Throwable -> L1d java.io.IOException -> L5a
            goto L2c
        L1d:
            r9 = move-exception
            r0 = r7
            r1 = r9
            r0.addSuppressed(r1)     // Catch: java.io.IOException -> L5a
            goto L2c
        L28:
            r0 = r6
            r0.close()     // Catch: java.io.IOException -> L5a
        L2c:
            r0 = r8
            return r0
        L2f:
            r8 = move-exception
            r0 = r8
            r7 = r0
            r0 = r8
            throw r0     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L5a
        L37:
            r10 = move-exception
            r0 = r6
            if (r0 == 0) goto L57
            r0 = r7
            if (r0 == 0) goto L53
            r0 = r6
            r0.close()     // Catch: java.lang.Throwable -> L48 java.io.IOException -> L5a
            goto L57
        L48:
            r11 = move-exception
            r0 = r7
            r1 = r11
            r0.addSuppressed(r1)     // Catch: java.io.IOException -> L5a
            goto L57
        L53:
            r0 = r6
            r0.close()     // Catch: java.io.IOException -> L5a
        L57:
            r0 = r10
            throw r0     // Catch: java.io.IOException -> L5a
        L5a:
            r6 = move-exception
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.C0097bP.m468a(java.io.File):boolean");
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Path resultConverter(ButtonType buttonType) {
        if (buttonType == ButtonType.OK && this.pathField.getPath() != null) {
            return Paths.get(this.pathField.getPath(), new String[0]);
        }
        return null;
    }
}
