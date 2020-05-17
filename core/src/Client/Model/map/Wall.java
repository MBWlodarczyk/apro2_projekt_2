package Client.Model.map;

public class Wall extends Obstacle {
    public Wall(int y,int x){
        super("field/wall.png", x, y);
        this.x = x;
        this.y = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }
}
