package eu.shooktea.passkeeper.type;

import eu.shooktea.passkeeper.Cipherable;
import eu.shooktea.passkeeper.ui.ColumnGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;
import java.util.List;

public class Note implements Cipherable {
    public Note(String title, String text) {
        this.title = new SimpleStringProperty(title);
        this.text = new SimpleStringProperty(text);
    }

    public Note() {
        this("", "");
    }

    @Override
    public List<ColumnGenerator> getColumnsWithProperties() {
        return Arrays.asList(
                new ColumnGenerator().label("Title").fieldName("title"),
                new ColumnGenerator().label("Text").fieldName("text")
        );
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    private StringProperty title;
    private StringProperty text;
}
