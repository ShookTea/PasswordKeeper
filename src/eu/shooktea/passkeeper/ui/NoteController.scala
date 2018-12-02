package eu.shooktea.passkeeper.ui

import eu.shooktea.passkeeper.`type`.Note
import eu.shooktea.passkeeper.{Main, Storage}
import javafx.fxml.FXML
import javafx.scene.control.{TextArea, TextField}

class NoteController {
  @FXML var noteTitle: TextField = _
  @FXML var noteText: TextArea = _

  def goBack() : Unit = Main showWindow "Window"

  def saveNote() : Unit = {
    val title: String = noteTitle.getText
    val text: String = noteText.getText
    Storage savedCipherableElement Note(title, text)
    Main showWindow "Window"
  }
}
