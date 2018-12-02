package eu.shooktea.passkeeper.ui;

import javafx.scene.control.TableView;

public interface TableViewMaker<T> {
    void make(TableView<T> tv);
}
