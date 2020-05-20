package Client.Model.obstacles;

import Client.Model.Entity;

public abstract class Obstacle extends Entity {
    private int x;
    private int y;

    public Obstacle(int y, int x) {
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
