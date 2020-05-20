package Client.Model.terrain;

public class Water extends Terrain {

    private int x,y;

    public Water(int y, int x) {
        super(y, x);
        this.x = x;
        this.y = y;
    }
}
