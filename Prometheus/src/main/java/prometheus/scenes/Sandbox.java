package prometheus.scenes;

import static prometheus.constants.GlobalConstants.CANVAS_HEIGHT;
import static prometheus.constants.GlobalConstants.CANVAS_WIDTH;
import static prometheus.constants.GlobalConstants.SCENE_HEIGHT;
import static prometheus.constants.GlobalConstants.SCENE_WIDTH;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import prometheus.GameLoop;
import prometheus.PlayerStats;
import prometheus.enemies.Wizard;
import prometheus.entity.Entity;
import prometheus.entity.player.Player;
import prometheus.gamecontroller.EventHandler;
import prometheus.maps.Map;
import prometheus.utils.FileUtils;


public class Sandbox {

    static Scene s;
    static Group root;
    static Canvas c;
    static GraphicsContext gc;
    private static boolean sceneStarted;
    static Player sandboxPlayer;
    public static String username;
    static{
        sceneStarted=false;
    }

	private static ArrayList<Entity> entities = new ArrayList<Entity>();

	public static ArrayList<Entity> getEntities(){
		return entities;
	}

	public static boolean addEntityToGame(Entity e){
		if(!entities.contains(e)){
			entities.add(e);
			return true;
		} else {
			return false;
		}
	}
	
	private static void initValues() {
        root = new Group();
        s = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
	}
	
    private static void introScreen() {
        root.getChildren().clear();
        Label label1 = new Label("Enter a username:");
        HBox hb = new HBox();
        
        TextField tf = new TextField();
        tf.setPromptText("Enter a Username:");
        tf.setOnAction(e -> {
//        	Button button = new Button();
//        	button.setText("Play");
//        	button.setOnAction(a -> init2());
//        	button.setLayoutX(280);
//        	button.setLayoutY(300);
        	username = tf.getText();
        	FileUtils.getOrCreateFile(username);
        	root.getChildren().clear();
//        	root.getChildren().addAll(button);
        	
			Stage outStage = new Stage();
			outStage.setX(1920 / 2 - 350);
			outStage.setY(1080 / 2 - 155);
			
//			String uriString = new File("C:\\coding\\csci2020u\\InterfaceV2\\src\\application\\MoBamba.mp3").toURI()
//			.toString();
//			MediaPlayer player = new MediaPlayer(new Media(uriString));
//			player.play();
			
			BorderPane borderPane = new BorderPane(); // borderpane is being used for the main menu screen
			TextArea leaderBoard = new TextArea(); // this text area will be used for the leaderboard
			VBox vbox = new VBox(20); // creates vbox with spacing 20

			Button playGame = new Button("Play Game"); // users will click this button to enter the game
			Button stats = new Button("Stats"); // users will click this button to view their stats
			Button credits = new Button("Credits"); // will display all personal that worked on the game for user to see
			playGame.setOnAction(a -> init2());
			
			credits.setOnAction(a -> credits(outStage));

			stats.setOnAction(a -> stats(outStage));
			
			
			vbox.getChildren().addAll(playGame, stats, credits); // inserts all three buttons into vbox

			borderPane.setLeft(vbox); // sets postion of the vbox for the on the borderpane

			borderPane.setRight(leaderBoard); // sets the position of the leaderboard on the borderpane
			root.getChildren().addAll(borderPane);

//			credits.setOnAction(a -> {
//				credits(outStage);
//			});
//
//			stats.setOnAction(a -> {
//				stats(outStage);
//			});
        });
        
        hb.getChildren().addAll(label1, tf);
        hb.setSpacing(10);
        
        root.getChildren().add(hb);
    }
    
