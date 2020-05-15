package Client.Gui_v2.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class GameObject extends Rectangle {


    private Texture heroTexture;
    private Texture obstacleTexture;



    public GameObject(Texture heroTexture, Texture obstacleTexture) {
//        this.fieldTexture = fieldTexture;
        this.heroTexture = heroTexture;
        this.obstacleTexture = obstacleTexture;
        this.height = 32;
        this.width = 32;
    }


    public void draw(SpriteBatch batch) {
//        batch.draw(fieldTexture, x, y);

        if(heroTexture != null) {
            Sprite s = new Sprite(heroTexture, 0, 0, heroTexture.getWidth(), heroTexture.getHeight());
            s.setPosition(x, y);
            s.draw(batch);
        }
        if(obstacleTexture!= null){
            Sprite s2 = new Sprite(obstacleTexture,0,0,obstacleTexture.getWidth(),obstacleTexture.getHeight());
            s2.setPosition(x,y);
            s2.draw(batch);
        }
    }
}
