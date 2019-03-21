package prometheus.entity.staticobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import prometheus.constants.GlobalConstants;
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
    private Color wallColor;
    private Image sprite;
    RectBoundedBox entityBoundary;


    public Wall (int x, int y) {
    	positionX = x;
    	positionY = y;

    	width = 16;
    	height = 16;
    	
        Image img = ImageUtils.loadImage("Resources/img/sprites_without_border.png");
        sprite  = ImageUtils.crop(img, 348, 123, 16, 16);
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
    	Sandbox.getGraphicsContext().drawImage(sprite, positionX, positionY, 30, 30);
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
        return false;
    }

	@Override
	public boolean onCollision(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

}
