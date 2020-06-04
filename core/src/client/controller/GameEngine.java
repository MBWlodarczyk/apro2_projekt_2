package client.controller;

import client.model.map.Field;
import client.model.map.GameMap;
import client.model.skills.Skill;

import java.util.*;

public class GameEngine {
    //Public methods section

    /**
     * Class to get all possible field to apply move.
     *
     * @param map  Map to check.
     * @param move Move to check.
     * @return boolean array of field where can be applied and where cannot.
     */
    public static boolean[][] getValid(GameMap map, Move move) {
        boolean[][] marked = new boolean[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        bfs(map, marked, move.getFrom().getY(), move.getFrom().getX(),move.getWhat().getDistance());
        return marked;
    }

    /**
     * Method to check is the move is valid.
     *
     * @param map  Map to check.
     * @param move Move to check.
     * @return true if possible, false otherwise.
     */
    public static boolean isValid(GameMap map, Move move) {
        boolean[][] marked = getValid(map, move);
        return marked[move.getWhere().getY()][move.getWhere().getX()];
    }

    /**
     * method to check if there is a wall on way of skill
     *
     * @param map
     * @param move
     * @return
     */
    public static boolean isWallOnWay(GameMap map, Move move) {
        return false;
    }

    /**
     * Method to check if move is along the rules
     *
     * @param move
     * @param moves (queue)
     * @return
     */
    public static boolean checkMove(Move move, Queue<Move> moves) {
        //check if tile is crossable
        if (move.getWhere().getObstacle() != null && !move.getWhere().getObstacle().isCrossable()) {
            System.out.println("This tile is not crossable");
            return true;
        }
        // check if hero has moved yet.
        for (Move m : moves) {
            if (!m.equals(move) && m.getWho().equals(move.getWho())) {
                System.out.println("This hero has made a move already");
                return true;
            }
        }
        return false;
    }

    /**
     * path of flood type skill first Xaxis then Yaxis
     *
     * @param gameMap
     * @param position
     * @param destination
     * @return array of next fields
     */
    public static Queue<Field> findPath(GameMap gameMap, Field position, Field destination, Skill skill) {
        int maxDistance = skill.getDistance(); //for furthrer development (walls etc)
        Queue<Field> result = new LinkedList<>();
        //if -1 you have to subtract, if 1 you add
        int Xdir = (int) Math.signum(destination.getX() - position.getX());
        int Ydir = (int) Math.signum(destination.getY() - position.getY());
        int x = position.getX();
        int y = position.getY();
        while (x != destination.getX()) {
            x += Xdir;
            result.add(gameMap.getFieldsArray()[y][x]);
        }
        while (y != destination.getY()) {
            y += Ydir;
            result.add(gameMap.getFieldsArray()[y][x]);
        }
        return result;
    }

    //Private methods section

    /**
     * BFS method to look for valid fields
     */
    //bfs build on queue
    private static void bfs(GameMap map, boolean[][] marked, int y, int x, int distance) {
        //do zapamietania co juz jest na stosie
        boolean[][] isOnStack = new boolean[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        //do przechowywania jak daleko jest dane pole
        int[][] steps = new int[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        LinkedList<Integer> listY = new LinkedList<>();
        LinkedList<Integer> listX = new LinkedList<>();
        listY.add(y); //dodanie pierwszego elementu
        listX.add(x);
        steps[y][x] = 0; //dodanie liczby krokow do pierwszego elementu

        int[] yNbr = new int[]{0,-1,0,1}; //4 sąsiedzi pola
        int[] xNbr = new int[]{1,0,-1,0};

        while (!listY.isEmpty()&&!listX.isEmpty()){
            int tempY = listY.poll();
            int tempX = listX.poll();
            marked[tempY][tempX] = true;
            int tempSteps = steps[tempY][tempX]; //cos tutsj z liczeniem w którym "kroku" jestesmy - analogicznie do odejmowania

            for(int k=0;k<4;k++){
                if(tempSteps<distance && checkField(map, tempY+yNbr[k], tempX+xNbr[k], isOnStack)) {
                    listY.add(tempY + yNbr[k]);
                    listX.add(tempX + xNbr[k]);
                    isOnStack[tempY + yNbr[k]][tempX + xNbr[k]] = true; //isOnStack przechowuje pola dodane do stosu
                    steps[tempY + yNbr[k]][tempX + xNbr[k]] = tempSteps+1; //zmienia liczbe krokow w ktorych mozna dojsc do pola
                }
            }
        }
    }

    /**
     * Method to help dfs validate if the field is not null or wall.
     */
    private static boolean fieldValid(GameMap map, int y, int x) {
        return y < map.getFieldsArray().length && y >= 0 && x >= 0 &&
                x < map.getFieldsArray()[0].length &&
                (map.getFieldsArray()[y][x].getObstacle() == null || map.getFieldsArray()[y][x].getObstacle().isCrossable());//Typy ktore nie moga byc przekroczone tutaj dodawac
    }

    /**
     * Method to help dfs validate if the field is valid, already marked or already on the stack
     */
    private  static boolean checkField(GameMap map, int y, int x, boolean[][] isOnStack) {
        return fieldValid(map, y, x) && !isOnStack[y][x];
    }
}
