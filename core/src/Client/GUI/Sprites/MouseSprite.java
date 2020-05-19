package Client.GUI.Sprites;

import Client.Controller.HandleInput;
import Client.GUI.Utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MouseSprite extends Sprite {
    public MouseSprite(Texture texture) {
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());
        setPosition(-50, -50);


    }

    private void setPosition() {
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        if (x > Constants.TEXTURE_SIZE && x < Constants.WIDTH - 11 * Constants.TEXTURE_SIZE &&
                y > Constants.TEXTURE_SIZE && y < Constants.HEIGHT - Constants.TEXTURE_SIZE) {
            setPosition(x - x % Constants.TEXTURE_SIZE, Constants.HEIGHT -Constants.TEXTURE_SIZE- (y - y % Constants.TEXTURE_SIZE));
        } else {
            setPosition(-50, -50);
        }

/*
version which require gameObject
        for (int i = 1; i < 22-1; i++) {
            for (int j = 1; j < 22-1; j++) {
                if (PlayScreen.gameObjects[i][j].contains(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY())) {
                    setPosition(PlayScreen.gameObjects[i][j].x, PlayScreen.gameObjects[i][j].y);
                }
            }
        }
 */
    }

    @Override
    public void draw(Batch batch) {
        setPosition();
        super.draw(batch);
    }
}
