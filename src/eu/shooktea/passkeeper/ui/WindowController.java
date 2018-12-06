package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Note;
import eu.shooktea.passkeeper.type.Password;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class WindowController {

    @FXML private TableView<Note> notesTable;
    @FXML private TableView<Password> passwordsTable;

    @FXML
    private void initialize() {
        Type.NOTE.applyTableViewMaker(notesTable);
        Type.PASSWORD.applyTableViewMaker(passwordsTable);
    }

    @FXML
    private void createNewNote() {
        Type.NOTE.showNewWindow();
    }

    @FXML
    private void createNewPassword() {
        Type.PASSWORD.showNewWindow();
    }
}
