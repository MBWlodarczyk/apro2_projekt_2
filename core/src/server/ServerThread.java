package server;

import client.controller.Turn;
import client.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class to represent single server client relation on server side
 */
public class ServerThread extends Thread {
    public final Object lock = new Object();
    public ObjectOutputStream os;
    public ObjectInputStream is;
    public String name;
    public Turn received;
    public boolean init;
    public Player player;
    Socket sock;
    boolean exit;
    private Server server;

    /**
     * Public constructor
     *
     * @param sock   of connection
     * @param is     input stream from socket
     * @param os     output stream from socket
     * @param name   of client
     * @param server server reference
     */
    public ServerThread(Socket sock, ObjectInputStream is, ObjectOutputStream os, String name, Server server) {
        System.out.println("Server: Creating thread for " + name);
        this.is = is;
        this.os = os;
        this.name = name;
        this.sock = sock;
        this.server = server;
        this.start();
    }

    @Override
    public void run() {
        init = server.playerNumber != server.initPlayer;
        System.out.println("Server: Running thread for " + name);
        try {
            if (init) { //initializing game

                send(); //sending starting map
                receive(); //receiving initial vector of heroes
                recordPlayer(received.getOwner()); // recording player in server
                checkIfAllConnected(); // checking if game can be started

            } else { // reconnect

                send();
                receive(); //receiving initial player info
                recordPlayerIfExisting(received.getOwner());
                sendWithTurnInfo(); //send map again //
            }

            while (!exit) {
                receiveIfTurnNotSend(); //receive move if player hasn't made a move yet
                checkIfAllSend(); //check if all have sent and then send map else wait

            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            server.removeClient(this);
            server.playersClients.remove(this);
            System.out.println("Server: disconnect " + name);
            this.dispose();
        }
    }

    public void dispose() {
        exit = true;
    }

    /**
     * Method to send map to player
     */
    public synchronized void send() throws IOException {
        os.reset();
        os.writeObject(server.answer);// sending object
        os.flush();
    }

    /**
     * Method to recieve turn from player
     */
    public synchronized void receive() throws IOException, ClassNotFoundException {
        this.received = (Turn) is.readObject();
        System.out.println("Server: received object from " + name);
    }

    /**
     * Method to record player (used during init)
     *
     * @param player player to record
     */
    private synchronized void recordPlayer(Player player) throws IOException {
        if (player != null & !server.checkIfPlayerExists(player)) {
            this.player = player;
            server.playersClients.put(this, player);
            server.players.add(player);
            server.initPlayer++;
            this.name += " (" + player.getNick() + ")";
        } else{
            server.answer.setWrongNickPassword(true);
            send();
            server.answer.setWrongNickPassword(false);
            server.removeClient(this);
            server.playersClients.remove(this);
            System.out.println("Server: disconnect " + name);
            this.dispose();
        }

    }

    /**
     * Checks if all players connected and then inits the game (used only during init)
     */
    private synchronized void checkIfAllConnected() throws IOException {
        if (server.playerNumber == server.initPlayer && !server.gameInit) {
            server.init();
            server.sendToAll(false);
            server.unlock();
        }
    }

    /**
     * Records the reconnecting player if he is in server players.
     */
    private synchronized void recordPlayerIfExisting(Player player) throws IOException {
        if (player != null && server.checkIfPlayerExists(player)) {

            this.player = player;
            server.playersClients.put(this, server.getPlayer(player));
            this.name += " (" + player.getNick() + ")";
        } else {
            server.answer.setWrongNickPassword(true);
            send();
            server.answer.setWrongNickPassword(false);
            server.removeClient(this);
            server.playersClients.remove(this);
            System.out.println("Server: disconnect " + name);
            this.dispose();
        }
    }

    /**
     * Checks if player didn't send turn already and receives it.
     */
    private synchronized void receiveIfTurnNotSend() throws IOException, ClassNotFoundException {
        if (!server.hasSendTurn(player)) {
            System.out.println("Server: Waiting for turn from " + name);
            receive();
            if (server.gameInit & checkTurn(received)) {
                server.turns.add(received);
            }

        }
    }

    /**
     * Check if the turn came for the good player
     *
     * @param turn turn to check
     * @return true if the turn is valid
     */
    private synchronized boolean checkTurn(Turn turn) {
        return received.getOwner().equals(player) &
                turn.getMoves().stream()
                        .allMatch(move -> move.getWho().getOwner().equals(player));
    }

    /**
     * Checks if all players send turns, if not then locks the thread
     */
    private synchronized void checkIfAllSend() throws IOException, InterruptedException {
        synchronized (lock) {
            {
                System.out.println("Server: lock " + name);
                if (!server.check()) {
                    lock.wait();
                }
            }
        }
    }

    /**
     * Sends answer with initial info then reconnecting
     */
    private synchronized void sendWithTurnInfo() throws IOException {
        if (server.hasSendTurn(player)) {
            server.answer.setHasSendMove(true);
            send();
            server.answer.setHasSendMove(false);
        } else {
            send();
        }
    }
}