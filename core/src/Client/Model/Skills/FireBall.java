package Client.Model.Skills;

public class FireBall extends Skill {
    @Override
    public String toString() {
        return "FireBall";
    }

    public FireBall(int distance) {
        this.distance = distance;
        this.aggressive = true;
    }
}
