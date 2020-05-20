package Client.Model;

import Client.Model.Heroes.Hero;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    final private String nick;
    final private byte[] passhash;
    private ArrayList<Hero> heroes;

    public Player(String nick, byte[] hash) {
        this.heroes = new ArrayList<>(4);
        this.nick = nick;
        this.passhash = hash;
    }

    public String getNick() {
        return nick;
    }

    public byte[] getPasshash() {
        return passhash;
    }

    public void addHero(Hero hero) {
        if (hero.getOwner() == this) {
            heroes.add(hero);
        }
    }
}
