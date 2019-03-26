package prometheus.entity;

import prometheus.entity.boundedbox.RectBoundedBox;

public interface Entity {
	
    boolean isColliding(Entity b); 		 // detects collision with another entity
    boolean isPlayerCollisionFriendly(); // determines whether collision causes damage
    void onCollision(Entity e); 
    void draw();				         // draw entity on screen
    int getPositionX();
    int getPositionY();
    RectBoundedBox getBoundingBox();
    
}
