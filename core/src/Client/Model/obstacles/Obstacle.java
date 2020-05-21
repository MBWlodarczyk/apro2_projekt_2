package Client.Model.obstacles;

import Client.Model.Entity;

/**
 * Class representing single obstacle on map.
 */
public abstract class Obstacle extends Entity {

    public Obstacle() {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
