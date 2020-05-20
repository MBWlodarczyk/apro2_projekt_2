package Client.Model.map;

public class Trap extends Obstacle {
    private int type; // czy zadaje damage, czy może zatrzymuje gracza itd.
    protected int damage;
    private boolean immobilize; // czy zatrzymuje gracza

    public Trap(int damage) {
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = true;
        this.damage = damage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

