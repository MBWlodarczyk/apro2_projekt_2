package client.model.heroes;

import client.model.Player;
import client.model.skills.Arrow;
import client.model.skills.Stay;
import client.model.skills.Walk;

public class Archer extends Hero {
    final int id;

    public Archer(Player owner, int y, int x) {
        super(owner, 110, 100, 50, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Stay());
        this.skills.add(new Arrow());
    }
    public String toString() {
        return "Archer";
    }
}
