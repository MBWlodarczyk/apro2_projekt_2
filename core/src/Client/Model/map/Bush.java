package Client.Model.map;

public class Bush extends Obstacle {
    public Bush(int y,int x){
        super("field/bush.png", x, y);
        this.x = x;
        this.y = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }
}
