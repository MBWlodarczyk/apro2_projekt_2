package client.model.skills;

/**
 * Abstract class to represent skill of stomping.
 */
public class Stomp extends Skill {

    public Stomp() {
        distance = 5;
        value = -10;
        range = 3;
        cost=25;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.AreaRange;

        soundPath = "sound/stomp.mp3";
    }
}
