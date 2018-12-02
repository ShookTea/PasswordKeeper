package eu.shooktea.passkeeper.ui;

import eu.shooktea.passkeeper.Main;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
import eu.shooktea.passkeeper.type.Note;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class WindowController {

    @FXML
    private TableView<Note> notesTable;

    @FXML
    private void initialize() {
        List<Note> notes = Storage.filter(Type.NOTE);
        notesTable.getColumns().clear();
        TableColumn<Note, String> title = new TableColumn<>("Title");
        TableColumn<Note, String> text = new TableColumn<>("Text");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        text.setCellValueFactory(new PropertyValueFactory<>("text"));
        notesTable.getColumns().addAll(title, text);
        notesTable.setItems(FXCollections.observableList(notes));
    }

    @FXML
    private void createNewNote() {
        Main.showWindow("Note", "Create new note");
    }
}
