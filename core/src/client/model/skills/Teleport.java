package client.model.skills;

import client.model.heroes.Hero;
import client.model.map.GameMap;

/**
 * Abstract class to represent skill of teleportation.
 */
public class Teleport extends Skill {

    public Teleport() {
        distance = 10;
        range = 1;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;

        soundPath = "sound/teleport.mp3";
    }

}
