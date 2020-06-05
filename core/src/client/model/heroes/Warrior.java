package client.model.heroes;

import client.model.Player;
import client.model.skills.Melee;
import client.model.skills.Stay;
import client.model.skills.Stomp;
import client.model.skills.Walk;

public class Warrior extends Hero {

    final int id;

    public Warrior(Player owner, int y, int x) {
        super(owner, 120, 100, 100, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Stay());
        this.skills.add(new Melee());
        this.skills.add(new Stomp());
    }
}
