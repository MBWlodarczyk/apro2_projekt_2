package Server;

import Client.Controller.Move;
import Client.Controller.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * test
 */
public class ServerThread extends Thread {
    public final Object lock = new Object();
    public ObjectOutputStream os;
    public String name;
    public boolean reciever;
    public Turn recieved;
    private Socket socket;
    public ObjectInputStream is;
    boolean exit;
    public boolean init;

    public ServerThread(Socket sock, ObjectInputStream is, ObjectOutputStream os, String name) throws IOException {
        System.out.println("Creating thread");
        socket = sock;
        this.is = is;
        this.os = os;
        this.name = name;
        this.start();
    }

    @Override
    public void run() {
        System.out.println("Running");
        try {
            os.reset();
            os.writeObject(Server.getMap());// sending object
            os.flush();
            this.recieved = (Turn) is.readObject();
            System.out.println("received object from " + name);
            Server.initPlayer++;
            reciever=true;
            if(Server.playerNumber==Server.initPlayer) {
                Server.init();
                Server.send(false);
                Server.unlock();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (!exit) {
            reciever=false;
            try {


                        this.recieved = (Turn) is.readObject();
                        System.out.println("received object from " + name);
                        reciever = true;

                    synchronized (lock) {
                        {
                            System.out.println("lock " + name);
                            if (!Server.check())
                                lock.wait();
                        }
                }

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                Server.removeClient(this);
                System.out.println("disconnect " + name);
                this.dispose();
            }
        }
    }
    public void dispose()
    {
        exit = true;
    }
}