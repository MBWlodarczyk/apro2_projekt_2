package client.model.skills;

/**
 * Abstract class to represent skill of jumping over something.
 */
public class Jump extends Skill {
    public Jump(int distance) {
        this.distance = distance;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;
    }
}
