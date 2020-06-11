package client.model.heroes;

import client.model.Player;
import client.model.skills.*;

public class Necromancer extends Hero {
    final int id;

    public Necromancer(Player owner, int y, int x) {
        super(owner, 100, 90, 90, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Stay());
        this.skills.add(new Necromancy());
        this.skills.add(new Heal(false));
        this.skills.add(new SettingTrap());
    }
}