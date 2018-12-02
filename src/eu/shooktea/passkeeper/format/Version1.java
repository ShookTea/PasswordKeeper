package eu.shooktea.passkeeper.format;

import eu.shooktea.passkeeper.Cipherable;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.Type;
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
        loadFromDataInputStream(new DataInputStream(is));
    }

    protected void loadFromDataInputStream(DataInputStream dis) throws IOException {
        String identifier;
        while (!(identifier = dis.readUTF()).equals("EOF")) switch(identifier) {
            case "NOTE": Storage.store(loadNote(dis)); break;
            default: throw new IOException("Wrong format");
        }
    }

    @Override
    void storeToOutputStream(OutputStream os) throws IOException {
        storeToDataOutputStream(new DataOutputStream(os));
    }

    protected void storeToDataOutputStream(DataOutputStream dos) throws IOException {
        List<Cipherable> entries = Storage.getAll();
        for (Cipherable element : entries) {
            if (element instanceof Note) {
                saveNote(dos, (Note)element);
            }
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
