package Client.Model.Skills;

public class FireBall extends Skill {
    public FireBall(int distance) {
        this.distance = distance;
        this.aggressive = true;
    }

    @Override
    public String toString() {
        return "FireBall";
    }
}
