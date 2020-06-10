package client.controller;

/**
 * All inputs from client
 */

public class Inputs {
    public static boolean sendTurn;
    public static boolean anyHeroChosen;
    public static int skillChosen;
    public static int x, y;
    public static int[] tab = new int[2];
    public static String nick;
    public static String ip;
    public static String port;
    public static byte[] password;
    public static boolean[] chosen = new boolean[6];
    public static ControllerState currentState;
}
