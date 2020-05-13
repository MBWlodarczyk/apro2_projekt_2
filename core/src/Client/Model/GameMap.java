package Client.Model;


import Client.Model.Heroes.Warrior;

/**
 * Class to represent map
 */
public class GameMap {

    /**
     * Two dimensions field array representation of map
     */
    private Field[][] map;


    public GameMap(int size){
        this.map = new Field[size][size];
        loadMap();
    }

    public static void main(String[] args) {
        GameMap gameMap = new GameMap(10);
        System.out.println(gameMap);
    }

    private void loadMap(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++) {
                map[i][j] = new Field(i,j,Type.Grass);
            }
        }
        map[3][2] = new Field(3,2,Type.Wall);
        map[3][2].addEntity(new Warrior(new Player("jakiś typ")));
    }

    public Field[][] getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                sb.append(map[i][j].getType()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
