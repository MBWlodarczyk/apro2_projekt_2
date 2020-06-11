package client.model.skills;

/**
 * Class to represent skill of setting up a trap.
 */
public class SettingTrap extends Skill {

    public SettingTrap() {
        distance = 2;
        value = -40;
        range = 1;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/trap_wire.mp3";
    }

}
