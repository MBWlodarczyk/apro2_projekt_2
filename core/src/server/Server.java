package server;

import client.controller.Turn;
import client.model.Player;
import client.model.heroes.Hero;
import client.model.map.GameMap;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

/**
 * Main class to implement client server communication on server side
 */
public class Server {

    public final ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
    public final HashMap<ServerThread, Player> playersClients = new HashMap<>();
    public ArrayList<Player> players = new ArrayList<>();
    public Answer answer = new Answer(new GameMap(22));
    public ArrayList<Turn> turns = new ArrayList<>();
    public int playerNumber;
    public int initPlayer;
    boolean gameInit;
    ServerSocket server;
    private boolean exit = false;
    private int port;

    public Server(int playerNumber) throws IOException {
        //initServer();
        this.playerNumber = playerNumber;
        this.server = new ServerSocket(1701);
        InputThread playerInput = new InputThread(this);
        run();
    }

    public static void main(String[] args) throws IOException {
        new Server(2);
    }

    private void run() throws IOException {
        System.out.println("Server: Waiting for players");
        int number = 1;
        while (!exit) {
            Socket socket = server.accept();
            acceptConnection(number, socket);
            number++;
        }
    }

    private void acceptConnection(int i, Socket s) throws IOException {
        String name = "client " + i;
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
        ServerThread t = new ServerThread(s, is, os, name, this);
        clients.add(t);
    }

    public void dispose() {
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
                System.out.println("Server: Unlocking " + client.name);
            }
        }
    }

    public GameMap getMap() {
        return answer.getMap();
    }

    //TODO reformat sendToAll when we have engine
    public synchronized void sendToAll(boolean moves) throws IOException {

        if (moves) {
            for (Turn turn : turns) {
                ServerEngine.move(answer.getMap(), turn.getMoves().poll());
            }
            for (Turn turn : turns) {
                ServerEngine.move(answer.getMap(), turn.getMoves().poll());
            }
            for (Turn turn : turns) {
                ServerEngine.move(answer.getMap(), turn.getMoves().poll());
            }
            for (Turn turn : turns) {
                ServerEngine.move(answer.getMap(), turn.getMoves().poll());
            }
            turns.clear();
        }

        ArrayList<ServerThread> temp = (ArrayList<ServerThread>) clients.clone();
        while (temp.size() != 0) {

            System.out.println("Server: sending to all");
            if (!temp.get(0).sock.isOutputShutdown()) {
                try {
                    temp.get(0).os.reset();
                    temp.get(0).os.writeObject(answer);// sending object
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
                initPlayer(3, 1, 19);
            case 3:
                initPlayer(2, 19, 1);
            case 2:
                initPlayer(1, 19, 19);
            case 1:
                initPlayer(0, 1, 1);
        }
        gameInit = true;
    }

    public synchronized boolean checkIfPlayerExists(Player player) {

        return players.stream().anyMatch(player1 -> player1.equals(player));
    }

    public synchronized Player getPlayer(Player player) {
        return players.stream()
                .filter(player1 -> player1.equals(player))
                .findAny()
                .orElse(null);
    }

    public synchronized void clearTurns() {
        turns = new ArrayList<>();
    }

    public synchronized boolean hasSendTurn(Player player) {
        return turns.stream().anyMatch(turn -> turn.getOwner().equals(player));
    }

    //ugly method don't look
    private synchronized void initPlayer(int playerNumber, int CornerX, int CornerY) {
        Turn turn = clients.get(playerNumber).recieved;
        clients.get(playerNumber).player = clients.get(playerNumber).recieved.getOwner();
        Hero hero1 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        Hero hero2 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        Hero hero3 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        Hero hero4 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        answer.getMap().getFieldsArray()[CornerX][CornerY].setHero(hero1);
        answer.getMap().getFieldsArray()[CornerX][CornerY + 1].setHero(hero2);
        answer.getMap().getFieldsArray()[CornerX + 1][CornerY].setHero(hero3);
        answer.getMap().getFieldsArray()[CornerX + 1][CornerY + 1].setHero(hero4);
    }

    private synchronized void initServer() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Specify the port:");
            this.port = sc.nextInt();
            System.out.println("Specify player number:");
            this.playerNumber = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Not a valid input, try again.");
        }
    }

    public void save(String filepath) throws IOException {
        Save save = new Save(this.answer, this.turns, this.playerNumber, this.players, this.initPlayer, this.gameInit);
        FileOutputStream fileOut = new FileOutputStream(filepath); //TODO zapisywanie w folderze maps
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(save);
        objectOut.close();
        System.out.println("Saved game");
    }

    public void load(String filepath) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filepath);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Save save = (Save) objectIn.readObject();
        System.out.println("Loaded game");
        this.answer = save.answer;
        this.turns = save.turns;
        this.playerNumber = save.playerNumber;
        this.players = save.players;
        this.initPlayer = save.initPlayer;
        this.gameInit = save.gameInit;
        this.sendToAll(false);
    }
}
