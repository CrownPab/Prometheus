package prometheus.entity.player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import prometheus.GameLoop;
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

    public Player() {
        init(64, 64);
    }

    public Player(int posX, int posY) {
        init(posX, posY);
        health = 100;
        isAlive = true;
    }

    private void init(int x, int y) {
        name = "Player";
        this.health = 100;

        positionX = x;
        positionY = y;

        playerBoundary = new RectBoundedBox(positionX, positionY, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
        
        Image img = ImageUtils.loadImage("Resources/img/sprites_without_border.png");
//        down  = ImageUtils.crop(img, 0, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
//        left  = ImageUtils.crop(img, 30, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
//        up = ImageUtils.crop(img, 60, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
//        right = ImageUtils.crop(img, 90, 0, GlobalConstants.PLAYER_WIDTH, GlobalConstants.PLAYER_HEIGHT);
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
       // playerBoundary.setPosition(positionX, positionY);
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
//    	GameLoop.animTimer.stop();
    	Sandbox.stopGame();
    }

    private boolean checkCollisions(int x, int y) {
    	playerBoundary.setPosition(x, y);

        for (Entity e : Sandbox.getEntities()) {
            if (e != this && isColliding(e) && !e.isPlayerCollisionFriendly()) {
            	playerBoundary.setPosition(positionX, positionY);
                /*
                System.out.println("Player x="+getPositionX()+" y="
                        +getPositionY()+" colliding with x="+e.getPositionX()
                        +" y="+e.getPositionY());
                */
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
    public void removeFromScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        playerBoundary.setPosition(positionX, positionY);
        return playerBoundary;
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return true;
    }

	@Override
	public boolean onCollision(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
}
