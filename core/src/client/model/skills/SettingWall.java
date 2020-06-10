package client.model.skills;

import client.model.map.GameMap;
import client.model.obstacles.Wall;

/**
 * Class to represent skill of setting a wall.
 */
public class SettingWall extends Skill { //temporary: unbreakable wall

    public SettingWall() {
        distance = 7;
        value = 0;
        range = 1;

        afterAttack = SkillProperty.StayOnSpot;
        useDistance = SkillProperty.NoLob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/block.mp3";
    }

    public void buildWall(GameMap gameMap, int y, int x) {
        if (gameMap.getFieldsArray()[y][x].getHero() == null)
            gameMap.getFieldsArray()[y][x].setObstacle(new Wall());
    }
}
