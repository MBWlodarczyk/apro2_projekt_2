package Client.Model.terrain;

import Client.Model.Entity;

/**
 * This is the part of a field that make no interaction with hero, it just exist in that poor pixel world
 */
public abstract class Terrain extends Entity {
    private final int x;
    private final int y;

    public Terrain(int y, int x) {
        this.x = x;
        this.y = y;
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
