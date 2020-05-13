package Client.Controller;

import Client.Model.Player;

import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * test
 */
public class Turn implements Serializable {
    private Player owner;
    private PriorityQueue<Move> moves;
    public int x;
    public int y;
public Turn(int x, int y){
    this.x=x;
    this.y=y;
}
//    public Turn(Player owner) {
//        this.owner = owner;
//        this.moves = new PriorityQueue<Move>();
//    }
    public void addMove(Move move){
        moves.add(move);
    }
}
