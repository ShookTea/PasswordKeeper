package eu.shooktea.passkeeper;

import eu.shooktea.passkeeper.ui.ColumnGenerator;

import java.util.List;

public interface Cipherable {
    List<ColumnGenerator> getColumnsWithProperties();
}
