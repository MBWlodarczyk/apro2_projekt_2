package Client.Model.map;


import Client.Controller.Move;
import Client.Model.Heroes.Hero;
import Client.Model.obstacles.Wall;
import Client.Model.terrain.Grass;

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
            map[i][0].setObstacle(new Wall(i, 0));
            map[0][i].setObstacle(new Wall(0, i));
            map[map.length - 1][i].setObstacle(new Wall(map.length - 1, i));
            map[i][map.length - 1].setObstacle(new Wall(i, map.length - 1));
        }
//        byte[] hash = new byte[2];
//        map[2][3].setHero(new Archer(new Player("ktos",hash),2,3));
//        map[10][5].setHero(new Archer(new Player("ktos",hash),10,5));
//        map[3][7].setHero(new Archer(new Player("ktos",hash),3,7));
        map[10][3].setObstacle(new Wall(10,3));
        map[11][3].setObstacle(new Wall(11,3));

        map[10][18].setObstacle(new Wall(10,18));
        map[11][18].setObstacle(new Wall(11,18));

        map[3][10].setObstacle(new Wall(3,10));
        map[3][11].setObstacle(new Wall(3,11));

        map[18][10].setObstacle(new Wall(18,10));
        map[18][11].setObstacle(new Wall(18,11));

        map[8][6].setObstacle(new Wall(8,6));
        map[8][7].setObstacle(new Wall(8,7));
        map[8][8].setObstacle(new Wall(8,8));
        map[7][8].setObstacle(new Wall(7,8));
        map[6][8].setObstacle(new Wall(6,8));

        map[13][6].setObstacle(new Wall(13,6));
        map[13][7].setObstacle(new Wall(13,7));
        map[13][8].setObstacle(new Wall(13,8));
        map[14][8].setObstacle(new Wall(14,8));
        map[15][8].setObstacle(new Wall(15,8));

        map[8][13].setObstacle(new Wall(8,13));
        map[8][14].setObstacle(new Wall(8,14));
        map[8][15].setObstacle(new Wall(8,15));
        map[7][13].setObstacle(new Wall(7,13));
        map[6][13].setObstacle(new Wall(6,13));

        map[13][13].setObstacle(new Wall(13,13));
        map[13][14].setObstacle(new Wall(13,14));
        map[13][15].setObstacle(new Wall(13,15));
        map[14][13].setObstacle(new Wall(14,13));
        map[15][13].setObstacle(new Wall(15,13));
    }

    public Field[][] getMap() {
        return map;
    }


    public void move(GameMap gameMap, Move move) {
        Hero temp = move.getWho();
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        gameMap.getMap()[y][x].setHero(null);

        x = move.getWhere().getX();
        y = move.getWhere().getY();
        gameMap.getMap()[y][x].setHero(temp);
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
