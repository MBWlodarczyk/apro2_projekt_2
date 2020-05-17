package Client.Model.Heroes;

import Client.Model.Entity;
import Client.Model.Player;
import Client.Model.Skills.Skill;

import java.util.ArrayList;

/**
 * Class to represent abstract hero
 */
public abstract class Hero extends Entity {
    static int idGen;
    /**
     * Start health
     */
    private final int startHealth;
    /**
     * ArrayList of skills
     */
    protected ArrayList<Skill> skills;
    /**
     * Owner of that hero
     */
    private Player owner;
    /**
     * Weight of hero (use in order to solve collisions)
     */
    private int weight;
    /**
     * Health of hero
     */
    private int health;
    private float heathStatus;
    protected int speed;

    public Hero(Player owner, int weight, int startHealth, int health, int speed) {
        this.owner = owner;
        this.speed = speed;
        this.weight = weight;
        this.health = health;
        this.startHealth = startHealth;
        this.heathStatus = (float) health / startHealth;
        this.skills = new ArrayList<>();
    }

    public static int getIdGen() {
        return idGen;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "owner=" + owner +
                ", skills=" + skills +
                ", weight=" + weight +
                ", health=" + health +
                ", startHealth=" + startHealth +
                ", heathStatus=" + heathStatus +
                '}';
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public float getHeathStatus() {
        return heathStatus;
    }
}