package prometheus.gamecontroller;

import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.Vector;
import javafx.scene.input.KeyCode;
import prometheus.constants.Direction;
import prometheus.constants.GlobalConstants;
import prometheus.enemies.Wizard;
import prometheus.entity.Entity;
import prometheus.entity.player.Player;
import prometheus.projectiles.Bubble;
import prometheus.scenes.Sandbox;

public class InputManager {

    public static void handlePlayerMovements(){
        List keyboardInputs = EventHandler.getInputList();
        Player player = Sandbox.getPlayer();

        if(keyboardInputs.contains(KeyCode.UP) || keyboardInputs.contains(KeyCode.W)){
            player.move(5,Direction.UP);
        }
        if(keyboardInputs.contains(KeyCode.DOWN) || keyboardInputs.contains(KeyCode.S)){
            player.move(5,Direction.DOWN);
        }
        if(keyboardInputs.contains(KeyCode.LEFT) || keyboardInputs.contains(KeyCode.A)){
            player.move(5,Direction.LEFT);
        }
        if(keyboardInputs.contains(KeyCode.RIGHT) || keyboardInputs.contains(KeyCode.D)){
            player.move(5,Direction.RIGHT);
        }
        
        if(keyboardInputs.contains(KeyCode.SPACE)){ 
        	if(System.currentTimeMillis() - Bubble.lastBubble > GlobalConstants.BUBBLE_COOLDOWN)
        		Sandbox.addEntityToGame(new Bubble(player.getPositionX(), player.getPositionY(), player.currentDirection));
        }  
    }

}
