package client.model.skills;

import client.model.map.GameMap;
import client.model.obstacles.Wall;

/**
 * Class to represent skill of setting a wall.
 */
public class SettingWall extends Skill { //temporary: unbreakable wall

    public SettingWall() {
        distance = 7;
        value = -10;
        range = 1;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;

        soundPath = "";
    }

    public void buildWall(GameMap gameMap, int x, int y) {
        gameMap.getFieldsArray()[x][y].setObstacle(new Wall());
    }
}
