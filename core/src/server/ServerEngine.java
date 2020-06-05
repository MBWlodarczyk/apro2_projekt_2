package server;

import client.controller.GameEngine;
import client.controller.Move;
import client.controller.Turn;
import client.model.Player;
import client.model.heroes.Hero;
import client.model.map.Field;
import client.model.map.GameMap;
import client.model.skills.SkillProperty;
import client.model.skills.Stay;
import client.model.skills.Walk;

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
        int health;
        if (move.getWhat().getRangeType() == SkillProperty.FloodRange) {
            for (Field f : GameEngine.findPath(gameMap, move.getFrom(), move.getWhere(), move.getWhat())) {
                if(f.getHero() == null) continue;
                health = f.getHero().getHealth() + move.getWhat().getValue();
                f.getHero().setHealth(health);
                System.out.println(f.getHero().toString() + " is at: " + health + "HP");
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.PointRange) {
            if(move.getWhere().getHero() !=null) {
                health = move.getWhere().getHero().getHealth() + move.getWhat().getValue();
                move.getWhere().getHero().setHealth(health);
                System.out.println(move.getWhere().getHero().toString() + " is at: " + health + "HP");
            }
        }
        if (move.getWhat().getRangeType() == SkillProperty.AreaRange) {
            Queue<Point> inRange = fieldsInRadius(gameMap, move.getWhere(), move.getWhat().getRange());
            inRange.add(new Point(move.getWhere().getX(),move.getWhere().getY()));
            while(!inRange.isEmpty()){
                Point temp = inRange.poll();
                Field target = gameMap.getFieldsArray()[(int)temp.getY()][(int)temp.getX()];
                if(target.getHero() == null) continue;
                health = target.getHero().getHealth() + move.getWhat().getValue();
                target.getHero().setHealth(health);
                System.out.println(target.getHero().toString() + " is at: " + health + "HP");
            }
        }
        //movement section
        if (move.getWhat().getAfterAttack() == SkillProperty.GoToTarget) {
            moveHero(gameMap, move);
        }
    }
    private static void replenishMana(GameMap gameMap){
        Field[][] map = gameMap.getFieldsArray();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].getHero() != null) {
                    map[i][j].getHero().replenishMana();
                }
            }
        }
    }
    private static void moveHero(GameMap gameMap, Move move) {
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

    public static Player checkWin(GameMap gameMap) {
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
    public static Queue<Point> fieldsInRadius(GameMap gameMap, Field target, int radius){
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
