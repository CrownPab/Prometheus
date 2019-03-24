package prometheus.entity;

import prometheus.entity.boundedbox.RectBoundedBox;

public interface Entity {
    boolean isColliding(Entity b);
    boolean isPlayerCollisionFriendly();
    void onCollision(Entity e);
    void draw();
    int getPositionX();
    int getPositionY();
    RectBoundedBox getBoundingBox();
    
}
