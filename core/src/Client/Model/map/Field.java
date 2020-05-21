package Client.Model.map;

import Client.Model.Heroes.Hero;
import Client.Model.obstacles.Obstacle;
import Client.Model.terrain.Terrain;

import java.io.Serializable;

/**
 * Class to represent single field
 */
public class Field implements Serializable {
    /**
     * X coordinate of the field
     */
    final private int x;
    /**
     * Y coordinate of the field
     */
    final private int y;
    /**
     * Hero on field
     */
    private Hero hero;
    /**
     * Obstacle on field
     */
    private Obstacle obstacle;
    /**
     * Terrain on a field
     */
    private Terrain terrain;


    public Field(int y, int x) {
        this.x = x;
        this.y = y;
        this.hero = null;
        this.obstacle = null;
        this.terrain = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        if(hero!=null){
            hero.setX(x); //in order to change the x and y Hero coordinates
            hero.setY(y);  //FK BLAD
        }
        this.hero = hero;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
        obstacle.setX(x);
        obstacle.setY(y);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
        terrain.setX(x);
        terrain.setY(y);
    }
}
