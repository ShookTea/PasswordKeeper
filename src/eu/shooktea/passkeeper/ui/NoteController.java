package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Main;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Note;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class NoteController extends AbstractController {
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
    private void saveNote() {
        Note note = new Note(noteTitle.getText(), noteText.getText());
        Storage.saveCipherableElement(note);
        Main.showWindow("Window");
    }

    @FXML
    private void deleteNote() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Deleting note");
        alert.setContentText("Do you really want to delete note '" + noteTitle.getText() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Storage.deleteCipherableElement();
            Main.showWindow("Window");
        }
    }
}
