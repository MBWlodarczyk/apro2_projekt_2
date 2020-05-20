package Client.Model.Heroes;

import Client.Model.Player;
import Client.Model.Skills.Walk;

public class Archer extends Hero {
    final int id;
    private int x, y;

    public Archer(Player owner, int y, int x) {
        super(owner, 110, 100, 50, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
