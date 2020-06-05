package server;


import client.model.map.GameMap;
import com.badlogic.gdx.utils.Json;

public class Config {
    private int playerNumber;
    private int serverPort;

    public Config(int playerNumber, int serverPort) {
        this.playerNumber = playerNumber;
        this.serverPort = serverPort;
    }

    public Config() {
    }

    public static void main(String[] args) {
        GameMap map = new GameMap(22);
        Json json = new Json();
        System.out.println(json.toJson(map.getFieldsArray()[1][1].getTerrain()));
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}