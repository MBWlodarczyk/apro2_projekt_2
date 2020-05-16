package Client.Model.Heroes;

import Client.Model.Player;
import Client.Model.Skills.Walk;

public class Archer extends Hero {
    final int id;

    public Archer(Player owner) {
        super(owner, 110, 100, 50, 5);
        id = idGen++;
        this.skills.add(new Walk(3));
    }
}
