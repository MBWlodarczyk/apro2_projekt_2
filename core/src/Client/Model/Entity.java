package Client.Model;

import java.io.Serializable;

/**
 * Abstract class to represent single entity
 */
public abstract class Entity implements Serializable {
    /**
     * Boolean to hold if entity is invisible
     */
    private boolean invisible;
    /**
     * Boolean to hold if entity is fixed
     */
    private boolean fixed;

    public boolean isFixed() {
        return fixed;
    }

    public boolean isInvisible() {
        return invisible;
    }
}
