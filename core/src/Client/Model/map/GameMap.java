package Client.Model.map;


import Client.Controller.Move;
import Client.Model.Heroes.Hero;
import Client.Model.obstacles.Grass;
import Client.Model.obstacles.Wall;

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
                map[i][j].setObstacle(new Grass(i, j));
            }
        }
    }

    // PRIMITIVE VERY PRIMITIVE XD //TODO find out way to read map
    private void addWalls() {
        for (int i = 0; i < map.length; i++) {
            map[i][0].setObstacle(new Wall(i, 0));
            map[0][i].setObstacle(new Wall(0, i));
            map[map.length - 1][i].setObstacle(new Wall(map.length - 1, i));
            map[i][map.length - 1].setObstacle(new Wall(i, map.length - 1));
        }
//        byte[] hash = new byte[2];
//        map[2][3].setHero(new Archer(new Player("ktos",hash),2,3));
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
                sb.append(map[i][j].getHero()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
