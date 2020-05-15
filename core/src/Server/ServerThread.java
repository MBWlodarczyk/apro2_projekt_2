package Server;

import Client.Controller.Move;

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
    public boolean reciever;
    public Move recieved;
    private Socket socket;
    private ObjectInputStream is;

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
        while (true) {
            reciever = false;
            try {
                this.recieved = (Move) is.readObject();
                System.out.println("recieved object from " + name);
                reciever = true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                synchronized (lock) {
                    {
                        System.out.println("lock " + name);
                        if (!Server.check())
                            lock.wait();
                    }
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}