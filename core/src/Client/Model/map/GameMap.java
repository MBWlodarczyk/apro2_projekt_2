package Client.Model.map;


import Client.Controller.Move;
import Client.Model.Heroes.*;
import Client.Model.Player;

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
        addWalls();
    }

    // tester method
    private void loadMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Field(i, j);
                map[i][j].setObstacle(new Grass());
            }
        }
        byte[] hash = new byte[3];
        map[1][1].setHero(new Archer(new Player("ktos",hash),1,1));
    }

    // PRIMITIVE VERY PRIMITIVE XD //TODO find out way to read map
    private void addWalls(){
        for(int i = 0; i < map.length;i++){
            map[i][0].setObstacle(new Wall());
            map[0][i].setObstacle(new Wall());
            map[map.length-1][i].setObstacle(new Wall());
            map[i][map.length-1].setObstacle(new Wall());
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
