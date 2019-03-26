package prometheus.entity.staticobjects;

import javafx.scene.image.Image;
import prometheus.entity.Entity;
import prometheus.entity.StaticEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.scenes.Sandbox;
import prometheus.utils.ImageUtils;

public class Wall implements StaticEntity {
	public int positionX = 0;
	public int positionY = 0;
	private int height;
	private int width;
	private Image sprite;
	RectBoundedBox boundry;
	
	/**
	 * Init for Wall
	 * 
	 * @param x position of x coordinate for wall
	 * @param y position of y coordinate for wall
	 */
	public Wall(int x, int y) {
		positionX = x;
		positionY = y;
		width = 32;
		height = 32;
		
		//Image of the wall
		sprite = ImageUtils.loadImage("Resources/img/sprites/wall.png");
		//Bounding box of the wall
		boundry = new RectBoundedBox(positionX - 6, positionY - 4, width + 5, height);
	}
	
    /**
     * Function to check if the wall is colliding with another entity
     */
	@Override
	public boolean isColliding(Entity b) {
		throw new UnsupportedOperationException("Unsupported");
	}
	
    /**
     * Function to draw the wall
     */
	@Override
	public void draw() {
		Sandbox.getGraphicsContext().drawImage(sprite, positionX, positionY, 40, 40);
	}
	
    /**
     * Get the current x position of the wall
     */
	@Override
	public int getPositionX() {
		return positionX;
	}
	
    /**
     * Get the current y position of the wall
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
		return boundry;
	}
	
    /**
     * Return if players should walk through walls
     */
	@Override
	public boolean isPlayerCollisionFriendly() {
		return false;
	}

	@Override
	public void onCollision(Entity e) {}
}
