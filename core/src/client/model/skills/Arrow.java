package client.model.skills;

/**
 * Abstract class to represent skill of shooting an arrow.
 */
public class Arrow extends Skill {

    public Arrow() {
        distance = 15;
        value = -20;
        range = 1;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;
    }

    public void fireArrow(int yh, int xh, int yt, int xt) {

    }
}
