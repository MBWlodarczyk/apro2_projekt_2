package Client.Model;


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
    private void loadMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Field(i, j, Type.Grass);
            }
        }
//        map[3][2] = new Field(3, 2, Type.Wall);
        map[3][3].setHero(new Warrior(new Player("Jakiś typ")));
        map[3][4].setHero(new Paladin(new Player("Jakiś typ")));
        map[3][5].setHero(new Archer(new Player("Jakiś typ")));

    }

    public Field[][] getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                sb.append(map[i][j].getType()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
