package server;

import java.io.IOException;
import java.util.Scanner;

/**
 * Thread to communicate with the server
 */
public class InputThread extends Thread {

    public Server server;
    public boolean exit;
    private Scanner sc;

    public InputThread(Server server) {
        this.sc = new Scanner(System.in);
        this.server = server;
        this.start();
    }

    @Override
    public void run() {
        String line = "";
        while (!exit) {
            if (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.startsWith("exit")) {
                    System.out.println("Server stopped");
                    server.dispose();
                    System.exit(0);
                    this.dispose();
                }
                else if (line.startsWith("save")) {
                    String path = line.split(" ")[1];
                    try {
                        server.save(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (line.startsWith("load")) {
                    String path = line.split(" ")[1];
                    try {
                        server.load(path);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Can't locate the file");
                    }
                }
                else if (line.startsWith("list")) {
                    System.out.println("Server: Printing clients:");
                    for (ServerThread client : server.clients) {
                        System.out.println(client.player.getNick());
                        System.out.println(client.sock.getRemoteSocketAddress().toString());
                        System.out.println();
                    }
                }
                else if (line.startsWith("kill")){
                    int x = Integer.parseInt(line.split(" ")[1]);
                    int y = Integer.parseInt(line.split(" ")[2]);
                    ServerEngine.kill(x,y,server.getMap());
                    System.out.println("Server: killing hero on spot " + x +" " + y);
                }
            }
        }
    }

    public void dispose() {
        exit = true;
    }
}
