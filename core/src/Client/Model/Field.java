package Client.Model;

import java.io.Serializable;
import java.util.ArrayList;

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
    private Entity hero;
        /**
     * Obstacle on field
     */
    private Entity obstacle;


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

    public Entity getHero() {
        return hero;
    }

    public void setHero(Entity hero) {
        this.hero = hero;
    }

    public Entity getObstacle() {
        return obstacle;
    }

    public void setObstacle(Entity obstacle) {
        this.obstacle = obstacle;
    }
}
