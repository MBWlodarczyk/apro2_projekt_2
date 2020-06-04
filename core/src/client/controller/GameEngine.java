package client.controller;

import client.model.map.Field;
import client.model.map.GameMap;
import client.model.skills.Skill;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GameEngine {
    //Public methods section

    /**
     * Class to get all possible field to apply move.
     *
     * @param map  Map to check.
     * @param position
     * @param skill
     * @return boolean array of field where can be applied and where cannot.
     */
    public static boolean[][] getValid(GameMap map, Field position, Skill skill) {
        boolean[][] marked = new boolean[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        dfs(map, marked, position.getY(), position.getX(), skill.getDistance());
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
        boolean[][] marked = getValid(map, move.getFrom(), move.getWhat());
        return marked[move.getWhere().getY()][move.getWhere().getX()];
    }

    /**
     * method to check if there is a wall on way of skill firx X then Y
     *
     * @param gameMap
     * @param move
     * @return
     */
    public static boolean isWallOnWay(GameMap gameMap, Move move) {
        //if -1 you have to subtract, if 1 you add
        int Xdir = (int) Math.signum(move.getWhere().getX() - move.getFrom().getX());
        int Ydir = (int) Math.signum(move.getWhere().getY() - move.getFrom().getY());
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        while (x != move.getWhere().getX()) {
            x += Xdir;
            if(gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
                return true;
        }
        while (y != move.getWhere().getY()) {
            y += Ydir;
            if(gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
                return true;
        }
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
            if(gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
                return result; //skill stops on wall
            result.add(gameMap.getFieldsArray()[y][x]);
        }
        while (y != destination.getY()) {
            y += Ydir;
            if(gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
                return result; //skill stops on wall
            result.add(gameMap.getFieldsArray()[y][x]);
        }
        return result;
    }

    //Private methods section

    /**
     * DFS method to look for valid fields
     */
    private static void dfs(GameMap map, boolean[][] marked, int y, int x, int distance) {
        marked[y][x] = true;
        if (fieldValid(map, y, x - 1) && distance > 0) {
            dfs(map, marked, y, x - 1, distance - 1);
        }
        if (fieldValid(map, y, x + 1) && distance > 0) {
            dfs(map, marked, y, x + 1, distance - 1);
        }
        if (fieldValid(map, y - 1, x) && distance > 0) {
            dfs(map, marked, y - 1, x, distance - 1);
        }
        if (fieldValid(map, y + 1, x) && distance > 0) {
            dfs(map, marked, y + 1, x, distance - 1);
        }
    }

    //new dfs build on stack
//    private static void dfs(GameMap map, boolean[][] marked, int y, int x, int distance) {
//        boolean[][] isOnStack = new boolean[map.getFieldsArray().length][map.getFieldsArray()[0].length];  //do zapamietania co juz jest na stosie
//        int[][] steps = new int[map.getFieldsArray().length][map.getFieldsArray()[0].length]; //do przechowywania jak daleko jest dane pole
//        Stack<Integer> stackY = new Stack<>();
//        Stack<Integer> stackX = new Stack<>();
//        stackY.push(y); //dodanie pierwszego elementu
//        stackX.push(x);
//        steps[y][x] = 0; //dodanie liczby krokow do pierwszego elementu
//
//        while (!stackY.empty()&&!stackX.empty()){
//            int tempY = stackY.pop();
//            int tempX = stackX.pop();
//            marked[tempY][tempX] = true;
//            int tempSteps = steps[tempY][tempX];
//            //cos tutsj z liczeniem w którym "kroku" jestesmy - analogicznie do odejmowania
//            int i =1;
//            if (checkField(map, tempY, tempX - i, marked, isOnStack, tempSteps, distance)) {
//                        pushField(tempY, tempX-i, tempSteps,stackY,stackX,isOnStack,steps);
////                        stackY.push(tempY);
////                        stackX.push(tempX - i);
////                        isOnStack[tempY][tempX - i] = true; //isOnStack przechowuje pola dodane do stosu
////                        steps[tempY][tempX - i] = tempSteps+1;
//            }
//            if (checkField(map, tempY, tempX + i, marked, isOnStack, tempSteps, distance)) { //marked sprawdza czy byly odwiedzone
//                pushField(tempY, tempX+i, tempSteps,stackY,stackX,isOnStack,steps);
////                stackY.push(tempY);
////                        stackX.push(tempX + i);
////                        isOnStack[tempY][tempX + i] = true;
////                        steps[tempY][tempX + i] = tempSteps+1;
//            }
//            if (checkField(map, tempY - i, x, marked, isOnStack, tempSteps, distance)) {
//                pushField(tempY-i, tempX, tempSteps,stackY,stackX,isOnStack,steps);
////                        stackY.push(tempY - i);
////                        stackX.push(tempX);
////                        isOnStack[tempY - i][tempX] = true;
////                        steps[tempY - i][tempX] = tempSteps+1;
//            }
//            if (checkField(map, tempY + i, tempX, marked, isOnStack, tempSteps, distance)) {
//                pushField(tempY+i, tempX, tempSteps,stackY,stackX,isOnStack,steps);
////                stackY.push(tempY + i);
////                        stackX.push(tempX);
////                        isOnStack[tempY + i][tempX] = true;
////                       steps[tempY - i][tempX] = tempSteps+1;
//            }
//        }
//    }

    /**
     * Method to help dfs validate if the field is not null or wall.
     */
    private static boolean fieldValid(GameMap map, int y, int x) {
        return y < map.getFieldsArray().length && y >= 0 && x >= 0 && x < map.getFieldsArray()[0].length && (map.getFieldsArray()[y][x].getObstacle() == null || map.getFieldsArray()[y][x].getObstacle().isCrossable());//Typy ktore nie moga byc przekroczone tutaj dodawac
    }

    /**
     * Method to help dfs validate if the field is valid, already marked or already on the stack
     */
    private  static boolean checkField(GameMap map, int y, int x, boolean[][] marked, boolean[][] isOnStack, int steps, int distance) {
        return fieldValid(map, y, x) && !isOnStack[y][x] && !marked[y][x] && steps+1<distance;
    }

    private static void pushField(int y, int x, int tempSteps, Stack<Integer> stackY, Stack<Integer> stackX, boolean[][] isOnStack, int[][] steps){
        stackY.push(y);
        stackX.push(x);
        isOnStack[y][x] = true; //isOnStack przechowuje pola dodane do stosu
        steps[y][x] = tempSteps+1;
    }
}
