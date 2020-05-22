package Server;

import Client.Controller.Turn;
import Client.Model.Heroes.Hero;
import Client.Model.Player;
import Client.Model.map.GameMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Main class to implement client server communication on server side
 */
public class Server {

    public final ArrayList<ServerThread> clients = new ArrayList<>();
    public final HashMap<ServerThread, Player> playersClients = new HashMap<>();
    public final ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Turn> turns = new ArrayList<>();
    public int playerNumber;
    public int initPlayer;
    boolean gameInit;
    private final GameMap map = new GameMap(22);
    private boolean exit=false;

    public Server(int playerNumber) throws IOException {

        this.playerNumber = playerNumber;
        ServerSocket server = new ServerSocket(1701);
        int i = 1;


        while (!exit) {
            Socket s = server.accept();

            String name = "client " + i;
            i++;
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            ServerThread t = new ServerThread(s, is, os, name,this);
            clients.add(t);
        }
    }


    public static void main(String[] args) throws IOException {
        new Server(2);
    }

    public void dispose(){
        exit = true;
    }

    public synchronized boolean check() throws IOException {
        boolean marker;
        marker = turns.size() == playerNumber;
        if (marker) {
            unlock();
            if (initPlayer == playerNumber) {
                sendToAll(true);
            }
            clearTurns();
        }
        return marker;
    }

    public synchronized void unlock() {
        for (ServerThread client : clients) {
            synchronized (client.lock) {
                client.lock.notify();
                client.reciever = false;
                System.out.println("Unlocking " + client.name);
            }
        }
    }

    public GameMap getMap() {
        return map;
    }

    public synchronized void sendToAll(boolean moves) throws IOException {

        if (moves) {
            for (Turn turn : turns) {
                map.move(map, turn.getMoves().poll());
            }
            for (Turn turn : turns) {
                map.move(map, turn.getMoves().poll());
            }
            for (Turn turn : turns) {
                map.move(map, turn.getMoves().poll());
            }
            for (Turn turn : turns) {
                map.move(map, turn.getMoves().poll());
            }
            turns.clear();
        }

        ArrayList<ServerThread> temp;
        temp = (ArrayList<ServerThread>) clients.clone();

        while (temp.size() != 0) {

            System.out.println("Sending");
            if (!temp.get(0).sock.isOutputShutdown()) {
                try {
                    temp.get(0).os.reset();
                    temp.get(0).os.writeObject(map);// sending object
                    temp.get(0).os.flush();
                    temp.remove(temp.get(0));
                } catch (SocketException e) {
                    temp.get(0).sock.close();
                    playersClients.remove(temp.get(0));
                    System.out.println("disconnect error" + temp.get(0).name);
                    removeClient(temp.get(0));
                    temp.remove(temp.get(0));
                }
            }

        }
    }

    public synchronized void removeClient(ServerThread client) {
        clients.remove(client);
    }

    public synchronized void init() {
        switch (initPlayer) {
            case 4:

                Turn turn = clients.get(3).recieved;
                clients.get(3).player = clients.get(3).recieved.getOwner();
                Hero hero1 = turn.getMoves().poll().getWho();
                Hero hero2 = turn.getMoves().poll().getWho();
                Hero hero3 = turn.getMoves().poll().getWho();
                Hero hero4 = turn.getMoves().poll().getWho();
                map.getMap()[1][20].setHero(hero1);
                map.getMap()[2][19].setHero(hero2);
                map.getMap()[1][20].setHero(hero3);
                map.getMap()[2][19].setHero(hero4);

            case 3:

                turn = clients.get(2).recieved;
                clients.get(2).player = clients.get(2).recieved.getOwner();
                hero1 = turn.getMoves().poll().getWho();
                hero2 = turn.getMoves().poll().getWho();
                hero3 = turn.getMoves().poll().getWho();
                hero4 = turn.getMoves().poll().getWho();
                map.getMap()[20][1].setHero(hero1);
                map.getMap()[20][2].setHero(hero2);
                map.getMap()[19][1].setHero(hero3);
                map.getMap()[19][2].setHero(hero4);

            case 2:

                turn = clients.get(1).recieved;
                clients.get(1).player = clients.get(1).recieved.getOwner();
                hero1 = turn.getMoves().poll().getWho();
                hero2 = turn.getMoves().poll().getWho();
                hero3 = turn.getMoves().poll().getWho();
                hero4 = turn.getMoves().poll().getWho();
                map.getMap()[20][20].setHero(hero1);
                map.getMap()[20][19].setHero(hero2);
                map.getMap()[19][20].setHero(hero3);
                map.getMap()[19][19].setHero(hero4);

            case 1:

                turn = clients.get(0).recieved;
                clients.get(0).player = clients.get(0).recieved.getOwner();
                hero1 = turn.getMoves().poll().getWho();
                hero2 = turn.getMoves().poll().getWho();
                hero3 = turn.getMoves().poll().getWho();
                hero4 = turn.getMoves().poll().getWho();
                map.getMap()[1][1].setHero(hero1);
                map.getMap()[1][2].setHero(hero2);
                map.getMap()[2][1].setHero(hero3);
                map.getMap()[2][2].setHero(hero4);

        }
        gameInit = true;
    }

    public synchronized boolean look(String nick, byte[] passhash) {
        for (Player player : players) {
            if (player.getNick().equals(nick) && Arrays.equals(player.getPasshash(), passhash)) {
                return true;
            }
        }
        return false;
    }

    public synchronized Player get(String nick, byte[] passhash) {
        for (Player player : players) {
            if (player.getNick().equals(nick) && player.getPasshash() == passhash) {
                return player;
            }
        }
        return null;
    }

    public synchronized void clearTurns() {
        turns = new ArrayList<>();
    }

    public synchronized boolean hasSendTurn(Player player) {
        for (Turn turn : turns) {
            if (turn.getOwner().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
