package Client.Controller;

import java.util.Iterator;

public class GameEngine {
    public static void checkMove(Move move, Client client){
        // looking for hero move.getWho()
        for (Move m:client.getSend().getMoves()) {
            if(!move.equals(m) && move.getWho().equals(m.getWho())){
                client.getSend().rmMove();
                System.out.println("This hero has made a move already");
            }
        }
    }
}
