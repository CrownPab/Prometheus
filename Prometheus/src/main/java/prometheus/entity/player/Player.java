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
    RectBoundedBox playerBoundary;

    Image up;
    Image down;
    Image left;
    Image right;

    public Direction currentDirection;

    public int positionX = 0;
    public int positionY = 0;

    String name;
    PlayerStats stats;

    public Player() {
        init(64, 64);
        
        name = Sandbox.getUsername();
        stats = new PlayerStats(name);
    }

    public Player(int posX, int posY) {
        init(posX, posY);
        isAlive = true;
        
        name = Sandbox.getUsername();
        stats = new PlayerStats(name);
    }

    public void init(int x, int y) {
        positionX = x;
        positionY = y;
        this.health = 100;

        playerBoundary = new RectBoundedBox(positionX, positionY, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        
        Image img = ImageUtils.loadImage("Resources/img/sprites_without_border.png");
        down  = ImageUtils.crop(img, 271, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        right  = ImageUtils.crop(img, 271, 123, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        up = ImageUtils.crop(img, 331, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        left = ImageUtils.crop(img, 301, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
    }

    public void move(Direction direction) {
        move(1, direction);
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean isColliding(Entity b) {
        RectBoundedBox otherEntityBoundary = (RectBoundedBox) b.getBoundingBox();
        return playerBoundary.checkCollision(otherEntityBoundary);
    }

    @Override
    public void draw() {
    	if(this.currentDirection == null)
    		this.currentDirection = Direction.UP;
    	switch (this.currentDirection) {
		case DOWN:
			Sandbox.getGraphicsContext().drawImage(this.down, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		case LEFT:
			Sandbox.getGraphicsContext().drawImage(this.left, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		case RIGHT:
			Sandbox.getGraphicsContext().drawImage(this.right, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		case UP:
			Sandbox.getGraphicsContext().drawImage(this.up, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		default:
			Sandbox.getGraphicsContext().drawImage(this.down, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
    	
    	}
    }

    @Override
    public void die() {
    	this.stats.addDeaths(1);
    	Sandbox.stopGame(false);
    }

    private boolean checkCollisions(int x, int y) {
    	playerBoundary.setPosition(x, y);

        for (Entity e : Sandbox.getEntities()) {
            if (e != this && isColliding(e) && !e.isPlayerCollisionFriendly()) {
//            	playerBoundary.setPosition(positionX, positionY);
                return true;
            }
            if(e != this && isColliding(e)) {
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
                	if(!checkCollisions(positionX, positionY - steps)) {
	                    positionY -= steps;
	                    currentDirection = Direction.UP;
                	}
                    break;
                case DOWN:
                	if(!checkCollisions(positionX, positionY + steps)) {
                		positionY += steps;
	                    currentDirection = Direction.DOWN;
                	}
                    break;
                case LEFT:
                	if(!checkCollisions(positionX - steps, positionY)) {
                		positionX -= steps;
	                    currentDirection = Direction.LEFT;
                	}
                    break;
                case RIGHT:
                	if(!checkCollisions(positionX + steps, positionY)) {
                		 positionX += steps;
	                    currentDirection = Direction.RIGHT;
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
}
