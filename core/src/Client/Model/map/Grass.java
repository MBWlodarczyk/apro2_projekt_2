package Client.Model.map;

public class Grass extends Obstacle {
    public Grass(){
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
