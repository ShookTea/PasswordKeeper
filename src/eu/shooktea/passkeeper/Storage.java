package eu.shooktea.passkeeper;

import eu.shooktea.passkeeper.format.Format;
import eu.shooktea.passkeeper.format.IncorrectPasswordException;
import eu.shooktea.passkeeper.ui.CreatePasswordDialog;
import eu.shooktea.passkeeper.ui.PasswordDialog;
import javafx.scene.control.PasswordField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        saveData();
    }

    public static void update(int index, Cipherable c) {
        elements.set(index, c);
        saveData();
    }

    public static <T extends Cipherable> List<T> filter(Type t) {
        return elements.stream()
                .filter(t::isInstance)
                .map(t::<T>mapInstance)
                .collect(Collectors.toList());
    }

    public static void setObjectToEdit(Cipherable c) {
        editedIndex = elements.indexOf(c);
    }

    public static boolean isCurrentlyEdited(Type type) {
        if (editedIndex == -1) return false;
        return type.isInstance(elements.get(editedIndex));
    }

    public static <T extends Cipherable> T getCurrentlyEdited(Type type) {
        if (editedIndex == -1) return null;
        return type.mapInstance(elements.get(editedIndex));
    }

    public static List<Cipherable> getAll() {
        return elements;
    }

    public static void loadData() throws IOException {
        File f = getFile();
        if (!f.exists()) {
            CreatePasswordDialog cpd = new CreatePasswordDialog();
            boolean passwordSet = false;
            while (!passwordSet) {
                Optional<char[][]> passwords = cpd.showAndWait();
                if (passwords.isPresent()) {
                    if (!Arrays.equals(passwords.get()[0], passwords.get()[1])) {
                        cpd.error("Passwords must be equal.");
                    }
                    else if (passwords.get()[0].length < 8) {
                        cpd.error("Your password should be at least 8 characters long.");
                    }
                    else {
                        Storage.password = passwords.get()[0];
                        passwordSet = true;
                        f.createNewFile();
                    }
                } else {
                    System.exit(0);
                }
            }
        }
        else {
            PasswordDialog pd = new PasswordDialog();
            boolean decoded = false;
            while (!decoded) {
                Optional<char[]> password = pd.showAndWait();
                if (password.isPresent()) {
                    Storage.password = password.get();
                    FileInputStream fis = new FileInputStream(f);
                    try {
                        Format.load(fis);
                        decoded = true;
                    } catch (IncorrectPasswordException e) {
                        pd.error("Wrong password. Please try again.");
                    }
                } else {
                    System.exit(0);
                }
            }
        }
    }

    private static void saveData() {
        try {
            File f = getFile();
            if (!f.exists()) {
                f.createNewFile();
            }
            else {
                FileOutputStream fos = new FileOutputStream(f);
                Format.save(fos);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public static char[] getPassword() {
        return password;
    }

    private static List<Cipherable> elements = new ArrayList<>();
    private static int editedIndex = -1;
    private static char[] password = new char[0];
    private static File file = null;
}
