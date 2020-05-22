package client.model.obstacles;

import client.model.Entity;

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
