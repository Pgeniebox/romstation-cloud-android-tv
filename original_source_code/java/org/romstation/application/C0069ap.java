package org.romstation.application;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.ap */
/* JADX INFO: compiled from: ExceptionDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/ap.class */
public class C0069ap extends ApplicationFXMLDialog<ButtonType> {

    /* JADX INFO: renamed from: a */
    private final Throwable f146a;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextArea textArea;

    public C0069ap(Throwable throwable) {
        this.f146a = throwable;
        load(getClass().getResource("/fxml/dialog/exceptionDialog.fxml"));
    }

    @FXML
    private void initialize() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Throwable th = null;
        try {
            try {
                this.f146a.printStackTrace(printWriter);
                this.textArea.setText(stringWriter.toString());
                if (printWriter != null) {
                    if (0 != 0) {
                        try {
                            printWriter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        printWriter.close();
                    }
                }
                setResizable(true);
            } catch (Throwable th3) {
                th = th3;
                throw th3;
            }
        } catch (Throwable th4) {
            if (printWriter != null) {
                if (th != null) {
                    try {
                        printWriter.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                } else {
                    printWriter.close();
                }
            }
            throw th4;
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public ButtonType resultConverter(ButtonType buttonType) {
        return buttonType;
    }
}
