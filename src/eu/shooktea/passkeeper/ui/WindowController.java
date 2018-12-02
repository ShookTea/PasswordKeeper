package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Main;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Note;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class WindowController {

    @FXML
    private TableView<Note> notesTable;

    @FXML
    private void initialize() {
        Type.NOTE.applyTableViewMaker(notesTable);
        notesTable.setRowFactory(tv -> {
            TableRow<Note> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    Storage.setObjectToEdit(row.getItem());
                    Main.showWindow("Note", "Create new note");
                }
            });
            return row;
        });
    }

    @FXML
    private void createNewNote() {
        Main.showWindow("Note", "Create new note");
    }
}
