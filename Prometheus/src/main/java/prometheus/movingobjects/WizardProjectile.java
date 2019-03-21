package prometheus.movingobjects;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import prometheus.GameLoop;
import prometheus.constants.Direction;
import prometheus.constants.GlobalConstants;
import prometheus.entity.Entity;
import prometheus.entity.MovingEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.entity.player.Player;
import prometheus.entity.staticobjects.AcidPool;
import prometheus.entity.staticobjects.Wall;
import prometheus.scenes.Sandbox;

public class WizardProjectile implements MovingEntity{

    public int positionX = 0;
    public int positionY = 0;
    private int height;
    private int width;
    RectBoundedBox entityBoundary;
    public Direction currentDirection = Direction.UP;
    public boolean alive;
    public DoubleProperty x  = new SimpleDoubleProperty();
    public DoubleProperty y  = new SimpleDoubleProperty();

    
    public WizardProjectile(int posx, int posy, Direction d) {
        positionX = posx;
    	positionY = posy;
    	width = 16;
    	height = 16;
    	this.currentDirection = d;
    	this.alive = true;
        entityBoundary = new RectBoundedBox(positionX, positionY, width, height);
        x.set(this.positionX);
        y.set(this.positionY);
        Timeline tl = createTimeline(Sandbox.getPlayer());
        tl.setOnFinished(e -> alive = false);
        tl.play();
    }
    
    public boolean isAlive(){
    	return alive;
    }
    
    @Override
    public boolean isColliding(Entity b) {
        RectBoundedBox otherEntityBoundary = b.getBoundingBox();
        return entityBoundary.checkCollision(otherEntityBoundary);
    }

    @Override
    public void draw() {
    	Sandbox.getGraphicsContext().fillOval(x.doubleValue()+5, y.doubleValue()+5, 10, 10);
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
        return entityBoundary;
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return true;
    }

	@Override
	public boolean onCollision(Entity e) {
		if(e instanceof Wall) {
			this.alive = false;
		}
		if(e instanceof AcidPool) {
			this.alive = false;
		}
		if(e instanceof Player) {
			((Player) e).reduceHealth(10);
			this.alive = false;
		}
		
		return true;
	}
	
    public boolean checkCollisions(int x, int y) {
    	
    	entityBoundary.setPosition(x, y);
    	
        for (Entity e : Sandbox.getEntities()) {
            if(e != this && isColliding(e)) {
            	this.onCollision(e);
            }
        }
        return false;
    }
    
    public Timeline createTimeline(Entity e) {
    	 double distance = Math.sqrt(Math.pow(x.doubleValue() - e.getPositionX(), 2) + Math.pow(y.doubleValue() - e.getPositionY(), 2));
    	 double maxDistance = Math.sqrt(Math.pow(GlobalConstants.CANVAS_WIDTH, 2) + Math.pow(GlobalConstants.CANVAS_HEIGHT, 2));

    	 Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                    new KeyValue(x, this.positionX),
                    new KeyValue(y, this.positionY)
            ),
            new KeyFrame(Duration.seconds(((distance/maxDistance)) * 10),
                    new KeyValue(x, e.getPositionX()),
                    new KeyValue(y, e.getPositionY())
            )
        );
        timeline.setCycleCount(1);
        
        return timeline;
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

//            if ((positionX < 10 || positionX > GlobalConstants.CANVAS_WIDTH-10 || positionY < 10 || positionY > GlobalConstants.CANVAS_HEIGHT-10));
//            	this.alive = false;

			checkCollisions(positionX, positionY);
		}

	}
    

}
