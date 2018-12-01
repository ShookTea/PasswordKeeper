package eu.shooktea.passkeeper.`type`

import eu.shooktea.passkeeper.PasswordGenerator
import org.junit.{Assert, Test}

class NoteTest {
  @Test
  def creatingEmptyNote() : Unit = {
    Assert.assertEquals("", Note().text)
  }

  @Test
  def creatingNodeWithText() : Unit = {
    val text = "ABC123"
    Assert.assertEquals(text, Note(text).text)
  }

  @Test
  def convertToArray() : Unit = {
    val text = "ABC123"
    val bytes = Array[Byte](0x41, 0x42, 0x43, 0x31, 0x32, 0x33)
    Assert.assertArrayEquals(bytes, Note(text).toBytes())
  }

  @Test
  def convertFromArray() : Unit = {
    val text = "ABC123"
    val bytes = Array[Byte](0x41, 0x42, 0x43, 0x31, 0x32, 0x33)
    Assert.assertEquals(text, Note(bytes).text)
  }

  @Test
  def convertToAndFromArray() : Unit = {
    val textA = PasswordGenerator(50)
    val noteA = Note(textA)
    val bytes = noteA.toBytes()
    val noteB = Note(bytes)
    val textB = noteB.text
    Assert.assertEquals(textA, textB)
  }
}
