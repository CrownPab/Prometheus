package prometheus;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import prometheus.constants.GlobalConstants;
import prometheus.enemies.Runner;
import prometheus.enemies.Wizard;
import prometheus.entity.Entity;
import prometheus.entity.player.Player;
import prometheus.gamecontroller.InputManager;
import prometheus.maps.Map;
import prometheus.projectiles.Bubble;
import prometheus.projectiles.WizardProjectile;
import prometheus.scenes.Sandbox;
import prometheus.utils.ImageUtils;

public class GameLoop {

    static double currentGameTime;
    static double oldGameTime;
    static double deltaTime;
    final static long startNanoTime = System.nanoTime();
    public static AnimationTimer animTimer;

    public static double getCurrentGameTime() {
        return currentGameTime;
    }

    public static void start(GraphicsContext gc) {
        animTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
            	oldGameTime = currentGameTime;
            	currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
            	deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0, 0, GlobalConstants.CANVAS_WIDTH, GlobalConstants.CANVAS_WIDTH);
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
                ((Bubble) entity).move(2, ((Bubble) entity).getCurrentDirection());
            }
            
            else if(entity instanceof WizardProjectile) {
            	WizardProjectile wiz = ((WizardProjectile) entity);
                wiz.checkCollisions((int)wiz.getX().doubleValue(), (int)wiz.getY().doubleValue());
            }
            
            else if(entity instanceof Wizard) { 
            	Wizard wiz = ((Wizard) entity);
            	if(Map.lvl <= 2) {
            		if((System.currentTimeMillis() - wiz.lastShot) >= 1000) {
            			((Wizard) entity).shoot(10);
            			wiz.lastShot = System.currentTimeMillis();
            		}
            	}
            	else {
            		if((System.currentTimeMillis() - wiz.lastShot) >= 500) {
            			((Wizard) entity).shoot(5);
            			wiz.lastShot = System.currentTimeMillis();
            		}
            	}
            }
            
            else if(entity instanceof Runner) { 
            	if(Map.lvl <= 2)
            		((Runner) entity).move(1, ((Runner) entity).getDirectionTo(Sandbox.getPlayer()));
            	else 
            		((Runner) entity).move(2, ((Runner) entity).getDirectionTo(Sandbox.getPlayer()));

            }
        }
        
        Map.waveProgress();
    }

    public static void renderGame() {
        for (Entity e : Sandbox.getEntities()) {
            e.draw();
        }
        if((Sandbox.getPlayer().getHealth()>=90) && (Sandbox.getPlayer().getHealth()<=100))
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthFull.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        	
        }else if(Sandbox.getPlayer().getHealth()>=80 && Sandbox.getPlayer().getHealth()<=89)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthNine.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }else if(Sandbox.getPlayer().getHealth()>=70 && Sandbox.getPlayer().getHealth()<=79)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthEight.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }else if(Sandbox.getPlayer().getHealth()>=60 && Sandbox.getPlayer().getHealth()<=69)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthSeven.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }else if(Sandbox.getPlayer().getHealth()>=50 && Sandbox.getPlayer().getHealth()<=59)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthSix.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }else if(Sandbox.getPlayer().getHealth()>=40 && Sandbox.getPlayer().getHealth()<=49)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthFive.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }else if(Sandbox.getPlayer().getHealth()>=30 && Sandbox.getPlayer().getHealth()<=39)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthFour.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }else if(Sandbox.getPlayer().getHealth()>=20 && Sandbox.getPlayer().getHealth()<=29)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthThree.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);;
        }else if(Sandbox.getPlayer().getHealth()>=10 && Sandbox.getPlayer().getHealth()<=19)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthTwo.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }else if(Sandbox.getPlayer().getHealth()>=0 && Sandbox.getPlayer().getHealth()<=9)
        {
        	Sandbox.setHealth(ImageUtils.loadImage("Resources/img/healthOne.png"));
        	Sandbox.getGraphicsContext().drawImage(Sandbox.getHealth(), 0,0,150,40);
        }
    }
    

}
