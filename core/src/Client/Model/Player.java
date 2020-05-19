package Client.Model;

import Client.Model.Heroes.Hero;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private ArrayList<Hero> heroes;
    final private String nick;
    final private byte[] passhash;

    public Player(String nick,byte[] hash) {
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

    public boolean addHero(Hero hero) {
        if(hero.getOwner()==this){
        return heroes.add(hero);
    }
        return false;
}
}
