package client.model.heroes;

import client.model.Entity;
import client.model.Player;
import client.model.skills.Skill;

import java.util.ArrayList;

/**
 * Class to represent abstract hero
 */
public abstract class Hero extends Entity {

    static int idGen;
    private final int maxHealth;
    private final int maxMana;
    protected ArrayList<Skill> skills;
    private int speed;
    private Player owner;
    private int weight;
    private int health;
    private int x;
    private int y;
    private int mana;

    public Hero(Player owner, int weight, int startHealth, int health, int speed, int y, int x) {
        this.owner = owner;
        this.speed = speed;
        this.weight = weight;
        this.health = health;
        this.maxHealth = startHealth;
        this.mana = health;
        this.maxMana = startHealth;
        this.skills = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public void replenishMana() {
        if (mana + 10 < maxMana) {
            mana += 10;
        } else mana = maxMana;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public Player getOwner() {
        return owner;
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

    public int getHealth() {
        return health;
    }

    public boolean isDead(){
        return this.health == 0;
    }

    public void ressurect(){
        this.health=100;
    }

    public void setHealth(int health) {
        if(this.health==0) return;
        if(health>this.maxHealth){
            this.health=maxHealth;
            return;
        }
        if(health>0)this.health = health;
        else this.health=0;
    }

    public String description() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName() + '\n');
        sb.append("Owned: " + owner.getNick() + '\n');
        sb.append("Speed: " + speed + '\n');
        sb.append("Weight: " + weight + '\n');
        sb.append("Max health: " + maxHealth + '\n');
        sb.append("Current health: " + health + '\n');
        sb.append("Max mana: " + maxMana + '\n');
        sb.append("Current mana: " + mana + '\n');
        return sb.toString();
    }

    public int getSpeed() {
        return speed;
    }
}