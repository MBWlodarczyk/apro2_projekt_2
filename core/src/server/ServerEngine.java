package server;

import client.controller.GameEngine;
import client.controller.Move;
import client.controller.Turn;
import client.model.Player;
import client.model.heroes.Hero;
import client.model.map.Field;
import client.model.map.GameMap;
import client.model.skills.SkillProperty;
import client.model.skills.Walk;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ServerEngine {

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
        if(move.getWhat().toString().equals("Stay")) return;
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
                boolean[][] possibilities = GameEngine.getValid(gameMap,move.getWhere(),new Walk(k));
                Queue<Point> pos = new LinkedList<>();
                for (int i = 0; i< possibilities.length;i++) {
                    for (int j = 0; j < possibilities[i].length; j++) {
                        if(i==move.getWhere().getX() && j==move.getWhere().getY()) continue;
                        if(possibilities[i][j]) pos.add(new Point(i,j));
                    }
                }
                if(pos.isEmpty()){
                    k++;
                    continue;
                }
                Point temp = pos.poll();
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

    public static Player checkWin(GameMap gameMap){
        Field[][] map = gameMap.getFieldsArray();
        Player winner = null;
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j].getHero()!=null){
                    winner = map[i][j].getHero().getOwner();
                    break;
                }
            }
        }
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j].getHero()!=null){
                    if(map[i][j].getHero().getOwner()!=winner)
                        return null;
                }
            }
        }
        return winner;
    }
}
