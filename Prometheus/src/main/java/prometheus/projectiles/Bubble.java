package prometheus.projectiles;

import javafx.scene.paint.Color;
import prometheus.GameLoop;
import prometheus.constants.Direction;
import prometheus.enemies.Runner;
import prometheus.enemies.Wizard;
import prometheus.entity.Entity;
import prometheus.entity.MovingEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.entity.staticobjects.AcidPool;
import prometheus.entity.staticobjects.Wall;
import prometheus.scenes.Sandbox;

public class Bubble implements MovingEntity{
	
	private static Long lastBubble = System.currentTimeMillis();
	private int positionX = 0;
	private int positionY = 0;
    private int height;
    private int width;
    private RectBoundedBox entityBoundary;
    private Direction currentDirection;
    private boolean alive;

    /**
     * @param posx initial x position of bubble
     * @param posy initial y position of bubble
     * @param direction the direction the bubble is flying in
     * @param baseSpeed Base speed of projectile before distance scaling
     */
    public Bubble(int x, int y, Direction direction) {
    	setLastBubble(System.currentTimeMillis());
        positionX = x;
    	positionY = y;
    	width = 16;
    	height = 16;
    	if(direction == null)
    		direction = Direction.UP;
    	currentDirection = direction;
    	this.alive = true;
        entityBoundary = new RectBoundedBox(positionX-5, positionY-5, width+5, height+5);
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
    	Sandbox.getGraphicsContext().setStroke(Color.CORNFLOWERBLUE);
    	Sandbox.getGraphicsContext().strokeOval(positionX+10, positionY+10, 20, 20);
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
     * Changes the alive status of the projectile based on map, and entity collision and inflict damage
     */
	@Override
	public void onCollision(Entity e) {
		if(e instanceof Wall) {
			this.alive = false;
		}
		if(e instanceof AcidPool) {
			this.alive = false;
		}
		if(e instanceof Wizard) {
			((Wizard) e).reduceHealth(10);
			this.alive = false;
		}
		if(e instanceof Runner) {
			((Runner) e).reduceHealth(10);
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
    private boolean checkCollisions(int x, int y) {
    	
    	entityBoundary.setPosition(x, y);
    	
        for (Entity e : Sandbox.getEntities()) {
            if(e != this && isColliding(e)) {
            	this.onCollision(e);
            }
        }
        return false;
    }
    

	@Override
	public void move(int steps, Direction direction) {
        steps *= GameLoop.getDeltaTime();

        if (steps == 0) {
            return;
        } else {
            switch (direction) {
                case UP:
	                positionY -= steps;
	                currentDirection = Direction.UP;
                    break;
                case DOWN:
                		positionY += steps;
                		currentDirection = Direction.DOWN;
                    break;
                case LEFT:
                		positionX -= steps;
                		currentDirection = Direction.LEFT;
                        break;
                case RIGHT:
                		 positionX += steps;
                		 currentDirection = Direction.RIGHT;
                    break;
                default:
            }

            checkCollisions(positionX, positionY);
        }
		
	}
	
	/**
	 * Get the long representation of when the last bubble was shot
	 * @return long of last time bubble was shot
	 */
	public static Long getLastBubble() {
		return lastBubble;
	}
	
	/**
	 * Set the last time the bubble was shot
	 * @param lastBubble time of last bubble shot
	 */
	public static void setLastBubble(Long lastBubble) {
		Bubble.lastBubble = lastBubble;
	}
	
	/**
	 * 
	 * @return Direction bubble is flying in
	 */
	public Direction getCurrentDirection() {
		return currentDirection;
	}
}
