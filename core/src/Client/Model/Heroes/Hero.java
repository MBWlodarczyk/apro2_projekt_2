package Client.Model.Heroes;

import Client.Model.Entity;
import Client.Model.Player;
import Client.Skills.Skill;

import java.util.ArrayList;

public abstract class Hero extends Entity {
    /**
     * Owner of that hero
     */
    private Player owner;
    /**
     * ArrayList of skills
     */
    private ArrayList<Skill> skills;
    /**
     * Weight of hero (use in order to solve collisions)
     */
    private int weight;
    /**
     * Health of hero
     */
    private int health;
    /**
     * Distance a hero can travel in a single turn.
     */
    private int moveDistance;
    static int idGen;

    public Hero(Player owner, int weight, int health, int moveDistance) {
        this.owner = owner;
        this.weight = weight;
        this.health = health;
        this.moveDistance = moveDistance;
    }

    @Override
    public String toString() {
        return "{" +
                "owner=" + owner +
                ", skills=" + skills +
                ", weight=" + weight +
                ", health=" + health +
                ", moveDistance=" + moveDistance +
                '}';
    }
}