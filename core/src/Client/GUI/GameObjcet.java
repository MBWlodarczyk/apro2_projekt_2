package Client.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class GameObjcet extends Rectangle {

    private Texture fieldTexture;
    private Texture heroTexture;
    private Texture obstacleTexture;

    public GameObjcet(Texture fieldTexture) {
        this.fieldTexture = fieldTexture;
        this.height = fieldTexture.getHeight();
        this.width = fieldTexture.getWidth();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(fieldTexture, x, y);
    }

    public Texture getFieldTexture() {
        return fieldTexture;
    }

    public void setFieldTexture(Texture fieldTexture){
        this.fieldTexture = fieldTexture;
    }
}
