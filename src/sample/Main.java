package sample;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        primaryStage.setTitle("Encrypto");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();

        //Closing this main Window
        PauseTransition delay = new PauseTransition(Duration.seconds(15));
        delay.setOnFinished(e -> primaryStage.close());
        delay.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
