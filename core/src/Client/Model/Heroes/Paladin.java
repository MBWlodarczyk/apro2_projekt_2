package Client.Model.Heroes;

import Client.Model.Player;
import Client.Model.Skills.Walk;

public class Paladin extends Hero {
    final int id;

    public Paladin(Player owner,int y, int x) {
        super("heroes/paladin/png",x,y,owner, 110, 100, 100, 5);
        id = idGen++;
        this.skills.add(new Walk(3));
    }
}
