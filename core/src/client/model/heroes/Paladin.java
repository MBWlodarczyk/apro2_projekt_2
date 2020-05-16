package client.model.heroes;

import client.model.Player;
import client.model.skills.Walk;

public class Paladin extends Hero {
    final int id;

    public Paladin(Player owner) {
        super(owner, 110, 100, 100);
        id = idGen++;
        this.skills.add(new Walk(3));
    }
}
