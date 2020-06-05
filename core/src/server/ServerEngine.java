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
    public static void performTurns(GameMap gameMap, ArrayList<Turn> turns) {
        PriorityQueue<Move> result = new PriorityQueue<>(4);
        for (int i = 0; i < 4; i++) {
            for (Turn turn : turns) {
                if (turn.getMoves().isEmpty()) continue;
                result.add(turn.getMoves().poll());
            }
            for (int k = 0; k < result.size(); k++) {
                move(gameMap, result.poll());
            }
        }
        replenishMana(gameMap);
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
        //damage dealing section
        if (move.getWhat().getRangeType() == SkillProperty.FloodRange && !(move.getWhat() instanceof Walk)) {
            for (Field f : GameEngine.findPath(gameMap, move.getFrom(), move.getWhere(), move.getWhat())) {
                if(f.getHero() == null) continue;
                addDamage(f.getHero(), move.getWhat().getValue());
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.PointRange) {
            if(move.getWhere().getHero() !=null) {
                addDamage(move.getWhere().getHero(),move.getWhat().getValue());
            }else {
                if (move.getWhat() instanceof SettingTrap || move.getWhat() instanceof SettingWall) {
                    buildObstacle(gameMap, move);
                }
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.AreaRange) {
            Queue<Point> inRange = fieldsInRadius(gameMap, move.getWhere(), move.getWhat().getRange());
            inRange.add(new Point(move.getWhere().getX(),move.getWhere().getY()));
            while(!inRange.isEmpty()){
                Point temp = inRange.poll();
                Field target = gameMap.getFieldsArray()[(int)temp.getY()][(int)temp.getX()];
                if(target.getHero() == null) continue;
                addDamage(target.getHero(),move.getWhat().getValue());
            }
        }
        //movement section
        if (move.getWhat().getAfterAttack() == SkillProperty.GoToTarget) {
            moveHero(gameMap, move);
        }
    }
    private static void replenishMana(GameMap gameMap){
        Field[][] map = gameMap.getFieldsArray();
        for (Field[] fields : map) {
            for (int j = 0; j < map[0].length; j++) {
                if (fields[j].getHero() != null) {
                    fields[j].getHero().replenishMana();
                }
            }
        }
    }
    private static void moveHero(GameMap gameMap, Move move) {
        if(move.getWhere().getObstacle() != null){
            addDamage(move.getWho(),move.getWhere().getObstacle().getDamage());
        }
        if(move.getWhat() instanceof Stay) return;
        if(move.getWhere()==move.getFrom())return;
        if (gameMap.getFieldsArray()[move.getWhere().getY()][move.getWhere().getX()].getHero() == null) {
            Hero temp = move.getWho();
            int x = move.getFrom().getX();
            int y = move.getFrom().getY();
            gameMap.getFieldsArray()[y][x].setHero(null);

            x = move.getWhere().getX();
            y = move.getWhere().getY();
            gameMap.getFieldsArray()[y][x].setHero(temp);
        } else {
            if (move.getWhere().getHero()== null || move.getWho().getWeight() <= move.getWhere().getHero().getWeight()) return;
            Move next = null;
            int k=1;
            do{
                Queue<Point> possibilities = fieldsInRadius(gameMap, move.getWhere(), k);
                if(possibilities.isEmpty()){
                    k++;
                    continue;
                }
                Point temp = possibilities.poll();
                next = new Move(move.getWhere().getHero(), gameMap.getFieldsArray()[(int)temp.getY()][(int)temp.getX()]
                        , move.getWhere(), new Walk(k));
            }while (next == null || !GameEngine.isValid(gameMap, next));
            Hero temp = move.getWho();
            int x = move.getFrom().getX();
            int y = move.getFrom().getY();
            gameMap.getFieldsArray()[y][x].setHero(null);
            moveHero(gameMap, next);
            x = move.getWhere().getX();
            y = move.getWhere().getY();
            gameMap.getFieldsArray()[y][x].setHero(temp);
        }
    }
    private static void buildObstacle(GameMap gameMap, Move move) {
        int y = move.getWhere().getY();
        int x = move.getWhere().getX();
        Obstacle o = null;
        if(move.getWhat() instanceof SettingWall)
            o = new Wall();
        if(move.getWhat() instanceof SettingWall)
            o = new Trap(move.getWhat().getValue());
        if(gameMap.getFieldsArray()[y][x].getHero()==null) {
            gameMap.getFieldsArray()[y][x].setObstacle(o);
            System.out.println("Trap set on:" + x +" "+y);
        }
    }
    private static void addDamage(Hero hero, int damage){
        int health = hero.getHealth() + damage;
        hero.setHealth(health);
        System.out.println(hero.toString() + " is at: " + health + "HP");
    }

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
    private static Queue<Point> fieldsInRadius(GameMap gameMap, Field target, int radius){
        boolean[][] possibilities = GameEngine.getValid(gameMap,target,new Walk(radius));
        Queue<Point> inRange = new LinkedList<>();
        for (int i = 0; i< possibilities.length;i++) {
            for (int j = 0; j < possibilities[i].length; j++) {
                if(i==target.getX() && j==target.getY()) continue;
                if(possibilities[i][j]) inRange.add(new Point(i,j));
            }
        }
        return inRange;
    }
}
