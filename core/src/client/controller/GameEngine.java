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
     * @param map  Map to check.
     * @param move Move to check.
     * @return boolean array of field where can be applied and where cannot.
     */
    public static boolean[][] getValid(GameMap map, Move move) {
        boolean[][] marked = new boolean[map.getFieldsArray().length][map.getFieldsArray()[0].length];
        dfs(map, marked, move.getFrom().getY(), move.getFrom().getX(), move.getWhat().getDistance());
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

    /**
     * Method to help dfs validate if the field is not null or wall.
     */
    private static boolean fieldValid(GameMap map, int y, int x) {
        return y < map.getFieldsArray().length && y >= 0 && x >= 0 && x < map.getFieldsArray()[0].length && (map.getFieldsArray()[y][x].getObstacle() == null || map.getFieldsArray()[y][x].getObstacle().isCrossable());//Typy ktore nie moga byc przekroczone tutaj dodawac
    }
}
