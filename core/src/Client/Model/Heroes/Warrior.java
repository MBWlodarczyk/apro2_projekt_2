package Client.Model.Heroes;

import Client.Model.Player;

public class Warrior extends Hero {
    final int id;

    public Warrior(Player owner) {
        super(owner, 110, 100, 3);
        id = idGen++;
    }
}
