package eu.shooktea.passkeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Storage {
    private Storage() {}

    public static void saveCipherableElement(Cipherable c) {
        if (editedIndex == -1) {
            store(c);
        }
        else {
            update(editedIndex, c);
        }
        editedIndex = -1;
    }

    public static void clear() {
        elements.clear();
    }

    public static void store(Cipherable c) {
        elements.add(c);
    }

    public static void update(int index, Cipherable c) {
        elements.set(index, c);
    }

    public static <T extends Cipherable> List<T> filter(Type t) {
        return elements.stream()
                .filter(t::isInstance)
                .map(t::<T>mapInstance)
                .collect(Collectors.toList());
    }

    public static List<Cipherable> getAll() {
        return elements;
    }

    private static List<Cipherable> elements = new ArrayList<>();
    private static int editedIndex = -1;
}
