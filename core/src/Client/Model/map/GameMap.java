package Client.Model.map;


import Client.Controller.Move;
import Client.Model.Heroes.*;

import java.io.Serializable;

/**
 * Class to represent map
 */
public class GameMap implements Serializable {

    /**
     * Two dimensions field array representation of map
     */
    private Field[][] map;


    public GameMap(int size) {
        this.map = new Field[size][size];
        loadMap();
    }

    // tester method
    public void loadMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Field(i, j);
            }
        }
    }

    public Field[][] getMap() {
        return map;
    }


    public void move(GameMap map, Move move) {
            Hero temp = move.getWho();
            int x = move.getFrom().getX();
            int y = move.getFrom().getY();
            map.getMap()[y][x].setHero(null);
            x = move.getWhere().getX();
            y = move.getWhere().getY();
            map.getMap()[y][x].setHero(temp);

    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                sb.append(map[i][j].getObstacle()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
