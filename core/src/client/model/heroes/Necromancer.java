package Client.Model.Heroes;

import Client.Model.Player;
import Client.Model.Skills.Walk;

public class Necromancer extends Hero {
    final int id;

    public Necromancer(Player owner) {
        super(owner, 110, 100, 100, 5);
        id = idGen++;
        this.skills.add(new Walk(3));
    }
}