package Client.Controller;

import Client.Model.Player;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class representing single turn consisting of 4 moves.
 */
public class Turn implements Serializable {
    private Player owner;
    private Queue<Move> moves = new PriorityQueue<>();

    public Queue<Move> getMoves() {
        return moves;
    }

    public Turn(Player owner) {
        this.owner = owner;
        this.moves = new LinkedList<Move>();
    }
    public void addMove(Move move) {
        moves.add(move);
    }

    @Override
    public String toString() {
        return "Turn{" +
                "owner=" + owner +
                ", moves=" + moves +
                '}';
    }

    public void clearMoves() {
        this.moves = new LinkedList<Move>();
    }
}
