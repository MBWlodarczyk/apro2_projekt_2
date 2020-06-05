package client.view.sprites;

import client.model.Entity;
import client.view.utility.Constants;
import client.view.utility.Drawable;
import client.view.utility.Updatable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class EntitySprite<E extends Entity> implements Updatable, Drawable {
    private final E entity;
    private final Sprite sprite;
//    private final Sprite exclusiveHero;

    public EntitySprite(E entity, Texture texture) {
        this.entity = entity;
        this.sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
//        this.exclusiveHero = new Sprite(, 0, 0, texture.getWidth(), texture.getHeight());

    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
//        if(entity instanceof Hero)
//            exclusiveHero.draw(batch);
    }

    @Override
    public void update(float delta) {
        sprite.setPosition(entity.getY() * Constants.TEXTURE_SIZE, Constants.HEIGHT - (entity.getX() + 1) * Constants.TEXTURE_SIZE);
//        exclusiveHero.setPosition(entity.getY() * Constants.TEXTURE_SIZE, Constants.HEIGHT - (entity.getX() + 1) * Constants.TEXTURE_SIZE);
    }
}