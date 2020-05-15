package Client.Model.Heroes;

import Client.Model.Entity;
import Client.Model.Player;
import Client.Model.Skills.Skill;

import java.util.ArrayList;

public abstract class Hero extends Entity {
    static int idGen;
    /**
     * Owner of that hero
     */
    private Player owner;
    /**
     * ArrayList of skills
     */
    protected ArrayList<Skill> skills;
    /**
     * Weight of hero (use in order to solve collisions)
     */
    private int weight;
    /**
     * Health of hero
     */
    private int health;
    /**
     * Start health
     */
    private final int startHealth;



    public Hero(Player owner, int weight, int startHealth,int health) {
        this.owner = owner;
        this.weight = weight;
        this.health = health;
        this.startHealth = startHealth;
        this.skills = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{" +
                "owner=" + owner +
                ", skills=" + skills +
                ", weight=" + weight +
                ", health=" + health +
                '}';
    }
}