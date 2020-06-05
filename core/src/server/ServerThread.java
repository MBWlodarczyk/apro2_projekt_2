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
    public String name;
    public Turn recieved;
    public ObjectInputStream is;
    public boolean init;
    public Player player;
    Socket sock;
    boolean exit;
    private Server server;

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
                recordPlayer(recieved.getOwner()); // recording player in server
                checkIfAllConnected(); // checking if game can be started

            } else { // reconnect

                send();
                receive(); //receiving initial player info
                recordPlayerIfExisting(recieved.getOwner());
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

    public synchronized void send() throws IOException {
        os.reset();
        os.writeObject(server.answer);// sending object
        os.flush();
    }

    public synchronized void receive() throws IOException, ClassNotFoundException {
        this.recieved = (Turn) is.readObject();
        System.out.println("Server: received object from " + name);
    }

    private synchronized void recordPlayer(Player player) {
        if (player != null) {
            this.player = player;
            server.playersClients.put(this, player);
            server.players.add(player);
            server.initPlayer++;
            this.name += " (" + player.getNick() + ")";
        }
    }

    private synchronized void checkIfAllConnected() throws IOException {
        if (server.playerNumber == server.initPlayer && !server.gameInit) {
            server.init();
            server.sendToAll(false);
            server.unlock();
        }
    }

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

    private synchronized void receiveIfTurnNotSend() throws IOException, ClassNotFoundException {
        if (!server.hasSendTurn(player)) {
            System.out.println("Server: Waiting for turn from " + name);
            receive();
            if(server.gameInit & checkTurn(recieved)){

            server.turns.add(recieved);
            }

        }
    }

    private synchronized boolean checkTurn(Turn turn){
        return recieved.getOwner().equals(player) &
                turn.getMoves().stream()
                        .allMatch(move -> move.getWho().getOwner().equals(player));
    }

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