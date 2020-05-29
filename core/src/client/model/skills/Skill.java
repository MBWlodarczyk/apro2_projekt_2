package client.model.skills;

import java.io.Serializable;

/**
 * Abstract class to represent single skill.
 */
public abstract class Skill implements Serializable {
    protected int distance;  //distance of move (how far can go)
    protected int value;  //value of damage
    protected int range;  //range of attack (how far attack can spread)
    protected SkillProperty afterAttack;  //where is hero after attack
    protected SkillProperty useDistance;  //whether can use over uncrossable or not etc.
    protected SkillProperty rangeType;
    protected String soundPath;


    public int getDistance() {
        return distance;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public int getValue() {
        return value;
    }

    public int getRange() {
        return range;
    }

    public SkillProperty getAfterAttack() {
        return afterAttack;
    }

    public SkillProperty getUseDistance() {
        return useDistance;
    }

    public SkillProperty getRangeType() {
        return rangeType;
    }

    @Override
    public String toString() {
        return String.valueOf(getClass().getSimpleName());
    }

}
