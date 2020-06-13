package client.model.skills;

/**
 * Abstract class to represent skill of walking.
 */
public class Walk extends Skill {

    /**
     * @param distance longest possible distance to walk
     */
    public Walk(int distance) {
        this.distance = distance;
        cost=0;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/ruch.mp3";
    }

}