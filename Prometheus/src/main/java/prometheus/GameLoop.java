package prometheus;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import prometheus.constants.GlobalConstants;
import prometheus.enemies.Runner;
import prometheus.enemies.Wizard;
import prometheus.entity.Entity;
import prometheus.gamecontroller.InputManager;
import prometheus.maps.Map;
import prometheus.projectiles.Bubble;
import prometheus.projectiles.WizardProjectile;
import prometheus.scenes.Sandbox;

public class GameLoop {
	//Test commit
    static double currentGameTime;
    static double oldGameTime;
    static double deltaTime;
    final static long startNanoTime = System.nanoTime();
    public static AnimationTimer animTimer;

    public static double getCurrentGameTime() {
        return currentGameTime;
    }

    public static void start(GraphicsContext gc) {
        GameState.gameStatus=GlobalConstants.GameStatus.Running;
        animTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
            	oldGameTime = currentGameTime;
            	currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
            	deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0, 0, GlobalConstants.CANVAS_WIDTH, GlobalConstants.CANVAS_WIDTH);
                //TODO This will have to be something like, currentScene.getEntities()
                updateGame();
                renderGame();
            }
        };
        animTimer.start();
    }

    public static double getDeltaTime() {
    	return deltaTime * 100;
    }

    public static void updateGame() {
        InputManager.handlePlayerMovements();
        ArrayList<Entity> entities = Sandbox.getEntities();
        Iterator<Entity> it = entities.iterator();
  
        
        while (it.hasNext()) {
            Entity entity = it.next();
            if(entity instanceof Bubble) {
                boolean alive = ((Bubble) entity).isAlive();
                if(!alive){
                    it.remove();
                }
            }
            
            else if(entity instanceof WizardProjectile) {
                boolean alive = ((WizardProjectile) entity).isAlive();
                if(!alive){
                    it.remove();
                }
            }
            
            else if(entity instanceof Wizard) {
                boolean alive = ((Wizard) entity).isAlive();
                if(!alive){
                    it.remove();
                    Map.mobCount--;
                    //acidDrop()
                }
            }
            else if(entity instanceof Runner) {
                boolean alive = ((Runner) entity).isAlive();
                if(!alive){
                    it.remove();
                    Map.mobCount--;
                    //acidDrop();
                }
            }
        }
        
        for(Entity entity: new ArrayList<Entity>(entities)) {
            if(entity instanceof Bubble) {
                ((Bubble) entity).move(2, ((Bubble) entity).currentDirection);
            }
            
            else if(entity instanceof WizardProjectile) {
//                ((WizardProjectile) entity).move(1, ((WizardProjectile) entity).currentDirection);
            	WizardProjectile wiz = ((WizardProjectile) entity);
                wiz.checkCollisions((int)wiz.x.doubleValue(), (int)wiz.y.doubleValue());
            }
            
            else if(entity instanceof Wizard) { 
				if (Math.random() < 0.005)
					((Wizard) entity).shoot();
            }
            
            else if(entity instanceof Runner && Map.lvl <= 2) { 
				((Runner) entity).move(1, ((Runner) entity).getDirectionTo(Sandbox.sandboxPlayer));
            }
            else if(entity instanceof Runner && Map.lvl >2) { 
				((Runner) entity).move(2, ((Runner) entity).getDirectionTo(Sandbox.sandboxPlayer));
            }
            
        }
        
        Map.waveProgress();
    }

    public static void renderGame() {
        for (Entity e : Sandbox.getEntities()) {
            e.draw();
        }
    }
    

}
