package client.model.skills;

/**
 * Abstract class to represent skill of healing on hero.
 */
public class Heal extends Skill {
    public Heal(boolean Strong) {
        if (Strong) {
            distance = 4;
            value = +20;
            range = 1;
            cost=20;

            afterAttack = SkillProperty.GoToTarget;
            useDistance = SkillProperty.Flood;
            rangeType = SkillProperty.PointRange;
        } else {
            distance = 10;
            value = +5;
            range = 2;

            afterAttack = SkillProperty.StayOnSpot;
            useDistance = SkillProperty.Lob;
            rangeType = SkillProperty.AreaRange;
        }
        soundPath = "sound/uzdrowienie.mp3";
    }
}
