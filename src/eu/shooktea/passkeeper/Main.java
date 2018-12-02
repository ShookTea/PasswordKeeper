package eu.shooktea.passkeeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Storage.loadData();
        Main.showWindow("Window", "Password Keeper", stage);
    }

    public static void main(String[] args) {
        Main.launch(args);
    }

    public static void showWindow(String fxmlFileName) {
        String title = "";
        switch(fxmlFileName) {
            case "Window": title = "Password Keeper"; break;
        }
        if (fxmlFileName.equals("Window")) {
            Storage.setObjectToEdit(null);
        }
        showWindow(fxmlFileName, title, null);
    }

    public static void showWindow(String fxmlFileName, String title) {
        showWindow(fxmlFileName, title, null);
    }

    public static void showWindow(String fxmlFileName, String title, Stage stage) {
        if (stage != null) {
            Main.stage = stage;
        }

        Parent root;
        try {
            root = FXMLLoader.load(Main.class.getResource("/eu/shooktea/passkeeper/ui/" + fxmlFileName + ".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (scene == null) {
            scene = new Scene(root, 600, 400);
        }
        else {
            scene.setRoot(root);
        }

        Main.stage.setTitle(title);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    private static Stage stage = null;
    private static Scene scene = null;
}
