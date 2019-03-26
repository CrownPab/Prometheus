package prometheus.entity;

public interface KillableEntity extends Entity {
	
	
	/**
	 * set entity death status aand removes from board
	 */
    public void die(); 	
    
    /**
	 * removes health from entity when attacked
	 */
    
    public void reduceHealth(int damage);	
    
    /**
	 * returns health
	 */
    
    public int getHealth();					
}
