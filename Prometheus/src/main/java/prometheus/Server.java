package prometheus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import prometheus.utils.FileUtils;

public class Server {
	
	static DataInputStream inputFromClient;
	static DataOutputStream outputToClient;
	
	public static void main(String[] args) {
		System.out.println("Server started");
		try {
			ServerSocket serverSocket = new ServerSocket(2500);
			Socket socket = serverSocket.accept();
			
			inputFromClient = new DataInputStream(socket.getInputStream());
			outputToClient = new DataOutputStream(socket.getOutputStream());

			while (true) {
				String name = inputFromClient.readUTF();
				int kills = inputFromClient.readInt();
				int deaths = inputFromClient.readInt();
				
				System.out.println("Username: " + name + " Kills: " + kills + " Deaths: " + deaths);
				
				FileUtils.setPlayerStats(name, kills, deaths);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}