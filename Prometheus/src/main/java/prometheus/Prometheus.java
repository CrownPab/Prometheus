package prometheus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prometheus.constants.GlobalConstants;
import prometheus.scenes.Sandbox;

public class Prometheus extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(GlobalConstants.GAME_NAME);
        Sandbox.setupScene();
        Scene s = Sandbox.getScene();
        String css = Prometheus.class.getResource("style.css").toExternalForm(); 
        s.getStylesheets().add(css);
        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
