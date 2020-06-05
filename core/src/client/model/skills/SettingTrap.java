package client.model.skills;

import client.model.map.GameMap;
import client.model.obstacles.Trap;

/**
 * Class to represent skill of setting up a trap.
 */
public class SettingTrap extends Skill {

    public SettingTrap() {
        distance = 2;
        value = -15;
        range = 1;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;

        soundPath = "";
    }

    public SettingTrap(int damage) {
        distance = 2;
        value = damage;
        range = 1;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }

    public void buildTrap(GameMap gameMap, int x, int y) {
        if(gameMap.getFieldsArray()[x][y].getHero()==null)
        gameMap.getFieldsArray()[x][y].setObstacle(new Trap(value));
    }
}
