package client.model.skills;

import client.model.heroes.Hero;
import client.model.map.GameMap;

/**
 * Abstract class to represent skill of teleportation.
 */
public class Teleport extends Skill {
    
    public Teleport(){
        distance = 15;
        range = 1;

        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Lob;
        rangeType = SkillProperty.PointRange;
    }
    
    public void teleportation(GameMap gameMap, int x, int y, Hero hero){
        gameMap.getFieldsArray()[hero.getX()][hero.getY()].setHero(null);
        gameMap.getFieldsArray()[x][y].setHero(hero);
    }
}
