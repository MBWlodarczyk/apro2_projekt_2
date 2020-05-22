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
    public boolean reciever;
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
        System.out.println("Running");

        if(init) {
            try {
                os.reset();
                os.writeObject(server.getMap());// sending object
                os.flush();

                this.recieved = (Turn) is.readObject();
                System.out.println("received object from " + name);

                server.initPlayer++;
                reciever = true;

            if (recieved.getOwner() != null) {
                this.player=recieved.getOwner();
                server.playersClients.put(this, recieved.getOwner());
                server.players.add(recieved.getOwner());
            }

            if (server.playerNumber == server.initPlayer) {
                server.init();
                server.sendToAll(false);
                server.unlock();
            }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else { // reconnect //TODO reconnect when turn has been already made (checker)
            try {

            send();

            recieve();

            if (recieved.getOwner() != null && server.look(recieved.getOwner().getNick(),recieved.getOwner().getPasshash())) {

                this.player = recieved.getOwner();
                server.playersClients.put(this, server.get(recieved.getOwner().getNick(),recieved.getOwner().getPasshash()));

                send();

                reciever = true;
            } else {
                server.removeClient(this);
                server.playersClients.remove(this);
                System.out.println("disconnect " + name);
                this.dispose();
            }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}       reciever = !init;
        while (!exit) {
            try {
                if(!server.hasSendTurn(player)) {
                    System.out.println("Waiting for turn from " + name);
                    recieve();
                    server.turns.add(recieved);
                    reciever = true;
                }
                    synchronized (lock) {
                        {
                            System.out.println("lock " + name);
                            if (!server.check()){
                                lock.wait();
                                }
                        }
                    }

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
}