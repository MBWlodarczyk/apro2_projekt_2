package Client.Controller;

import Client.GUI.SwordGame;
import Client.Model.Heroes.*;
import Client.Model.Player;
import Client.Model.Skills.Walk;
import Client.Model.map.Field;
import Client.Model.map.GameMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * test
 */
public class Client {
    public ObjectInputStream is;
    public ObjectOutputStream os;
    private Turn send;
    private GameMap received;
    private boolean isSend = false;

    public Client(SwordGame game, boolean init) throws Exception {
        //Todo reconnecting marked as not init;
        Socket s = new Socket("127.0.0.1", 1701);
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        Object lock = new Object();
        Player player = new Player(game.nick, game.password);
        game.player = player;
        send = new Turn(player);
        if (init) {
            createTurn(send, game);
            received = (GameMap) is.readObject();
            System.out.println("Reading...");
            System.out.println("Sending...");
            os.reset();
            os.writeObject(send);
            send.clearMoves();
            isSend = true;
            os.flush();
        }
        final Object finalLock = lock;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    received = (GameMap) is.readObject();
                    System.out.println("Reading...");
                    isSend = false;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (true) {
                    synchronized (finalLock) {
                        if (send != null && !isSend && send.getMoves().size() == 4) {
                            try {
                                System.out.println("Sending...");
                                os.reset();
                                os.writeObject(send);
                                isSend = true;
                                os.flush();
                                send.clearMoves();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (isSend) {
                            try {
                                received = (GameMap) is.readObject();
                                System.out.println("Reading...");
                                isSend = false;
                                send.clearMoves();
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.out.println(received);
                        }
                    }
                }

            }
        }
        );
        t.start();
    }

    public Turn getSend() {
        return send;
    }

    public GameMap getReceived() {
        return this.received;
    }

    private void createTurn(Turn turn, SwordGame game) {
        if (game.chosen[0]) {
            Archer hero = new Archer(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(1, 1), new Field(1, 1), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[1]) {
            Necromancer hero = new Necromancer(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(2, 2), new Field(2, 2), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[2]) {
            Paladin hero = new Paladin(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(3, 3), new Field(3, 3), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[3]) {
            Priest hero = new Priest(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(4, 4), new Field(4, 4), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[4]) {
            Warrior hero = new Warrior(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(5, 5), new Field(5, 5), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[5]) {
            Wizard hero = new Wizard(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(6, 6), new Field(6, 6), new Walk(5))));
            turn.getOwner().addHero(hero);
        }

    }
}
