package Server;

import Client.Model.map.GameMap;

import java.io.Serializable;

public class Answer implements Serializable {
    public GameMap getMap() {
        return map;
    }

    public Answer(GameMap map) {
        this.map = map;
    }

    private GameMap map = new GameMap(22);
    private boolean hasSendMove = false; //reconnect variable
    private boolean WrongNickPassword = false; //reconnect flag

    public boolean isWrongNickPassword() {
        return WrongNickPassword;
    }

    public void setWrongNickPassword(boolean wrongNickPassword) {
        WrongNickPassword = wrongNickPassword;
    }

    public boolean hasSendMove() {
        return hasSendMove;
    }

    public void setHasSendMove(boolean hasSendMove) {
        this.hasSendMove = hasSendMove;
    }

}
