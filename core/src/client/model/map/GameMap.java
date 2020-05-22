package client.model.map;


import client.controller.Move;
import client.model.heroes.Hero;
import client.model.obstacles.Wall;
import client.model.terrain.Grass;

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
                map[i][j].setTerrain(new Grass(i,j));
            }
        }
    }

    // PRIMITIVE VERY PRIMITIVE //TODO find out way to read map
    private void addWalls() {
        for (int i = 0; i < map.length; i++) {
            map[i][0].setObstacle(new Wall());
            map[0][i].setObstacle(new Wall());
            map[map.length - 1][i].setObstacle(new Wall());
            map[i][map.length - 1].setObstacle(new Wall());
        }
//        byte[] hash = new byte[2];
//        map[2][3].setHero(new Archer(new Player("ktos",hash),2,3));
//        map[10][5].setHero(new Archer(new Player("ktos",hash),10,5));
//        map[3][7].setHero(new Archer(new Player("ktos",hash),3,7));
        map[10][3].setObstacle(new Wall());
        map[11][3].setObstacle(new Wall());

        map[10][18].setObstacle(new Wall());
        map[11][18].setObstacle(new Wall());

        map[3][10].setObstacle(new Wall());
        map[3][11].setObstacle(new Wall());

        map[18][10].setObstacle(new Wall());
        map[18][11].setObstacle(new Wall());

        map[8][6].setObstacle(new Wall());
        map[8][7].setObstacle(new Wall());
        map[8][8].setObstacle(new Wall());
        map[7][8].setObstacle(new Wall());
        map[6][8].setObstacle(new Wall());

        map[13][6].setObstacle(new Wall());
        map[13][7].setObstacle(new Wall());
        map[13][8].setObstacle(new Wall());
        map[14][8].setObstacle(new Wall());
        map[15][8].setObstacle(new Wall());

        map[8][13].setObstacle(new Wall());
        map[8][14].setObstacle(new Wall());
        map[8][15].setObstacle(new Wall());
        map[7][13].setObstacle(new Wall());
        map[6][13].setObstacle(new Wall());

        map[13][13].setObstacle(new Wall());
        map[13][14].setObstacle(new Wall());
        map[13][15].setObstacle(new Wall());
        map[14][13].setObstacle(new Wall());
        map[15][13].setObstacle(new Wall());
    }

    public Field[][] getFieldsArray() {
        return map;
    }


    public void move(GameMap gameMap, Move move) {
        Hero temp = move.getWho();
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        gameMap.getFieldsArray()[y][x].setHero(null);

        x = move.getWhere().getX();
        y = move.getWhere().getY();
        gameMap.getFieldsArray()[y][x].setHero(temp);
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
