package client.view.sprites;

import client.model.heroes.Hero;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HeroSprite extends EntitySprite<Hero> {
    private Sprite sprite;
    private boolean ownership;

    public HeroSprite(Hero hero, Texture texture, Texture ownershipTexture,boolean ownership) {
        super(hero, texture);
        this.ownership = ownership;
        if(ownership) {
            sprite = new Sprite(ownershipTexture,0,0,Constants.TEXTURE_SIZE,Constants.TEXTURE_SIZE);
            sprite.setPosition(hero.getY() * Constants.TEXTURE_SIZE, Constants.HEIGHT - (hero.getX() + 1) * Constants.TEXTURE_SIZE);
        }
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        if(ownership)
            sprite.draw(batch);
        super.draw(batch, delta);
    }
}
