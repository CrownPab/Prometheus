package prometheus.projectiles;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import prometheus.constants.Direction;
import prometheus.constants.GlobalConstants;
import prometheus.entity.Entity;
import prometheus.entity.MovingEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.entity.player.Player;
import prometheus.entity.staticobjects.AcidPool;
import prometheus.entity.staticobjects.Wall;
import prometheus.maps.Map;
import prometheus.scenes.Sandbox;

public class WizardProjectile implements MovingEntity{

    private int positionX = 0;
    private int positionY = 0;
    private int height;
    private int width;
    private RectBoundedBox entityBoundary;
    private boolean alive;
    private DoubleProperty x  = new SimpleDoubleProperty();
    private DoubleProperty y  = new SimpleDoubleProperty();
    private int baseSpeed;

    /**
     * 
     * @param posx initial x position of projectile
     * @param posy initial y position of projectile
     * @param baseSpeed Base speed of projectile before distance scaling
     */
    public WizardProjectile(int posx, int posy, int baseSpeed) {
        positionX = posx;
    	positionY = posy;
    	width = 16;
    	height = 16;
    	this.baseSpeed = baseSpeed;
    	this.alive = true;
        entityBoundary = new RectBoundedBox(positionX, positionY, width, height);
        getX().set(this.positionX);
        getY().set(this.positionY);
        Timeline tl = createTimeline(Sandbox.getPlayer());
        tl.setOnFinished(e -> alive = false);
        tl.play();
    }
    
    /**
     * Function that checks if the entity is alive and should be rendered
     * @return boolean true if entity is alive, false otherwise 
     */
    public boolean isAlive(){
    	return alive;
    }
    
    /**
     * Function to check if the projectile is colliding with another entity
     */
    @Override
    public boolean isColliding(Entity b) {
        RectBoundedBox otherEntityBoundary = b.getBoundingBox();
        return entityBoundary.checkCollision(otherEntityBoundary);
    }
    
    /**
     * Function to draw the projectile
     */
    @Override
    public void draw() {
    	Sandbox.getGraphicsContext().setFill(Color.RED);
    	Sandbox.getGraphicsContext().fillOval(getX().doubleValue()+10, getY().doubleValue()+10, 10, 10);
    }
    
    /**
     * Get the current x position of the projectile
     */
    @Override
    public int getPositionX() {
        return positionX;
    }
    
    /**
     * Get the current y position of the projectile
     */
    @Override
    public int getPositionY() {
        return positionY;
    }
    
    /**
     * Get the bounding box of the projectile, for collisions
     */
    @Override
    public RectBoundedBox getBoundingBox() {
        return entityBoundary;
    }
    
    /**
     * Return if the projectile should go through players
     */
    @Override
    public boolean isPlayerCollisionFriendly() {
        return true;
    }
    
    /**
     * Changes the alive status of the projectile based on map, and entity collision
     */
	@Override
	public void onCollision(Entity e) {
		//If map is less than 4, don't go through walls
		if(e instanceof Wall && Map.lvl < 4) {
			this.alive = false;
		}
		//If map is less than 4, don't go through walls
		if(e instanceof AcidPool && Map.lvl < 4) {
			this.alive = false;
		}
		if(e instanceof Player) {
			((Player) e).reduceHealth(10);
			this.alive = false;
		}
	}
	
	/**
	 * Loop through all entites and check collisions
	 * 
	 * @param x location of collision x
	 * @param y location of collision y
	 * @return If a collision occurred
	 */
    public boolean checkCollisions(int x, int y) {
    	
    	entityBoundary.setPosition(x, y);
    	
        for (Entity e : Sandbox.getEntities()) {
            if(e != this && isColliding(e)) {
            	this.onCollision(e);
            }
        }
        return false;
    }
    
    /**
     * Create a timeline object to move the projectile to the entity
     * @param e Entity that the projectile is moving towards
     * @return timeline object that moves projectile
     */
    public Timeline createTimeline(Entity e) {
    	//Scale the speed based on distance
    	 double distance = Math.sqrt(Math.pow(getX().doubleValue() - e.getPositionX(), 2) + Math.pow(getY().doubleValue() - e.getPositionY(), 2));
    	 double maxDistance = Math.sqrt(Math.pow(GlobalConstants.CANVAS_WIDTH, 2) + Math.pow(GlobalConstants.CANVAS_HEIGHT, 2));
    	 
    	 //Create timeline from x,y to entity position
    	 Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(((distance/maxDistance)) * this.baseSpeed),
                    new KeyValue(getX(), e.getPositionX()),
                    new KeyValue(getY(), e.getPositionY())
            )
        );
        timeline.setCycleCount(1);
        
        return timeline;
    }

	@Override
	public void move(int steps, Direction direction) {
		
	}
	
	/**
	 * @return Get the x double property value from the timeline
	 */
	public DoubleProperty getX() {
		return x;
	}

	/**
	 * @return Get the y double property value from the timeline
	 */
	public DoubleProperty getY() {
		return y;
	}
}
