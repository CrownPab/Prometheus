package prometheus;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.geometry.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.*;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent; 
import javafx.scene.input.KeyCode; 
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.scene.text.TextAlignment;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Stage outStage = new Stage();
			outStage.setX(1920/2-350);
	        outStage.setY(1080/2-155);
	        
	        
	        String uriString = new File("C:\\coding\\csci2020u\\InterfaceV2\\src\\application\\MoBamba.mp3").toURI().toString();
	        MediaPlayer player = new MediaPlayer( new Media(uriString));
	        player.play();
	        
			BorderPane root = new BorderPane(); // borderpane is being used for the main menu screen 
			TextArea leaderBoard = new TextArea(); //this text area will be used for the leaderboard 
			VBox vbox = new VBox(20); // creates vbox with spacing 20 
			
			Button playGame = new Button("Play Game"); //users will click this button to enter the game 
			Button stats = new Button("Stats"); // users will click this button to view their stats 
			Button credits = new Button("Credits"); // will display all personal that worked on the game for user to see 
			
			vbox.getChildren().addAll(playGame,stats,credits); // inserts all three buttons into vbox 
			
			root.setLeft(vbox); // sets postion of the vbox for the on the borderpane 
			
			
			root.setRight(leaderBoard); // sets the position of the leaderboard on the borderpane 
			
			
			credits.setOnAction(e->{
	            credits(outStage);
	        });
			
			stats.setOnAction(e->{
	            stats(outStage);
	        });
			
			
			Scene scene = new Scene(root,800,800);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void credits(Stage outStage) {
		Pane pane = new Pane(); 
		
		
		Text credit1 = new Text("Atharva Shinde"); 
		Text credit2 = new Text("Gagandeep Pabla"); 
		Text credit3 = new Text("Lucas Simone");
		Text credit4 = new Text("Vital Golab"); 
		
		
		credit1.setX(50);
		credit1.setY(220);
		
		credit2.setX(50);
		credit2.setY(240);
		
		credit3.setX(50);
		credit3.setY(260);
		
		
		credit4.setX(50);
		credit4.setY(280);
		
		
		
	
		
		pane.getChildren().addAll(credit1,credit2,credit3,credit4);
		
		
		
		
		  Scene outScene = new Scene(pane,200,200);
	      outStage.setTitle("Credits");
	      outStage.setResizable(false);
	      outStage.setScene(outScene);
	      outStage.show();
	      
	      
	      //This will animate the text to move upwards 
	      new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						
						
						while (true) {
							
							double y = credit1.getY();
							double y2 = credit2.getY();
							double y3= credit3.getY();
							double y4 = credit4.getY();

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									credit1.setY(y-1);
									credit2.setY(y2-1);
									credit3.setY(y3-1);
									credit4.setY(y4-1);
								}
							});
							Thread.sleep(200);
						}
					}
					catch (InterruptedException ex) {
					}
				}
			}).start();
	  }
	
	private void stats(Stage outStage) {
		Pane pane = new Pane(); 
		
		// These will be used to label the stats 
		Text stat1 = new Text("Statistic One: ");
		Text stat2 = new Text("Statistic Two: "); 
		Text stat3 = new Text("Statistic Three: ");
		//THis is a placeholder image 
		//We are going to put the sprite of the character here 
		ImageView imageView = new ImageView("file:///C:\\coding\\csci2020u\\InterfaceV2\\src\\application\\china.gif");
		
		imageView.setX(35);
		imageView.setY(1);
		
		pane.getChildren().add(imageView);
		
		stat1.setX(35);
		stat1.setY(100);
		
		
		stat2.setX(35);
		stat2.setY(120);
		
		stat3.setX(35);
		stat3.setY(140);
		
		pane.getChildren().addAll(stat1,stat2,stat3);
		
		Scene outScene = new Scene(pane,200,200);
	    outStage.setTitle("Credits");
	    outStage.setResizable(false);
	    outStage.setScene(outScene);
	    outStage.show();
		
		
	}
	
	      
	}

