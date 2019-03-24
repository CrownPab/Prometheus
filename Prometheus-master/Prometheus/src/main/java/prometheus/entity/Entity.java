package prometheus.entity;

import prometheus.entity.boundedbox.RectBoundedBox;

public interface Entity {
    boolean isColliding(Entity b);
    boolean isPlayerCollisionFriendly();
    boolean onCollision(Entity e);
    void draw();
    void removeFromScene();
    int getPositionX();
    int getPositionY();
    RectBoundedBox getBoundingBox();
    
}
