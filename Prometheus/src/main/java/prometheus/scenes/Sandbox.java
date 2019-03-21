package prometheus.scenes;

import static prometheus.constants.GlobalConstants.CANVAS_HEIGHT;
import static prometheus.constants.GlobalConstants.CANVAS_WIDTH;
import static prometheus.constants.GlobalConstants.SCENE_HEIGHT;
import static prometheus.constants.GlobalConstants.SCENE_WIDTH;

import java.util.Vector;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import prometheus.GameLoop;
import prometheus.entity.Entity;
import prometheus.entity.player.Player;
import prometheus.entity.staticobjects.AcidPool;
import prometheus.entity.staticobjects.Wall;
import prometheus.gamecontroller.EventHandler;


public class Sandbox {

    static Scene s;
    static Group root;
    static Canvas c;
    static GraphicsContext gc;
    private static boolean sceneStarted;
    static Player sandboxPlayer;
    static{
        sceneStarted=false;
    }

	private static Vector<Entity> entities = new Vector<Entity>();

	public static Vector<Entity> getEntities(){
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
	
    private static void introScreen() {
    	System.out.println("New Scene");
        root = new Group();
        s = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        
        
        Label label1 = new Label("Enter a username:");
        HBox hb = new HBox();
        
        TextField tf = new TextField();
        tf.setPromptText("Enter a Username:");
        tf.setOnAction(e -> {
        	Button button = new Button();
        	button.setText("Play");
        	button.setOnAction(a -> init2());
        	button.setLayoutX(280);
        	button.setLayoutY(300);
        	root.getChildren().clear();
        	root.getChildren().addAll(button);
        });
        
        hb.getChildren().addAll(label1, tf);
        hb.setSpacing(10);
        
        root.getChildren().add(hb);
    }

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
        
        //load map
        loadMap();

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
    	button2.setText("Restart");
    	button2.setOnAction(e -> init2());
    	button2.setLayoutX(280);
    	button2.setLayoutY(340);
    	root.getChildren().addAll(button, button2);
    }


    //Eventually this should take some kind of map input, maybe a text file or something
    public static void loadMap() {
    	Vector<Wall> walls = new Vector<Wall>();
    	Vector<AcidPool> pools = new Vector<AcidPool>();

    	for(int i = 0; i < SCENE_WIDTH; i += 32){
    		for(int j = 0; j < SCENE_HEIGHT; j += 32){
    			if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    				walls.add(new Wall(i, j));
    			}
//    			else if(i % 5 == 0)
//    				if(ThreadLocalRandom.current().nextInt(0, 2) < 1)
//    					pools.add(new AcidPool(i,j));
    		}
    	}

    	for(Wall wall : walls) {
    		addEntityToGame(wall);
    	}
    	
    	for(AcidPool wall : pools) {
    		addEntityToGame(wall);
    	}
    }

    public static void setupScene(){
        if(!sceneStarted){
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
