package eu.shooktea.passkeeper.format;

import eu.shooktea.passkeeper.Cipherable;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.type.Note;

import java.io.*;
import java.util.List;

/**
 * First version of Password Keeper format. Extension of First Abstract Format {@link AbstractFormat}.
 * Data is made of entries starting with UTF-8 identifier:
 *
 * NOTE - {@link eu.shooktea.passkeeper.type.Note} type, with parameters:
 *  * hasTitle (BOOL) - describes if given Note has a setted title
 *  * title (UTF) - only if hasTitle = true
 *  * hasText (BOOL) - describes if given Note has a text
 *  * text (UTF) - only if hasText = true
 * EOF - empty entry, marking end of file.
 */
public class Version1 extends AbstractFormat {
    @Override
    void loadFromInputStream(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        String identifier;
        do {
            identifier = dis.readUTF();
            loadByIdentifier(identifier, dis);
        }
        while (!identifier.equals("EOF"));
    }

    void loadByIdentifier(String identifier, DataInputStream dis) throws IOException {
        switch (identifier) {
            case "NOTE": Storage.getAll().add(loadNote(dis)); break;
            case "EOF": break;
            default: throw new IOException("Wrong format");
        }
    }

    @Override
    void storeToOutputStream(OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        List<Cipherable> elements = Storage.getAll();
        for (Cipherable element : elements) {
            storeElement(element, dos);
        }
        dos.writeUTF("EOF");
    }

    void storeElement(Cipherable element, DataOutputStream dos) throws IOException {
        if (element instanceof Note) {
            saveNote(dos, (Note)element);
        }
    }

    @Override
    public int getVersionNumber() {
        return 1;
    }

    protected static Note loadNote(DataInputStream dis) throws IOException {
        String title = dis.readBoolean() ? dis.readUTF() : "";
        String text = dis.readBoolean() ? dis.readUTF() : "";
        return new Note(title, text);
    }

    protected static void saveNote(DataOutputStream dos, Note note) throws IOException {
        dos.writeUTF("NOTE");
        String title = note.getTitle();
        if (title == null || title.length() == 0) {
            dos.writeBoolean(false);
        }
        else {
            dos.writeBoolean(true);
            dos.writeUTF(title);
        }

        String text = note.getText();
        if (text == null || text.length() == 0) {
            dos.writeBoolean(false);
        }
        else {
            dos.writeBoolean(true);
            dos.writeUTF(text);
        }
    }
}
