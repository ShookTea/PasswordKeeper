package eu.shooktea.passkeeper;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;

public enum Type {
    NOTE("Note", "Note", "Create new note", "Edit note");

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
            table.getColumns().clear();
            Map<String, String> columns = chr.getColumnsWithProperties();
            for(String key : columns.keySet()) {
                TableColumn<T, String> column = new TableColumn<>(key);
                column.setCellValueFactory(new PropertyValueFactory<>(columns.get(key)));
                table.getColumns().add(column);
            }
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

    private Class cls;
    private String editWindow;
    private String newTitle;
    private String editTitle;
}
