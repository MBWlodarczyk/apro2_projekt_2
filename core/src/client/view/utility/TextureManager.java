package client.view.utility;

import client.controller.Client;
import client.controller.ControllerState;
import client.controller.GameEngine;
import client.controller.Move;
import client.model.Player;
import client.model.heroes.*;
import client.model.map.Field;
import client.model.map.GameMap;
import client.model.obstacles.Trap;
import client.model.obstacles.Wall;
import client.model.terrain.Grass;
import client.model.terrain.Water;
import client.view.SwordGame;
import client.view.sprites.HeroSprite;
import client.view.sprites.ObstacleSprite;
import client.view.sprites.SkillDistanceSprite;
import client.view.sprites.TerrainSprite;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import static client.controller.Inputs.*;

/**
 * Update textures in all arrayLists which are
 */

public class TextureManager {

    private SwordGame swordGame;

    public TextureManager(SwordGame swordGame) {
        this.swordGame = swordGame;
    }

    /**
     * Group off all update methods
     *
     * @param client              Client
     * @param obstacleSprites     arrayList of obstacles
     * @param terrainSprites      arrayList of terrain
     * @param heroesSprites       arrayList of heroes
     * @param skillDistanceSprite arrayList of skillDistance
     */
    public void update(Client client,
                       ArrayList<ObstacleSprite> obstacleSprites, ArrayList<TerrainSprite> terrainSprites, ArrayList<HeroSprite> heroesSprites,
                       SkillDistanceSprite skillDistanceSprite) {
        Field[][] map = client.getReceived().getMap().getFieldsArray();
        rewriteMap(map, client.player, obstacleSprites, terrainSprites, heroesSprites);
        if (currentState == ControllerState.PERFORM_SKILL)
            skillDistance(map, client.getReceived().getMap(), skillDistanceSprite);
    }

    private void skillDistance(Field[][] map, GameMap gameMap, SkillDistanceSprite skillDistanceSprite) {
        Move move = new Move(map[y][x].getHero(), map[tab[0]][tab[1]], map[y][x], map[y][x].getHero().getSkills().get(skillChosen));
        boolean[][] marked = GameEngine.getValid(gameMap, move.getWhere(), move.getWhat());
        skillDistanceSprite.setSprites(marked);
    }

    private void rewriteMap(Field[][] map, Player player,
                            ArrayList<ObstacleSprite> obstacleSprites, ArrayList<TerrainSprite> terrainSprites, ArrayList<HeroSprite> heroesSprites) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].getObstacle() != null) { //TODO after debuging add there
                    obstacleSprites.add(new ObstacleSprite(map[i][j].getObstacle(), checkObstacleTexture(map[i][j])));
                }
                if (map[i][j].getTerrain() != null) {
                    terrainSprites.add(new TerrainSprite(map[i][j].getTerrain(), checkTerrainTexture(map[i][j])));
                }
                if (map[i][j].getHero() != null) {
                    heroesSprites.add(new HeroSprite(map[i][j].getHero(), checkHeroTexture(map[i][j]),
                            swordGame.heroOwnershipTexture, heroOwnership(map[i][j], player)));
                }
            }
        }
    }

    private boolean heroOwnership(Field field, Player player) {
        return field.getHero().getOwner().equals(player);
    }

    private Texture checkHeroTexture(Field field) {
        if (field.getHero() instanceof Paladin)
            return swordGame.paladinTexture;
        if (field.getHero() instanceof Warrior)
            return swordGame.warriorTexture;
        if (field.getHero() instanceof Archer)
            return swordGame.archerTexture;
        if (field.getHero() instanceof Necromancer)
            return swordGame.necromancerTexture;
        if (field.getHero() instanceof Wizard)
            return swordGame.wizardTexture;
        if (field.getHero() instanceof Priest)
            return swordGame.priestTexture;
        return null;
    }

    private Texture checkTerrainTexture(Field field) {
        if (field.getTerrain() instanceof Grass)
            return swordGame.grassTexture;
        if (field.getTerrain() instanceof Water)
            return swordGame.waterTexture;
        return null;
    }

    private Texture checkObstacleTexture(Field field) {
        if (field.getObstacle() instanceof Wall)
            return swordGame.wallTexture;
        if (field.getObstacle() instanceof Trap)
            return swordGame.trapTexture;
        return null;
    }
}
