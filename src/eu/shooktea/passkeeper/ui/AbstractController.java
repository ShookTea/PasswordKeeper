package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Cipherable;
import eu.shooktea.passkeeper.Main;
import eu.shooktea.passkeeper.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class AbstractController {

    @FXML
    protected void goBack() {
        Main.showWindow("Window", "Password Keeper");
    }

    @FXML
    protected void deleteElement() {
        String typeName = getTypeName();
        String elementName = getElementName();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Deleting " + typeName);
        alert.setContentText("Do you really want to delete " + typeName + " '" + elementName + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Storage.deleteCipherableElement();
            Main.showWindow("Window");
        }
    }

    protected void saveElement(Cipherable cipherable) {
        Storage.saveCipherableElement(cipherable);
        goBack();
    }

    protected abstract String getTypeName();
    protected abstract String getElementName();
}
