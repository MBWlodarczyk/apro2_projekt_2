package Client.Model;

import Client.Model.Heroes.Hero;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private ArrayList<Hero> heroes;
    private String nick;

    public Player(String nick) {
        this.heroes = new ArrayList<>(4);
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }
}
