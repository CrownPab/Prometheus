package prometheus.entity;

public interface KillableEntity extends Entity {

    public void die();
    public void reduceHealth(int damage);
    public int getHealth();

}
