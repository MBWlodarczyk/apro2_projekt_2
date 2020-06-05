package client.model.obstacles;

import client.model.Entity;

/**
 * Class representing single obstacle on map.
 */
public abstract class Obstacle extends Entity {
    protected int damage;
    protected int type; // czy zadaje damage, czy może zatrzymuje gracza itd.
    protected boolean immobilize; // czy zatrzymuje gracza


    public Obstacle() {

    }

    public int getDamage() {
        return damage;
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
