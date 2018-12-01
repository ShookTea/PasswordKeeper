package eu.shooktea.passkeeper

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class Main extends Application {
  override def start(stage: Stage): Unit = {
    Main.showWindow("Window", "Password Keeper", stage)
  }
}

object Main {
  def main(args: Array[String]) : Unit = Application.launch(classOf[Main], args: _*)

  def showWindow(fxmlFileName: String, title: String = null, stage: Stage = null) : Unit = {
    if (stage != null) {
      Main.stage = stage
    }
    val root: Parent = FXMLLoader load getClass.getResource("/eu/shooktea/passkeeper/ui/" + fxmlFileName + ".fxml")
    if (scene == null) {
      scene = new Scene(root, 600, 400)
    }
    else {
      scene setRoot root
    }
    Main.stage setTitle (title match {
      case s:String => s
      case null => windows(fxmlFileName)
    })
    Main.stage setScene scene
    Main.stage show()
  }

  private var stage: Stage = _
  private var scene: Scene = _
  private val windows: Map[String, String] = Map(
    ("Window", "Password Keeper"),
    ("Note", "Note editing")
  )
}
