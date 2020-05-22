package Server;

import Client.Controller.Turn;
import Client.Model.Player;

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
    Socket sock;
    public Turn recieved;
    public ObjectInputStream is;
    boolean exit;
    public boolean init;
    public Player player;
    private Server server;

    public ServerThread(Socket sock, ObjectInputStream is, ObjectOutputStream os, String name,Server server) {
        System.out.println("Creating thread");
        this.is = is;
        this.os = os;
        this.name = name;
        this.sock = sock;
        this.server=server;
        this.start();
    }

    @Override
    public void run() {
        init=server.playerNumber != server.initPlayer;
        System.out.println("Running thread");
        try {
            if(init) { //initializing game

                send(); //sending starting map
                recieve(); //receiving initial vector of heroes
                recordPlayer(recieved.getOwner()); // recording player in server
                checkIfAllConnected(); // checking if game can be started

            } else { // reconnect

                send(); //sending actual map
                recieve(); //receiving initial player info
                recordPlayerIfExisting(recieved.getOwner());
                sendWithTurnInfo(); //send map again //
            }
        }catch (IOException | ClassNotFoundException e) {
                System.out.println("cos nie pykło w reconnect/init handling");
                e.printStackTrace();
        }

        while (!exit) {
            try {
                receiveIfTurnNotSend(); //receive move if player hasn't made a move yet
                checkIfAllSend(); //check if all have sent and then send map else wait

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                server.removeClient(this);
                server.playersClients.remove(this);
                System.out.println("disconnect " + name);
                this.dispose();
            }
        }
    }

    public void dispose()
    {
        exit = true;
    }

    public synchronized void send() throws IOException {
        os.reset();
        os.writeObject(server.getMap());// sending object
        os.flush();
    }

    public synchronized void recieve() throws IOException, ClassNotFoundException {
        this.recieved = (Turn) is.readObject();
        System.out.println("received object from " + name);
    }

    private synchronized void recordPlayer(Player player) {
        if (player != null) {
            this.player = player;
            server.playersClients.put(this, player);
            server.players.add(player);
            server.initPlayer++;
            this.name += " (" + player.getNick() +")";
        }
    }

    private synchronized void checkIfAllConnected() throws IOException {
        if (server.playerNumber == server.initPlayer) {
            server.init();
            server.sendToAll(false);
            server.unlock();
        }
    }

    private synchronized void recordPlayerIfExisting(Player player) {
        if (player != null && server.checkIfPlayerExists(player.getNick(),player.getPasshash())) {

            this.player = player;
            server.playersClients.put(this, server.getPlayer(player.getNick(),player.getPasshash()));
            this.name += " (" + player.getNick() +")";
        } else {
            server.removeClient(this);
            server.playersClients.remove(this);
            System.out.println("disconnect " + name);
            this.dispose();
        }
    }

    private synchronized void receiveIfTurnNotSend() throws IOException, ClassNotFoundException {
        if(!server.hasSendTurn(player)) {
            System.out.println("Waiting for turn from " + name);
            recieve();// receiveIfTurnNotSend
            server.turns.add(recieved);
        }
    }

    private synchronized void checkIfAllSend() throws IOException, InterruptedException {
        synchronized (lock) {
            {
                System.out.println("lock " + name);
                if (!server.check()){
                    lock.wait();
                }
            }
        }
    }
    private synchronized void sendWithTurnInfo() throws IOException {
        if(server.hasSendTurn(player)) {
            server.getMap().setHasSendMove(true);
            send();
            server.getMap().setHasSendMove(false);
        } else {
            send();
        }
    }
}