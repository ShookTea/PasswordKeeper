package eu.shooktea.passkeeper;

import eu.shooktea.passkeeper.ui.ColumnGenerator;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public enum Type {
    NOTE("Note", "Note", "Create new note", "Edit note"),
    PASSWORD("Password", "Password", "Create new password", "Edit password");

    private Type(String typeName, String editWindow, String newTitle, String editTitle) {
        try {
            this.cls = Class.forName("eu.shooktea.passkeeper.type." + typeName);
            this.editWindow = editWindow;
            this.newTitle = newTitle;
            this.editTitle = editTitle;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInstance(Cipherable c) {
        return cls.isInstance(c);
    }

    public <T extends Cipherable> void applyTableViewMaker(TableView<T> table) {
        try {
            Cipherable chr = (Cipherable)cls.newInstance();
            table.getColumns().setAll(chr.getColumnsWithProperties()
                    .stream()
                    .map(ColumnGenerator::<T>apply)
                    .collect(Collectors.toList())
            );
        } catch (Exception ignored) {}
        table.setItems(FXCollections.observableList(Storage.filter(this)));
        table.setRowFactory(tv -> {
            TableRow<T> row = new TableRow();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    Storage.setObjectToEdit(row.getItem());
                    Main.showWindow(this.editWindow, this.editTitle);
                }
            });
            return row;
        });
    }

    public <T extends Cipherable> T mapInstance(Cipherable c) {
        return (T)cls.cast(c);
    }

    public void showNewWindow() {
        Main.showWindow(editWindow, newTitle);
    }

    private Class cls;
    private String editWindow;
    private String newTitle;
    private String editTitle;
}
