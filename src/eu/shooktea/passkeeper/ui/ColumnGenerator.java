package eu.shooktea.passkeeper.ui;

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

    public String label = "";
    public String fieldName = "";
}
