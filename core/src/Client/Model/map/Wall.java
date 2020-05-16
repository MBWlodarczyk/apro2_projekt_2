package Client.Model.map;

public class Wall extends Obstacle {
    public Wall(int y,int x){
        this.x = x;
        this.y = y;
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }
}
