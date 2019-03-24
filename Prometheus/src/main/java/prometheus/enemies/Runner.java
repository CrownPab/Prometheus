package prometheus.enemies;

import javafx.scene.image.Image;
import prometheus.GameLoop;
import prometheus.constants.Direction;
import prometheus.constants.GlobalConstants;
import prometheus.entity.Entity;
import prometheus.entity.KillableEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.entity.player.Player;
import prometheus.scenes.Sandbox;
import prometheus.utils.ImageUtils;

public class Runner implements KillableEntity {

    private int health;
    private boolean isAlive;
    RectBoundedBox boundry;

    Image up;
    Image down;
    Image left;
    Image right;

    public Direction currentDirection;

    public int positionX = 0;
    public int positionY = 0;

    String name;

    public Runner() {
        init(64,64);
    }

    public Runner(int posX, int posY) {
        init(posX, posY);
        health = 10;
        isAlive = true;
    }
    
    private void init(int x, int y) {
        name = "Runner";
        positionX = x;
        positionY = y;

        boundry = new RectBoundedBox(positionX, positionY, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        
        Image img = ImageUtils.loadImage("Resources/img/sprites_without_border.png");
        down  = ImageUtils.crop(img, 271, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        right  = ImageUtils.crop(img, 271, 123, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        up = ImageUtils.crop(img, 331, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        left = ImageUtils.crop(img, 301, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
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
        return boundry.checkCollision(otherEntityBoundary);
    }

    @Override
    public void draw() {
    	if(this.currentDirection == null)
    		this.currentDirection = Direction.UP;
    	this.currentDirection = getDirectionTo(Sandbox.getPlayer());
    	
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
		case UP_LEFT:
			Sandbox.getGraphicsContext().drawImage(this.up, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		case UP_RIGHT:
			Sandbox.getGraphicsContext().drawImage(this.up, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
		default:
			Sandbox.getGraphicsContext().drawImage(this.down, positionX, positionY, GlobalConstants.PLAYER_WIDTH * GlobalConstants.PLAYER_SCALE, GlobalConstants.PLAYER_HEIGHT * GlobalConstants.PLAYER_SCALE);
			break;
    	
    	}
    }

    @Override
    public void die() {
    	this.isAlive = false;
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

    @Override
    public RectBoundedBox getBoundingBox() {
        boundry.setPosition(positionX, positionY);
        return boundry;
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return true;
    }
    public Direction getDirectionTo(Entity e) {

		int deltaX = e.getPositionX() - this.positionX;
		int deltaY = e.getPositionY() - this.positionY;
		
		if(deltaX == 0) {
			if(deltaY == 0) {
				return Direction.DOWN;
			}
			else if(deltaY > 0) {
				return Direction.DOWN;
			}
			else if(deltaY < 0) {
				return Direction.UP;
			}
		}
		
		if(deltaY == 0) {
			if(deltaX == 0) {
				return Direction.DOWN;
			}
			else if(deltaX > 0) {
				return Direction.RIGHT;
			}
			else if(deltaX < 0) {
				return Direction.LEFT;
			}
		}
		
		if(deltaY > 0 && deltaX > 0)
			return Direction.DOWN_RIGHT;
		
		if(deltaY > 0 && deltaX < 0)
			return Direction.DOWN_LEFT;
		
		if(deltaY < 0 && deltaX > 0)
			return Direction.UP_RIGHT;
		
		if(deltaY < 0 && deltaX < 0)
			return Direction.UP_LEFT;
		
		return Direction.DOWN;
	}
   
    @Override
	public void onCollision(Entity e) {
		if(e instanceof Player) {
			((Player) e).reduceHealth(1);
		}
	}
    
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
			case DOWN_RIGHT:
				positionX += steps;
				positionY += steps;
				currentDirection = Direction.DOWN_RIGHT;
				break;
			case DOWN_LEFT:
				positionX -= steps;
				positionY += steps;
				currentDirection = Direction.DOWN_LEFT;
				break;
			case UP_LEFT:
				positionX -= steps;
				positionY -= steps;
				currentDirection = Direction.UP_LEFT;
				break;
			case UP_RIGHT:
				positionX += steps;
				positionY -= steps;
				currentDirection = Direction.UP_RIGHT;
				break;
			default:
				break;
			}
			checkCollisions(positionX, positionY);
		}

	}
	public boolean checkCollisions(int x, int y) {
    	
    	boundry.setPosition(x, y);
    	
        for (Entity e : Sandbox.getEntities()) {
            if(e != this && isColliding(e)) {
            	this.onCollision(e);
            }
        }
        return false;
    }
}