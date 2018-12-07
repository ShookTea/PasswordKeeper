package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Main;
import javafx.fxml.FXML;

public abstract class AbstractController {

    @FXML
    protected void goBack() {
        Main.showWindow("Window", "Password Keeper");
    }
}
