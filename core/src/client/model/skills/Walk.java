package client.model.skills;

public class Walk extends Skill {

    public Walk(int distance) {
        range = distance;
    }

    @Override
    public String toString() {
        return "Walk";
    }
}
