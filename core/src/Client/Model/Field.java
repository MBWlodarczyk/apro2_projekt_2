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
     * List of entities on field
     */
    private ArrayList<Entity> entities;


    public Field(int y, int x, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        entities = new ArrayList<>(2);
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

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Method that add entity to filed. There could be only two entities.
     */
    public void addEntity(Entity e) {
        entities.add(e);
    }
}
