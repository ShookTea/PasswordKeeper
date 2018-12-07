package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Password;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class PasswordController extends AbstractController {

    @FXML private TextField passwordName;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField visiblePassword;
    @FXML private TextField url;
    @FXML private HBox passwordBox;
    @FXML private ToggleButton showPassword;

    @FXML
    private void initialize() {
        if (Storage.isCurrentlyEdited(Type.PASSWORD)) {
            Password p = Storage.getCurrentlyEdited(Type.PASSWORD);
            passwordName.setText(p.getName());
            username.setText(p.getLogin());
            password.setText(p.getPassword());
            url.setText(p.getUrl());
        }
        visiblePassword.textProperty().bindBidirectional(password.textProperty());

        passwordBox.getChildren().remove(visiblePassword);
        showPassword.selectedProperty().addListener((observable, oldValue, newValue) -> {
            passwordBox.getChildren().set(0, newValue ? visiblePassword : password);
        });
    }

    @FXML
    private void savePassword() {
        this.saveElement(new Password(passwordName.getText(), url.getText(), username.getText(), password.getText()));
    }

    @Override
    protected String getTypeName() {
        return "password";
    }

    @Override
    protected String getElementName() {
        return passwordName.getText();
    }
}
