package Client.Model.map;


import Client.Controller.DistanceValidator;
import Client.Controller.Move;
import Client.Model.Heroes.*;
import Client.Model.Player;
import Client.Model.Type;

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
//        map[3][2] = new Field(3, 2, Type.Wall);
        map[3][3].setHero(new Warrior(new Player("Jakiś typ"),3,3));
        map[3][4].setHero(new Paladin(new Player("Jakiś typ"),3,4));
        map[3][5].setHero(new Archer(new Player("Jakiś typ"),3,5));
        map[3][6].setHero(new Wizard(new Player("Jakiś typ"),3,6));

    }

    public Field[][] getMap() {
        return map;
    }


    public void move(GameMap map, Move move) {
        if (DistanceValidator.isValid(map, move)) {
            Hero temp = move.getWho();
            int x = move.getFrom().getX();
            int y = move.getFrom().getY();
            map.getMap()[y][x].setHero(null);
            x = move.getWhere().getX();
            y = move.getWhere().getY();
            map.getMap()[y][x].setHero(temp);
        }
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
