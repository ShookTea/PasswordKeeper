package eu.shooktea.passkeeper.ui

import eu.shooktea.passkeeper.Main
import javafx.fxml.FXML
import javafx.scene.control.{TextArea, TextField}

class NoteController {
  @FXML var noteTitle: TextField = _
  @FXML var noteText: TextArea = _

  def goBack() : Unit = Main showWindow "Window"
}
