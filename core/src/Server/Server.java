package Server;

import Client.Model.map.GameMap;
import Client.Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * test
 */
public class Server {
    private static ArrayList<ServerThread> clients = new ArrayList<>();
    private static GameMap map = new GameMap(22);
    private static ArrayList<Player> players;
    private static int playerNumber;
    public Server(int playerNumber) throws IOException {
        this.playerNumber=playerNumber;
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
                }
            }
            if (marker) {
                unlock();
                send();
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



    public static synchronized void send() throws IOException {
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
        for (ServerThread client : clients) {
            client.os.reset();
            client.os.writeObject(map);// sending object
            client.os.flush();
        }
    }
    public static synchronized void removeClient(ServerThread client){
        clients.remove(client);
    }
}

