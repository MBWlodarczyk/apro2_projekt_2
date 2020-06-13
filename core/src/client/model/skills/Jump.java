package client.model.skills;

/**
 * Abstract class to represent skill of jumping over something.
 */
public class Jump extends Skill {
    public Jump(int distance) {
        this.distance = distance;
        cost=0;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/skok.mp3";
    }
}
