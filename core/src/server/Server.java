package server;

import client.model.GameMap;

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

    public static void main(String[] args) throws IOException, ClassNotFoundException {


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

    public static synchronized void unlock() {
        for (ServerThread client : clients) {
            synchronized (client.lock) {
                client.lock.notify();
                client.reciever = false;
                System.out.println("Unlocking " + client.name);
            }
        }
    }

    public static synchronized void send() throws IOException {
        for (ServerThread client : clients) {
            map.move(map, client.recieved);
        }
        for (ServerThread client : clients) {
            client.os.reset();
            client.os.writeObject(map);// sending object
            System.out.println(map);
            client.os.flush();
        }
    }
}

