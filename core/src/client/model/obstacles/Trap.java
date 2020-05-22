package client.model.obstacles;
/**
 * Class representing single trap on map.
 */
public class Trap extends Obstacle {
    protected int damage;
    private int type; // czy zadaje damage, czy może zatrzymuje gracza itd.
    private boolean immobilize; // czy zatrzymuje gracza

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

