package Client.Controller;

import Client.Model.Field;
import Client.Model.Heroes.Hero;
import Client.Model.Player;
import Client.Model.Skills.Skill;

import java.io.Serializable;

public class Move implements Serializable {
    private Player whose;
    private Hero who;
    private Field where;
    private Skill what;

    public Move(Player whose, Hero who, Field where, Skill what) {
        this.whose = whose;
        this.who = who;
        this.where = where;
        this.what = what;
    }
}
