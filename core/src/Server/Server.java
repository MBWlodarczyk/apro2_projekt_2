package Server;

import Client.Controller.Turn;
import Client.Model.Heroes.Hero;
import Client.Model.map.GameMap;
import Client.Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * test
 */
public class Server {
    public static ArrayList<ServerThread> clients = new ArrayList<>();
    public static HashMap<ServerThread,Player> playersClients = new HashMap<>();
    public static ArrayList<Player> players = new ArrayList<>();
    private static GameMap map = new GameMap(22);
    public static int playerNumber;
    public static int initPlayer;
    static boolean gameInit;
    public Server(int playerNumber) throws IOException {
        Server.playerNumber =playerNumber;
        ServerSocket server = new ServerSocket(1701);
        int i = 1;

        while (true) {
            Socket s = server.accept();

            String name = "client " + i;
            i++;
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            ServerThread t = new ServerThread(s, is, os, name);
            clients.add(t);
        }
    }


    public static synchronized boolean check() throws IOException {
        if(clients.size() == Server.playerNumber){
            boolean marker = true;
            for (ServerThread client : clients) {
                if (!client.reciever) {
                    marker = false;
                    break;
                }
            }
            if (marker) {
                unlock();
                if(initPlayer==playerNumber) {
                    send(true);
                }
            }
            return marker;
        }
        return false;
    }

    public static synchronized void unlock() {
        for (ServerThread client : clients) {
            synchronized (client.lock) {
                client.lock.notify();
                client.reciever = false;
                System.out.println("Unlocking " + client.name);
            }
        }
    }

    public static GameMap getMap() {
        return map;
    }



    public static synchronized void send(boolean moves) throws IOException {
        if(moves){
            for (ServerThread client : clients) {
                map.move(map, client.recieved.getMoves().poll());
            }
            for (ServerThread client : clients) {
                map.move(map, client.recieved.getMoves().poll());
            }
            for (ServerThread client : clients) {
                map.move(map, client.recieved.getMoves().poll());
            }
            for (ServerThread client : clients) {
                map.move(map, client.recieved.getMoves().poll());
            }
        }
        for (ServerThread client : clients) {
            System.out.println("Sending");
            client.os.reset();
            client.os.writeObject(map);// sending object
            client.os.flush();
        }
    }
    public static synchronized void removeClient(ServerThread client){
        clients.remove(client);
    }

    public static void main(String[] args) throws IOException {
        new Server(2);
    }

    public static synchronized void init(){
        switch(initPlayer){
            case 4:
                Turn turn = clients.get(3).recieved;
                clients.get(3).player=clients.get(3).recieved.getOwner();
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
                clients.get(2).player=clients.get(2).recieved.getOwner();
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
                clients.get(1).player=clients.get(1).recieved.getOwner();
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
                clients.get(0).player=clients.get(0).recieved.getOwner();
                hero1 = turn.getMoves().poll().getWho();
                hero2 = turn.getMoves().poll().getWho();
                hero3 = turn.getMoves().poll().getWho();
                hero4 = turn.getMoves().poll().getWho();
                map.getMap()[1][1].setHero(hero1);
                map.getMap()[1][2].setHero(hero2);
                map.getMap()[2][1].setHero(hero3);
                map.getMap()[2][2].setHero(hero4);
        }
        Server.gameInit = true;
    }
}
