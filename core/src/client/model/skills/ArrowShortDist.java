package client.model.skills;

/**
 * Abstract class to represent skill of shooting an arrow.
 * Short distance, but can go above uncrossable.
 */
public class ArrowShortDist extends Skill {

    public ArrowShortDist() {
        distance = 4;
        value = -20;
        range = 1;
        cost=10;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/strzala_short.mp3";
    }

    public void fireArrow(int yh, int xh, int yt, int xt) {

    }
}
