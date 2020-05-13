package Client.Controller;

import Client.Model.Field;
import Client.Model.GameMap;
import Client.Model.Heroes.Paladin;
import Client.Model.Player;
import Client.Model.Skills.Walk;
import Client.Model.Type;
import com.badlogic.gdx.Game;

import java.util.Arrays;

public class DistanceValidator {
    public static boolean[][] getValid(GameMap map, Move move){
        boolean[][] marked=new boolean[map.getMap().length][map.getMap()[0].length];
        dfs(map,marked,move.getFrom().getY(),move.getFrom().getX(),move.getWhat().getDistance());
        return marked;
    }
    private static void dfs(GameMap map, boolean[][] marked, int y, int x, int distance){
        marked[y][x]=true;
        if(fieldValid(map,y,x-1)&&distance>0){
            dfs(map,marked,y,x-1,distance-1);
        }
        if(fieldValid(map,y,x+1)&&distance>0){
            dfs(map,marked,y,x+1,distance-1);
        }
        if(fieldValid(map,y-1,x)&&distance>0){
            dfs(map,marked,y-1,x,distance-1);
        }
        if(fieldValid(map,y+1,x)&&distance>0){
            dfs(map,marked,y+1,x,distance-1);
        }
    }
    private static boolean fieldValid(GameMap map, int y, int x) {
        return y < map.getMap().length && y >= 0 && x >= 0 && x < map.getMap()[0].length && map.getMap()[y][x].getType() == Type.Grass;//Typy ktore nie moga byc przekroczone tutaj dodawac
    }
    public static boolean isValid(GameMap map, Move move){
        boolean[][] marked = getValid(map,move);
        return marked[move.getWhere().getY()][move.getWhere().getX()];
    }

    //testing for class
    public static void main(String[] args) {
        GameMap map = new GameMap(16);
        Player owner = new Player("xd");
        Move move = new Move(owner,new Paladin(owner),new Field(4,4,Type.Grass),new Field(15,0,Type.Grass), new Walk(3));
        boolean[][] marked = getValid(map,move);
        print(marked);
        System.out.println(map);
        System.out.println(isValid(map,move));
    }
    public static void print(boolean[][] arr){
        for(int i=0;i<arr.length;i++){
            System.out.println();
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
        }
        System.out.println();
    }
}
