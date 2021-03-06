package client.model.skills;

/**
 * Abstract class to represent skill of shooting an arrow.
 * Long distance, cannot go above uncrossable.
 */
public class ArrowLongDist extends Skill {

    public ArrowLongDist() {
        distance = 6;
        value = -10;
        range = 1;
        cost=5;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/strzala_long.mp3";
    }

    public void fireArrow(int yh, int xh, int yt, int xt) {

    }
}
