package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Main;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Note;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NoteController {
    @FXML private TextField noteTitle;
    @FXML private TextArea noteText;

    @FXML
    private void initialize() {
        if (Storage.isCurrentlyEdited(Type.NOTE)) {
            Note n = Storage.getCurrentlyEdited(Type.NOTE);
            noteTitle.setText(n.getTitle());
            noteText.setText(n.getText());
        }
    }

    @FXML
    private void goBack() {
        Main.showWindow("Window");
    }

    @FXML
    private void saveNote() {
        Note note = new Note(noteTitle.getText(), noteText.getText());
        Storage.saveCipherableElement(note);
        Main.showWindow("Window");
    }
}
