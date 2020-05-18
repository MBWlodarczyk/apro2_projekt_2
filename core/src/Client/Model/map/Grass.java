package Client.Model.map;

public class Grass extends Obstacle {
    public Grass(int y,int x){
        super("field/grass.png", x, y);
        this.x = x;
        this.y = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }
}
