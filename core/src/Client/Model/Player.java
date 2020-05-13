package Client.Model;

import Client.Model.Heroes.Hero;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    final private int id;
    private ArrayList<Hero> heroes;
    private String nick;
    private static int idGen;

    public Player(String nick) {
        this.id = idGen++;
        this.heroes = new ArrayList<>(4);
        this.nick = nick;
    }
}
