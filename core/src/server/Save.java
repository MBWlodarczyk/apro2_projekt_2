package server;

import client.controller.Turn;
import client.model.Player;
import client.model.map.GameMap;

import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable {
    public final Answer answer;
    public final ArrayList<Turn> turns;
    public final int playerNumber;
    public final ArrayList<Player> players;

    public Save(Answer answer, ArrayList<Turn> turns, int playerNumber, ArrayList<Player> players) {
        this.answer = answer;
        this.turns = turns;
        this.playerNumber = playerNumber;
        this.players = players;
    }

}
