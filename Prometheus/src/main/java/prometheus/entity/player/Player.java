package prometheus.entity.player;

import javafx.scene.image.Image;
import prometheus.GameLoop;
import prometheus.PlayerStats;
import prometheus.constants.Direction;
import prometheus.constants.GlobalConstants;
import prometheus.entity.Entity;
import prometheus.entity.KillableEntity;
import prometheus.entity.MovingEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.scenes.Sandbox;
import prometheus.utils.ImageUtils;

public class Player implements MovingEntity, KillableEntity {

	private int health;
	private boolean isAlive;
	private RectBoundedBox playerBoundary;
	private Image up;
	private Image down;
	private Image left;
	private Image right;
	private Direction currentDirection;
	private int positionX = 0;
	private int positionY = 0;
	private String name;
	private PlayerStats stats;

	/**
	 * Base player constructor
	 */
	public Player() {
		init(64, 64);

		name = Sandbox.getUsername();
		stats = new PlayerStats(name);
	}
	
	/**
	 * Player constructor with x and y positions
	 * @param posX x position for player
	 * @param posY y position for player
	 */
	public Player(int posX, int posY) {
		init(posX, posY);
		isAlive = true;
		
		//Set playername and stats
		name = Sandbox.getUsername();
		stats = new PlayerStats(name);
	}
	
	/**
	 * Initialize player object with a given x and y location
	 * 
	 * @param x location for player
	 * @param y location for player
	 */
	public void init(int x, int y) {
		positionX = x;
		positionY = y;
		this.health = 100;

		playerBoundary = new RectBoundedBox(positionX - 5, positionY - 5, GlobalConstants.PLAYER_WIDTH + 5,
				GlobalConstants.PLAYER_HEIGHT + 23);
		
		//Four different images for different viewing directions
		down = ImageUtils.loadImage("Resources/img/sprites/chungus.png");
		right = ImageUtils.loadImage("Resources/img/sprites/blobMain.png");
		up = ImageUtils.loadImage("Resources/img/sprites/blobMainBack.png");
		left = ImageUtils.loadImage("Resources/img/sprites/blobLeft.png");
	}
	
	/**
	 * Move function that defaults to 1 step
	 * @param direction The direction the player is moving in
	 */
	public void move(Direction direction) {
		move(1, direction);
	}
	
	/**
	 * Get player health as an integer
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Check if player is alive
	 * @return true is player is alive, false otherwise
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
    /**
     * Function to check if the player is colliding with another entity
     */
	@Override
	public boolean isColliding(Entity b) {
		RectBoundedBox otherEntityBoundary = (RectBoundedBox) b.getBoundingBox();
		return playerBoundary.checkCollision(otherEntityBoundary);
	}
	
    /**
     * Function to draw the player based on the direction they are facing
     */
	@Override
	public void draw() {
		if (this.getCurrentDirection() == null)
			this.currentDirection = Direction.UP;

		switch (this.getCurrentDirection()) {
		case DOWN:
			Sandbox.getGraphicsContext().drawImage(this.down, positionX, positionY,
					GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE + 15,
					GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE + 35);
			break;
		case LEFT:
			Sandbox.getGraphicsContext().drawImage(this.left, positionX, positionY + 9,
					GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE,
					GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		case RIGHT:
			Sandbox.getGraphicsContext().drawImage(this.right, positionX, positionY + 9,
					GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE,
					GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		case UP:
			Sandbox.getGraphicsContext().drawImage(this.up, positionX, positionY + 5,
					GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE,
					GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		default:
			Sandbox.getGraphicsContext().drawImage(this.down, positionX, positionY,
					GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE,
					GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		}
	}
	
	/**
	 * When player dies, add a death statistic and stop the game screen
	 */
	@Override
	public void die() {
		this.stats.addDeaths(1);
		Sandbox.stopGame(false);
	}

	private boolean checkCollisions(int x, int y) {
		playerBoundary.setPosition(x, y);

		for (Entity e : Sandbox.getEntities()) {
			if (e != this && isColliding(e) && !e.isPlayerCollisionFriendly()) {
				return true;
			}
			if (e != this && isColliding(e)) {
				e.onCollision(this);
			}
		}
		playerBoundary.setPosition(positionX, positionY);
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
				if (!checkCollisions(positionX, positionY - steps)) {
					positionY -= steps;
					this.currentDirection = Direction.UP;
				}
				break;
			case DOWN:
				if (!checkCollisions(positionX, positionY + steps)) {
					positionY += steps;
					this.currentDirection = Direction.DOWN;
				}
				break;
			case LEFT:
				if (!checkCollisions(positionX - steps, positionY)) {
					positionX -= steps;
					this.currentDirection = Direction.LEFT;
				}
				break;
			case RIGHT:
				if (!checkCollisions(positionX + steps, positionY)) {
					positionX += steps;
					this.currentDirection = Direction.RIGHT;
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void reduceHealth(int damage) {
		if (health - damage <= 0) {
			die();
		} else {
			health -= damage;
		}
	}

	@Override
	public int getPositionX() {
		return positionX;
	}

	@Override
	public int getPositionY() {
		return positionY;
	}

	public PlayerStats getStats() {
		return this.stats;
	}

	@Override
	public RectBoundedBox getBoundingBox() {
		playerBoundary.setPosition(positionX, positionY);
		return playerBoundary;
	}

	@Override
	public boolean isPlayerCollisionFriendly() {
		return true;
	}

	@Override
	public void onCollision(Entity e) {

	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}
}
