package client.model.skills;

/**
 * Abstract class to represent skill of short range attack.
 */
public class Melee extends Skill {
    public Melee(int value) {
        distance = 2;
        this.value = value;
        range = 1;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/miecz.mp3";
    }
}
