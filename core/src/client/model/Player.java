package client.model;

import client.model.heroes.Hero;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private static int idGen;
    final private int id;
    private ArrayList<Hero> heroes;
    private String nick;

    public Player(String nick) {
        this.id = idGen++;
        this.heroes = new ArrayList<>(4);
        this.nick = nick;
    }
}
