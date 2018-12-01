package eu.shooktea.passkeeper

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class Main extends Application {
  override def start(stage: Stage): Unit = {
    val root: Parent = FXMLLoader load getClass.getResource("/eu/shooktea/passkeeper/ui/Window.fxml")
    val scene = new Scene(root, 600, 400)
    stage setTitle "Password Keeper"
    stage setScene scene
    stage show()
  }
}

object Main {
  def main(args: Array[String]) : Unit = Application.launch(classOf[Main], args: _*)
}
