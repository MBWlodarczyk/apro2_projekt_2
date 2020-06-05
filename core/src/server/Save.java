package server;

import client.controller.Turn;
import client.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent save of the game - contains all field that need to be saved.
 */
public class Save implements Serializable {
    public final Answer answer;
    public final ArrayList<Turn> turns;
    public final int playerNumber;
    public final ArrayList<Player> players;
    public int initPlayer;
    public boolean gameInit;

    public Save(Answer answer, ArrayList<Turn> turns, int playerNumber, ArrayList<Player> players, int initPlayer, boolean gameInit) {
        this.answer = answer;
        this.turns = turns;
        this.playerNumber = playerNumber;
        this.players = players;
        this.initPlayer = initPlayer;
        this.gameInit = gameInit;
    }

}
