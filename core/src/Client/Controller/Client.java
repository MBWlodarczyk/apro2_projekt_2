package Client.Controller;

import Client.Model.GameMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * test
 */
public class Client {
    ObjectInputStream is;
    ObjectOutputStream os;
    private Move send;
    private GameMap received = new GameMap(22);
    private boolean isSend;
    public Client() throws Exception {
        Socket s = new Socket("127.0.0.1", 1701);
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (send != null && !isSend) {
                        try {
                            os.writeObject(send);
                            isSend = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (isSend) {
                        try {
                            received = (GameMap) is.readObject();
                            System.out.println("Reading...");
                            isSend=false;
                            send=null;
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        System.out.println(received);
                    }
                }

            }
        }
        );
        t.start();
    }

    public void setSend(Move send) {
        this.send = send;
    }

    public GameMap getReceived() {
        return this.received;
    }

}
