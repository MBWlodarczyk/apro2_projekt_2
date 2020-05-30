package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class InputThread extends Thread{

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
        String line="";
        while(!exit){
            if(sc.hasNextLine()){
                line = sc.nextLine();
                if(line.startsWith("exit")){
                    System.out.println("Server stopped");
                    server.dispose();
                    System.exit(0);
                    this.dispose();
                }
                if(line.startsWith("save")){
                    String path = line.split(" ")[1];
                    try {
                        server.save(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(line.startsWith("load")){
                    String path = line.split(" ")[1];
                    try {
                        server.load(path);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Can't locate the file");
                    }
                }
                if(line.startsWith("list"))
                    System.out.println("Server: Printing clients:");
                    for(ServerThread client : server.clients){
                        System.out.println(client.player.getNick());
                        System.out.println(client.sock.getRemoteSocketAddress().toString());
                        System.out.println();
                }
            }
        }
    }
    public void dispose(){
        exit = true;
    }
}
