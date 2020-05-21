package Client.Controller;

import Client.Model.Heroes.Hero;
import Client.Model.Player;
import Client.Model.Skills.Skill;
import Client.Model.map.Field;

import java.io.Serializable;

/**
 * Class representing single move
 */
public class Move implements Serializable {
    private Player whose;
    private Hero who;
    private Field from;
    private Field where;
    private Skill what;

    public Move(Hero who, Field where, Field from, Skill what) {
        this.whose = who.getOwner();
        this.who = who;
        this.from = from;
        this.where = where;
        this.what = what;
    }

    public Player getWhose() {
        return whose;
    }

    public Hero getWho() {
        return who;
    }

    public Field getWhere() {
        return where;
    }

    public Field getFrom() {
        return from;
    }

    public Skill getWhat() {
        return what;
    }


        @Override
    public String toString() {
            return "[" + getWhose().getNick() + "] " + getWho().toString() + " standing on [" + getFrom().getY() + ", " + getFrom().getX() + "] used " + getWhat().toString() + " on [" + getWhere().getY() + ", " + getWhere().getX() + "]";
    }
}
