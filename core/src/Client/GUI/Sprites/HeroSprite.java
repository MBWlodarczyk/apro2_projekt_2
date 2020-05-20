package Client.GUI.Sprites;

import Client.Model.Heroes.Hero;
import com.badlogic.gdx.graphics.Texture;

public class HeroSprite extends EntitySprite<Hero> {

    public HeroSprite(Hero hero, Texture texture) {
        super(hero, texture);
    }
}
