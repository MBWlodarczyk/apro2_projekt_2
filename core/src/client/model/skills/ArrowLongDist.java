package client.model.skills;

/**
 * Abstract class to represent skill of shooting an arrow.
 * Long distance, cannot go above uncrossable.
 */
public class ArrowLongDist extends Skill {

    public ArrowLongDist() {
        distance = 7;
        value = -20;
        range = 1;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;

        soundPath= "sound/strzala_long.mp3";
    }

    public void fireArrow(int yh, int xh, int yt, int xt) {

    }
}
