package Client.Model.obstacles;

public class Bush extends Obstacle {
    public Bush(int y, int x) {
        super(y, x);
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }
}
