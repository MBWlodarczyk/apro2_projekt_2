package client.model.skills;

/**
 * Abstract class to represent skill of teleportation.
 */
public class Teleport extends Skill {

    public Teleport() {
        distance = 10;
        range = 1;
        cost=20;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/teleport.mp3";
    }

}
