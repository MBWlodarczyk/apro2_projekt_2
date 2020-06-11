package client.model.heroes;

import client.model.Player;
import client.model.skills.*;

public class Paladin extends Hero {
    final int id;

    public Paladin(Player owner, int y, int x) {
        super(owner, 120, 120, 120, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Stay());
        this.skills.add(new Melee(-20));
        this.skills.add(new SettingTrap());
        this.skills.add(new Heal(false));
    }
}
