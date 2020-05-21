package Client.Model.obstacles;
/**
 * Class representing single wall on map.
 */
public class Wall extends Obstacle {
    public Wall(int y, int x) {
        super(y, x);
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }

    @Override
    public String toString() {
        return "Wall";
    }
}
