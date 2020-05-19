package Client.GUI.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class GameObject extends Rectangle {


    private Texture heroTexture;
    private Texture obstacleTexture;
    private Texture healthTexture;
    private float percent;


    public GameObject(Texture heroTexture, Texture obstacleTexture, Texture healthTexture, float percent) {
        this.heroTexture = heroTexture;
        this.obstacleTexture = obstacleTexture;
        this.healthTexture = healthTexture;
        this.percent = percent;
        this.height = Constants.TEXTURE_SIZE;
        this.width = Constants.TEXTURE_SIZE;
    }

    public GameObject(Texture obstacleTexture) {
        this.heroTexture = null;
        this.obstacleTexture = obstacleTexture;
        this.healthTexture = null;
        this.percent = 0.0f;
        this.height = Constants.TEXTURE_SIZE;
        this.width = Constants.TEXTURE_SIZE;
    }


    public void draw(SpriteBatch batch) {
        // if there is a hero
        if (heroTexture != null) {
            Sprite s = new Sprite(heroTexture, 0, 0, heroTexture.getWidth(), heroTexture.getHeight());
            s.setPosition(x, y);
            s.draw(batch);
            Sprite s1 = new Sprite(healthTexture, 0, 0, (int) (healthTexture.getWidth() * percent), healthTexture.getHeight());
            s1.setPosition(x, y);
            s1.draw(batch);
        }
        //if there is not hero
        if (obstacleTexture != null) {
            Sprite s2 = new Sprite(obstacleTexture, 0, 0, obstacleTexture.getWidth(), obstacleTexture.getHeight());
            s2.setPosition(x, y);
            s2.draw(batch);
        }
    }
}
