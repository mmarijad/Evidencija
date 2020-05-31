package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{

      /* User Slavica = new User();
        Slavica.setFirstName("Slavica");
        Slavica.setLastName("Cukteraš");
        Slavica.setEmail("slavica.cukteras@gmail.com");
        Slavica.setPassword("slaviceslavice");
        Slavica.setRole("user");
        Slavica.save();*/

      Main.primaryStage = primaryStage;
        Main.showWindow(
                 getClass(),
                "/view/Login.fxml",
                "Prijavite se", 515, 310, 550, 200);
    }

    public static void showWindow(Class windowClass, String viewName, String title, int w, int h, int x, int y) throws IOException {
            Parent root = FXMLLoader.load(windowClass.getResource(viewName));
            File css = new File("styles.css");
            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root, w, h));
            primaryStage.show();
            primaryStage.setX(x);
            primaryStage.setY(y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
