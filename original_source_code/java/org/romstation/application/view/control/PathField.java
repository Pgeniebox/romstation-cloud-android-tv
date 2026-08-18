package org.romstation.application.view.control;

import java.io.IOException;
import java.util.logging.Level;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/PathField.class */
public class PathField extends HBox {

    /* JADX INFO: renamed from: a */
    private final String f736a;

    @FXML
    private TextField textField;

    @FXML
    private Button explorerButton;

    /* JADX INFO: renamed from: b */
    private ObjectProperty<EventHandler<ActionEvent>> f737b;

    public PathField() {
        this(null);
    }

    public PathField(String path) {
        this.f737b = new SimpleObjectProperty();
        this.f736a = path;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/control/pathField.fxml"));
        fxmlLoader.setResources(RomStation.m44d());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    @FXML
    private void initialize() {
        this.textField.setText(this.f736a);
    }

    public TextField getTextField() {
        return this.textField;
    }

    public void clear() {
        this.textField.clear();
    }

    public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
        return this.explorerButton.onActionProperty();
    }

    public EventHandler<ActionEvent> getOnAction() {
        return this.explorerButton.getOnAction();
    }

    public void setOnAction(EventHandler<ActionEvent> onAction) {
        this.explorerButton.setOnAction(onAction);
    }

    public StringProperty pathProperty() {
        return this.textField.textProperty();
    }

    public String getPath() {
        return this.textField.getText();
    }

    public void setPath(String path) {
        this.textField.setText(path);
    }
}
