package eu.shooktea.passkeeper.ui;

import javafx.scene.control.*;
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

    public ColumnGenerator hide() {
        this.hide = true;
        return this;
    }

    public <T> TableColumn<T, String> apply() {
        TableColumn<T, String> column = new TableColumn<>(label);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        if (hide) {
            column.setCellFactory(tStringTableColumn -> new PasswordFieldCell());
        }
        return column;
    }

    private String label = "";
    private String fieldName = "";
    private boolean hide = false;
}

class PasswordFieldCell<T> extends TableCell<T, String> {
    private Label label;

    PasswordFieldCell() {
        label = new Label();
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setGraphic(null);
    }

    private String genDotString() {
        StringBuilder dots = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            dots.append("\u2022");
        }
        return dots.toString();
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            label.setText(genDotString());
            setGraphic(label);
        } else {
            setGraphic(null);
        }
    }
}