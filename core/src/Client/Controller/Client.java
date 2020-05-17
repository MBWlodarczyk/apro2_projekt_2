package Client.Controller;

import Client.Model.Player;
import Client.Model.map.GameMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * test
 */
public class Client {
    public ObjectInputStream is;
    public ObjectOutputStream os;
    private Turn send = new Turn(new Player("wtf"));
    private GameMap received;
    private boolean isSend=false;

    public Client() throws Exception {
        received = new GameMap(22);
        Socket s = new Socket("127.0.0.1", 1701);
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        Object lock = new Object();

        final Object finalLock = lock;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (finalLock){
                        if (send != null && !isSend && send.getMoves().size() == 4) {
                            try {
                                System.out.println("Sending...");
                                os.reset();
                                os.writeObject(send);
                                isSend = true;
                                os.flush();
                                send.clearMoves();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (isSend) {
                            try {
                                received = (GameMap) is.readObject();
                                System.out.println("Reading...");
                                isSend = false;
                                send.clearMoves();
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.out.println(received);
                        }
                    }
                }

            }
        }
        );
        t.start();
    }

    public Turn getSend() {
        return send;
    }

    public GameMap getReceived() {
        return this.received;
    }

}
