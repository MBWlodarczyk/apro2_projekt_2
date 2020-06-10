package client.model.map;


import client.model.obstacles.Trap;
import client.model.obstacles.Wall;
import client.model.terrain.Grass;
import client.model.terrain.Water;

import java.io.*;
import java.util.Scanner;

/**
 * Class to represent map
 */
public class GameMap implements Serializable {

    /**
     * Two dimensions field array representation of map
     */
    private Field[][] map;

    public GameMap(GameMap copy){
        this.map = copy.map;
    }

    public GameMap(int size) {
        this.map = new Field[size][size];
    }

    /**
     * Reads map from .txt file
     * @param mapName name of map
     * @return map in arrays of strings
     * @throws IOException
     */
    public String[][] readMapFromFile(String mapName) throws IOException {
        String mapPath = "map_"+mapName+".txt";
        Scanner scanner = new Scanner(new File(mapPath));
        String[][] mapString = new String[map.length][map.length];
        int rowNumber=0;
        while(scanner.hasNextLine()) {
            String[] row = scanner.nextLine().split(";");
            System.arraycopy(row, 0, mapString[rowNumber], 0, row.length);
            rowNumber++;
        }
        return mapString;
    }

    /**
     * Loads map if txt file was read correctly
     * @param mapString map in arrays of strings
     */
    public void loadMap(String[][] mapString){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Field(i, j);
                map[i][j].setTerrain(new Grass(i, j));
                if(mapString[i][j].equals("s")){ //s from sea
                    map[i][j].setTerrain(new Water(i,j));
                }
                if(mapString[i][j].equals("w")){
                    map[i][j].setObstacle(new Wall());
                }
            }
        }
    }

    /**
     * Loads map with grass, if there was bug with reading txt file
     */
    public void loadMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Field(i, j);
                map[i][j].setTerrain(new Grass(i, j));
            }
        }
    }

    public Field[][] getFieldsArray() {
        return map;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[j][i].getObstacle() instanceof Trap)
                    sb.append("TRAP").append(" ");
                else
                    sb.append(" null");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
