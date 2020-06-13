package server;

import client.controller.GameEngine;
import client.controller.Move;
import client.controller.Turn;
import client.model.Player;
import client.model.heroes.Hero;
import client.model.map.Field;
import client.model.map.GameMap;
import client.model.obstacles.Obstacle;
import client.model.obstacles.Trap;
import client.model.obstacles.Wall;
import client.model.skills.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ServerEngine {
    /**
     * method to perform turns when server recieves them
     */
    public static Answer performTurns(GameMap gameMap, ArrayList<Turn> turns) {
        GameMap local = new GameMap(gameMap);
        ArrayList<String> coms = new ArrayList<String>();
        PriorityQueue<Move> result = new PriorityQueue<>(4);
        for (int i = 0; i < 4; i++) {
            for (Turn turn : turns) {
                if (turn.getMoves().isEmpty()) continue;
                result.add(turn.getMoves().poll());
            }
            for (int k = 0; k < result.size(); k++) {
                move(local, result.poll(), coms);
            }
        }
        replenishMana(local);
        //coms.forEach(System.out::println);
        return new Answer(local,coms);
    }

    /**
     * static method for handling skills
     *
     * @param gameMap
     * @param move
     */
    public static void move(GameMap gameMap, Move move, ArrayList<String> res) {
        //distance check section
        if (!GameEngine.isValid(gameMap, move)) return;
        if (move.getWhat().getUseDistance() == SkillProperty.NoLob) {
            if (GameEngine.isWallOnWay(gameMap, move)) return;
        }
        //mana check section
        if(move.getWho().getMana()<move.getWhat().getCost()){
            res.add(move.getWho().toString() + " is exhausted");
            return;
        }
        //damage dealing section
        if (!(move.getWhat() instanceof Walk) && move.getWhat().getRangeType() == SkillProperty.FloodRange) {
            for (Field f : GameEngine.findPath(gameMap, move.getFrom(), move.getWhere(), move.getWhat())) {
                if (f.getHero() == null) continue;
                res.add(addDamage(gameMap, f.getY(), f.getX(), move.getWhat().getValue(),move));
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.PointRange) {
            if (move.getWhere().getHero() != null) {
                if(move.getWhat() instanceof Necromancy){
                    gameMap.getFieldsArray()[move.getWhere().getY()][move.getWhere().getX()].getHero().ressurect();
                }
                res.add(addDamage(gameMap, move.getWhere().getY(), move.getWhere().getX(), move.getWhat().getValue(),move));
            } else {
                if (move.getWhat() instanceof SettingTrap || move.getWhat() instanceof SettingWall) {
                    res.add(buildObstacle(gameMap, move));
                }
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.AreaRange) {
            Queue<Point> inRange = fieldsInRadius(gameMap, move.getWhere(), move.getWhat().getRange());
            inRange.add(new Point(move.getWhere().getX(), move.getWhere().getY()));
            while (!inRange.isEmpty()) {
                Point temp = inRange.poll();
                Field target = gameMap.getFieldsArray()[(int) temp.getY()][(int) temp.getX()];
                if (target.getHero() == null) continue;
                res.add(addDamage(gameMap, target.getY(), target.getX(), move.getWhat().getValue(),move));
            }
        }
        //movement section
        Point afterattack = null;
        if (move.getWhat().getAfterAttack() == SkillProperty.GoToTarget) {
            afterattack = moveHero(gameMap, move);
        }
        if(afterattack == null) afterattack = new Point(move.getFrom().getX(), move.getFrom().getY());
        //decrease mana section
        Hero temporary = gameMap.getFieldsArray()[afterattack.y][afterattack.x].getHero();
        temporary.setMana(temporary.getMana()-move.getWhat().getCost());
        res.add(temporary.toString() + " has " + temporary.getMana() + "MP");
    }

    /**
     * method to restore 10 mana points after move.
     * @param gameMap
     */
    private static void replenishMana(GameMap gameMap) {
        Field[][] map = gameMap.getFieldsArray();
        for (Field[] fields : map) {
            for (int j = 0; j < map[0].length; j++) {
                if (fields[j].getHero() != null) {
                    fields[j].getHero().replenishMana();
                }
            }
        }
    }

    /**
     * method handling hero movement considering weight, traps, and occupied fields
     * @param gameMap
     * @param move
     */
    private static Point moveHero(GameMap gameMap, Move move) {
        if (move.getWhere().getObstacle() != null) {
            addDamage(gameMap, move.getFrom().getY(), move.getFrom().getX(), move.getWhere().getObstacle().getDamage(),null);
            gameMap.getFieldsArray()[move.getWhere().getY()][move.getWhere().getX()].setObstacle(null);
        }
        if (move.getWhat() instanceof Stay){
            gameMap.getFieldsArray()[move.getFrom().getY()][move.getFrom().getX()].setHero(move.getWho());
            return null;
        }
        if (move.getWhere() == move.getFrom()) return null;
        if (move.getWhere().getHero() != null && move.getWho().getWeight() <= move.getWhere().getHero().getWeight()) return null;
        //moving hero
        Hero temp = gameMap.getFieldsArray()[move.getFrom().getY()][move.getFrom().getX()].getHero();
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        gameMap.getFieldsArray()[y][x].setHero(null);

        if(gameMap.getFieldsArray()[move.getWhere().getY()][move.getWhere().getX()].getHero() != null){
            //second hero movement
            Hero tmp = gameMap.getFieldsArray()[move.getWhere().getY()][move.getWhere().getX()].getHero();
            int xt = move.getWhere().getX();
            int yt = move.getWhere().getY();
            gameMap.getFieldsArray()[yt][xt].setHero(null);

            boolean changed = true;
            int k = 1;
            Queue<Point> possibilities;
            do {
                possibilities = fieldsInRadius(gameMap, move.getWhere(), k++);
                Point poss = possibilities.poll();
                while (!possibilities.isEmpty()) {
                    assert poss != null;
                    if (gameMap.getFieldsArray()[poss.y][poss.x].getHero() == null) {
                        tmp.setX((int) poss.getX());
                        tmp.setY((int) poss.getY());
                        gameMap.getFieldsArray()[poss.y][poss.x].setHero(tmp);
                        //spychanie na trap
                        if (gameMap.getFieldsArray()[poss.y][poss.x].getObstacle() != null) {
                            addDamage(gameMap, poss.y, poss.x,
                                    gameMap.getFieldsArray()[poss.y][poss.x].getObstacle().getDamage(),null);
                            gameMap.getFieldsArray()[poss.y][poss.x].setObstacle(null);
                        }
                        changed = false;
                        break;
                    }
                    poss = possibilities.poll();
                }
            } while (changed);
        }
        temp.setY(y);
        temp.setX(x);
        x = move.getWhere().getX();
        y = move.getWhere().getY();
        gameMap.getFieldsArray()[y][x].setHero(temp);
        return new Point(x,y);
    }

    /**
     * method for handling what obstacle should be built on field
     * @param gameMap
     * @param move
     */
    private static String buildObstacle(GameMap gameMap, Move move) {
        int y = move.getWhere().getY();
        int x = move.getWhere().getX();
        Obstacle o = null;
        if (move.getWhat() instanceof SettingWall)
            o = new Wall();
        if (move.getWhat() instanceof SettingTrap)
            o = new Trap(move.getWhat().getValue());
        if (gameMap.getFieldsArray()[y][x].getHero() == null) {
            gameMap.getFieldsArray()[y][x].setObstacle(o);
            return (o.toString() + " set on:" + x + " " + y);
        }
        else return null;
    }

    /**
     * method to deal damage to a hero.
     * damage is multiplied by how many friendly heroes are in 4-neighbourhood
     * @param map
     * @param y
     * @param x
     * @param damage
     * @param m
     */
    private static String addDamage(GameMap map, int y, int x, int damage,Move m) {
        int multiplier = 1;
        if(m!=null) {
            Queue<Point> check = fieldsInRadius(map, map.getFieldsArray()[m.getFrom().getY()][m.getFrom().getX()], 1);
            Player owner = m.getWhose();
            for (Point p : check) {
                if (map.getFieldsArray()[(int) p.getY()][(int) p.getX()].getHero() == null) continue;
                if (map.getFieldsArray()[(int) p.getY()][(int) p.getX()].getHero().getOwner().equals(owner))
                    multiplier++;
            }
        }
        int health = map.getFieldsArray()[y][x].getHero().getHealth() + (damage*multiplier);
        map.getFieldsArray()[y][x].getHero().setHealth(health);
        return (map.getFieldsArray()[y][x].getHero().toString() + " is at: " + health + "HP");
    }

    /**
     * player wins if there are no other player's heroes
     * @param gameMap
     * @return
     */
    static Player checkWin(GameMap gameMap) {
        Field[][] map = gameMap.getFieldsArray();
        Player winner = null;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].getHero() != null) {
                    winner = map[i][j].getHero().getOwner();
                    break;
                }
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].getHero() != null) {
                    if (map[i][j].getHero().getOwner() != winner)
                        return null;
                }
            }
        }
        return winner;
    }

    /**
     * Method returning queue of near fields in radius
     * @param gameMap
     * @param target
     * @param radius
     * @return
     */
    private static Queue<Point> fieldsInRadius(GameMap gameMap, Field target, int radius) {
        boolean[][] possibilities = GameEngine.getValid(gameMap, target, new Walk(radius));
        Queue<Point> inRange = new LinkedList<>();
        for (int i = 0; i < possibilities.length; i++) {
            for (int j = 0; j < possibilities[i].length; j++) {
                if (i == target.getX() && j == target.getY()) continue;
                if (possibilities[j][i]) inRange.add(new Point(i, j));
            }
        }
        return inRange;
    }
}
