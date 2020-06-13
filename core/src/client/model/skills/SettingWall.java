package client.model.skills;

/**
 * Class to represent skill of setting a wall.
 */
public class SettingWall extends Skill { //temporary: unbreakable wall

    public SettingWall() {
        distance = 3;
        value = 0;
        range = 1;
        cost=25;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/block.mp3";
    }
}
