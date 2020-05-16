package Client.Model;

import java.io.Serializable;

/**
 * Abstract class to represent single entity
 */
public abstract class Entity implements Serializable {
    protected boolean isFixed = false; // can it be moved by a hero
    protected boolean isVisible = true; // is it visible
    protected boolean isCrossable = true; // can you pass through it and see over/through it
    protected boolean isAttackable = true;
    protected int x, y;

    @Override
    public String toString() { // na razie, pewnie będzie przysłoniona w każdej klasie dziedziczącej
        return this.getClass().toString();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isCrossable() {
        return isCrossable;
    }

    public boolean isAttackable() {
        return isAttackable;
    }
}
