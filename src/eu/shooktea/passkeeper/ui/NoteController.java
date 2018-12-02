package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Main;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.type.Note;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NoteController {
    @FXML private TextField noteTitle;
    @FXML private TextArea noteText;

    @FXML
    private void goBack() {
        Main.showWindow("Window");
    }

    @FXML
    private void saveNote() {
        Note note = new Note(noteTitle.getText(), noteText.getText());
        Storage.savedCipherableElement(note);
        Main.showWindow("Window");
    }
}
