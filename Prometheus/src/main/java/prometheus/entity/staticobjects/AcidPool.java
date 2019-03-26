package prometheus.entity.staticobjects;

import javafx.scene.image.Image;
import prometheus.entity.Entity;
import prometheus.entity.StaticEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.entity.player.Player;
import prometheus.scenes.Sandbox;
import prometheus.utils.ImageUtils;

public class AcidPool implements StaticEntity {
	private int positionX = 0;
	private int positionY = 0;
	private int height;
	private int width;
	private Image sprite;
	private RectBoundedBox boundry;
	
	/**
	 * Init for Acid pool
	 * 
	 * @param x position of x coordinate for pool
	 * @param y position of y coordinate for pool
	 */
	public AcidPool(int x, int y) {
		positionX = x;
		positionY = y;
		width = 16;
		height = 16;

		sprite = ImageUtils.loadImage("Resources/img/sprites/acid.png");
		boundry = new RectBoundedBox(positionX, positionY, width, height);
	}
	
    /**
     * Function to check if the wall is colliding with another entity
     */
	@Override
	public boolean isColliding(Entity b) {
		throw new UnsupportedOperationException("Unsupported.");
	}
	
    /**
     * Function to draw the wall
     */
	@Override
	public void draw() {
		Sandbox.getGraphicsContext().drawImage(sprite, positionX, positionY, 32, 32);
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
		return true;
	}
	
	/**
	 * Describes what should happen if a player collides with the pool
	 */
	@Override
	public void onCollision(Entity e) {
		if (e instanceof Player) {
			((Player) e).reduceHealth(2);
		}
	}

}