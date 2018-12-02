package eu.shooktea.passkeeper.`type`

import eu.shooktea.passkeeper.PasswordGenerator
import org.junit.{Assert, Test}

class NoteTest {
  @Test
  def creatingEmptyNote() : Unit = {
    Assert.assertEquals("", Note().text)
  }

  @Test
  def creatingNoteWithText() : Unit = {
    val text = "ABC123"
    val title = "123ABC"
    val note = Note(title, text)
    Assert.assertEquals(title, note.title)
    Assert.assertEquals(text, note.text)
  }

  @Test
  def convertToAndFromArray() : Unit = {
    val textA = PasswordGenerator(500)
    val titleA = PasswordGenerator(10)
    val noteA = Note(titleA, textA)
    val bytes = noteA.toBytes()
    val noteB = Note(bytes)
    val titleB = noteB.title
    val textB = noteB.text
    Assert.assertEquals(titleA, titleB)
    Assert.assertEquals(textA, textB)
  }
}