	public static void credits(Stage outStage) {
		Pane pane = new Pane();

		Text credit1 = new Text("Atharva Shinde");
		Text credit2 = new Text("Gagandeep Pabla");
		Text credit3 = new Text("Lucas Simone");
		Text credit4 = new Text("Vital Golub");

		credit1.setX(50);
		credit1.setY(220);

		credit2.setX(50);
		credit2.setY(240);

		credit3.setX(50);
		credit3.setY(260);

		credit4.setX(50);
		credit4.setY(280);

		pane.getChildren().addAll(credit1, credit2, credit3, credit4);

		Scene outScene = new Scene(pane, 200, 200);
		outStage.setTitle("Credits");
		outStage.setResizable(false);
		outStage.setScene(outScene);
		outStage.show();

		// This will animate the text to move upwards
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					while (true) {

						double y = credit1.getY();
						double y2 = credit2.getY();
						double y3 = credit3.getY();
						double y4 = credit4.getY();

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								credit1.setY(y - 3);
								credit2.setY(y2 - 3);
								credit3.setY(y3 - 3);
								credit4.setY(y4 - 3);
							}
						});
						Thread.sleep(200);
					}
				} catch (InterruptedException ex) {
				}
			}
		}).start();
	}
	
	public static void stats(Stage outStage) {
		Pane pane = new Pane();

		// These will be used to label the stats
		Text stat1 = new Text("Kills: " + (new PlayerStats(username).kills));
		Text stat2 = new Text("Deaths: " + (new PlayerStats(username).deaths));
		// THis is a placeholder image
		// We are going to put the sprite of the character here
		ImageView imageView = new ImageView("file:///C:\\coding\\csci2020u\\InterfaceV2\\src\\application\\china.gif");

		imageView.setX(35);
		imageView.setY(1);

		pane.getChildren().add(imageView);

		stat1.setX(35);
		stat1.setY(100);

		stat2.setX(35);
		stat2.setY(120);

		pane.getChildren().addAll(stat1, stat2);

		Scene outScene = new Scene(pane, 200, 200);
		outStage.setTitle("Credits");
		outStage.setResizable(false);
		outStage.setScene(outScene);
		outStage.show();

	}
    
    /*
    private static void init() {
    	System.out.println("New Scene");
        root = new Group();
        s = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        c = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.getChildren().add(c);
        gc = c.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        gc.setFill(Color.BLUE);
        GameLoop.start(gc);

        //Initialize Objects
        Player p = new Player();
        setPlayer(p);
        
        //load map
        loadMap();

        //should be called at last it based on player
        EventHandler.attachEventHandlers(s);

    }
    */
    
    private static void init2() {
    	System.out.println("New Scene");
    	root.getChildren().clear();
        c = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.getChildren().add(c);
        gc = c.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        gc.setFill(Color.BLUE);
        entities.clear();
        GameLoop.start(gc);

        //Initialize Objects
        Player p = new Player();
        setPlayer(p);
        
        Wizard w = new Wizard(32,32);
    	addEntityToGame(w);
        
        //load map
        Map.Map2();
        

        //should be called at last it based on player
        EventHandler.attachEventHandlers(s);

    }
    
    public static void stopGame() {
    	GameLoop.animTimer.stop();
    	root.getChildren().clear();
    	Button button = new Button();
    	button.setText("Play Again");
    	button.setOnAction(e -> init2());
    	button.setLayoutX(280);
    	button.setLayoutY(300);
    	
    	Button button2 = new Button();
    	button2.setText("Main menu");
    	button2.setOnAction(e -> introScreen());
    	button2.setLayoutX(280);
    	button2.setLayoutY(340);
    	root.getChildren().addAll(button, button2);
    }



    public static void setupScene(){
        if(!sceneStarted){
        	initValues();
        	introScreen();
//            init();
            sceneStarted=true;
        }
    }
    public static Scene getScene() {
        return s;
    }

    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static Canvas getCanvas() {
        return c;
    }

    public static void setPlayer(Player p){
        sandboxPlayer = p;
        addEntityToGame(p);
    }
    public static Player getPlayer(){
        return sandboxPlayer;
    }
}
