package client.model.skills;

/**
 * Abstract class to represent skill of performing necromancy on hero.
 */
public class Necromancy extends Skill {
    public Necromancy(){
        this.distance = 10;
        this.range = 1;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }

}