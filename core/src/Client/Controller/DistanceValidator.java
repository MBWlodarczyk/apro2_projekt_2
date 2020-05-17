package Client.Controller;

import Client.Model.Heroes.Paladin;
import Client.Model.Player;
import Client.Model.Skills.Walk;
import Client.Model.map.Field;
import Client.Model.map.GameMap;

/**
 * Class to validate if skill target is in range.
 */
public class DistanceValidator {
    /**
     * Class to get all possible field to apply move.
     *
     * @param map  Map to check.
     * @param move Move to check.
     * @return boolean array of field where can be applied and where cannot.
     */
    public static boolean[][] getValid(GameMap map, Move move) {

        boolean[][] marked = new boolean[map.getMap().length][map.getMap()[0].length];
        dfs(map, marked, move.getFrom().getY(), move.getFrom().getX(), move.getWhat().getDistance());
        return marked;
    }

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
        return y < map.getMap().length && y >= 0 && x >= 0 && x < map.getMap()[0].length && (map.getMap()[y][x].getObstacle()==null || map.getMap()[y][x].getObstacle().isCrossable());//Typy ktore nie moga byc przekroczone tutaj dodawac
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

    //testing for class
    public static void main(String[] args) {
        GameMap map = new GameMap(16);
        Player owner = new Player("xd");
        Move move = new Move(new Paladin(owner), new Field(4, 4), new Field(15, 0), new Walk(3));
        boolean[][] marked = getValid(map, move);
        print(marked);
        System.out.println(map);
        System.out.println(isValid(map, move));
    }

    //class to print array of booleans
    public static void print(boolean[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println();
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
        }
        System.out.println();
    }
}
