package client.model.heroes;

import client.model.Player;
import client.model.skills.Walk;

public class Necromancer extends Hero {
    final int id;

    public Necromancer(Player owner, int y, int x) {
        super(owner, 110, 100, 50, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
    }
    public String toString() {
        return "Necromancer";
    }
}