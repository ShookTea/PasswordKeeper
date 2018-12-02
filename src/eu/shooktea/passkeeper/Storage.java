package eu.shooktea.passkeeper;

import java.io.File;
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

    private static File getFile() {
        if (file == null) {
            String url = Main.class.getResource("/eu/shooktea/passkeeper/Main.class").toString();
            if (url.startsWith("jar:file:")) {
                url = url.substring("jar:file:".length()).replaceAll("!.*$", "");
            }
            else {
                url = url.substring("file:".length());
            }
            File classOrJarFile = new File(url);
            File directory = classOrJarFile.getParentFile();
            file = new File(directory, ".password_keeper");
        }
        return file;
    }

    private static List<Cipherable> elements = new ArrayList<>();
    private static int editedIndex = -1;
    private static File file = null;
}
