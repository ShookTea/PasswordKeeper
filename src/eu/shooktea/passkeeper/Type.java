package eu.shooktea.passkeeper;

import eu.shooktea.passkeeper.type.Note;
import eu.shooktea.passkeeper.ui.TableViewMaker;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public enum Type {
    NOTE("Note", tv -> {
        TableColumn<Note, String> title = new TableColumn<>("Title");
        TableColumn<Note, String> text = new TableColumn<>("Text");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        text.setCellValueFactory(new PropertyValueFactory<>("text"));
        tv.getColumns().setAll(title, text);
    });

    private Type(String typeName, TableViewMaker tvm) {
        try {
            this.cls = Class.forName("eu.shooktea.passkeeper.type." + typeName);
            this.tvm = tvm;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInstance(Cipherable c) {
        return cls.isInstance(c);
    }

    public void applyTableViewMaker(TableView tv) {
        tvm.make(tv);
        tv.setItems(FXCollections.observableList(Storage.filter(this)));
    }

    public <T extends Cipherable> T mapInstance(Cipherable c) {
        return (T)cls.cast(c);
    }

    private Class cls;
    private TableViewMaker tvm;
}
