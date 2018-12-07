package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Password;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PasswordController extends AbstractController {

    @FXML private TextField passwordName;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField visiblePassword;
    @FXML private TextField url;
    @FXML private HBox passwordBox;

    @FXML
    private void initialize() {
        if (Storage.isCurrentlyEdited(Type.PASSWORD)) {
            Password p = Storage.getCurrentlyEdited(Type.PASSWORD);
            passwordName.setText(p.getName());
            username.setText(p.getLogin());
            password.setText(p.getPassword());
            url.setText(p.getUrl());
        }
        passwordBox.getChildren().remove(visiblePassword);
        visiblePassword.layoutXProperty().bind(password.layoutXProperty());
        visiblePassword.layoutYProperty().bind(password.layoutYProperty());
        visiblePassword.visibleProperty().bind(password.visibleProperty().not());
        visiblePassword.textProperty().bind(password.textProperty());
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
