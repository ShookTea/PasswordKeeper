package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Note;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class WindowController {

    @FXML
    private TableView<Note> notesTable;

    @FXML
    private void initialize() {
        Type.NOTE.applyTableViewMaker(notesTable);
    }

    @FXML
    private void createNewNote() {
        Type.NOTE.showNewWindow();
    }
}
