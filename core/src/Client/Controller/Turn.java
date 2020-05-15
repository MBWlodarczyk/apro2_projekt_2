package Client.Controller;

import Client.Model.Player;

import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * Class representing single turn consisting of 4 moves.
 */
public class Turn implements Serializable {
    public int x;
    public int y;
    private Player owner;
    private PriorityQueue<Move> moves = new PriorityQueue<>();

    public Turn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //    public Turn(Player owner) {
//        this.owner = owner;
//        this.moves = new PriorityQueue<Move>();
//    }
    public void addMove(Move move) {
        moves.add(move);
    }
}
