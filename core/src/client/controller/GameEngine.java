package client.controller;

import client.model.map.Field;
import client.model.map.GameMap;
import client.model.skills.Skill;

import java.util.LinkedList;
import java.util.Queue;

public class GameEngine {
    //Public methods section

    /**
     * Class to get all possible field to apply move.
     *
     * @param map      Map to check.
     * @param position
     * @param skill
     * @return boolean array of field where can be applied and where cannot.
     */
    public static boolean[][] getValid(GameMap map, Field position, Skill skill) {
        boolean[][] marked = new boolean[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        bfs(map, marked, position.getY(), position.getX(), skill.getDistance());
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
            if (gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
                return true;
        }
        while (y != move.getWhere().getY()) {
            y += Ydir;
            if (gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
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
            if (gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
                return result; //skill stops on wall
            result.add(gameMap.getFieldsArray()[y][x]);
        }
        while (y != destination.getY()) {
            y += Ydir;
            if (gameMap.getFieldsArray()[y][x].getObstacle() != null && gameMap.getFieldsArray()[y][x].getObstacle().isCrossable())
                return result; //skill stops on wall
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
        //remembers fields already added to list
        boolean[][] isOnList = new boolean[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        //counts how far field is from hero
        int[][] steps = new int[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        LinkedList<Integer> listY = new LinkedList<>();
        LinkedList<Integer> listX = new LinkedList<>();
        listY.add(y); //add first field to list
        listX.add(x);
        steps[y][x] = 0; //add steps for first field

        int[] yNbr = new int[]{0, -1, 0, 1}; //4 neighbours of field to visit
        int[] xNbr = new int[]{1, 0, -1, 0};

        while (!listY.isEmpty() && !listX.isEmpty()) {
            int tempY = listY.poll();
            int tempX = listX.poll();
            marked[tempY][tempX] = true;
            int tempSteps = steps[tempY][tempX];

            for (int k = 0; k < 4; k++) {
                if (tempSteps < distance && checkField(map, tempY + yNbr[k], tempX + xNbr[k], isOnList)) {
                    listY.add(tempY + yNbr[k]);
                    listX.add(tempX + xNbr[k]);
                    isOnList[tempY + yNbr[k]][tempX + xNbr[k]] = true; //marks that field is on list
                    steps[tempY + yNbr[k]][tempX + xNbr[k]] = tempSteps + 1; //adds steps for field
                }
            }
        }
    }

    /**
     * Method to help bfs validate if the field is not null or wall.
     */
    private static boolean fieldValid(GameMap map, int y, int x) {
        return y < map.getFieldsArray().length && y >= 0 && x >= 0 &&
                x < map.getFieldsArray()[0].length &&
                (map.getFieldsArray()[y][x].getObstacle() == null || map.getFieldsArray()[y][x].getObstacle().isCrossable());//Typy ktore nie moga byc przekroczone tutaj dodawac
    }

    /**
     * Method to help bfs validate if the field is valid, already marked or already on the list
     */
    private static boolean checkField(GameMap map, int y, int x, boolean[][] isOnList) {
        return fieldValid(map, y, x) && !isOnList[y][x];
    }
}
