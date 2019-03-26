package prometheus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prometheus.constants.GlobalConstants;
import prometheus.scenes.Sandbox;

public class Prometheus extends Application {
	
	public static Socket socket;
	public static DataOutputStream toServer = null;
	public static DataInputStream fromServer = null;
	
    @Override
    public void start(Stage primaryStage) {
    	try {
			socket = new Socket("localhost", 2500); // set Socket 
			fromServer = new DataInputStream(socket.getInputStream()); // set input stream
			toServer = new DataOutputStream(socket.getOutputStream()); // set output stream
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
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
