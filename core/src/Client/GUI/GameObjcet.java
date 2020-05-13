package Client.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class GameObjcet extends Rectangle {

    private Texture texture;

    public GameObjcet(Texture texture) {
        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture,x,y);
    }

    public Texture getTexture() {
        return texture;
    }
}
