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
    private GameMap received = new GameMap(16);
    public Client() throws Exception {
        Socket s = new Socket("127.0.0.1", 1701);
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Scanner sc = new Scanner(System.in);
                    int y = sc.nextInt();
                    int x = sc.nextInt();
                    Turn move = new Turn(y, x);
                    try {
                        os.writeObject(move);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Reading...");
                    try {
                        received = (GameMap) is.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println(received);
                }
            }
        }
        );
        t.start();
    }

    public GameMap getReceived() {
        return this.received;
    }

}
