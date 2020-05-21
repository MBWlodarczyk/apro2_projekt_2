package Client.Model.Heroes;

import Client.Model.Player;
import Client.Model.Skills.Fireball;
import Client.Model.Skills.Walk;

public class Wizard extends Hero {
    final int id;

    public Wizard(Player owner, int y, int x) {
        super(owner, 110, 100, 50, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Fireball());
    }
    public String toString() {
        return "Wizard";
    }
}