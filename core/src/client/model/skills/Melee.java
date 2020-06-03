package client.model.skills;

/**
 * Abstract class to represent skill of short range attack.
 */
public class Melee extends Skill {
    public Melee() {
        distance = 2;
        value = -10;
        range = 0;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/miecz.mp3";
    }
}
