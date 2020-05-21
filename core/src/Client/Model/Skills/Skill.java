package Client.Model.Skills;

import java.io.Serializable;

/**
 * Abstract class to represent single skill.
 */
public abstract class Skill implements Serializable {
    protected int distance;
    protected int value;
    protected int range;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
