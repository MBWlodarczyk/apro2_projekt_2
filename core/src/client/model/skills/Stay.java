package client.model.skills;

/**
 * Abstract class to represent skill of staying in place.
 */
public class Stay extends Skill {

    public Stay() {
        distance = 0;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;

        soundPath= "";
    }

}
