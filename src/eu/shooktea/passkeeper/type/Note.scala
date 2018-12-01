package eu.shooktea.passkeeper.`type`

import java.nio.charset.Charset

import eu.shooktea.passkeeper.Cipherable

class Note(val text: String) extends Cipherable[Note] {
  def this() = this("")
  override def toBytes(): Array[Byte] = text getBytes Note.charset
  override def fromBytes(bytes: Array[Byte]): Note = new Note(new String(bytes, Note.charset))
}

object Note {
  def apply(text: String = ""): Note = new Note(text)
  def apply(bytes: Array[Byte]): Note = new Note().fromBytes(bytes)

  private val charset: Charset = Charset forName "UTF-8"
}
