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
}
