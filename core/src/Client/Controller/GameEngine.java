package Client.Controller;

import java.util.Queue;

public class GameEngine {
    public static boolean checkMove(Move move, Queue<Move> moves){
        // check if hero has moved yet.
        for (Move m:moves) {
            if(!m.equals(move) && m.getWho().equals(move.getWho())){
                System.out.println("This hero has made a move already");
                return true;
            }
        }
        return false;
    }
}
