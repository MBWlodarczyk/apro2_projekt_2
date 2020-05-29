package client.model.skills;

/**
 * Class to represent skill of shooting fireball.
 */
public class Fireball extends Skill {

    public Fireball() {
        distance = 5;
        value = -10;
        range = 3;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.FloodRange;

        soundPath= "sound/fireball.mp3";
    }

    public void throwFireball(int yh, int xh, int yt, int xt) {

    }

}
