package client.model.obstacles;

/**
 * Class representing single trap on map.
 */
public class Trap extends Obstacle {

    //TODO add owned to trap in order to recognize whose trap it is

    public Trap(int damage) {
        super();
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = true;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isImmobilize() {
        return immobilize;
    }

    public void setImmobilize(boolean immobilize) {
        this.immobilize = immobilize;
    }

}

