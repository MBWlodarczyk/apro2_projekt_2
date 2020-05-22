package client.view.sprites;

import client.view.utility.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;


public class MoveDistanceSprite {

    private ArrayList<Sprite> moveDistance;
    private Texture texture;

    public MoveDistanceSprite(Texture texture) {
        this.texture = texture;
        moveDistance = new ArrayList<>();
    }

    public void setSprites(boolean[][] marked) {
        Sprite s;
        for (int i = 1; i < Constants.GAME_SIZE - 1; i++) {
            for (int j = 1; j < Constants.GAME_SIZE - 1; j++) {
                if (marked[i][j]) {
                    s = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
                    s.setPosition(i * Constants.TEXTURE_SIZE, Constants.HEIGHT - (j + 1) * Constants.TEXTURE_SIZE);
                    moveDistance.add(s);
                }
            }
        }
    }

    public void clear() {
        moveDistance.clear();
    }


    public void draw(Batch batch) {
        for (Sprite value : moveDistance) value.draw(batch);
    }

}
