package org.romstation.application.view.control;

import java.io.IOException;
import java.util.logging.Level;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.romstation.application.RomStation;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/SearchField.class */
public class SearchField extends HBox {

    @FXML
    private TextField textField;

    @FXML
    private Button clearButton;

    public SearchField() {
        this(null);
    }

    public SearchField(String text) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/control/searchField.fxml"));
        fxmlLoader.setResources(RomStation.m44d());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.textField.setText(text);
        } catch (IOException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
    }

    public TextField getTextField() {
        return this.textField;
    }

    @FXML
    public void clear() {
        this.textField.clear();
    }

    public StringProperty textProperty() {
        return this.textField.textProperty();
    }

    public String getText() {
        return this.textField.getText();
    }

    public void setText(String text) {
        this.textField.setText(text);
    }

    public String getPromptText() {
        return this.textField.getPromptText();
    }

    public void setPromptText(String text) {
        this.textField.setPromptText(text);
    }

    @FXML
    private void initialize() {
        this.clearButton.visibleProperty().bind(this.textField.textProperty().isNotEmpty());
    }
}
