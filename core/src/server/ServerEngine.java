package server;

import client.controller.GameEngine;
import client.controller.Move;
import client.controller.Turn;
import client.model.heroes.Hero;
import client.model.map.Field;
import client.model.map.GameMap;
import client.model.skills.SkillProperty;

import java.util.Queue;

public class ServerEngine {
    /**
     * static method for making sendable answer
     *
     * @param players
     * @param map
     * @return
     */
    public static Answer Calculate(Turn[] players, GameMap map) {
        Answer answer = new Answer(map);
        Queue<Move> player0 = players[0].getMoves();
        Queue<Move> player1 = players[1].getMoves();
        Queue<Move> player2 = players[2].getMoves();
        Queue<Move> player3 = players[3].getMoves();
        for (int i = 0; i < 4; i++) {
            move(map, player0.poll());
        }

        return answer;
    }

    /**
     * static method for handling skills
     *
     * @param gameMap
     * @param move
     */
    public static void move(GameMap gameMap, Move move) {
        //distance check section
        if (!GameEngine.isValid(gameMap, move)) return;
        if (move.getWhat().getUseDistance() == SkillProperty.NoLob) {
            if (GameEngine.isWallOnWay(gameMap, move)) return;
        }
        //movement section
        if (move.getWhat().getAfterAttack() == SkillProperty.GoToTarget) {
            moveHero(gameMap, move);
        }
        if (move.getWhat().toString().equals("Walk") || move.getWhat().toString().equals("Stay")) return;
        //damage dealing section
        int health;
        if (move.getWhat().getRangeType() == SkillProperty.FloodRange) {
            for (Field f : GameEngine.findPath(gameMap, move.getFrom(), move.getWhere(), move.getWhat())) {
                try {
                    health = f.getHero().getHealth() + move.getWhat().getValue();
                    f.getHero().setHealth(health);
                    System.out.println(f.getHero().toString() + " is at: " + health + "HP");
                } catch (NullPointerException ignored) {
                }
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.PointRange) {
            try {
                health = move.getWhere().getHero().getHealth() + move.getWhat().getValue();
                move.getWhere().getHero().setHealth(health);
                System.out.println(move.getWhere().getHero().toString() + " is at: " + health + "HP");
            } catch (NullPointerException ignored) {
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.AreaRange) {
            for (int i = -move.getWhat().getRange(); i < move.getWhat().getRange(); i++) {
                for (int j = -move.getWhat().getRange(); j < move.getWhat().getRange(); j++) {
                    try {
                        if (gameMap.getFieldsArray()[move.getWhere().getY() + i][move.getWhere().getX() + j].getHero() != null) {
                            health = gameMap.getFieldsArray()[move.getWhere().getY() + i][move.getWhere().getX() + j].getHero().getHealth()
                                    + move.getWhat().getValue();
                            gameMap.getFieldsArray()[move.getWhere().getY() + i][move.getWhere().getX() + j].getHero().setHealth(health);
                            //System.out.println(gameMap.getFieldsArray()[move.getWhere().getY() + i][move.getWhere().getX() + j].getHero().toString()+ " is at: "+ health + "HP");
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
        }
    }

    private static void moveHero(GameMap gameMap, Move move) {
        if (gameMap.getFieldsArray()[move.getWhere().getY()][move.getWhere().getX()].getHero() == null) {
            Hero temp = move.getWho();
            int x = move.getFrom().getX();
            int y = move.getFrom().getY();
            gameMap.getFieldsArray()[y][x].setHero(null);

            x = move.getWhere().getX();
            y = move.getWhere().getY();
            gameMap.getFieldsArray()[y][x].setHero(temp);
        } else {
            if (move.getWho().getWeight() < move.getWhere().getHero().getWeight()) return;
            Move next = new Move(move.getWhere().getHero(), gameMap.getFieldsArray()[move.getWhere().getY() + 1][move.getWhere().getX() + 1]
                    , move.getWhere(), move.getWhat()); //moving next hero - move.where is now from, new where is +[1,1]
            moveHero(gameMap, next);
            Hero temp = move.getWho();
            int x = move.getFrom().getX();
            int y = move.getFrom().getY();
            gameMap.getFieldsArray()[y][x].setHero(null);

            x = move.getWhere().getX();
            y = move.getWhere().getY();
            gameMap.getFieldsArray()[y][x].setHero(temp);
        }
    }
}
