package server;

import client.controller.Turn;
import client.model.Player;
import client.model.heroes.Hero;
import client.model.map.GameMap;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

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

    public ArrayList<ServerThread> clients = new ArrayList<>();
    public InputThread playerInput;
    public HashMap<ServerThread, Player> playersClients = new HashMap<>();
    public ArrayList<Player> players = new ArrayList<>();
    public Answer answer;
    public ArrayList<Turn> turns = new ArrayList<>();
    public int playerNumber;
    public int initPlayer;
    boolean gameInit;
    ServerSocket server;
    private boolean exit = false;
    private int port;
    private int mapType; //type of a map

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server() throws IOException {
        loadConfig();
        answer = new Answer(new GameMap(22,mapType));
        this.server = new ServerSocket(this.port);
        this.playerInput = new InputThread(this);
        run();
    }


    /**
     * Method loading config from config.json
     */
    private void loadConfig() {
        JsonReader file = new JsonReader();
        JsonValue configJson = file.parse(new FileHandle("config.json"));
        JsonValue playerNumber = configJson.get("playerNumber");
        this.playerNumber = playerNumber.asInt();
        JsonValue port = configJson.get("ServerPort");
        this.port = port.asInt();
        JsonValue mapType = configJson.get("mapType");
        this.mapType = mapType.asInt();
        System.out.println(this.playerNumber);
        System.out.println(this.port);
        System.out.println(this.mapType);
    }

    /**
     * Main loop of the server
     */
    private void run() throws IOException {
        System.out.println("Server: Waiting for players");
        int number = 1;
        while (!exit) {
            Socket socket = server.accept();
            acceptConnection(number, socket);
            number++;
        }
    }

    /**
     * Method accepting and handling single connection
     * @param i local client number
     * @param s socket to add
     */
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

    /**
     * Method to check if all players inited and send turns. When true it calls sendToAll
     * @return true if all send, false otherwise
     */
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

    /**
     * Method unlocking all clients
     */
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

    /**
     * Method to compute moves and send map to all
     * @param moves true if you want to perform moves
     */
    public synchronized void sendToAll(boolean moves) throws IOException {

        if (moves) {
            answer = ServerEngine.performTurns(answer.getMap(), turns);
            turns.clear();
            if (ServerEngine.checkWin(answer.getMap()) != null) { //checking if game is already won
                answer.setGameWon(true);
                answer.setWinner(ServerEngine.checkWin(answer.getMap()));
                System.out.println("Game won by " + answer.getWinner().getNick());
            } else {
                answer.setGameWon(false);
                answer.setWinner(null);
            }
        }

        ArrayList<ServerThread> temp = (ArrayList<ServerThread>) clients.clone();
        while (temp.size() != 0) { // this part is pretty ugly
            //it send map to all and if map is send removes them from arraylist till array list isn't empty.
            // this solves the problem with socketexception as its the only way to detect losing connection
            System.out.println("Server: sending to all");
            if (!temp.get(0).sock.isOutputShutdown()) {
                try {
                    temp.get(0).os.reset();
                    temp.get(0).os.writeObject(answer);
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
        if (answer.isGameWon()) newGame(); //starts new game is the last is won
    }

    public synchronized void removeClient(ServerThread client) {
        clients.remove(client);
    }

    /**
     * Inits the players on map
     */
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

    /**
     * @return true if player exists in server, false otherwise
     */
    public synchronized boolean checkIfPlayerExists(Player player) {

        return players.stream().anyMatch(player1 -> player1.equals(player));
    }

    /**
     * @return the player if he exists in server else returns null
     */
    public synchronized Player getPlayer(Player player) {
        return players.stream()
                .filter(player1 -> player1.equals(player))
                .findAny()
                .orElse(null);
    }

    public synchronized void clearTurns() {
        turns = new ArrayList<>();
    }

    /**
     * Method to check if player has already send turn
     * @param player player to check
     * @return true if sent
     */
    public synchronized boolean hasSendTurn(Player player) {
        return turns.stream().anyMatch(turn -> turn.getOwner().equals(player));
    }

    /**
     * Method initing one player
     * @param playerNumber which player it is starting from 0
     * @param CornerX x corner of init place
     * @param CornerY y corner of init place
     */
    private synchronized void initPlayer(int playerNumber, int CornerX, int CornerY) {
        Turn turn = clients.get(playerNumber).received;
        clients.get(playerNumber).player = clients.get(playerNumber).received.getOwner();
        Hero hero1 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        Hero hero2 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        Hero hero3 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        Hero hero4 = Objects.requireNonNull(turn.getMoves().poll()).getWho();
        answer.getMap().getFieldsArray()[CornerX][CornerY].setHero(hero1);
        answer.getMap().getFieldsArray()[CornerX][CornerY + 1].setHero(hero2);
        answer.getMap().getFieldsArray()[CornerX + 1][CornerY].setHero(hero3);
        answer.getMap().getFieldsArray()[CornerX + 1][CornerY + 1].setHero(hero4);
    }
    /**
     * Method to save the game to file
     */
    public void save(String filepath) throws IOException {
        Save save = new Save(this.answer, this.turns, this.playerNumber, this.players, this.initPlayer, this.gameInit);
        FileOutputStream fileOut = new FileOutputStream(filepath); //TODO zapisywanie w folderze maps
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(save);
        objectOut.close();
        System.out.println("Saved game");
    }
    /**
     * Method to load the game from file
     */
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

    /**
     * Method to restart the game
     */
    public void newGame() {
        this.answer.setMap(new GameMap(22,2));
        this.answer.setWinner(null);
        this.answer.setGameWon(false);
        this.answer.setWrongNickPassword(false);
        this.answer.setHasSendMove(false);
        this.initPlayer = 0;
        this.gameInit = false;
        this.players = new ArrayList<>();
        this.turns = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.playersClients = new HashMap<>();
    }
}
