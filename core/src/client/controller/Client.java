package client.controller;

import client.model.Player;
import client.model.heroes.*;
import client.model.map.Field;
import client.model.skills.Walk;
import client.view.SwordGame;
import server.Answer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static client.controller.Inputs.*;

/**
 * Class to implement client server communication on client side
 */
public class Client {
    final Object lock = new Object();
    public ObjectInputStream is;
    public ObjectOutputStream os;
    public Socket sock;
    public boolean isReceived = false;
    public boolean wrongPass;
    public Player player;
    boolean exit = false;
    private Turn send;
    private Answer received;
    private boolean isSend = false;

    public Client(SwordGame swordGame,String host,int port, final boolean init) throws Exception {

        Socket s = new Socket(host, port);
        sock = s;
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        player = new Player(nick, password);
        swordGame.player = player;

        send = new Turn(player);

        receive();

        if (init) {
            createTurn(send, swordGame);
        }

        send();

        Thread t = new Thread(() -> {
            try {
                receive();// init message saying everything is ok
                isReceived = true;
                isSend = received.hasSendMove();
                wrongPass = received.isWrongNickPassword();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            while (!exit) {
                synchronized (lock) {
                    try {
                        if (send != null && !isSend && sendTurn) {
                            sendTurn = false;
                            send();
                            isReceived = false;
                        } else {
                            lock.wait(500); //optimalization - while not running whole time
                        }
                        if (isSend) {
                            receive();
                            isReceived = true;
                            isSend = false;
                            send.clearMoves();


                        }
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    public Turn getSend() {
        return send;
    }

    public boolean isSend() {
        return isSend;
    }

    public Answer getReceived() {
        return this.received;
    }

    /**
     * Method initing turn with chosen hero info (used during init)
     *
     * @param turn Turn to init
     * @param game game object
     */
    private void createTurn(Turn turn, SwordGame game) {
        if (chosen[0]) {
            Archer hero = new Archer(turn.getOwner(), 5, 5);
            turn.addMove((new Move(hero, new Field(1, 1), new Field(1, 1), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (chosen[1]) {
            Necromancer hero = new Necromancer(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(2, 2), new Field(2, 2), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (chosen[2]) {
            Paladin hero = new Paladin(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(3, 3), new Field(3, 3), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (chosen[3]) {
            Priest hero = new Priest(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(4, 4), new Field(4, 4), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (chosen[4]) {
            Warrior hero = new Warrior(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(5, 5), new Field(5, 5), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (chosen[5]) {
            Wizard hero = new Wizard(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(6, 6), new Field(6, 6), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
    }

    public void dispose() {
        exit = true;
    }

    public synchronized void send() throws IOException {
        System.out.println("Sending...");
        os.reset();
        os.writeObject(send);
        send.clearMoves();
        isSend = true;
        os.flush();
    }

    public synchronized void receive() throws IOException, ClassNotFoundException {
        received = (Answer) is.readObject();
        System.out.println("Reading...");
    }


}
