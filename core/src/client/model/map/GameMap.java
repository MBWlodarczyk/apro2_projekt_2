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

    public GameMap(int size,int mapNumber) {
        this.map = new Field[size][size];
        try {
            loadMap(readMapFromFile(mapNumber));
        }
        catch (IOException e) {
            e.printStackTrace();
            loadMap();
        }
    }

    private String[][] readMapFromFile(int mapNumber) throws IOException {
        String mapPath;
        if(mapNumber==1)  { mapPath = "mapTXT.txt";}
            else mapPath = "map_maze.txt";
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

     //Loads map from file if file was correctly found
    private void loadMap(String[][] mapString){
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

    // Loads map with grass if there's no file found
    private void loadMap() {
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
                if(map[i][j].getObstacle() instanceof Trap)
                    sb.append("TRAP").append(" ");
                else
                    sb.append(" null");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
