package eu.shooktea.passkeeper.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PasswordController extends AbstractController {

    @FXML private TextField passwordName;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private TextField url;

    @FXML
    private void savePassword() {
        this.goBack();
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
