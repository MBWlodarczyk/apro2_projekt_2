package client.view.sprites;

import client.model.heroes.Hero;
import com.badlogic.gdx.graphics.Texture;

public class HeroSprite extends EntitySprite<Hero> {

    public HeroSprite(Hero hero, Texture texture) {
        super(hero, texture);
    }
}
