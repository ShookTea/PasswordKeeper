package eu.shooktea.passkeeper.ui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ColumnGenerator {
    public ColumnGenerator() {}

    public ColumnGenerator label(String label) {
        this.label = label;
        return this;
    }

    public ColumnGenerator fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public <T> TableColumn<T, String> apply() {
        TableColumn<T, String> column = new TableColumn<>(label);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        return column;
    }

    private String label = "";
    private String fieldName = "";
}
