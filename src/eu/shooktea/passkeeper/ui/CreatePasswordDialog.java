package eu.shooktea.passkeeper.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CreatePasswordDialog extends Dialog<char[][]> {
    private PasswordField passwordFieldA;
    private PasswordField passwordFieldB;

    public CreatePasswordDialog() {
        setTitle("Set password");
        setHeaderText("Please create a new password.");
        setWidth(300);

        ButtonType passwordButtonType = new ButtonType("Encrypt", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(passwordButtonType, ButtonType.CANCEL);

        passwordFieldA = new PasswordField();
        passwordFieldA.setPromptText("Password");

        passwordFieldB = new PasswordField();
        passwordFieldB.setPromptText("Repeat password");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(passwordFieldA, passwordFieldB);
        vBox.setPadding(new Insets(20));

        VBox.setVgrow(passwordFieldA, Priority.ALWAYS);
        VBox.setVgrow(passwordFieldB, Priority.ALWAYS);

        getDialogPane().setContent(vBox);

        Platform.runLater(() -> passwordFieldA.requestFocus());

        setResultConverter(dialogButton -> {
            if (dialogButton.getButtonData() == passwordButtonType.getButtonData()) {
                char[] a = passwordFieldA.getText().toCharArray();
                char[] b = passwordFieldB.getText().toCharArray();
                return new char[][]{a, b};
            }
            return null;
        });
    }

    public void error(String message) {
        passwordFieldA.setText("");
        passwordFieldB.setText("");
        setHeaderText(message);
    }
}
