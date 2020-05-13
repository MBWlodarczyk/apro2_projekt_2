package Client.Model.Heroes;

import Client.Model.Player;

public class Paladin extends Hero {
    final int id;

    public Paladin(Player owner) {
        super(owner, 110, 100, 3);
        id = idGen++;
    }
}
