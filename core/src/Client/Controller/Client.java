package Client.Controller;

import Client.Controller.Turn;
import Client.Model.GameMap;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * test
 */
public class Client {
    static GameMap received;
    public static void main(String[] args) throws Exception
    {
        Socket s=new Socket("127.0.0.1",1701);
        ObjectInputStream is=new ObjectInputStream(s.getInputStream());
        ObjectOutputStream os=new ObjectOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);
        while(true){
            int y = sc.nextInt();
            int x = sc.nextInt();
            Turn move = new Turn();
            os.writeObject(move);
            System.out.println("Reading...");
            received =(GameMap)is.readObject();
            System.out.println(received);
        }
    }
}
