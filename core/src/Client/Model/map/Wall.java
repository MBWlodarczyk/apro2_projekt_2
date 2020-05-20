package Client.Model.map;

public class Wall extends Obstacle {
    public Wall(){
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
