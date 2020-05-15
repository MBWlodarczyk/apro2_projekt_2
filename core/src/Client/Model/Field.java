package Client.Model;

import Client.Model.Heroes.Hero;

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
     * Type of field
     */
    final private Type type;
    /**
     * Hero on field
     */
    private Hero hero;
    /**
     * Obstacle on field
     */
    private Obstacle obstacle;


    public Field(int y, int x, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hero = null;
        this.obstacle = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
