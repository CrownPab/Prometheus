package prometheus.enemies;

import javafx.scene.image.Image;
import prometheus.constants.Direction;
import prometheus.constants.GlobalConstants;
import prometheus.entity.Entity;
import prometheus.entity.KillableEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.projectiles.WizardProjectile;
import prometheus.scenes.Sandbox;
import prometheus.utils.ImageUtils;

public class Wizard implements KillableEntity {

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
	public long lastShot;

    public Wizard() {
        init(64,64);
    }

    public Wizard(int posX, int posY) {
        init(posX, posY);
        health = 10;
        isAlive = true;
    }
    
    public void shoot(int speed) {    
    	Sandbox.addEntityToGame(new WizardProjectile(positionX, positionY, getDirectionTo(Sandbox.getPlayer()), speed));
    }

    private void init(int x, int y) {
        name = "Wizard";
        positionX = x;
        positionY = y;

        boundry = new RectBoundedBox(positionX, positionY, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        
        Image img = ImageUtils.loadImage("Resources/img/sprites_without_border.png");
//        down  = ImageUtils.crop(img, 0, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
//        left  = ImageUtils.crop(img, 30, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
//        up = ImageUtils.crop(img, 60, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
//        right = ImageUtils.crop(img, 90, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        down  = ImageUtils.crop(img, 271, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        right  = ImageUtils.crop(img, 271, 123, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        up = ImageUtils.crop(img, 331, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        left = ImageUtils.crop(img, 301, 93, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        this.lastShot = System.currentTimeMillis();
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
    	Sandbox.getPlayer().getStats().addKills(1);
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
        return false;
    }

	@Override
	public void onCollision(Entity e) {

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
}