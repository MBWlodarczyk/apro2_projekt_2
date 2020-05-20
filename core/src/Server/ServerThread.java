package Server;

import Client.Controller.Turn;
import Client.Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * test
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

    public ServerThread(Socket sock, ObjectInputStream is, ObjectOutputStream os, String name) {
        System.out.println("Creating thread");
        this.is = is;
        this.os = os;
        this.name = name;
        this.sock = sock;
        this.start();
    }

    @Override
    public void run() {
        init=Server.playerNumber != Server.initPlayer;
        System.out.println("Running");
if((init)) {
    try {
        os.reset();
        os.writeObject(Server.getMap());// sending object
        os.flush();
        this.recieved = (Turn) is.readObject();
        System.out.println("received object from " + name);
        Server.initPlayer++;
        reciever = true;
        if (recieved.getOwner() != null) {
            this.player=recieved.getOwner();
            Server.playersClients.put(this, recieved.getOwner());
            Server.players.add(recieved.getOwner());
        }
        if (Server.playerNumber == Server.initPlayer) {
            Server.init();
            Server.send(false);
            Server.unlock();
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}else { // reconnect
    try {
        os.reset();
        os.writeObject(Server.getMap());
        os.flush();
        this.recieved = (Turn) is.readObject();
        System.out.println("received reconnect from " + name);
        if (recieved.getOwner() != null && Server.look(recieved.getOwner().getNick(),recieved.getOwner().getPasshash())) {
            this.player = recieved.getOwner();
            Server.playersClients.put(this, Server.get(recieved.getOwner().getNick(),recieved.getOwner().getPasshash()));
            os.reset();
            os.writeObject(Server.getMap());// sending object
            os.flush();
            reciever = true;
        } else{
            Server.removeClient(this);
            Server.playersClients.remove(this);
            System.out.println("disconnect " + name);
            this.dispose();
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}       reciever = !init;
        while (!exit) {
            try {
                if(!Server.hasSendTurn(player)) {
                    System.out.println("Waiting for turn from " + name);
                    this.recieved = (Turn) is.readObject();
                    Server.turns.add(recieved);
                    System.out.println("received object from " + name);
                    reciever = true;
                }
                    synchronized (lock) {
                        {
                            System.out.println("lock " + name);
                            if (!Server.check()){
                                lock.wait();
                                }
                        }
                    }

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                Server.removeClient(this);
                Server.playersClients.remove(this);
                System.out.println("disconnect " + name);
                e.printStackTrace();
                this.dispose();
            }
        }
    }
    public void dispose()
    {
        exit = true;
    }
}