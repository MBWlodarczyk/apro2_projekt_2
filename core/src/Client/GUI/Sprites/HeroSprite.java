package Client.GUI.Sprites;

import Client.GUI.Utility.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HeroSprite implements Drawable {
    private final Sprite hero;


    public HeroSprite(Texture texture) {
        this.hero = new Sprite(texture,0,0,texture.getWidth(),texture.getHeight());

    }

    @Override
    public void draw(SpriteBatch batch) {
        hero.draw(batch);

    }

}
