package Client.Model.Heroes;

import Client.Model.Player;
import Client.Model.Skills.Walk;

public class Priest extends Hero {
    final int id;

    public Priest(Player owner,int y, int x) {
        super("heroes/priest.png",x,y,owner, 110, 100, 100, 5);
        id = idGen++;
        this.skills.add(new Walk(3));
    }
}
