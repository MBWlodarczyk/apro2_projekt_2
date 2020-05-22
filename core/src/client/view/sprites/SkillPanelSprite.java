package client.view.sprites;

import client.view.utility.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SkillPanelSprite extends Sprite {
    public SkillPanelSprite(Texture texture) {
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());
        setPosition(Constants.HEIGHT, 0);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
