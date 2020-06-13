package client.model.heroes;

import client.model.Player;
import client.model.skills.*;

public class Warrior extends Hero {
    final int id;

    public Warrior(Player owner, int y, int x) {
        super(owner, 120, 120, 120, 5, y, x,70);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Stay());
        this.skills.add(new Melee(-20));
        this.skills.add(new Stomp());
        this.skills.add(new Jump(3));
    }
}
