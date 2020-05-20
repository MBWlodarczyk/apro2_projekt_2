package Client.Model.obstacles;

public class Grass extends Obstacle {
    public Grass(int y, int x) {
        super(y, x);
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }

    @Override
    public String toString() {
        return "Grass";
    }
}
