package Client.Controller;

import java.util.Queue;

public class GameEngine {
    public static boolean checkMove(Move move, Queue<Move> moves){
        //check if tile is crossable
        /*
        if(!move.getWhere().getObstacle().isCrossable()){
            System.out.println("This tile is not crossable");
            return true;
        }

         */
        /*
        if(move.getWhere().getHero() != null){
            System.out.println("This tile is occupied");
            return true;
        }
        */
        // check if hero has moved yet.
        for (Move m:moves) {
            if(!m.equals(move) && m.getWho().equals(move.getWho())){
                System.out.println("This hero has made a move already");
                return true;
            }
        }
        /*
        // check if tile is occupied
        for (Move m:moves) {
            if(move.getWhere().equals(m.getWhere())){
                System.out.println("This tile will be occupied");
                return true;
            }
        }
        */
        return false;
    }
}
