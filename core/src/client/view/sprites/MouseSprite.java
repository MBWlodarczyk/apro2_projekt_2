package client.view.sprites;

import client.view.utility.Constants;
import client.view.utility.Drawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Sprite of Mouse
 */
public class MouseSprite extends Sprite {
    public MouseSprite(Texture texture) {
        super(texture, texture.getWidth(), texture.getHeight());
        setPosition(-50, -50); //set default position as - 50, -50, thanks to that mouse is invisible when we start game
    }

    /**
     * set Position by computing the x, y position of mouse
     */
    private void setPosition() {
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        if (x > Constants.TEXTURE_SIZE && x < Constants.WIDTH - 11 * Constants.TEXTURE_SIZE &&
                y > Constants.TEXTURE_SIZE && y < Constants.HEIGHT - Constants.TEXTURE_SIZE) {
            setPosition(x - x % Constants.TEXTURE_SIZE, Constants.HEIGHT - Constants.TEXTURE_SIZE - (y - y % Constants.TEXTURE_SIZE));
        } else {
            setPosition(-50, -50);
        }
    }

    @Override
    public void draw(Batch batch) {
        setPosition();
        super.draw(batch);
    }
}
