package org.romstation.application;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import org.romstation.application.network.C0217b;
import org.romstation.application.task.C0240h;
import org.romstation.application.view.control.ApplicationFXMLDialog;

/* JADX INFO: renamed from: org.romstation.application.az */
/* JADX INFO: compiled from: EmulatorFileDownloadConfirmationDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/az.class */
public class C0080az extends ApplicationFXMLDialog<ButtonType> {

    /* JADX INFO: renamed from: a */
    private final C0240h f157a;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ImageView emulatorImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private FlowPane systemsFlowPane;

    @FXML
    private Label messageLabel;

    public C0080az(C0240h context) {
        this.f157a = context;
        load(getClass().getResource("/fxml/dialog/choice/emulatorFileDownloadConfirmationDialog.fxml"));
    }

    @FXML
    private void initialize() {
        this.emulatorImageView.setImage(new Image(C0217b.m961b() + this.f157a.m1021c().getDocumentElement().getAttribute("image"), false));
        this.nameLabel.setText(this.f157a.m1022d().getDocumentElement().getAttribute("name"));
        this.f157a.m1020b().forEach(jsonElement -> {
            JsonObject systemJsonObject = jsonElement.getAsJsonObject();
            ImageView imageView = new ImageView(new Image(C0217b.m961b() + systemJsonObject.get("image").getAsString(), false));
            Tooltip.install(imageView, new Tooltip(systemJsonObject.get("name").getAsString()));
            this.systemsFlowPane.getChildren().add(imageView);
        });
        this.messageLabel.setText(String.format(getResources().getString("emulatorFileDownloadConfirmationDialog.content.text"), this.f157a.m1022d().getDocumentElement().getAttribute("name")));
        this.dialogPane.getButtonTypes().add(new ButtonType(getResources().getString("emulatorFileDownloadConfirmationDialog.button.download"), ButtonBar.ButtonData.YES));
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
