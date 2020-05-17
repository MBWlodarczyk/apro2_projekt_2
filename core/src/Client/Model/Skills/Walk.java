package Client.Model.Skills;

public class Walk extends Skill {
    @Override
    public String toString() {
        return "Walk";
    }

    public Walk(int distance) {
        this.distance = distance;
        this.aggressive = false;
    }
}
