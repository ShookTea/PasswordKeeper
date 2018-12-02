package eu.shooktea.passkeeper.type;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoteTest {

    @Test
    public void creatingEmptyNote() {
        Note n = new Note();
        assertEquals("", n.getText());
        assertEquals("", n.getTitle());
    }

    @Test
    public void creatingNoteWithTextAndTitle() {
        String title = "Title of a note";
        String text = "Lorem ipsum dolor sit amet";
        Note n = new Note(title, text);
        assertEquals(title, n.getTitle());
        assertEquals(text, n.getText());
    }

    @Test
    public void saveAndReadByteArray() {
        String title = "Title of a note";
        String text = "Lorem ipsum dolor sit amet";
        Note a = new Note(title, text);
        byte[] array = a.toBytes();
        Note b = new Note().fromBytes(array);
        assertEquals(title, b.getTitle());
        assertEquals(text, b.getText());
    }
}
