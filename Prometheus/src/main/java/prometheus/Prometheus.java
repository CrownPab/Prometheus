package prometheus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prometheus.constants.GlobalConstants;
import prometheus.scenes.Sandbox;
import prometheus.utils.FileUtils;

public class Prometheus extends Application {
	
	public static Socket socket;
	public static DataOutputStream toServer = null;
	public static DataInputStream fromServer = null;
	
	static DataInputStream inputFromClient = null;
	static DataOutputStream outputToClient = null;
	
	static boolean serverActive = true;
	
    @Override
    public void start(Stage primaryStage) {
    	try {
    		runServer();
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
        primaryStage.setOnCloseRequest(e -> {
        	serverActive = false;
        	System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static void runServer() {
    	new Thread(() ->{
    		System.out.println("Server started");
    		try {
    			ServerSocket serverSocket = new ServerSocket(2500);
    			Socket socket = serverSocket.accept();
    			
    			inputFromClient = new DataInputStream(socket.getInputStream());
    			outputToClient = new DataOutputStream(socket.getOutputStream());

    			while (serverActive) {
    				if(inputFromClient != null && outputToClient != null) {
    					String name = inputFromClient.readUTF();
    					int kills = inputFromClient.readInt();
    					int deaths = inputFromClient.readInt();
    					
    					System.out.println("Username: " + name + " Kills: " + kills + " Deaths: " + deaths);
    					
    					FileUtils.setPlayerStats(name, kills, deaths);
    				}
    			}
    			
    			socket.close();
    			serverSocket.close();
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    	}).start();
    }

}
