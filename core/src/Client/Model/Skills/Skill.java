package Client.Model.Skills;

public abstract class Skill {
    public int damage;
    public int distance;
    public boolean aggressive;  //if(aggressive == false) to nie jest sprawdzay damage i distance



    public int getDamage() {
        return damage;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isAggressive() {
        return aggressive;
    }
}
