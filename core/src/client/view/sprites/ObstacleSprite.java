package client.view.sprites;

import client.model.obstacles.Obstacle;
import com.badlogic.gdx.graphics.Texture;

public class ObstacleSprite extends EntitySprite<Obstacle> {

    public ObstacleSprite(Obstacle obstacle, Texture texture) {
        super(obstacle, texture);
    }
}
