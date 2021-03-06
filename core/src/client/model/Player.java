package client.model;

import client.model.heroes.Hero;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class to represent single player.
 */
public class Player implements Serializable {
    final private String nick;
    final private byte[] passhash;
    private ArrayList<Hero> heroes;
    private boolean turn = true;

    public Player(String nick, byte[] hash) {
        this.heroes = new ArrayList<>(4);
        this.nick = nick;
        this.passhash = hash;
    }

    public String getNick() {
        return nick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(nick, player.nick) &&
                Arrays.equals(passhash, player.passhash);
    }


    public byte[] getPasshash() {
        return passhash;
    }

    public void addHero(Hero hero) {
        if (hero.getOwner() == this) {
            heroes.add(hero);
        }
    }

    public boolean hasMove() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
