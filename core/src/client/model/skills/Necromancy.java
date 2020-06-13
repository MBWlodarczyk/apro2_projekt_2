package client.model.skills;

/**
 * Abstract class to represent skill of performing necromancy on hero.
 */
public class Necromancy extends Skill {
    public Necromancy() {
        this.distance = 5;
        this.range = 1;
        cost=50;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/necromancy.mp3";
    }

}