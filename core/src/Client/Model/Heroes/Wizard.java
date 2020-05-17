package Client.Model.Heroes;

import Client.Model.Player;
import Client.Model.Skills.FireBall;
import Client.Model.Skills.Walk;

public class Wizard extends Hero {
    final int id;

    public Wizard(Player owner,int y, int x) {
        super("heroes/wizard.png",x,y,owner, 110, 100, 100, 5);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new FireBall(10));
    }
}