package prometheus.scenes;

import static prometheus.constants.GlobalConstants.CANVAS_HEIGHT;
import static prometheus.constants.GlobalConstants.CANVAS_WIDTH;
import static prometheus.constants.GlobalConstants.SCENE_HEIGHT;
import static prometheus.constants.GlobalConstants.SCENE_WIDTH;

import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import prometheus.GameLoop;
import prometheus.Prometheus;
import prometheus.entity.Entity;
import prometheus.entity.player.Player;
import prometheus.gamecontroller.EventHandler;
import prometheus.maps.Map;
import prometheus.utils.FileUtils;
import prometheus.utils.ImageUtils;


public class Sandbox {

    static Scene s;
    static Group root;
    static Image health;
    static Image floor;
    static Canvas c;
    static GraphicsContext gc;
    static boolean sceneStarted;
    private static Player sandboxPlayer;
    static String username;
    static{
        sceneStarted=false;
    }
    
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * Get list of active entities
	 * @return ArrayList of entities
	 */
	public static ArrayList<Entity> getEntities(){
		return entities;
	}
	
	/**
	 * Function to add an entity to the game
	 * @param e	Entity being added
	 * @return boolean to see if the addition was successful
	 */
	public static boolean addEntityToGame(Entity e){
		if(!entities.contains(e)){
			entities.add(e);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Initialize the scene and root values for the other screens
	 */
	private static void initValues() {
        root = new Group();
        s = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    	
	}
	
	/**
	 * Creates the intro screen for the program, this is where the player enters a username
	 */
    private static void introScreen() {
        root.getChildren().clear();
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        pane.getStyleClass().add("root2");
        Text tex = new Text("");
        Label label1 = new Label("                     Enter a username:");
        HBox hb = new HBox(10);
        VBox vb = new VBox(5);
        label1.getStyleClass().add("text2");
        TextField tf = new TextField();
        tf.setPromptText("Enter a Username:");
        tf.setOnAction(e -> {
        	username = tf.getText();
        	FileUtils.getOrCreateFile(username);
        	root.getChildren().clear();
        	mainMenu();
        });
        tf.setPrefWidth(100);
        hb.getChildren().addAll(label1,tf);
        ImageView imageView = new ImageView(ImageUtils.loadImage("Resources\\img\\Blob\\Blob\\PrometheusBackground.png"));
        vb.getChildren().addAll(hb,imageView);

        pane.add(tex, 100, 100);
        pane.add(vb,0,0);

        root.getChildren().add(pane);
    }
    
    /**
     * Creates the main menu screen with credits, leaderboard, play game and stats for the player
     */
    public static void mainMenu() {
    	root.getChildren().clear();
    	
		Stage outStage = new Stage();
		outStage.setX(1920 / 2 - 350);
		outStage.setY(1080 / 2 - 155);

		VBox pane = new VBox();
		TextArea leaderBoard = new TextArea();
		
		HBox vbox = new HBox(220);
		
		Pane pane2 = new Pane();
		
		for(Entry<String, Integer> entry: FileUtils.getHighscoresList().entrySet()) {
			leaderBoard.appendText(entry.getKey() + " - Kills: " + entry.getValue() + " Deaths: " + FileUtils.getPlayerDeaths(entry.getKey())+"\n");
		}

		leaderBoard.setStyle("-fx-control-inner-background:orangered; -fx-font-family: Consolas; -fx-font-size: 16px;" + 
				" -fx-font-family: \"Arial Black\";" + 
				" -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, white 0%, red 50%);" + 
				" -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 );");
		Text leadBoard = new Text("Leaderboard");
		leadBoard.getStyleClass().add("text1");
		leadBoard.setX(210);
		leadBoard.setY(400);
		pane2.getChildren().add(leadBoard);
		Button playGame = new Button("Play Game");
		playGame.getStyleClass().add("green");
		Button stats = new Button("Stats");
		stats.getStyleClass().add("green");
		Button credits = new Button("Credits");
		credits.getStyleClass().add("green");
		playGame.setOnAction(a -> init());
		
		credits.setOnAction(a -> credits(outStage));

		stats.setOnAction(a -> stats(outStage));
		
		
		vbox.getChildren().addAll(playGame, stats, credits); // inserts all three buttons into vbox

		pane.getChildren().addAll(vbox,pane2,leaderBoard);
		root.getChildren().addAll(pane);
		vbox.getStyleClass().add("root2");
		pane.getStyleClass().add("root2");
    }
    
    /**
     * Creates a credit stage, separate from the main stage and scene
     * @param outStage Stage that will be popped out
     */
	public static void credits(Stage outStage) {
		Pane pane = new Pane();
		pane.getStyleClass().add("root");
		Text credit1 = new Text("Atharva Shinde");
		Text credit2 = new Text("Gagandeep Pabla");
		Text credit3 = new Text("Lucas Simone");
		Text credit4 = new Text("Vital Golub");
		
		credit1.getStyleClass().add("text2");
		credit1.setX(40);
		credit1.setY(220);
		
		credit2.getStyleClass().add("text2");
		credit2.setX(40);
		credit2.setY(240);

		credit3.getStyleClass().add("text2");
		credit3.setX(40);
		credit3.setY(260);

		credit4.getStyleClass().add("text2");
		credit4.setX(40);
		credit4.setY(280);

		pane.getChildren().addAll(credit1, credit2, credit3, credit4);

		Scene outScene = new Scene(pane, 200, 200);
		String css = Prometheus.class.getResource("style.css").toExternalForm(); 
	    outScene.getStylesheets().add(css);
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
	
	/**
     * Creates a stats stage, separate from the main stage and scene
     * @param outStage Stage that will be popped out
	 */
	public static void stats(Stage outStage) {
		Pane pane = new Pane();

		// These will be used to label the stats
		Text stat1 = new Text("Kills: " + FileUtils.getPlayerKills(username));
		Text stat2 = new Text("Deaths: " + FileUtils.getPlayerDeaths(username));
		// THis is a placeholder image
		// We are going to put the sprite of the character here
		ImageView imageView = new ImageView(ImageUtils.loadImage("Resources/img/sprites/blobMain.png"));
		pane.getStyleClass().add("root");
		imageView.setX(0);
		imageView.setY(1);

		pane.getChildren().add(imageView);

		stat1.setX(100);
		stat1.setY(15);

		stat2.setX(100);
		stat2.setY(35);

		pane.getChildren().addAll(stat1, stat2);

		Scene outScene = new Scene(pane, 200, 50);
		String css = Prometheus.class.getResource("style.css").toExternalForm(); 
	    outScene.getStylesheets().add(css);
		outStage.setTitle("Stats");
		outStage.setResizable(false);
		outStage.setScene(outScene);
		outStage.show();

	}
    
	/**
	 * Game init function, creates the first map and sets the canvas
	 */
    private static void init() {
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
        
        //load map
        Map.Map1();
        
        //should be called at last it based on player
        EventHandler.attachEventHandlers(s);

    }
    
    /**
     * End game screen for the game, win or lose
     * @param victory boolean to check if player won or lost
     */
    public static void stopGame(boolean victory) {
    	GameLoop.animTimer.stop();
    	root.getChildren().clear();
    	
    	Pane pane = new Pane();
    	pane.setMinHeight(SCENE_HEIGHT+50);
    	pane.setMinWidth(SCENE_WIDTH+50);
        pane.getStyleClass().add("root2");
        
   		Text tf = new Text();
   		
    	if(victory) {
    		tf.setText("You've won!");
    		tf.setStroke(Color.GREEN);
    	}
    	else {
    		tf.setText("You have lost!");
    		tf.setStroke(Color.RED);
    	}
    	tf.setFont(new Font(30));
    	tf.setLayoutX(240);
    	tf.setLayoutY(200);
    	
    	Button button = new Button();
    	button.setText("Play Again");
		button.getStyleClass().add("green");
    	button.setOnAction(e -> init());
    	button.setLayoutX(280);
    	button.setLayoutY(300);
    	
    	Button button2 = new Button();
    	button2.setText("Main menu");
		button2.getStyleClass().add("green");
    	button2.setOnAction(e -> mainMenu());
    	button2.setLayoutX(280);
    	button2.setLayoutY(340);
    	
    	Button button3 = new Button();
    	button3.setText("New User");
		button3.getStyleClass().add("green");
    	button3.setOnAction(e -> introScreen());
    	button3.setLayoutX(280);
    	button3.setLayoutY(380);
    	pane.getChildren().addAll(tf, button, button2, button3);
    	root.getChildren().addAll(pane);
    }
    
    /**
     * Set up the scene for the first time
     */
    public static void setupScene(){
        if(!sceneStarted){
        	initValues();
        	introScreen();
            sceneStarted=true;
        }
    }
    
    /**
     * 
     * @return main scene object
     */
    public static Scene getScene() {
        return s;
    }
    
    /**
     * 
     * @return returns main graphics context
     */
    public static GraphicsContext getGraphicsContext() {
        return gc;
    }
    
    /**
     * 
     * @return main canvas from gram screen
     */
    public static Canvas getCanvas() {
        return c;
    }
    
    /**
     * Set played object and add them to the game
     * @param p player object that should be added
     */
    public static void setPlayer(Player p){
    	sandboxPlayer = p;
        addEntityToGame(p);
    }
    
    /**
     * 
     * @return Player object stored in sandbox
     */
    public static Player getPlayer(){
        return sandboxPlayer;
    }
    
    /**
     * 
     * @return username of current player
     */
	public static String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @return current floor image
	 */
	public static Image getFloor() {
		return floor;
	}
	
	/**
	 * 
	 * @param floor floor image
	 */
	public static void setFloor(Image floor) {
		Sandbox.floor = floor;
	}
	
	/**
	 * 
	 * @return health of sandbox player as image
	 */
	public static Image getHealth() {
		return health;
	}
	
	/**
	 * 
	 * @param health image to store current health image
	 */
	public static void setHealth(Image health) {
		Sandbox.health = health;
	}
}
