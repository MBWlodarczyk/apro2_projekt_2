package Client.Model.Skills;

public class Walk extends Skill {
    public Walk(int distance) {
        this.distance = distance;
        this.aggressive = false;
    }

    @Override
    public String toString() {
        return "Walk";
    }
}
