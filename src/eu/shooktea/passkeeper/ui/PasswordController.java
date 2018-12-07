package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.type.Password;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PasswordController extends AbstractController {

    @FXML private TextField passwordName;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField url;

    @FXML
    private void savePassword() {
        this.saveElement(new Password(passwordName.getText(), username.getText(), password.getText(), url.getText()));
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
