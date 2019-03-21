package prometheus.entity;

import prometheus.constants.Direction;

public interface MovingEntity extends Entity {
	
    public void move(int steps, Direction direction);

}
