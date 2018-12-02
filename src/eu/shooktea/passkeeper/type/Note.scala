package eu.shooktea.passkeeper.`type`

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, DataInputStream, DataOutputStream}
import java.nio.charset.Charset

import eu.shooktea.passkeeper.Cipherable

class Note(val title: String, val text: String) extends Cipherable[Note] {
  def this() = this("", "")
  def this(text: String) = this("", text)

  override def toBytes(): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val dos = new DataOutputStream(baos)
    if (title == null || title.length == 0) {
      dos writeBoolean false
    } else {
      dos writeBoolean true
      dos writeUTF title
    }
    if (text == null || text.length == 0) {
      dos writeBoolean false
    } else {
      dos writeBoolean true
      dos writeUTF text
    }
    dos.flush()
    baos.toByteArray
  }
  override def fromBytes(bytes: Array[Byte]): Note = {
    val bais = new ByteArrayInputStream(bytes)
    val dis = new DataInputStream(bais)

    val title = if(dis.readBoolean()) dis.readUTF() else ""
    val text = if (dis.readBoolean()) dis.readUTF() else ""
    dis.close()
    Note(title, text)
  }
}

object Note {
  def apply(title: String = "", text: String = ""): Note = new Note(title, text)
  def apply(bytes: Array[Byte]): Note = new Note().fromBytes(bytes)

  private val charset: Charset = Charset forName "UTF-8"
}
