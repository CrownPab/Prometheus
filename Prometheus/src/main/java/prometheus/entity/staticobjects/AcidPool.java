package prometheus.entity.staticobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import prometheus.entity.Entity;
import prometheus.entity.StaticEntity;
import prometheus.entity.boundedbox.RectBoundedBox;
import prometheus.entity.player.Player;
import prometheus.scenes.Sandbox;
import prometheus.utils.ImageUtils;


public class AcidPool implements StaticEntity {
    public int positionX = 0;
    public int positionY = 0;
    private int height;
    private int width;
    private Color wallColor;
    private Image sprite;
    RectBoundedBox entityBoundary;


    public AcidPool (int x, int y) {
    	positionX = x;
    	positionY = y;

    	width = 16;
    	height = 16;
    	
        Image img = ImageUtils.loadImage("Resources/img/sprites_without_border.png");
        sprite  = ImageUtils.crop(img, 211, 123, 16, 16);
//    	sprite = new Sprite(this, 16, 0, 211, 123, 1, 16, 16, 2, false);
    	entityBoundary = new RectBoundedBox(positionX, positionY, width, height);
    }

    public void changeColor(Color color) {
        wallColor = color;
    }

    @Override
    public boolean isColliding(Entity b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw() {
    	Sandbox.getGraphicsContext().drawImage(sprite, positionX, positionY,31,31);
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
    public RectBoundedBox getBoundingBox()
    {
            return entityBoundary;
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return true;
    }

	@Override
	public boolean onCollision(Entity e) {
	    if(e instanceof Player){
	    	((Player) e).reduceHealth(10);
	    	System.out.println("Losing Health");
	    }
		return false;
	}

}