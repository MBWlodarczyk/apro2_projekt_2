package Client.Model.map;

public class Bush extends Obstacle {
    public Bush(){
        this.isFixed = true;
        this.isVisible = true;
        this.isCrossable = false;
        this.isAttackable = false;
    }
}
