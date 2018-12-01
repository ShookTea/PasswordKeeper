package eu.shooktea.passkeeper.ui

import eu.shooktea.passkeeper.Main
import javafx.fxml.FXML

class WindowController {
  @FXML
  def createNewNote() : Unit = Main showWindow "Note"
}
