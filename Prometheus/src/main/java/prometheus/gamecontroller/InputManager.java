package prometheus.gamecontroller;

import java.util.List;
import javafx.scene.input.KeyCode;
import prometheus.constants.Direction;
import prometheus.constants.GlobalConstants;
import prometheus.entity.player.Player;
import prometheus.projectiles.Bubble;
import prometheus.scenes.Sandbox;

public class InputManager {
	
	/**
	 * Method that handles player key inputs for movement and shooting
	 */
    public static void handlePlayerMovements(){
        List<KeyCode> keyboardInputs = EventHandler.getInputList();
        Player player = Sandbox.getPlayer();
        
        //Allow for arrow keys and wasd
        if(keyboardInputs.contains(KeyCode.UP) || keyboardInputs.contains(KeyCode.W)){
            player.move(3,Direction.UP);
        }
        if(keyboardInputs.contains(KeyCode.DOWN) || keyboardInputs.contains(KeyCode.S)){
            player.move(3,Direction.DOWN);
        }
        if(keyboardInputs.contains(KeyCode.LEFT) || keyboardInputs.contains(KeyCode.A)){
            player.move(3,Direction.LEFT);
        }
        if(keyboardInputs.contains(KeyCode.RIGHT) || keyboardInputs.contains(KeyCode.D)){
            player.move(3,Direction.RIGHT);
        }
        
        //Click space to shoot
        if(keyboardInputs.contains(KeyCode.SPACE)){ 
        	if(System.currentTimeMillis() - Bubble.getLastBubble() > GlobalConstants.BUBBLE_COOLDOWN)
        		Sandbox.addEntityToGame(new Bubble(player.getPositionX(), player.getPositionY(), player.getCurrentDirection()));
        }  
    }

}
