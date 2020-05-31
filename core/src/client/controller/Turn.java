package client.controller;

import client.model.Player;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class representing single turn consisting of 4 moves.
 */
public class Turn implements Serializable {
    private Player owner;
    private Queue<Move> moves;

    public Turn(Player owner) {
        this.owner = owner;
        this.moves = new LinkedList<>();
    }

    public Queue<Move> getMoves() {
        return moves;
    }

    public Player getOwner() {
        return owner;
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public void rmMove() {
        moves.remove();
    }

    public void removeLast(){
        if(moves.isEmpty())
            return;
        Queue<Move> moves1 = new LinkedList<>();
        while(moves.size() > 1){
            moves1.add(moves.remove());
        }
        this.moves = moves1;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Move m : moves)
            sb.append(m.toString() + '\n');
        return sb.toString();
    }

    public void clearMoves() {
        this.moves = new LinkedList<>();
    }

}
