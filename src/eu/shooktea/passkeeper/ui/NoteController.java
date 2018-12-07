package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Note;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        this.saveElement(new Note(noteTitle.getText(), noteText.getText()));
    }

    @Override
    protected String getTypeName() {
        return "note";
    }

    @Override
    protected String getElementName() {
        return noteTitle.getText();
    }
}
